package com.fearefull.todoreminder.ui.base;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.schedule.AlarmScheduler;
import com.fearefull.todoreminder.schedule.AppAlarmScheduler;
import com.fearefull.todoreminder.ui.about.AboutViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.AlarmManagerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.picker.date_picker.DatePickerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.picker.day_month_picker.DayMonthPickerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.picker.hour_time_picker.HourTimePickerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.picker.ringtone_picker.RingtonePickerItemViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.picker.ringtone_picker.RingtonePickerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.daily_repeat.DailyRepeatViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.monthly_repeat.MonthlyRepeatViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.once_repeat.OnceRepeatViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.weekly_repeat.WeeklyRepeatViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.yearly_repeat.YearlyRepeatViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.repeat_manager.RepeatManagerViewModel;
import com.fearefull.todoreminder.ui.alarm_manager.simple.SimpleViewModel;
import com.fearefull.todoreminder.ui.alarm_notification.AlarmNotificationViewModel;
import com.fearefull.todoreminder.ui.history.HistoryViewModel;
import com.fearefull.todoreminder.ui.home.HomeViewModel;
import com.fearefull.todoreminder.ui.main.MainViewModel;
import com.fearefull.todoreminder.ui.settings.SettingsViewModel;
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
    private Settings settings;

    @Inject
    public ViewModelProviderFactory(DataManager dataManager, SchedulerProvider schedulerProvider,
                                    AppAlarmScheduler alarmScheduler) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.alarmScheduler = alarmScheduler;
        this.settings = dataManager.getSettings();
        if (settings == null) {
            settings = new Settings();
            dataManager.setSettings(settings);

        }
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {

        // activities
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            //noinspection unchecked
            return (T) new SplashViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            //noinspection unchecked
            return (T) new MainViewModel(dataManager, schedulerProvider, settings);
        }

        // main fragments
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            //noinspection unchecked
            return (T) new HomeViewModel(dataManager, schedulerProvider, alarmScheduler, settings);
        }
        if (modelClass.isAssignableFrom(HistoryViewModel.class)) {
            //noinspection unchecked
            return (T) new HistoryViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(AboutViewModel.class)) {
            //noinspection unchecked
            return (T) new AboutViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(AlarmManagerViewModel.class)) {
            //noinspection unchecked
            return (T) new AlarmManagerViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(SettingsViewModel.class)) {
            //noinspection unchecked
            return (T) new SettingsViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(AlarmNotificationViewModel.class)) {
            //noinspection unchecked
            return (T) new AlarmNotificationViewModel(dataManager, schedulerProvider, alarmScheduler, settings);
        }
        if (modelClass.isAssignableFrom(SimpleViewModel.class)) {
            //noinspection unchecked
            return (T) new SimpleViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(RepeatManagerViewModel.class)) {
            //noinspection unchecked
            return (T) new RepeatManagerViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(RingtonePickerViewModel.class)) {
            //noinspection unchecked
            return (T) new RingtonePickerViewModel(dataManager, schedulerProvider, settings);
        }

        // pickers fragment
        if (modelClass.isAssignableFrom(HourTimePickerViewModel.class)) {
            //noinspection unchecked
            return (T) new HourTimePickerViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(DatePickerViewModel.class)) {
            //noinspection unchecked
            return (T) new DatePickerViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(DayMonthPickerViewModel.class)) {
            //noinspection unchecked
            return (T) new DayMonthPickerViewModel(dataManager, schedulerProvider, settings);
        }

        // repeats fragment
        if (modelClass.isAssignableFrom(OnceRepeatViewModel.class)) {
            //noinspection unchecked
            return (T) new OnceRepeatViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(DailyRepeatViewModel.class)) {
            //noinspection unchecked
            return (T) new DailyRepeatViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(WeeklyRepeatViewModel.class)) {
            //noinspection unchecked
            return (T) new WeeklyRepeatViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(MonthlyRepeatViewModel.class)) {
            //noinspection unchecked
            return (T) new MonthlyRepeatViewModel(dataManager, schedulerProvider, settings);
        }
        if (modelClass.isAssignableFrom(YearlyRepeatViewModel.class)) {
            //noinspection unchecked
            return (T) new YearlyRepeatViewModel(dataManager, schedulerProvider, settings);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}