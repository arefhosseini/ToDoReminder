package com.fearefull.todoreminder.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.fearefull.todoreminder.data.model.db.Snooze;
import com.fearefull.todoreminder.di.PreferenceInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_SCHEDULE = "PREF_KEY_SCHEDULE";
    private static final String PREF_KEY_SNOOZE = "PREF_KEY_SNOOZE";
    private final SharedPreferences prefs;
    private final Gson gson;

    @Inject
    AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        prefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    @Override
    public int getSchedule() {
        return prefs.getInt(PREF_KEY_SCHEDULE, -1);
    }

    @Override
    public void setSchedule(int schedule) {
        prefs.edit().putInt(PREF_KEY_SCHEDULE, schedule).apply();
    }

    @Override
    public List<Snooze> getAllSnoozes() {
        Type type = new TypeToken<List<Snooze>>(){}.getType();
        List<Snooze> snoozes = gson.fromJson(prefs.getString(PREF_KEY_SNOOZE, null), type);
        if (snoozes == null)
            return new ArrayList<>();
        return snoozes;
    }

    private void setSnoozeList(List<Snooze> snoozes) {
        String json = gson.toJson(snoozes);
        prefs.edit().putString(PREF_KEY_SNOOZE, json).apply();
    }

    @Override
    public void addSnooze(Snooze snooze) {
        List<Snooze> snoozes = getAllSnoozes();
        boolean isFound = false;
        for (Snooze s: snoozes) {
            if (snooze.isSame(s)) {
                s.set(snooze);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            snoozes.add(snooze);
        }
        setSnoozeList(snoozes);
    }

    @Override
    public void removeSnooze(Snooze snooze) {
        List<Snooze> snoozes = getAllSnoozes();
        snoozes.remove(snooze);
        Snooze selectedSnooze = null;
        for (Snooze s: snoozes) {
            if (snooze.isSame(s)) {
                s.set(snooze);
                selectedSnooze = s;
                break;
            }
        }
        if (selectedSnooze != null) {
            snoozes.remove(selectedSnooze);
            Timber.e("Removing");
        }
        setSnoozeList(snoozes);
    }

    @Override
    public Snooze getSnoozeByAlarmId(long alarmId) {
        for (Snooze snooze: getAllSnoozes()) {
            if (snooze.getAlarmId() == alarmId)
                return snooze;
        }
        return new Snooze();
    }
}
