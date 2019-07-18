package com.fearefull.todoreminder.ui.alarm_manager.once_repeat;

import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class OnceRepeatFragmentModule {

    @Provides
    @Named("OnceRepeat")
    BaseViewPagerAdapter provideBaseViewPagerAdapter(OnceRepeatFragment fragment) {
        return new BaseViewPagerAdapter(fragment.getChildFragmentManager());
    }
}