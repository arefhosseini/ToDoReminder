package com.fearefull.todoreminder;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.ui.about.AboutViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.AlarmManagerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.once_repeat.OnceRepeatViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.once_repeat.date_picker.DatePickerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.once_repeat.half_hour_time_picker.HalfHourTimePickerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.repeat_manager.RepeatManagerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.simple.SimpleViewModel;
import com.fearefull.todoreminder.ui.alarm_notification.AlarmNotificationViewModel;
import com.fearefull.todoreminder.ui.home.HomeViewModel;
import com.fearefull.todoreminder.ui.login.LoginViewModel;
import com.fearefull.todoreminder.ui.main.MainViewModel;
import com.fearefull.todoreminder.ui.splash.SplashViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;

    @Inject
    ViewModelProviderFactory(DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }


    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            //noinspection unchecked
            return (T) new SplashViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            //noinspection unchecked
            return (T) new LoginViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            //noinspection unchecked
            return (T) new MainViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            //noinspection unchecked
            return (T) new HomeViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(AboutViewModel.class)) {
            //noinspection unchecked
            return (T) new AboutViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(AlarmManagerViewModel.class)) {
            //noinspection unchecked
            return (T) new AlarmManagerViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(AlarmNotificationViewModel.class)) {
            //noinspection unchecked
            return (T) new AlarmNotificationViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(SimpleViewModel.class)) {
            //noinspection unchecked
            return (T) new SimpleViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(OnceRepeatViewModel.class)) {
            //noinspection unchecked
            return (T) new OnceRepeatViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(HalfHourTimePickerViewModel.class)) {
            //noinspection unchecked
            return (T) new HalfHourTimePickerViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(DatePickerViewModel.class)) {
            //noinspection unchecked
            return (T) new DatePickerViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(RepeatManagerViewModel.class)) {
            //noinspection unchecked
            return (T) new RepeatManagerViewModel(dataManager, schedulerProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}