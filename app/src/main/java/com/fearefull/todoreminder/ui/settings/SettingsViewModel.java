package com.fearefull.todoreminder.ui.settings;

import androidx.databinding.ObservableBoolean;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.data.model.other.type.HourType;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class SettingsViewModel extends BaseViewModel<SettingsNavigator> {

    private final Settings settings;

    private ObservableBoolean isFullHourTypeEnabled;

    public SettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        settings = getDataManager().getSettings();

        isFullHourTypeEnabled = new ObservableBoolean(settings.getHourType() == HourType.FULL_HOUR);
    }

    public void onNavigationBackClick() {
        getNavigator().goBack();
    }

    public Settings getSettings() {
        return settings;
    }

    public ObservableBoolean getIsFullHourTypeEnabled() {
        return isFullHourTypeEnabled;
    }

    public void onFullHourTypeSwitchClick() {
        settings.changeHourType();
    }
}
