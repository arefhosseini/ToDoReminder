package com.fearefull.todoreminder.ui.alarm_manager.picker.hour_time_picker;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HourTimePickerFragmentProvider {

    @ContributesAndroidInjector
    abstract HourTimePickerFragment provideHourTimePickerFragmentFactory();
}
