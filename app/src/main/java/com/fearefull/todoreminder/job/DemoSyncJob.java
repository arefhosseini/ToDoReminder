package com.fearefull.todoreminder.job;

import androidx.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.fearefull.todoreminder.ui.alarm_notification.AlarmNotificationActivity;

public class DemoSyncJob extends Job {

    public static final String TAG = "job_demo_tag";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {

        getContext().startActivity(AlarmNotificationActivity.newIntent(getContext()));

        return Result.SUCCESS;
    }

    public static void scheduleJob(long time) {
        new JobRequest.Builder(DemoSyncJob.TAG)
                .setExact(time)
                .build()
                .schedule();
    }
}