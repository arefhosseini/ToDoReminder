package com.fearefull.todoreminder.ui.alarm_manager;

import com.fearefull.todoreminder.ui.alarm_manager.picker.date_picker.DatePickerFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.picker.day_month_picker.DayMonthPickerFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.picker.hour_time_picker.HourTimePickerFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.daily_repeat.DailyRepeatFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.monthly_repeat.MonthlyRepeatFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.once_repeat.OnceRepeatFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.weekly_repeat.WeeklyRepeatFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.yearly_repeat.YearlyRepeatFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.repeat_manager.RepeatManagerDialogFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.simple.SimpleFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AlarmManagerFragmentProvider {

    @ContributesAndroidInjector(modules = {
            AlarmManagerFragmentModule.class,
            SimpleFragmentProvider.class,
            OnceRepeatFragmentProvider.class,
            DailyRepeatFragmentProvider.class,
            WeeklyRepeatFragmentProvider.class,
            MonthlyRepeatFragmentProvider.class,
            YearlyRepeatFragmentProvider.class,
            HourTimePickerFragmentProvider.class,
            DatePickerFragmentProvider.class,
            DayMonthPickerFragmentProvider.class,
            RepeatManagerDialogFragmentProvider.class,
    })
    abstract AlarmManagerFragment provideAlarmManagerFragmentFactory();
}
