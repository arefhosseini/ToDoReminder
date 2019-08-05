package com.fearefull.todoreminder.ui.alarm_manager.picker.ringtone_picker;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RingtonePickerDialogFragmentProvider {

    @ContributesAndroidInjector(modules = RingtonePickerDialogFragmentModule.class)
    abstract RingtonePickerDialogFragment provideRingtonePickerDialogFragment();
}

