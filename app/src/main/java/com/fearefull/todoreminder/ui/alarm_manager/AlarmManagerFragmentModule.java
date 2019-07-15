package com.fearefull.todoreminder.ui.alarm_manager;

import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;

@Module
public class AlarmManagerFragmentModule {
    @Provides
    LinearLayoutManager provideLinearLayoutManager(AlarmManagerFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    RepeatAdapter provideRepeatAdapter() {
        return new RepeatAdapter();
    }
}
