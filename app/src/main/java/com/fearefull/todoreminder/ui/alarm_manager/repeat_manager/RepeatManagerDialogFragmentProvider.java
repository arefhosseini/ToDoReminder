package com.fearefull.todoreminder.ui.alarm_manager.repeat_manager;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RepeatManagerDialogFragmentProvider {

    @ContributesAndroidInjector(modules = RepeatManagerDialogFragmentModule.class)
    abstract RepeatManagerDialogFragment provideRepeatManagerDialogFragment();
}
