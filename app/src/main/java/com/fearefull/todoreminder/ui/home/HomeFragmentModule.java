package com.fearefull.todoreminder.ui.home;

import androidx.recyclerview.widget.LinearLayoutManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeFragmentModule {
    @Provides
    @Named("Home")
    LinearLayoutManager provideLinearLayoutManager(HomeFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    AlarmAdapter provideAlarmAdapter() {
        return new AlarmAdapter();
    }
}
