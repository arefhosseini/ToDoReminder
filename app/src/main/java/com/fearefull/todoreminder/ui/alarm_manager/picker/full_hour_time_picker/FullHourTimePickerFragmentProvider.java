package com.fearefull.todoreminder.ui.alarm_manager.picker.full_hour_time_picker;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FullHourTimePickerFragmentProvider {

    @ContributesAndroidInjector
    abstract FullHourTimePickerFragment provideFullHourTimePickerFragmentFactory();
}
