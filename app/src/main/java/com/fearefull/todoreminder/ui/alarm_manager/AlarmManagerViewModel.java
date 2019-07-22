package com.fearefull.todoreminder.ui.alarm_manager;

import android.net.Uri;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.other.item.RepeatItem;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;
import com.kevalpatel.ringtonepicker.RingtonePickerListener;

import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class AlarmManagerViewModel extends BaseViewModel<AlarmManagerNavigator> {

    private Alarm alarm;
    private final ObservableField<String> titleString = new ObservableField<>();
    private final MutableLiveData<List<RepeatItem>> repeatItemsLiveData;
    private final ObservableField<String> ringtoneString = new ObservableField<>();
    private final ObservableField<String> repeatCounter = new ObservableField<>();
    private final MutableLiveData<Integer> currentTabPager;
    private final MutableLiveData<Integer> pageLimitPager;
    private boolean shouldUpdateAlarm = false;

    public AlarmManagerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        repeatItemsLiveData = new MutableLiveData<>();
        currentTabPager = new MutableLiveData<>();
        pageLimitPager = new MutableLiveData<>();
        pageLimitPager.setValue(Repeat.getCount());
        repeatCounter.set("0");
    }

    private void fetchRepeatData() {
        repeatItemsLiveData.setValue(AlarmUtils.getRepeatItems(alarm.getDefaultRepeat()));
    }

    public MutableLiveData<List<RepeatItem>> getRepeatItemsLiveData() {
        return repeatItemsLiveData;
    }

    public void onNavigationBackClick() {
        getNavigator().goBack();
    }

    public void onSaveClick() {
        if (shouldUpdateAlarm) {
            getCompositeDisposable().add(getDataManager()
                    .updateAlarm(alarm)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(result -> {
                        if (result)
                            getNavigator().save();
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

    public void onTitleTextChange(CharSequence s) {
        alarm.setTitle(s.toString());
        updateTitleString();
    }

    Alarm getAlarm() {
        return alarm;
    }

    void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    void initAlarm() {
        if (alarm.getRepeatCount() > 0)
            shouldUpdateAlarm = true;
        setIsLoading(true);
        fetchRepeatData();
        updateAlarm();
        openDefaultRepeatFragment();
    }

    void updateAlarm() {
        updateTitleString();
        updateRingtoneString();
        updateAddCounter(alarm.getRepeatCount());
    }

    void openDefaultRepeatFragment() {
        currentTabPager.setValue(alarm.getDefaultRepeat().getValue());
        setIsLoading(false);
    }

    void updateTitleString() {
        titleString.set(alarm.getTitle());
    }

    void updateRingtoneString() {
        ringtoneString.set(alarm.getRingtone());
    }

    void updateAddCounter(int counter) {
        if (counter == 0)
            getNavigator().clearBell();
        else if (Integer.parseInt(Objects.requireNonNull(repeatCounter.get())) == 0)
            getNavigator().createWithShakeBell();
        else if (counter > Integer.parseInt(Objects.requireNonNull(repeatCounter.get())))
            getNavigator().shakeBell();
        repeatCounter.set(String.valueOf(counter));
    }

    public ObservableField<String> getTitleString() {
        return titleString;
    }

    public ObservableField<String> getRingtoneString() {
        return ringtoneString;
    }

    public ObservableField<String> getRepeatCounter() {
        return repeatCounter;
    }

    public MutableLiveData<Integer> getCurrentTabPager() {
        return currentTabPager;
    }

    public MutableLiveData<Integer> getPageLimitPager() {
        return pageLimitPager;
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
            //alarm.getRingtoneString().setUri(ringtoneUri);
            updateRingtoneString();
        }
    };

    public void onRingtoneClick() {
        getNavigator().closeAllExpansions();
    }

    public void onRepeatManagerClick() {
        getNavigator().onShowRepeatManagerDialog();
    }

    Uri getDefaultRingtone() {
        return null;
    }
}
