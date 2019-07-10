package com.fearefull.todoreminder.ui.alarm_manager.date_picker;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DatePickerFragmentProvider {

    @ContributesAndroidInjector
    abstract DatePickerFragment provideDatePickerFragmentFactory();
}
