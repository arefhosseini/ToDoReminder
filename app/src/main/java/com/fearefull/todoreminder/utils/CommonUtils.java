package com.fearefull.todoreminder.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.provider.Settings;
import android.util.Patterns;

import com.fearefull.todoreminder.BuildConfig;
import com.fearefull.todoreminder.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class CommonUtils {

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getTimestamp() {
        return new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static String getAppVersionString(Context context) {
        return context.getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
    }

    public static AlertDialog showSingleChoiceItemDialog(Context context, int titleId,
                                                         String[] items, int defaultIndex,
                                                         DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        builder.setTitle(titleId);
        builder.setSingleChoiceItems(items, defaultIndex, listener);
        return builder.create();
    }

    public static AlertDialog show2ButtonDialogNoTitle(Context context, int message,
                                                       DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustomNoTitle);
        builder.setPositiveButton(context.getText(R.string.ok), listener);
        builder.setNegativeButton(context.getText(R.string.cancel), listener);
        builder.setMessage(message);
        return builder.create();
    }

    public static AlertDialog show2ButtonDialog(Context context, int titleId,
                                                DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        builder.setTitle(titleId);
        builder.setPositiveButton(context.getText(R.string.ok), listener);
        builder.setNegativeButton(context.getText(R.string.cancel), listener);
        builder.setMessage(R.string.history_delete_message);
        return builder.create();
    }

    public static void showCustomListDialog(Context context) {

    }

    public Map<String, String> getAlarmNotificationList(Context context) {
        RingtoneManager manager = new RingtoneManager(context);
        manager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = manager.getCursor();

        Map<String, String> list = new HashMap<>();
        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" + cursor.getString(RingtoneManager.ID_COLUMN_INDEX);

            list.put(notificationTitle, notificationUri);
        }
        return list;
    }
}