package com.fearefull.todoreminder.ui.alarm_manager.time_picker;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TimePickerFragmentProvider {

    @ContributesAndroidInjector
    abstract TimePickerFragment provideTimePickerFragmentFactory();
}