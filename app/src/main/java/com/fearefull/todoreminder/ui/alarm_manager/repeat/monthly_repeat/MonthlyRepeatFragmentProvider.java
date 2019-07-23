package com.fearefull.todoreminder.ui.alarm_manager.repeat.monthly_repeat;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MonthlyRepeatFragmentProvider {

    @ContributesAndroidInjector(modules = {
            MonthlyRepeatFragmentModule.class,
    })
    abstract MonthlyRepeatFragment provideMonthlyRepeatFragment();
}


