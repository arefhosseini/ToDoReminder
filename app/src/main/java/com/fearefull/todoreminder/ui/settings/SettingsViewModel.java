package com.fearefull.todoreminder.ui.settings;

import androidx.databinding.ObservableBoolean;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.data.model.other.type.HourType;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class SettingsViewModel extends BaseViewModel<SettingsNavigator> {

    private ObservableBoolean isFullHourTypeEnabled;

    public SettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        super(dataManager, schedulerProvider, settings);
        settings.setChanged(false);
        isFullHourTypeEnabled = new ObservableBoolean(settings.getHourType() == HourType.FULL_HOUR);
    }

    public void onNavigationBackClick() {
        saveChanges();
        getNavigator().goBack();
    }

    public ObservableBoolean getIsFullHourTypeEnabled() {
        return isFullHourTypeEnabled;
    }

    public void onFullHourTypeSwitchClick() {
        getSettings().changeHourType();
        getSettings().setChanged(true);
        isFullHourTypeEnabled.set(getSettings().getHourType() == HourType.FULL_HOUR);
        saveChanges();
    }

    private void saveChanges() {
        getDataManager().setSettings(getSettings());
    }
}
