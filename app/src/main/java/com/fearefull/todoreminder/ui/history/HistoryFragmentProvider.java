package com.fearefull.todoreminder.ui.history;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HistoryFragmentProvider {

    @ContributesAndroidInjector(modules = HistoryFragmentModule.class)
    abstract HistoryFragment provideHistoryFragmentFactory();
}
