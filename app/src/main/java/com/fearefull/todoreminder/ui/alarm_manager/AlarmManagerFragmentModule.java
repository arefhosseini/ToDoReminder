package com.fearefull.todoreminder.ui.alarm_manager;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AlarmManagerFragmentModule {
    @Provides
    @Named("AlarmManager")
    LinearLayoutManager provideLinearLayoutManager(AlarmManagerFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    @Named("AlarmManager")
    GridLayoutManager provideGridLayoutManager(AlarmManagerFragment fragment) {
        return new GridLayoutManager(fragment.getActivity(), 3, GridLayoutManager.HORIZONTAL, false);
    }

    @Provides
    RepeatAdapter provideRepeatAdapter() {
        return new RepeatAdapter();
    }

    @Provides
    AlarmTitleAdapter provideAlarmTitleAdapter() {
        return new AlarmTitleAdapter();
    }

    @Provides
    @Named("AlarmManager")
    BaseViewPagerAdapter provideBaseViewPagerAdapter(AlarmManagerFragment fragment) {
        return new BaseViewPagerAdapter(fragment.getChildFragmentManager());
    }
}
