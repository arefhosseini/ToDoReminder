package com.fearefull.todoreminder.ui.alarm_manager.repeat.daily_repeat;

import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DailyRepeatFragmentModule {

    @Provides
    @Named("DailyRepeat")
    BaseViewPagerAdapter provideBaseViewPagerAdapter(DailyRepeatFragment fragment) {
        return new BaseViewPagerAdapter(fragment.getChildFragmentManager());
    }

}