package com.fearefull.todoreminder.ui.alarm_manager;

import android.content.DialogInterface;
import android.net.Uri;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.other.HalfHourType;
import com.fearefull.todoreminder.data.model.other.RepeatItem;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;
import com.kevalpatel.ringtonepicker.RingtonePickerListener;

import java.util.List;

import timber.log.Timber;

public class AlarmManagerViewModel extends BaseViewModel<AlarmManagerNavigator> {

    private Alarm alarm;
    private HalfHourType halfHourType;
    private final ObservableField<String> timeString = new ObservableField<>();
    private final ObservableField<String> repeatString = new ObservableField<>();
    private final ObservableField<String> noteString = new ObservableField<>();
    private final MutableLiveData<List<RepeatItem>> repeatItemsLiveData;
    private final ObservableField<String> ringtoneString = new ObservableField<>();

    public AlarmManagerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        repeatItemsLiveData = new MutableLiveData<>();
    }

    private void fetchRepeatData() {
        setIsLoading(true);
        repeatItemsLiveData.setValue(AlarmUtils.getRepeatItems(alarm.getRepeat()));
    }

    public MutableLiveData<List<RepeatItem>> getRepeatItemsLiveData() {
        return repeatItemsLiveData;
    }

    String[] getHours() {
        return AlarmUtils.get12Hours().toArray(new String[0]);
    }

    String[] getMinutes() {
        return AlarmUtils.getMinutes().toArray(new String[0]);
    }

    String[] getHalfHourTypes() {
        return AlarmUtils.getHalfHourTypes().toArray(new String[0]);
    }

    public void onNavigationBackClick() {
        getNavigator().goBack();
    }

    public void onSaveClick() {
        getCompositeDisposable().add(getDataManager()
                .insertAlarm(alarm)
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
        if (alarm.getHours().isEmpty())
            alarm.add12HourByIndex(newVal, halfHourType);
        else
            alarm.edit12HourByIndex(newVal, halfHourType, 0);
        updateTimeString();
    }

    public void onMinutesPickerValueChange(int oldVal, int newVal) {
        Timber.i("minute index %d", newVal);
        if (alarm.getMinutes().isEmpty())
            alarm.addMinuteByIndex(newVal);
        else
            alarm.editMinuteByIndex(newVal, 0);
        updateTimeString();
    }

    public void onHalfHourTypesPickerValueChange(int oldVal, int newVal) {
        Timber.i("type index %d", newVal);
        halfHourType = Alarm.indexToHalfHourType(newVal);
        updateTimeString();
    }

    public void onNoteTextChange(CharSequence s) {
        alarm.setNote(s.toString());
        updateNoteString();
    }

    Alarm getAlarm() {
        return alarm;
    }

    void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    void initAlarm() {
        halfHourType = alarm.getHalfHourType();
        fetchRepeatData();
        updateAlarm();
        openDefaultRepeatFragment();
    }

    void updateAlarm() {
        updateTimeString();
        updateRepeatString();
        updateNoteString();
        updateRingtoneString();
    }

    void updateTimeString() {
        timeString.set(alarm.getTime12String(0, halfHourType));
    }

    void updateRepeatString(Repeat repeat) {
        alarm.setRepeat(repeat);
        updateRepeatString();
    }

    void openDefaultRepeatFragment() {
        if (alarm.getRepeat() == Repeat.ONCE)
            getNavigator().openOnceRepeatFragment();
    }

    void updateRepeatString() {
        repeatString.set(alarm.getRepeat().getText());
    }

    void updateNoteString() {
        noteString.set(alarm.getNote());
    }

    void updateRingtoneString() {
        ringtoneString.set(alarm.getRingtone());
    }

    public ObservableField<String> getTimeString() {
        return timeString;
    }

    public ObservableField<String> getRepeatString() {
        return repeatString;
    }

    public ObservableField<String> getNoteString() {
        return noteString;
    }

    public ObservableField<String> getRingtoneString() {
        return ringtoneString;
    }

    DialogInterface.OnClickListener repeatPickerOnClickListener = (dialog, which) -> {
        if (Alarm.indexToRepeat(which) != Repeat.CUSTOM) {
            alarm.setRepeat(Alarm.indexToRepeat(which));
            dialog.dismiss();
            updateRepeatString();
        }
        else {
            getNavigator().openCustomRepeatPickerFragment();
        }
    };

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

    Uri getDefaultRingtone() {
        return null;
    }

    int getIndexHour() {
        return alarm.getIndexHour12Hour(0);
    }

    int getIndexMinute() {
        return alarm.getIndexMinute(0);
    }

    int getIndexHalfHourIndex() {
        return alarm.getIndexHalfHourType(0);
    }
}
