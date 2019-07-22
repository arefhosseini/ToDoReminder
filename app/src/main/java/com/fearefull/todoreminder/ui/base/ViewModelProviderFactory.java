package com.fearefull.todoreminder.ui.base;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.schedule.AlarmScheduler;
import com.fearefull.todoreminder.schedule.AppAlarmScheduler;
import com.fearefull.todoreminder.ui.about.AboutViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.AlarmManagerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.daily_repeat.DailyRepeatViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.once_repeat.OnceRepeatViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.picker.date_picker.DatePickerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.picker.half_hour_time_picker.HalfHourTimePickerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.yearly_repeat.YearlyRepeatViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.repeat_manager.RepeatManagerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.simple.SimpleViewModel;
import com.fearefull.todoreminder.ui.alarm_notification.AlarmNotificationViewModel;
import com.fearefull.todoreminder.ui.home.HomeViewModel;
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
    private final AlarmScheduler alarmScheduler;

    @Inject
    public ViewModelProviderFactory(DataManager dataManager, SchedulerProvider schedulerProvider,
                                    AppAlarmScheduler alarmScheduler) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.alarmScheduler = alarmScheduler;
    }


    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            //noinspection unchecked
            return (T) new SplashViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            //noinspection unchecked
            return (T) new MainViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            //noinspection unchecked
            return (T) new HomeViewModel(dataManager, schedulerProvider, alarmScheduler);
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
            return (T) new AlarmNotificationViewModel(dataManager, schedulerProvider, alarmScheduler);
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
        if (modelClass.isAssignableFrom(DailyRepeatViewModel.class)) {
            //noinspection unchecked
            return (T) new DailyRepeatViewModel(dataManager, schedulerProvider);
        }
        if (modelClass.isAssignableFrom(YearlyRepeatViewModel.class)) {
            //noinspection unchecked
            return (T) new YearlyRepeatViewModel(dataManager, schedulerProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}