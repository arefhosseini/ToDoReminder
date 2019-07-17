package com.fearefull.todoreminder.ui.alarm_manager.once_repeat;

import com.fearefull.todoreminder.ui.base.BaseViewPagerAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class OnceRepeatFragmentModule {

    @Provides
    BaseViewPagerAdapter provideBaseViewPagerAdapter(OnceRepeatFragment fragment) {
        return new BaseViewPagerAdapter(fragment.getFragmentManager());
    }
}