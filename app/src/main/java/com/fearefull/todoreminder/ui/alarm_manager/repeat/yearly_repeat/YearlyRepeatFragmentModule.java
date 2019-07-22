package com.fearefull.todoreminder.ui.alarm_manager.repeat.yearly_repeat;

import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class YearlyRepeatFragmentModule {
    @Provides
    @Named("YearlyRepeat")
    BaseViewPagerAdapter provideBaseViewPagerAdapter(YearlyRepeatFragment fragment) {
        return new BaseViewPagerAdapter(fragment.getChildFragmentManager());
    }
}
