package com.fearefull.todoreminder;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;

import com.evernote.android.job.JobManager;
import com.fearefull.todoreminder.di.component.DaggerAppComponent;
import com.fearefull.todoreminder.job.MyJobCreator;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasBroadcastReceiverInjector;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application implements HasActivityInjector, HasBroadcastReceiverInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverInjector;

    @Inject
    CalligraphyConfig calligraphyConfig;

    @Inject
    JobManager jobManager;

    @Inject
    MyJobCreator myJobCreator;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
        return broadcastReceiverInjector;
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

        CalligraphyConfig.initDefault(calligraphyConfig);

        jobManager.addJobCreator(myJobCreator);
    }
}
