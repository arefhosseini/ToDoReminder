package com.fearefull.todoreminder.ui.settings;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SettingsFragmentProvider {
    @ContributesAndroidInjector
    abstract SettingsFragment provideSettingsFragmentFactory();
}
