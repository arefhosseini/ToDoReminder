package com.fearefull.todoreminder.ui.alarm_manager;

import android.net.Uri;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.data.model.other.item.AlarmTitleItem;
import com.fearefull.todoreminder.data.model.other.item.RepeatItem;
import com.fearefull.todoreminder.data.model.other.type.AlarmTitleType;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;
import com.kevalpatel.ringtonepicker.RingtonePickerListener;

import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class AlarmManagerViewModel extends BaseViewModel<AlarmManagerNavigator> {

    private Alarm alarm;
    private Repeat selectedRepeat;
    private int defaultRepeatCount;

    private final MutableLiveData<List<RepeatItem>> repeatItemsLiveData;
    private final MutableLiveData<List<AlarmTitleItem>> alarmTitleItemsLiveData;

    private final ObservableField<String> titleString = new ObservableField<>();
    private final ObservableField<Integer> defaultImageResTitle = new ObservableField<>();
    private final ObservableField<Integer> selectionTitleEditText = new ObservableField<>();
    private final ObservableField<String> ringtoneString = new ObservableField<>();
    private final ObservableField<String> repeatCounter = new ObservableField<>();
    private final ObservableField<String> snoozeDelayString = new ObservableField<>();
    private final ObservableField<String> snoozeCountString = new ObservableField<>();
    private ObservableBoolean isVibrateEnabled = new ObservableBoolean();
    private final MutableLiveData<Integer> currentTabPager;
    private final MutableLiveData<Integer> pageLimitPager;

    private boolean shouldUpdateAlarm = false;
    private boolean shouldExit = false;
    private boolean autoUpdateTitleEditText = false;

    public AlarmManagerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        super(dataManager, schedulerProvider, settings);
        repeatItemsLiveData = new MutableLiveData<>();
        alarmTitleItemsLiveData = new MutableLiveData<>();
        currentTabPager = new MutableLiveData<>();
        pageLimitPager = new MutableLiveData<>();

        pageLimitPager.setValue(Repeat.getCount());
        repeatCounter.set("0");
    }

    private void fetchRepeatData() {
        getCompositeDisposable().add(AlarmUtils.getRepeatItems(alarm.getDefaultRepeat())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(repeatItemsLiveData::postValue, Timber::e)
        );

        getCompositeDisposable().add(AlarmUtils.getAlarmTitleItems(alarm.getTitleType())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(alarmTitleItemsLiveData::postValue, Timber::e)
        );
        defaultImageResTitle.set(alarm.getTitleType().getImageRes());

        setSelectedRepeat(alarm.getDefaultRepeat());
        defaultRepeatCount = alarm.getRepeatCount();
    }

    public MutableLiveData<List<RepeatItem>> getRepeatItemsLiveData() {
        return repeatItemsLiveData;
    }

    public MutableLiveData<List<AlarmTitleItem>> getAlarmTitleItemsLiveData() {
        return alarmTitleItemsLiveData;
    }

    public void onNavigationBackClick() {
        getNavigator().goBack();
    }

    public void onSaveClick() {
        if (alarm.getRepeatCount() != defaultRepeatCount || shouldExit) {
            if (defaultRepeatCount > 0 && alarm.getRepeatCount() == 0)
                alarm.setIsEnable(false);
            else
                alarm.setIsEnable(true);
            if (shouldUpdateAlarm) {
                getCompositeDisposable().add(getDataManager()
                        .updateHistoriesByAlarm(alarm)
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe(historyResult -> {
                            if (historyResult) {
                                getCompositeDisposable().add(getDataManager()
                                        .updateAlarm(alarm)
                                        .subscribeOn(getSchedulerProvider().io())
                                        .observeOn(getSchedulerProvider().ui())
                                        .subscribe(alarmResult -> {
                                            if (alarmResult)
                                                getNavigator().save();
                                        }, Timber::e)
                                );
                            }
                        }, Timber::e)
                );
            }
            else {
                getCompositeDisposable().add(getDataManager()
                        .insertAlarm(alarm)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(result -> {
                            if (result)
                                getNavigator().save();
                        }, Timber::e)
                );
            }
        }
        else {
            shouldExit = true;
            getNavigator().getLastRepeat(selectedRepeat);
        }
    }

    public void onTitleTextChange(CharSequence s) {
        if (autoUpdateTitleEditText) {
            if (alarm.getTitle().length() == s.length() && selectionTitleEditText.get() != null &&
                    s.length() == selectionTitleEditText.get()) {
                selectionTitleEditText.set(0);
                selectionTitleEditText.set(s.length());
            }
            else
                selectionTitleEditText.set(s.length());
            autoUpdateTitleEditText = false;
        }
        alarm.setTitle(s.toString());
    }

    Alarm getAlarm() {
        return alarm;
    }

    void setAlarmById(long alarmId) {
        if (alarmId != -1) {
            getCompositeDisposable().add(getDataManager().getAlarmById(alarmId)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(alarm -> {
                        this.alarm = alarm;
                        shouldUpdateAlarm = true;
                        isVibrateEnabled.set(alarm.isVibrate());
                        initAlarm();
                    }, Timber::e)
            );
        }
        else {
            alarm = new Alarm();
            isVibrateEnabled.set(alarm.isVibrate());
            initAlarm();
        }
    }

    void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public Repeat getSelectedRepeat() {
        return selectedRepeat;
    }

    public void setSelectedRepeat(Repeat repeat) {
        this.selectedRepeat = repeat;
    }

    void initAlarm() {
        fetchRepeatData();
        updateAlarm();
        openDefaultRepeatFragment();
        updateSnoozeCount();
        updateSnoozeDelay();
        getNavigator().onSetUp();
    }

    void updateAlarm() {
        updateTitleString(alarm.getTitle());
        updateRingtoneString();
        updateAddCounter(alarm.getRepeatCount());
        if (shouldExit)
            onSaveClick();
    }

    private void updateTitleString(String title) {
        titleString.set(title);
        autoUpdateTitleEditText = true;
    }

    void updateAlarmTitle(AlarmTitleType titleType) {
        alarm.setTitleType(titleType);
        updateTitleString(titleType.getText() + " ");
        updateTitleImageRes(titleType.getImageRes());
    }

    private void updateTitleImageRes(int imageRes) {
        defaultImageResTitle.set(imageRes);
    }

    private void openDefaultRepeatFragment() {
        currentTabPager.setValue(alarm.getDefaultRepeat().getValue());
    }

    private void updateRingtoneString() {
        ringtoneString.set(alarm.getRingtone());
    }

    private void updateAddCounter(int counter) {
        if (counter == 0)
            getNavigator().clearBell();
        else if (Integer.parseInt(Objects.requireNonNull(repeatCounter.get())) == 0)
            getNavigator().createWithShakeBell();
        else if (counter >= Integer.parseInt(Objects.requireNonNull(repeatCounter.get())))
            getNavigator().shakeBell();
        repeatCounter.set(String.valueOf(counter));
    }

    private void updateSnoozeCount() {
        snoozeCountString.set(String.valueOf(alarm.getSnoozeType().getValue()));
    }

    private void updateSnoozeDelay() {
        snoozeDelayString.set(alarm.getSnoozeDelayMinute() + " دقیقه");
    }

    public ObservableField<String> getTitleString() {
        return titleString;
    }

    public ObservableField<Integer> getDefaultImageResTitle() {
        return defaultImageResTitle;
    }

    public ObservableField<Integer> getSelectionTitleEditText() {
        return selectionTitleEditText;
    }

    public ObservableField<String> getRingtoneString() {
        return ringtoneString;
    }

    public ObservableField<String> getRepeatCounter() {
        return repeatCounter;
    }

    public ObservableField<String> getSnoozeCountString() {
        return snoozeCountString;
    }

    public ObservableField<String> getSnoozeDelayString() {
        return snoozeDelayString;
    }

    public MutableLiveData<Integer> getCurrentTabPager() {
        return currentTabPager;
    }

    public MutableLiveData<Integer> getPageLimitPager() {
        return pageLimitPager;
    }

    public ObservableBoolean getIsVibrateEnabled() {
        return isVibrateEnabled;
    }

    /*DialogInterface.OnClickListener repeatPickerOnClickListener = (dialog, which) -> {
        if (Alarm.indexToRepeat(which) != Repeat.CUSTOM) {
            alarm.setRepeat(Alarm.indexToRepeat(which));
            dialog.dismiss();
            updateRepeatString();
        }
        else {
            getNavigator().openCustomRepeatPickerFragment();
        }
    };*/

    RingtonePickerListener ringtonePickerListener = (ringtoneName, ringtoneUri) -> {
        if (ringtoneUri != null) {
            alarm.setRingtone(ringtoneName);
            alarm.setUriRingtoneUri(ringtoneUri);
            updateRingtoneString();
        }
    };

    public void onRingtoneClick() {
        getNavigator().openRingtonePickerDialog();
    }

    public void onRepeatManagerClick() {
        getNavigator().onShowRepeatManagerDialog();
    }

    public void onIncreaseSnoozeDelay() {
        alarm.setSnoozeDelay(alarm.getSnoozeDelayMinute() + 1);
        updateSnoozeDelay();
    }

    public void onDecreaseSnoozeDelay() {
        if (alarm.getSnoozeDelayMinute() > 1) {
            alarm.setSnoozeDelay(alarm.getSnoozeDelayMinute() - 1);
            updateSnoozeDelay();
        }
    }

    public void onIncreaseSnoozeCount() {
        if (alarm.getSnoozeType().getValue() <= 9) {
            alarm.forwardSnoozeType();
            updateSnoozeCount();
        }
    }

    public void onDecreaseSnoozeCount() {
        if (alarm.getSnoozeType().getValue() >= 1) {
            alarm.backwardSnoozeType();
            updateSnoozeCount();
        }
    }

    public void onVibrateSwitchClick() {
        alarm.setVibrate(!alarm.isVibrate());
        isVibrateEnabled.set(alarm.isVibrate());
    }

    public void headerTitleClick() {
        getNavigator().changeExpansionTitleLayout();
    }

    public void headerRepeatClick() {
        getNavigator().changeExpansionRepeatLayout();
    }

    public void headerSnoozeClick() {
        getNavigator().changeExpansionSnoozeLayout();
    }

    public void headerOtherOptionsClick() {
        getNavigator().changeExpansionOtherOptionsLayout();
    }
}
