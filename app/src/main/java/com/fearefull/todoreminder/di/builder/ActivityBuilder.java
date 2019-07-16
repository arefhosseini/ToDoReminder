package com.fearefull.todoreminder.di.builder;

import com.fearefull.todoreminder.service.AlarmReceiver;
import com.fearefull.todoreminder.ui.about.AboutFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.AlarmManagerActivity;
import com.fearefull.todoreminder.ui.alarm_manager.AlarmManagerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.AlarmManagerFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.date_picker.DatePickerFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.time_picker.TimePickerFragment;
import com.fearefull.todoreminder.ui.alarm_manager.time_picker.TimePickerFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_notification.AlarmNotificationActivity;
import com.fearefull.todoreminder.ui.home.HomeFragmentProvider;
import com.fearefull.todoreminder.ui.login.LoginActivity;
import com.fearefull.todoreminder.ui.main.MainActivity;
import com.fearefull.todoreminder.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(
            modules = {
                    HomeFragmentProvider.class,
                    AboutFragmentProvider.class,
                    AlarmManagerFragmentProvider.class,
            })
    abstract MainActivity contributesMainActivity();

    @ContributesAndroidInjector
    abstract SplashActivity contributesSplashActivity();

    @ContributesAndroidInjector
    abstract LoginActivity contributesLoginActivity();

    @ContributesAndroidInjector(
            modules = {
                    TimePickerFragmentProvider.class,
                    DatePickerFragmentProvider.class,
            })
    abstract AlarmManagerActivity contributesAlarmManagerActivity();

    @ContributesAndroidInjector
    abstract AlarmNotificationActivity contributesAlarmNotification();
}
