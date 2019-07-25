package com.fearefull.todoreminder.ui.about;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class AboutViewModel extends BaseViewModel<AboutNavigator> {

    private final ObservableField<String> appVersion = new ObservableField<>();

    public AboutViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNavigationBackClick() {
        getNavigator().goBack();
    }

    public void onOpenCreatorClick() {
        getNavigator().openCreatorLink();
    }

    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    void updateAppVersion(String version) {
        appVersion.set(version);
    }
}