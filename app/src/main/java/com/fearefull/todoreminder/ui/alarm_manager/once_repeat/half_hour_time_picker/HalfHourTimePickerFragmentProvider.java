package com.fearefull.todoreminder.ui.alarm_manager.once_repeat.half_hour_time_picker;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HalfHourTimePickerFragmentProvider {

    @ContributesAndroidInjector
    abstract HalfHourTimePickerFragment provideHalfHourTimePickerFragmentFactory();
}
