package com.fearefull.todoreminder.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.util.Patterns;

import androidx.fragment.app.Fragment;

import com.fearefull.todoreminder.BuildConfig;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.google.gson.Gson;
import com.kevalpatel.ringtonepicker.RingtonePickerDialog;
import com.kevalpatel.ringtonepicker.RingtonePickerListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

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

    public static void showCustomListDialog(Context context) {

    }

    public static void showRingtonePicker(Fragment fragment, Uri defaultRingtone, RingtonePickerListener ringtonePickerListener) {
        RingtonePickerDialog.Builder ringtonePickerBuilder = new RingtonePickerDialog
                .Builder(Objects.requireNonNull(fragment.getContext()), fragment.getChildFragmentManager())

                //Set title of the dialog.
                //If set null, no title will be displayed.
                .setTitle(fragment.getString(R.string.choose_ringtone))

                //set the currently selected uri, to mark that ringtone as checked by default.
                //If no ringtone is currently selected, pass null.
                .setCurrentRingtoneUri(defaultRingtone)

                //Set true to allow allow user to select default ringtone set in phone settings.
                .displayDefaultRingtone(true)

                //Set true to allow user to select silent (i.e. No ringtone.).
                .displaySilentRingtone(true)

                //set the text to display of the positive (ok) button.
                //If not set OK will be the default text.
                .setPositiveButtonText(fragment.getString(R.string.confirm))

                //set text to display as negative button.
                //If set null, negative button will not be displayed.
                .setCancelButtonText(fragment.getString(R.string.cancel))

                //Set flag true if you want to play the sample of the clicked tone.
                .setPlaySampleWhileSelection(true)

                //Set the callback listener.
                .setListener(ringtonePickerListener);

        //Add the desirable ringtone types.
        ringtonePickerBuilder.addRingtoneType(RingtonePickerDialog.Builder.TYPE_ALARM);

        //Display the dialog.
        ringtonePickerBuilder.show();
    }
}