package com.fearefull.todoreminder.ui.home;

import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeFragmentModule {
    @Provides
    LinearLayoutManager provideLinearLayoutManager(HomeFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    AlarmAdapter provideAlarmAdapter() {
        return new AlarmAdapter();
    }
}
