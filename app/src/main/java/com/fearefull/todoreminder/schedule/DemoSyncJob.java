package com.fearefull.todoreminder.schedule;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.fearefull.todoreminder.ui.alarm_notification.AlarmNotificationActivity;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class DemoSyncJob extends Job {

    public static final String TAG = "job_tag";

    @Override
    @NonNull
    protected Result onRunJob(@NotNull Params params) {
        Timber.i(params.getExtras().getString("key", "lol"));
        getContext().getApplicationContext().startActivity(AlarmNotificationActivity.newIntent(getContext().getApplicationContext()));
        return Result.SUCCESS;
    }

    public static int scheduleJob(long time, PersistableBundleCompat extras) {
        return new JobRequest.Builder(DemoSyncJob.TAG)
                .setExact(time)
                .setExtras(extras)
                .build()
                .schedule();
    }
}