package com.fearefull.todoreminder.ui.alarm_manager.repeat.weekly_repeat;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class WeeklyRepeatFragmentModule {

    @Provides
    @Named("WeeklyRepeat")
    GridLayoutManager provideGridLayoutManager(WeeklyRepeatFragment fragment) {
        return new GridLayoutManager(fragment.getActivity(), 2);
    }

    @Provides
    DayWeekAdapter provideDayWeekAdapter() {
        return new DayWeekAdapter();
    }

    @Provides
    @Named("WeeklyRepeat")
    BaseViewPagerAdapter provideBaseViewPagerAdapter(WeeklyRepeatFragment fragment) {
        return new BaseViewPagerAdapter(fragment.getChildFragmentManager());
    }

}
