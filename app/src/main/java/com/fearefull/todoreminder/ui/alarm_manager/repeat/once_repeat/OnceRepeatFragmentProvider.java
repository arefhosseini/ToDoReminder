package com.fearefull.todoreminder.ui.alarm_manager.repeat.once_repeat;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OnceRepeatFragmentProvider {

    @ContributesAndroidInjector(modules = {
            OnceRepeatFragmentModule.class,
    })
    abstract OnceRepeatFragment provideOnceRepeatFragmentFactory();
}
