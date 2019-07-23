package com.fearefull.todoreminder.ui.alarm_manager.picker.day_month_picker;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DayMonthPickerFragmentProvider {

    @ContributesAndroidInjector
    abstract DayMonthPickerFragment provideDayMonthPickerFragmentFactory();
}

