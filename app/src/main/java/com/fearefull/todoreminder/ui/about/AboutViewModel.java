package com.fearefull.todoreminder.ui.about;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class AboutViewModel extends BaseViewModel<AboutNavigator> {

    public AboutViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNavigationBackClick() {
        getNavigator().goBack();
    }

    public void onOpenCreatorClick() {
        getNavigator().openCreatorLink();
    }
}