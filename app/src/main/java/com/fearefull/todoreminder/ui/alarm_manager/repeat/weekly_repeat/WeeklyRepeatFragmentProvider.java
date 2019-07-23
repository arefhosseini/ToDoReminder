package com.fearefull.todoreminder.ui.alarm_manager.repeat.weekly_repeat;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WeeklyRepeatFragmentProvider {

    @ContributesAndroidInjector(modules = {
            WeeklyRepeatFragmentModule.class,
    })
    abstract WeeklyRepeatFragment provideWeeklyRepeatFragment();
}

