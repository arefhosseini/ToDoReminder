package com.fearefull.todoreminder.ui.alarm_manager.picker.ringtone_picker;

import androidx.recyclerview.widget.LinearLayoutManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class RingtonePickerDialogFragmentModule {
    @Provides
    @Named("RingtonePicker")
    LinearLayoutManager provideLinearLayoutManager(RingtonePickerDialogFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    RingtonePickerAdapter provideRingtonePickerAdapter() {
        return new RingtonePickerAdapter();
    }
}
