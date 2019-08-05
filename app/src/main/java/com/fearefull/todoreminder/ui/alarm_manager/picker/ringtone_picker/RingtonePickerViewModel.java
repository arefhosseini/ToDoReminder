package com.fearefull.todoreminder.ui.alarm_manager.picker.ringtone_picker;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Ringtone;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.data.model.other.item.RingtonePickerItem;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.RingtoneUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;

import timber.log.Timber;

public class RingtonePickerViewModel extends BaseViewModel<RingtonePickerNavigator> {
    private final MutableLiveData<List<RingtonePickerItem>> ringtoneManagerItemLiveData;
    private Alarm alarm;
    private Ringtone ringtone;

    public RingtonePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        super(dataManager, schedulerProvider, settings);
        ringtoneManagerItemLiveData = new MutableLiveData<>();
    }

    void init(Alarm alarm) {
        this.alarm = alarm;
        this.ringtone = alarm.getRingtone();
        getCompositeDisposable().add(RingtoneUtils.getRingtoneList(getNavigator().getContext(), alarm.getRingtone())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(ringtoneManagerItemLiveData::setValue, Timber::e)
        );
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setRingtone(Ringtone ringtone) {
        this.ringtone = ringtone;
    }

    public MutableLiveData<List<RingtonePickerItem>> getRingtoneManagerItemLiveData() {
        return ringtoneManagerItemLiveData;
    }

    public void onConfirmClick() {
        alarm.setRingtone(ringtone);
        getNavigator().finishAndSave();
    }

    public void onCancelClick() {
        getNavigator().finish();
    }
}
