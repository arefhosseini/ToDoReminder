package com.fearefull.todoreminder.ui.alarm_manager;

import com.fearefull.todoreminder.ui.alarm_manager.once_repeat.OnceRepeatFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.date_picker.DatePickerFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.half_hour_time_picker.HalfHourTimePickerFragmentProvider;
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
            HalfHourTimePickerFragmentProvider.class,
            DatePickerFragmentProvider.class,
            RepeatManagerDialogFragmentProvider.class,
    })
    abstract AlarmManagerFragment provideAlarmManagerFragmentFactory();
}
