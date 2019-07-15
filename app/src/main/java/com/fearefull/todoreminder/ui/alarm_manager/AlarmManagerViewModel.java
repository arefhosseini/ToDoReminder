package com.fearefull.todoreminder.ui.alarm_manager;

import android.content.DialogInterface;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.AlarmModel;
import com.fearefull.todoreminder.data.model.other.Alarm;
import com.fearefull.todoreminder.data.model.other.RepeatType;
import com.fearefull.todoreminder.data.model.other.RepeatTypeItem;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;
import com.kevalpatel.ringtonepicker.RingtonePickerListener;

import java.util.List;

import timber.log.Timber;

public class AlarmManagerViewModel extends BaseViewModel<AlarmManagerNavigator> {

    private Alarm alarm;
    private final ObservableField<String> time = new ObservableField<>();
    private final ObservableField<String> date = new ObservableField<>();
    private final ObservableField<String> repeat = new ObservableField<>();
    private final ObservableField<String> note = new ObservableField<>();
    private final MutableLiveData<List<RepeatTypeItem>> repeatItemsLiveData;
    private final ObservableField<String> ringtone = new ObservableField<>();

    public AlarmManagerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        repeatItemsLiveData = new MutableLiveData<>();
        fetchRepeatData();
    }

    private void fetchRepeatData() {
        setIsLoading(true);
        repeatItemsLiveData.setValue(AlarmUtils.getRepeatTypeItems());
    }

    public MutableLiveData<List<RepeatTypeItem>> getRepeatItemsLiveData() {
        return repeatItemsLiveData;
    }

    String[] getHours() {
        return AlarmUtils.get12Hours().toArray(new String[0]);
    }

    String[] getMinutes() {
        return AlarmUtils.getMinutes().toArray(new String[0]);
    }

    String[] getTimeTypes() {
        return AlarmUtils.getTimeTypes().toArray(new String[0]);
    }

    public void onNavigationBackClick() {
        getNavigator().goBack();
    }

    public void onSaveClick() {
        getCompositeDisposable().add(getDataManager()
                .insertAlarm(new AlarmModel(0L, 0))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    if (result)
                        getNavigator().save();
                }, throwable -> {

                })
        );
    }

    public void onHoursPickerValueChange(int oldVal, int newVal) {
        Timber.i("hour index %d", newVal);
        alarm.getTime().setHour(AlarmUtils.indexToHour(newVal));
        updateTime();
    }

    public void onMinutesPickerValueChange(int oldVal, int newVal) {
        Timber.i("minute index %d", newVal);
        alarm.getTime().setMinute(AlarmUtils.indexToMinute(newVal));
        updateTime();
    }

    public void onTypesPickerValueChange(int oldVal, int newVal) {
        Timber.i("type index %d", newVal);
        alarm.getTime().setTimeType(AlarmUtils.indexToTimeType(newVal));
        updateTime();
    }

    public void onNoteTextChange(CharSequence s) {
        note.set(s.toString());
    }

    String[] getRepeats() {
        return AlarmUtils.getRepeatTypes().toArray(new String[0]);
    }

    Alarm getAlarm() {
        return alarm;
    }

    void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    void updateAlarm() {
        updateTime();
        updateDate();
        updateRepeat();
        updateNote();
        updateRingtone();
    }

    void updateTime() {
        time.set(alarm.getTime().toString());
    }

    void updateDate() {
        date.set(alarm.getDate().toString());
    }

    void updateRepeatType(RepeatType repeatType) {
        alarm.getRepeat().setType(repeatType);
        updateRepeat();
    }

    void updateRepeat() {
        repeat.set(alarm.getRepeat().toString());
    }

    void updateNote() {
        note.set(alarm.getNote());
    }

    void updateRingtone() {
        ringtone.set(alarm.getRingtone().getName());
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public ObservableField<String> getDate() {
        return date;
    }

    public ObservableField<String> getRepeat() {
        return repeat;
    }

    public ObservableField<String> getNote() {
        return note;
    }

    public ObservableField<String> getRingtone() {
        return ringtone;
    }

    int getRepeatDialogDefaultIndex() {
        return alarm.getRepeat().getType().getIndex();
    }

    DialogInterface.OnClickListener repeatPickerOnClickListener = (dialog, which) -> {
        if (AlarmUtils.indexToRepeatType(which) != RepeatType.CUSTOM) {
            alarm.getRepeat().setType(AlarmUtils.indexToRepeatType(which));
            dialog.dismiss();
            updateRepeat();
        }
        else {
            getNavigator().openCustomRepeatPickerFragment();
        }
    };

    RingtonePickerListener ringtonePickerListener = (ringtoneName, ringtoneUri) -> {
        if (ringtoneUri != null) {
            alarm.getRingtone().setName(ringtoneName);
            //alarm.getRingtone().setUri(ringtoneUri);
            updateRingtone();
        }
    };

    String getEveryCustomRepeat() {
        Timber.i(alarm.getRepeat().getCustomRepeat().getType().getText());
        return alarm.getRepeat().getCustomRepeat().getType().getText();
    }

    String getOnCustomRepeat() {
        Timber.i(alarm.getRepeat().getCustomRepeat().getOnString());
        return alarm.getRepeat().getCustomRepeat().getOnString();
    }

    public void onRingtoneClick() {
        getNavigator().closeAllExpansions();
    }

    Uri getDefaultRingtone() {
        return alarm.getRingtone().getUri();
    }
}