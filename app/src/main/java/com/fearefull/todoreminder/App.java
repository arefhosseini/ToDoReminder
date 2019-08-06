package com.fearefull.todoreminder;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;

import com.evernote.android.job.JobManager;
import com.fearefull.todoreminder.di.component.DaggerAppComponent;
import com.fearefull.todoreminder.schedule.AlarmScheduler;
import com.fearefull.todoreminder.schedule.AppJobCreator;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasBroadcastReceiverInjector;
import dagger.android.HasServiceInjector;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import timber.log.Timber;

public class App extends Application implements HasActivityInjector, HasBroadcastReceiverInjector,
        HasServiceInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverInjector;
    @Inject
    DispatchingAndroidInjector<Service> dispatchingServiceInjector;
    @Inject
    CalligraphyConfig calligraphyConfig;
    @Inject
    JobManager jobManager;
    @Inject
    AppJobCreator appJobCreator;
    @Inject
    AlarmScheduler alarmScheduler;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
        return broadcastReceiverInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingServiceInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        else
            Timber.plant(new Timber.DebugTree());

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(calligraphyConfig))
                .build()
        );

        jobManager.addJobCreator(appJobCreator);

        FirebaseAnalytics.getInstance(this);
    }
}
