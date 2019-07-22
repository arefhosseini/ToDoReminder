package com.fearefull.todoreminder.ui.alarm_manager.repeat.daily_repeat;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DailyRepeatFragmentProvider {

    @ContributesAndroidInjector(modules = {
            DailyRepeatFragmentModule.class,
    })
    abstract DailyRepeatFragment provideDailyRepeatFragment();
}
