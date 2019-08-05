package com.fearefull.todoreminder.ui.alarm_manager.picker.ringtone_picker;

import android.content.Context;

public interface RingtonePickerNavigator {
    void finish();
    void finishAndSave();
    Context getContext();
}
