package com.fearefull.todoreminder.schedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class AppJobCreator implements JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case DemoSyncJob.TAG:
                return new DemoSyncJob();
            default:
                return null;
        }
    }
}