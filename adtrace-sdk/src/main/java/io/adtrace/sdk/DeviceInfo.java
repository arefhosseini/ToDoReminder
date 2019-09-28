package io.adtrace.sdk;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static io.adtrace.sdk.Constants.HIGH;
import static io.adtrace.sdk.Constants.LARGE;
import static io.adtrace.sdk.Constants.LONG;
import static io.adtrace.sdk.Constants.LOW;
import static io.adtrace.sdk.Constants.MEDIUM;
import static io.adtrace.sdk.Constants.NORMAL;
import static io.adtrace.sdk.Constants.SMALL;
import static io.adtrace.sdk.Constants.XLARGE;


/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
class DeviceInfo {
    String playAdId;
    String playAdIdSource;
    Boolean isTrackingEnabled;
    private boolean nonGoogleIdsReadOnce = false;
    String macSha1;
    String macShortMd5;
    String androidId;
    String fbAttributionId;
    String clientSdk;
    String packageName;
    String appVersion;
    String deviceType;
    String deviceName;
    String deviceManufacturer;
    String osName;
    String osVersion;
    String apiLevel;
    String language;
    String country;
    String screenSize;
    String screenFormat;
    String screenDensity;
    String displayWidth;
    String displayHeight;
    String hardwareName;
    String abi;
    String buildName;
    String appInstallTime;
    String appUpdateTime;
    String installedApps;

    DeviceInfo(Context context, String sdkPrefix) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        Locale locale = Util.getLocale(configuration);
        int screenLayout = configuration.screenLayout;
        ContentResolver contentResolver = context.getContentResolver();

        packageName = getPackageName(context);
        appVersion = getAppVersion(context);
        deviceType = getDeviceType(screenLayout);
        deviceName = getDeviceName();
        deviceManufacturer = getDeviceManufacturer();
        osName = getOsName();
        osVersion = getOsVersion();
        apiLevel = getApiLevel();
        language = getLanguage(locale);
        country = getCountry(locale);
        screenSize = getScreenSize(screenLayout);
        screenFormat = getScreenFormat(screenLayout);
        screenDensity = getScreenDensity(displayMetrics);
        displayWidth = getDisplayWidth(displayMetrics);
        displayHeight = getDisplayHeight(displayMetrics);
        clientSdk = getClientSdk(sdkPrefix);
        fbAttributionId = getFacebookAttributionId(context);
        hardwareName = getHardwareName();
        abi = getABI();
        buildName = getBuildName();
        installedApps = getInstalledApps(context);
        appInstallTime = getAppInstallTime(context);
        appUpdateTime = getAppUpdateTime(context);

    }

    void reloadPlayIds(Context context) {
        playAdIdSource = null;
        for (int i = 0; i < 3; i += 1) {
            try {
                GooglePlayServicesClient.GooglePlayServicesInfo gpsInfo = GooglePlayServicesClient.getGooglePlayServicesInfo(context);
                playAdId = gpsInfo.getGpsAdid();
                if (playAdId != null) {
                    playAdIdSource = "service";
                    break;
                }
            } catch (Exception e) {
            }
            playAdId = Util.getPlayAdId(context);
            if (playAdId != null) {
                playAdIdSource = "library";
                break;
            }
        }
        for (int i = 0; i < 3; i += 1) {
            try {
                GooglePlayServicesClient.GooglePlayServicesInfo gpsInfo = GooglePlayServicesClient.getGooglePlayServicesInfo(context);
                isTrackingEnabled = gpsInfo.isTrackingEnabled();
                if (isTrackingEnabled != null) {
                    break;
                }
            } catch (Exception e) {
            }
            isTrackingEnabled = Util.isPlayTrackingEnabled(context);
            if (isTrackingEnabled != null) {
                break;
            }
        }
    }

    void reloadNonPlayIds(Context context) {
        if (nonGoogleIdsReadOnce) {
            return;
        }
        if (!Util.checkPermission(context, android.Manifest.permission.ACCESS_WIFI_STATE)) {
            AdTraceFactory.getLogger().warn("Missing permission: ACCESS_WIFI_STATE");
        }
        String macAddress = Util.getMacAddress(context);
        macSha1 = getMacSha1(macAddress);
        macShortMd5 = getMacShortMd5(macAddress);
        androidId = Util.getAndroidId(context);
        nonGoogleIdsReadOnce = true;
    }

    private String getMacAddress(Context context, boolean isGooglePlayServicesAvailable) {
        if (!isGooglePlayServicesAvailable) {
            if (!Util.checkPermission(context, android.Manifest.permission.ACCESS_WIFI_STATE)) {
                AdTraceFactory.getLogger().warn("Missing permission: ACCESS_WIFI_STATE");
            }
            return Util.getMacAddress(context);
        } else {
            return null;
        }
    }

    private String getPackageName(Context context) {
        return context.getPackageName();
    }


    private String getAppVersion(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            String name = context.getPackageName();
            PackageInfo info = packageManager.getPackageInfo(name, 0);
            return info.versionName;
        } catch (Exception e) {
            return null;
        }
    }

    private String getDeviceType(int screenLayout) {
        int screenSize = screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return "phone";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
            case 4:
                return "tablet";
            default:
                return null;
        }
    }

    private String getDeviceName() {
        return Build.MODEL;
    }

    private String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    private String getOsName() {
        return "android";
    }

    private String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    private String getApiLevel() {
        return "" + Build.VERSION.SDK_INT;
    }

    private String getLanguage(Locale locale) {
        return locale.getLanguage();
    }

    private String getCountry(Locale locale) {
        return locale.getCountry();
    }

    private String getBuildName() {
        return Build.ID;
    }

    private String getHardwareName() {
        return Build.DISPLAY;
    }

    private String getScreenSize(int screenLayout) {
        int screenSize = screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return SMALL;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return NORMAL;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return LARGE;
            case 4:
                return XLARGE;
            default:
                return null;
        }
    }

    private String getScreenFormat(int screenLayout) {
        int screenFormat = screenLayout & Configuration.SCREENLAYOUT_LONG_MASK;

        switch (screenFormat) {
            case Configuration.SCREENLAYOUT_LONG_YES:
                return LONG;
            case Configuration.SCREENLAYOUT_LONG_NO:
                return NORMAL;
            default:
                return null;
        }
    }

    private String getScreenDensity(DisplayMetrics displayMetrics) {
        int density = displayMetrics.densityDpi;
        int low = (DisplayMetrics.DENSITY_MEDIUM + DisplayMetrics.DENSITY_LOW) / 2;
        int high = (DisplayMetrics.DENSITY_MEDIUM + DisplayMetrics.DENSITY_HIGH) / 2;

        if (density == 0) {
            return null;
        } else if (density < low) {
            return LOW;
        } else if (density > high) {
            return HIGH;
        }
        return MEDIUM;
    }

    private String getDisplayWidth(DisplayMetrics displayMetrics) {
        return String.valueOf(displayMetrics.widthPixels);
    }

    private String getDisplayHeight(DisplayMetrics displayMetrics) {
        return String.valueOf(displayMetrics.heightPixels);
    }

    private String getClientSdk(String sdkPrefix) {
        if (sdkPrefix == null) {
            return Constants.CLIENT_SDK;
        } else {
            return Util.formatString("%s@%s", sdkPrefix, Constants.CLIENT_SDK);
        }
    }

    private String getMacSha1(String macAddress) {
        if (macAddress == null) {
            return null;
        }
        String macSha1 = Util.sha1(macAddress);

        return macSha1;
    }

    private String getMacShortMd5(String macAddress) {
        if (macAddress == null) {
            return null;
        }
        String macShort = macAddress.replaceAll(":", "");
        String macShortMd5 = Util.md5(macShort);

        return macShortMd5;
    }

    private String getFacebookAttributionId(final Context context) {
        try {
            final ContentResolver contentResolver = context.getContentResolver();
            final Uri uri = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
            final String columnName = "aid";
            final String[] projection = {columnName};
            final Cursor cursor = contentResolver.query(uri, projection, null, null, null);

            if (cursor == null) {
                return null;
            }
            if (!cursor.moveToFirst()) {
                cursor.close();
                return null;
            }

            final String attributionId = cursor.getString(cursor.getColumnIndex(columnName));
            cursor.close();
            return attributionId;
        } catch (Exception e) {
            return null;
        }
    }

    private String getABI() {
        String[] SupportedABIS = Util.getSupportedAbis();

        // SUPPORTED_ABIS is only supported in API level 21
        // get CPU_ABI instead
        if (SupportedABIS == null || SupportedABIS.length == 0) {
            return Util.getCpuAbi();
        }

        return SupportedABIS[0];
    }

    private String getAppInstallTime(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);

            String appInstallTime = Util.dateFormatter.format(new Date(packageInfo.firstInstallTime));

            return appInstallTime;
        } catch (Exception ex) {
            return null;
        }
    }

    private String getAppUpdateTime(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);

            String appInstallTime = Util.dateFormatter.format(new Date(packageInfo.lastUpdateTime));

            return appInstallTime;
        } catch (Exception ex) {
            return null;
        }
    }

    private String getInstalledApps(Context context) {
        List<String> list = new ArrayList<String>();
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((isSystemPackage(p) == false)) {
                list.add(p.packageName);

            }
        }
        return android.text.TextUtils.join(",", list);
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }
}
