package com.fearefull.todoreminder.schedule;

import androidx.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.fearefull.todoreminder.data.model.db.Snooze;
import com.fearefull.todoreminder.ui.alarm_notification.AlarmNotificationActivity;

import org.jetbrains.annotations.NotNull;

public class DemoSyncJob extends Job {

    public static final String TAG = "job_tag";

    @Override
    @NonNull
    protected Result onRunJob(@NotNull Params params) {
        getContext().startActivity(AlarmNotificationActivity.newIntent(getContext(),
                params.getExtras().getString(Snooze.SNOOZE_KEY, "")));
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