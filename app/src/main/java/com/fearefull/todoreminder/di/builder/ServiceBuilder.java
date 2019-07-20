package com.fearefull.todoreminder.di.builder;

import com.fearefull.todoreminder.schedule.ScheduleService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceBuilder {
    @ContributesAndroidInjector
    abstract ScheduleService contributeScheduleService();
}
