package com.fearefull.todoreminder.utils;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;

import com.fearefull.todoreminder.data.model.db.Ringtone;
import com.fearefull.todoreminder.data.model.other.item.RingtonePickerItem;
import com.fearefull.todoreminder.data.model.other.type.RingtoneType;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public final class RingtoneUtils {

    private RingtoneUtils() {

    }

    public static Observable<List<RingtonePickerItem>> getRingtoneList(Context context, Ringtone defaultRingtone) {
        return Observable.defer(() -> {
            List<RingtonePickerItem> list = new ArrayList<>();
            Ringtone ringtone;
            RingtonePickerItem ringtonePickerItem;

            ringtone = new Ringtone();
            ringtonePickerItem = new RingtonePickerItem(ringtone);
            if (ringtone.isSame(defaultRingtone))
                ringtonePickerItem.setDefault(true);
            list.add(ringtonePickerItem);

            ringtone = new Ringtone(RingtoneType.SILENT, RingtoneType.SILENT.getName(), null);
            ringtonePickerItem = new RingtonePickerItem(ringtone);
            if (ringtone.isSame(defaultRingtone))
                ringtonePickerItem.setDefault(true);
            list.add(ringtonePickerItem);

            RingtoneManager ringtoneManager = new RingtoneManager(context);
            ringtoneManager.setType(RingtoneManager.TYPE_ALARM);
            Cursor cursor = ringtoneManager.getCursor();
            if (cursor.getCount() != 0 && cursor.moveToFirst()) {
                while(!cursor.isAfterLast() && cursor.moveToNext()) {
                    ringtone = new Ringtone(
                            RingtoneType.ALARM,
                            ringtoneManager.getRingtone(cursor.getPosition()).getTitle(context),
                            ringtoneManager.getRingtoneUri(cursor.getPosition()).toString());
                    ringtonePickerItem = new RingtonePickerItem(ringtone);
                    if (ringtone.isSame(defaultRingtone))
                        ringtonePickerItem.setDefault(true);
                    list.add(ringtonePickerItem);
                }
                cursor.close();
            }

            return Observable.just(list);
        });
    }
}
