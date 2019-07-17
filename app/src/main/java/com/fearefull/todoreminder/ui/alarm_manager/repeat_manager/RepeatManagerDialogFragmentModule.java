package com.fearefull.todoreminder.ui.alarm_manager.repeat_manager;

import androidx.recyclerview.widget.LinearLayoutManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class RepeatManagerDialogFragmentModule {
    @Provides
    @Named("RepeatManager")
    LinearLayoutManager provideLinearLayoutManager(RepeatManagerDialogFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    RepeatManagerAdapter provideRepeatManagerAdapter() {
        return new RepeatManagerAdapter();
    }
}