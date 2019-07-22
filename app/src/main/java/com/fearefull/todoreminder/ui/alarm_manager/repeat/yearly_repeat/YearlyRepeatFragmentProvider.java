package com.fearefull.todoreminder.ui.alarm_manager.repeat.yearly_repeat;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class YearlyRepeatFragmentProvider {

    @ContributesAndroidInjector(modules = {
            YearlyRepeatFragmentModule.class,
    })
    abstract YearlyRepeatFragment provideYearlyRepeatFragmentFactory();
}
