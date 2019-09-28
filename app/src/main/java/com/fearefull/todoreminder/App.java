package com.fearefull.todoreminder;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evernote.android.job.JobManager;
import com.fearefull.todoreminder.di.component.DaggerAppComponent;
import com.fearefull.todoreminder.schedule.AlarmScheduler;
import com.fearefull.todoreminder.schedule.AppJobCreator;
import com.fearefull.todoreminder.ui.main.MainActivity;
import com.fearefull.todoreminder.utils.AppConstants;
import com.fearefull.todoreminder.utils.CommonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasBroadcastReceiverInjector;
import dagger.android.HasServiceInjector;
import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceConfig;
import io.adtrace.sdk.LogLevel;
import io.adtrace.sdk.OnDeeplinkResponseListener;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.metrix.sdk.Metrix;
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
    @Inject
    AdTraceConfig adTraceConfig;

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

        Metrix.getInstance().setOnDeeplinkResponseListener(deepLink -> {
            Timber.e(deepLink.toString());
            return true;
        });

        // Evaluate the deep link to be launched.
        adTraceConfig.setOnDeeplinkResponseListener(deepLink -> {
            Timber.e(deepLink.toString());
            return true;
        });

        Metrix.initialize(this, AppConstants.METRIX_APP_ID);
        adTraceConfig.setLogLevel(LogLevel.VERBOSE);
        AdTrace.onCreate(adTraceConfig);
        // add sample log to firebaseAnalytics
        CommonUtils.sendAdtraceSampleEvent(AppConstants.ADTRACE_EVENT_TOKEN_INIT1);
        registerActivityLifecycleCallbacks(new AdTraceLifecycleCallbacks());
        CommonUtils.sendAdtraceSampleEvent(AppConstants.ADTRACE_EVENT_TOKEN_INIT2);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Timber.w(task.getException());
                return;
            }
            // Get new Instance ID token
            String token = Objects.requireNonNull(task.getResult()).getToken();
            AdTrace.setPushToken(token, getApplicationContext());
            Timber.e("pushToken: %s", token);
        });



        jobManager.addJobCreator(appJobCreator);
    }

    private static final class AdTraceLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@NotNull Activity activity) {
            AdTrace.onResume();
        }

        @Override
        public void onActivityPaused(@NotNull Activity activity) {
            AdTrace.onPause();
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }
    }
}
