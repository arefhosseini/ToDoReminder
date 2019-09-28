package io.adtrace.sdk;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import io.adtrace.sdk.scheduler.SingleThreadCachedScheduler;
import io.adtrace.sdk.scheduler.ThreadExecutor;
import io.adtrace.sdk.scheduler.TimerCycle;
import io.adtrace.sdk.scheduler.TimerOnce;

import static io.adtrace.sdk.Constants.ACTIVITY_STATE_FILENAME;
import static io.adtrace.sdk.Constants.ATTRIBUTION_FILENAME;
import static io.adtrace.sdk.Constants.SESSION_CALLBACK_PARAMETERS_FILENAME;
import static io.adtrace.sdk.Constants.SESSION_PARTNER_PARAMETERS_FILENAME;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public class ActivityHandler implements IActivityHandler {
    private static long FOREGROUND_TIMER_INTERVAL;
    private static long FOREGROUND_TIMER_START;
    private static long BACKGROUND_TIMER_INTERVAL;
    private static long SESSION_INTERVAL;
    private static long SUBSESSION_INTERVAL;
    private static final String TIME_TRAVEL = "Time travel!";
    private static final String ACTIVITY_STATE_NAME = "Activity state";
    private static final String ATTRIBUTION_NAME = "Attribution";
    private static final String FOREGROUND_TIMER_NAME = "Foreground timer";
    private static final String BACKGROUND_TIMER_NAME = "Background timer";
    private static final String DELAY_START_TIMER_NAME = "Delay Start timer";
    private static final String SESSION_CALLBACK_PARAMETERS_NAME = "Session Callback parameters";
    private static final String SESSION_PARTNER_PARAMETERS_NAME = "Session Partner parameters";
    private static final String SESSION_PARAMETERS_NAME = "Session parameters";

    private ThreadExecutor executor;
    private IPackageHandler packageHandler;
    private ActivityState activityState;
    private ILogger logger;
    private TimerCycle foregroundTimer;
    private TimerOnce backgroundTimer;
    private TimerOnce delayStartTimer;
    private InternalState internalState;
    private String basePath;
    private String gdprPath;

    private DeviceInfo deviceInfo;
    private AdTraceConfig adTraceConfig; // always valid after construction
    private AdTraceAttribution attribution;
    private IAttributionHandler attributionHandler;
    private ISdkClickHandler sdkClickHandler;
    private SessionParameters sessionParameters;
    private InstallReferrer installReferrer;

    @Override
    public void teardown() {
        if (backgroundTimer != null) {
            backgroundTimer.teardown();
        }
        if (foregroundTimer != null) {
            foregroundTimer.teardown();
        }
        if (delayStartTimer != null) {
            delayStartTimer.teardown();
        }
        if (executor != null) {
            executor.teardown();
        }
        if (packageHandler != null) {
            packageHandler.teardown();
        }
        if (attributionHandler != null) {
            attributionHandler.teardown();
        }
        if (sdkClickHandler != null) {
            sdkClickHandler.teardown();
        }
        if (sessionParameters != null) {
            if (sessionParameters.callbackParameters != null) {
                sessionParameters.callbackParameters.clear();
            }
            if (sessionParameters.partnerParameters != null) {
                sessionParameters.partnerParameters.clear();
            }
        }

        teardownActivityStateS();
        teardownAttributionS();
        teardownAllSessionParametersS();

        packageHandler = null;
        logger = null;
        foregroundTimer = null;
        executor = null;
        backgroundTimer = null;
        delayStartTimer = null;
        internalState = null;
        deviceInfo = null;
        adTraceConfig = null;
        attributionHandler = null;
        sdkClickHandler = null;
        sessionParameters = null;
    }

    static void deleteState(Context context) {
        deleteActivityState(context);
        deleteAttribution(context);
        deleteSessionCallbackParameters(context);
        deleteSessionPartnerParameters(context);

        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
        sharedPreferencesManager.clear();
    }

    public class InternalState {
        boolean enabled;
        boolean offline;
        boolean background;
        boolean delayStart;
        boolean updatePackages;
        boolean firstLaunch;
        boolean sessionResponseProcessed;
        boolean firstSdkStart;
        boolean enableLocation;

        public boolean isEnableLocation() {
            return enableLocation;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public boolean isDisabled() {
            return !enabled;
        }

        public boolean isOffline() {
            return offline;
        }

        public boolean isOnline() {
            return !offline;
        }

        public boolean isInBackground() {
            return background;
        }

        public boolean isInForeground() {
            return !background;
        }

        public boolean isInDelayedStart() {
            return delayStart;
        }

        public boolean isNotInDelayedStart() {
            return !delayStart;
        }

        public boolean itHasToUpdatePackages() {
            return updatePackages;
        }

        public boolean isFirstLaunch() {
            return firstLaunch;
        }

        public boolean isNotFirstLaunch() {
            return !firstLaunch;
        }

        public boolean hasSessionResponseNotBeenProcessed() {
            return !sessionResponseProcessed;
        }

        public boolean hasFirstSdkStartOcurred() {
            return firstSdkStart;
        }

        public boolean hasFirstSdkStartNotOcurred() {
            return !firstSdkStart;
        }
    }

    private ActivityHandler(AdTraceConfig adTraceConfig) {
        init(adTraceConfig);

        // init logger to be available everywhere
        logger = AdTraceFactory.getLogger();

        logger.lockLogLevel();

        executor = new SingleThreadCachedScheduler("ActivityHandler");
        internalState = new InternalState();

        // enabled by default
        internalState.enabled = adTraceConfig.startEnabled != null ? adTraceConfig.startEnabled : true;
        // online by default
        internalState.offline = adTraceConfig.startOffline;
        // in the background by default
        internalState.background = true;
        // delay start not configured by default
        internalState.delayStart = false;
        // does not need to update packages by default
        internalState.updatePackages = false;
        // does not have the session response by default
        internalState.sessionResponseProcessed = false;
        // does not have first start by default
        internalState.firstSdkStart = false;

        internalState.enableLocation = true;

        executor.submit(new Runnable() {
            @Override
            public void run() {
                initI();
            }
        });
    }

    @Override
    public AdTraceConfig getAdTraceConfig() {
        return adTraceConfig;
    }

    @Override
    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    @Override
    public ActivityState getActivityState() {
        return activityState;
    }

    @Override
    public SessionParameters getSessionParameters() {
        return sessionParameters;
    }

    @Override
    public void init(AdTraceConfig adTraceConfig) {
        this.adTraceConfig = adTraceConfig;
    }

    public static ActivityHandler getInstance(AdTraceConfig adTraceConfig) {
        if (adTraceConfig == null) {
            AdTraceFactory.getLogger().error("AdTraceConfig missing");
            return null;
        }

        if (!adTraceConfig.isValid()) {
            AdTraceFactory.getLogger().error("AdTraceConfig not initialized correctly");
            return null;
        }

        if (adTraceConfig.processName != null) {
            int currentPid = android.os.Process.myPid();
            ActivityManager manager = (ActivityManager) adTraceConfig.context.getSystemService(Context.ACTIVITY_SERVICE);

            if (manager == null) {
                return null;
            }

            for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
                if (processInfo.pid == currentPid) {
                    if (!processInfo.processName.equalsIgnoreCase(adTraceConfig.processName)) {
                        AdTraceFactory.getLogger().info("Skipping initialization in background process (%s)", processInfo.processName);
                        return null;
                    }
                    break;
                }
            }
        }

        ActivityHandler activityHandler = new ActivityHandler(adTraceConfig);
        return activityHandler;
    }

    @Override
    public void onResume() {
        internalState.background = false;

        executor.submit(new Runnable() {
            @Override
            public void run() {
                delayStartI();

                stopBackgroundTimerI();

                startForegroundTimerI();

                logger.verbose("Subsession start");

                startI();
            }
        });
    }

    @Override
    public void onPause() {
        internalState.background = true;

        executor.submit(new Runnable() {
            @Override
            public void run() {
                stopForegroundTimerI();

                startBackgroundTimerI();

                logger.verbose("Subsession end");

                endI();
            }
        });
    }

    @Override
    public void trackEvent(final AdTraceEvent event) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                if (internalState.hasFirstSdkStartNotOcurred()) {
                    logger.warn("Event tracked before first activity resumed.\n" +
                            "If it was triggered in the Application class, it might timestamp or even send an install long before the user opens the app.");
                    startI();
                }
                trackEventI(event);
            }
        });
    }

    @Override
    public void finishedTrackingActivity(ResponseData responseData) {
        // redirect session responses to attribution handler to check for attribution information
        if (responseData instanceof SessionResponseData) {
            attributionHandler.checkSessionResponse((SessionResponseData)responseData);
            return;
        }
        // redirect sdk click responses to attribution handler to check for attribution information
        if (responseData instanceof SdkClickResponseData) {
            checkForInstallReferrerInfo((SdkClickResponseData) responseData);
            attributionHandler.checkSdkClickResponse((SdkClickResponseData)responseData);
            return;
        }
        // check if it's an event response
        if (responseData instanceof EventResponseData) {
            launchEventResponseTasks((EventResponseData)responseData);
            return;
        }
    }

    @Override
    public void setEnabled(final boolean enabled) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                setEnabledI(enabled);
            }
        });
    }

    @Override
    public void setOfflineMode(final boolean offline) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                setOfflineModeI(offline);
            }
        });
    }

    @Override
    public void enableLocation(final boolean enabled) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                enableLocationI(enabled);
            }
        });
    }

    @Override
    public boolean isEnabled() {
        return isEnabledI();
    }

    private boolean isEnabledI() {
        if (activityState != null) {
            return activityState.enabled;
        } else {
            return internalState.isEnabled();
        }
    }

    @Override
    public void readOpenUrl(final Uri url, final long clickTime) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                readOpenUrlI(url, clickTime);
            }
        });
    }

    private void updateAdidI(final String adid) {
        if (adid == null) {
            return;
        }

        if (adid.equals(activityState.adid)) {
            return;
        }

        activityState.adid = adid;
        writeActivityStateI();
        return;
    }

    @Override
    public boolean updateAttributionI(AdTraceAttribution attribution) {
        if (attribution == null) {
            return false;
        }

        if (attribution.equals(this.attribution)) {
            return false;
        }

        this.attribution = attribution;
        writeAttributionI();
        return true;
    }

    @Override
    public void setAskingAttribution(final boolean askingAttribution) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                setAskingAttributionI(askingAttribution);
            }
        });
    }

    @Override
    public void sendReftagReferrer() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                sendReftagReferrerI();
            }
        });
    }

    @Override
    public void sendInstallReferrer(final String installReferrer, final long referrerClickTimestampSeconds, final long installBeginTimestampSeconds) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                sendInstallReferrerI(installReferrer, referrerClickTimestampSeconds, installBeginTimestampSeconds);
            }
        });
    }

    @Override
    public void launchEventResponseTasks(final EventResponseData eventResponseData) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                launchEventResponseTasksI(eventResponseData);
            }
        });
    }

    @Override
    public void launchSdkClickResponseTasks(final SdkClickResponseData sdkClickResponseData) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                launchSdkClickResponseTasksI(sdkClickResponseData);
            }
        });
    }

    @Override
    public void launchSessionResponseTasks(final SessionResponseData sessionResponseData) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                launchSessionResponseTasksI(sessionResponseData);
            }
        });
    }

    @Override
    public void launchAttributionResponseTasks(final AttributionResponseData attributionResponseData) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                launchAttributionResponseTasksI(attributionResponseData);
            }
        });
    }

    @Override
    public void sendFirstPackages () {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                sendFirstPackagesI();
            }
        });
    }

    @Override
    public void addSessionCallbackParameter(final String key, final String value) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                addSessionCallbackParameterI(key, value);
            }
        });
    }

    @Override
    public void addSessionPartnerParameter(final String key, final String value) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                addSessionPartnerParameterI(key, value);
            }
        });
    }

    @Override
    public void removeSessionCallbackParameter(final String key) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                removeSessionCallbackParameterI(key);
            }
        });
    }

    @Override
    public void removeSessionPartnerParameter(final String key) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                removeSessionPartnerParameterI(key);
            }
        });
    }

    @Override
    public void resetSessionCallbackParameters() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                resetSessionCallbackParametersI();
            }
        });
    }

    @Override
    public void resetSessionPartnerParameters() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                resetSessionPartnerParametersI();
            }
        });
    }

    @Override
    public void setPushToken(final String token, final boolean preSaved) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                if (!preSaved) {
                    SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
                    sharedPreferencesManager.savePushToken(token);
                }

                if (internalState.hasFirstSdkStartNotOcurred()) {
                    // No install has been tracked so far.
                    // Push token is saved, ready for the session package to pick it up.
                    return;
                } else {
                    setPushTokenI(token);
                }
            }
        });
    }

    @Override
    public void gdprForgetMe() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                gdprForgetMeI();
            }
        });
    }

    @Override
    public void gotOptOutResponse() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                gotOptOutResponseI();
            }
        });
    }

    @Override
    public Context getContext() {
        return adTraceConfig.context;
    }

    public void foregroundTimerFired() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                foregroundTimerFiredI();
            }
        });
    }

    public void backgroundTimerFired() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                backgroundTimerFiredI();
            }
        });
    }

    @Override
    public String getAdid() {
        if (activityState == null) {
            return null;
        }
        return activityState.adid;
    }

    @Override
    public AdTraceAttribution getAttribution() {
        return attribution;
    }

    @Override
    public String getBasePath() {
        return this.basePath;
    }

    @Override
    public String getGdprPath() {
        return this.gdprPath;
    }

    public InternalState getInternalState() {
        return internalState;
    }

    private void initI() {
        SESSION_INTERVAL = AdTraceFactory.getSessionInterval();
        SUBSESSION_INTERVAL = AdTraceFactory.getSubsessionInterval();
        // get timer values
        FOREGROUND_TIMER_INTERVAL = AdTraceFactory.getTimerInterval();
        FOREGROUND_TIMER_START = AdTraceFactory.getTimerStart();
        BACKGROUND_TIMER_INTERVAL = AdTraceFactory.getTimerInterval();

        // has to be read in the background
        readAttributionI(adTraceConfig.context);
        readActivityStateI(adTraceConfig.context);

        sessionParameters = new SessionParameters();
        readSessionCallbackParametersI(adTraceConfig.context);
        readSessionPartnerParametersI(adTraceConfig.context);

        if (adTraceConfig.startEnabled != null) {
            if (adTraceConfig.preLaunchActionsArray == null) {
                adTraceConfig.preLaunchActionsArray = new ArrayList<IRunActivityHandler>();
            }
            adTraceConfig.preLaunchActionsArray.add(new IRunActivityHandler() {
                @Override
                public void run(ActivityHandler activityHandler) {
                    activityHandler.setEnabledI(adTraceConfig.startEnabled);
                }
            });
        }

        if (internalState.hasFirstSdkStartOcurred()) {
            internalState.enabled = activityState.enabled;
            internalState.updatePackages = activityState.updatePackages;
            internalState.firstLaunch = false;
        } else {
            internalState.firstLaunch = true; // first launch if activity state is null
        }

        readConfigFile(adTraceConfig.context);

        deviceInfo = new DeviceInfo(adTraceConfig.context, adTraceConfig.sdkPrefix);

        if (adTraceConfig.eventBufferingEnabled) {
            logger.info("Event buffering is enabled");
        }

        deviceInfo.reloadPlayIds(adTraceConfig.context);
        if (deviceInfo.playAdId == null) {
            logger.warn("Unable to get Google Play Services Advertising ID at start time");
            if (deviceInfo.macSha1 == null &&
                    deviceInfo.macShortMd5 == null &&
                    deviceInfo.androidId == null)
            {
                logger.error("Unable to get any device id's. Please check if Proguard is correctly set with AdTrace SDK");
            }
        } else {
            logger.info("Google Play Services Advertising ID read correctly at start time");
        }

        if (adTraceConfig.defaultTracker != null) {
            logger.info("Default tracker: '%s'", adTraceConfig.defaultTracker);
        }

        if (adTraceConfig.pushToken != null) {
            logger.info("Push token: '%s'", adTraceConfig.pushToken);
            if (internalState.hasFirstSdkStartOcurred()) {
                // since sdk has already started, try to send current push token
                setPushToken(adTraceConfig.pushToken, false);
            } else {
                // since sdk has not yet started, save current push token for when it does
                SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
                sharedPreferencesManager.savePushToken(adTraceConfig.pushToken);
            }
        } else {
            // since sdk has already started, check if there is a saved push from previous runs
            if (internalState.hasFirstSdkStartOcurred()) {
                SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
                String savedPushToken = sharedPreferencesManager.getPushToken();

                setPushToken(savedPushToken, true);
            }
        }

        // GDPR
        if (internalState.hasFirstSdkStartOcurred()) {
            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
            if (sharedPreferencesManager.getGdprForgetMe()) {
                gdprForgetMe();
            }
        }

        foregroundTimer = new TimerCycle(
                new Runnable() {
                    @Override
                    public void run() {
                        foregroundTimerFired();
                    }
                }, FOREGROUND_TIMER_START, FOREGROUND_TIMER_INTERVAL, FOREGROUND_TIMER_NAME);

        // create background timer
        if (adTraceConfig.sendInBackground) {
            logger.info("Send in background configured");

            backgroundTimer = new TimerOnce(new Runnable() {
                @Override
                public void run() {
                    backgroundTimerFired();
                }
            }, BACKGROUND_TIMER_NAME);
        }

        // configure delay start timer
        if (internalState.hasFirstSdkStartNotOcurred() &&
                adTraceConfig.delayStart != null &&
                adTraceConfig.delayStart > 0.0)
        {
            logger.info("Delay start configured");
            internalState.delayStart = true;
            delayStartTimer = new TimerOnce(new Runnable() {
                @Override
                public void run() {
                    sendFirstPackages();
                }
            }, DELAY_START_TIMER_NAME);
        }

        UtilNetworking.setUserAgent(adTraceConfig.userAgent);

        this.basePath = adTraceConfig.basePath;
        this.gdprPath = adTraceConfig.gdprPath;

        packageHandler = AdTraceFactory.getPackageHandler(this, adTraceConfig.context, toSendI(false));

        attributionHandler = AdTraceFactory.getAttributionHandler(this, toSendI(false));

        sdkClickHandler = AdTraceFactory.getSdkClickHandler(this, toSendI(true));

        if (isToUpdatePackagesI()) {
            updatePackagesI();
        }

        installReferrer = new InstallReferrer(adTraceConfig.context, new InstallReferrerReadListener() {
            @Override
            public void onInstallReferrerRead(String installReferrer, long referrerClickTimestampSeconds, long installBeginTimestampSeconds) { sendInstallReferrer(installReferrer, referrerClickTimestampSeconds, installBeginTimestampSeconds);
            }
        });
        preLaunchActionsI(adTraceConfig.preLaunchActionsArray);
        sendReftagReferrerI();
    }

    private void readConfigFile(Context context) {
        Properties properties;

        try  {
            InputStream inputStream = context.getAssets().open("adtrace_config.properties");
            properties = new Properties();
            properties.load(inputStream);
        } catch (Exception e) {
            logger.debug("%s file not found in this app", e.getMessage());
            return;
        }

        logger.verbose("adtrace_config.properties file read and loaded");

        String defaultTracker = properties.getProperty("defaultTracker");

        if (defaultTracker != null) {
            adTraceConfig.defaultTracker = defaultTracker;
        }
    }

    private void preLaunchActionsI(List<IRunActivityHandler> preLaunchActionsArray) {
        if (preLaunchActionsArray == null) {
            return;
        }

        for (IRunActivityHandler preLaunchAction : preLaunchActionsArray) {
            preLaunchAction.run(this);
        }
    }

    private void startI() {
        // check if it's the first sdk start
        if (internalState.hasFirstSdkStartNotOcurred()) {
            startFirstSessionI();
            return;
        }

        // it shouldn't start if it was disabled after a first session
        if (!activityState.enabled) {
            return;
        }

        updateHandlersStatusAndSendI();

        processSessionI();

        checkAttributionStateI();

        processCachedDeeplinkI();
    }

    private void startFirstSessionI() {
        // still update handlers status
        updateHandlersStatusAndSendI();

        activityState = new ActivityState();
        internalState.firstSdkStart = true;

        long now = System.currentTimeMillis();

        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
        activityState.pushToken = sharedPreferencesManager.getPushToken();
        // activityState.isGdprForgotten = sharedPreferencesManager.getGdprForgetMe();

        // track the first session package only if it's enabled
        if (internalState.isEnabled()) {
            if (!sharedPreferencesManager.getGdprForgetMe()) {
                activityState.sessionCount = 1; // this is the first session
                transferSessionPackageI(now);
                checkAfterNewStartI(sharedPreferencesManager);
            } else {
                gdprForgetMeI();
            }
        }

        activityState.resetSessionAttributes(now);
        activityState.enabled = internalState.isEnabled();
        activityState.updatePackages = internalState.itHasToUpdatePackages();

        writeActivityStateI();
        sharedPreferencesManager.removePushToken();
        sharedPreferencesManager.removeGdprForgetMe();

        // check for cached deep links
        processCachedDeeplinkI();

        // don't check attribution right after first sdk start
    }

    private void processSessionI() {
        if (activityState.isGdprForgotten) {
            return;
        }

        long now = System.currentTimeMillis();

        long lastInterval = now - activityState.lastActivity;

        if (lastInterval < 0) {
            logger.error(TIME_TRAVEL);
            activityState.lastActivity = now;
            writeActivityStateI();
            return;
        }

        // new session
        if (lastInterval > SESSION_INTERVAL) {
            trackNewSessionI(now);
            checkAfterNewStartI();
            return;
        }

        // new subsession
        if (lastInterval > SUBSESSION_INTERVAL) {
            activityState.subsessionCount++;
            activityState.sessionLength += lastInterval;
            activityState.lastActivity = now;
            logger.verbose("Started subsession %d of session %d",
                    activityState.subsessionCount,
                    activityState.sessionCount);
            writeActivityStateI();

            // Try to check if there's new referrer information.
            installReferrer.startConnection();

            return;
        }

        logger.verbose("Time span since last activity too short for a new subsession");
    }

    private void trackNewSessionI(final long now) {
        long lastInterval = now - activityState.lastActivity;

        activityState.sessionCount++;
        activityState.lastInterval = lastInterval;

        transferSessionPackageI(now);
        activityState.resetSessionAttributes(now);
        writeActivityStateI();
    }

    private void checkAttributionStateI() {
        if (!checkActivityStateI(activityState)) { return; }

        // if it's the first launch
        if (internalState.isFirstLaunch()) {
            // and it hasn't received the session response
            if (internalState.hasSessionResponseNotBeenProcessed()) {
                return;
            }
        }

        // if there is already an attribution saved and there was no attribution being asked
        if (attribution != null && !activityState.askingAttribution) {
            return;
        }

        attributionHandler.getAttribution();
    }

    private void processCachedDeeplinkI() {
        if (!checkActivityStateI(activityState)) {
            return;
        }

        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
        String cachedDeeplinkUrl = sharedPreferencesManager.getDeeplinkUrl();
        long cachedDeeplinkClickTime = sharedPreferencesManager.getDeeplinkClickTime();

        if (cachedDeeplinkUrl == null) {
            return;
        }
        if (cachedDeeplinkClickTime == -1) {
            return;
        }

        readOpenUrl(Uri.parse(cachedDeeplinkUrl), cachedDeeplinkClickTime);

        sharedPreferencesManager.removeDeeplink();
    }

    private void endI() {
        // pause sending if it's not allowed to send
        if (!toSendI()) {
            pauseSendingI();
        }

        if (updateActivityStateI(System.currentTimeMillis())) {
            writeActivityStateI();
        }
    }

    private void trackEventI(AdTraceEvent event) {
        if (!checkActivityStateI(activityState)) return;
        if (!isEnabledI()) return;
        if (!checkEventI(event)) return;
        if (!checkOrderIdI(event.orderId)) return;
        if (activityState.isGdprForgotten) return;

        long now = System.currentTimeMillis();

        activityState.eventCount++;
        updateActivityStateI(now);

        PackageBuilder eventBuilder = new PackageBuilder(adTraceConfig, deviceInfo, activityState, sessionParameters, internalState, now);
        ActivityPackage eventPackage = eventBuilder.buildEventPackage(event, internalState.isInDelayedStart());
        packageHandler.addPackage(eventPackage);

        if (adTraceConfig.eventBufferingEnabled) {
            logger.info("Buffered event %s", eventPackage.getSuffix());
        } else {
            packageHandler.sendFirstPackage();
        }

        // if it is in the background and it can send, start the background timer
        if (adTraceConfig.sendInBackground && internalState.isInBackground()) {
            startBackgroundTimerI();
        }

        writeActivityStateI();
    }

    private void launchEventResponseTasksI(final EventResponseData eventResponseData) {
        // try to update adid from response
        updateAdidI(eventResponseData.adid);

        Handler handler = new Handler(adTraceConfig.context.getMainLooper());

        // success callback
        if (eventResponseData.success && adTraceConfig.onEventTrackingSucceededListener != null) {
            logger.debug("Launching success event tracking listener");
            // add it to the handler queue
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (adTraceConfig == null) {
                        return;
                    }
                    if (adTraceConfig.onEventTrackingSucceededListener == null) {
                        return;
                    }
                    adTraceConfig.onEventTrackingSucceededListener.onFinishedEventTrackingSucceeded(eventResponseData.getSuccessResponseData());
                }
            };
            handler.post(runnable);

            return;
        }
        // failure callback
        if (!eventResponseData.success && adTraceConfig.onEventTrackingFailedListener != null) {
            logger.debug("Launching failed event tracking listener");
            // add it to the handler queue
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (adTraceConfig == null) {
                        return;
                    }
                    if (adTraceConfig.onEventTrackingFailedListener == null) {
                        return;
                    }
                    adTraceConfig.onEventTrackingFailedListener.onFinishedEventTrackingFailed(eventResponseData.getFailureResponseData());
                }
            };
            handler.post(runnable);

            return;
        }
    }

    private void launchSdkClickResponseTasksI(SdkClickResponseData sdkClickResponseData) {
        // try to update adid from response
        updateAdidI(sdkClickResponseData.adid);

        // use the same handler to ensure that all tasks are executed sequentially
        Handler handler = new Handler(adTraceConfig.context.getMainLooper());

        // try to update the attribution
        boolean attributionUpdated = updateAttributionI(sdkClickResponseData.attribution);

        // if attribution changed, launch attribution changed delegate
        if (attributionUpdated) {
            launchAttributionListenerI(handler);
        }
    }

    private void launchSessionResponseTasksI(SessionResponseData sessionResponseData) {
        // try to update adid from response
        updateAdidI(sessionResponseData.adid);

        // use the same handler to ensure that all tasks are executed sequentially
        Handler handler = new Handler(adTraceConfig.context.getMainLooper());

        // try to update the attribution
        boolean attributionUpdated = updateAttributionI(sessionResponseData.attribution);

        // if attribution changed, launch attribution changed delegate
        if (attributionUpdated) {
            launchAttributionListenerI(handler);
        }

        // if attribution didn't update and it's still null
        // ask for attribution
        if (this.attribution == null && activityState.askingAttribution == false) {
            this.attributionHandler.getAttribution();
        }

        // mark install as tracked on success
        if (sessionResponseData.success) {
            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
            sharedPreferencesManager.setInstallTracked();
        }

        // launch Session tracking listener if available
        launchSessionResponseListenerI(sessionResponseData, handler);

        // mark session response has proccessed
        internalState.sessionResponseProcessed = true;
    }

    private void launchSessionResponseListenerI(final SessionResponseData sessionResponseData, Handler handler) {
        // success callback
        if (sessionResponseData.success && adTraceConfig.onSessionTrackingSucceededListener != null) {
            logger.debug("Launching success session tracking listener");
            // add it to the handler queue
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (adTraceConfig == null) {
                        return;
                    }
                    if (adTraceConfig.onSessionTrackingSucceededListener == null) {
                        return;
                    }
                    adTraceConfig.onSessionTrackingSucceededListener.onFinishedSessionTrackingSucceeded(sessionResponseData.getSuccessResponseData());
                }
            };
            handler.post(runnable);

            return;
        }
        // failure callback
        if (!sessionResponseData.success && adTraceConfig.onSessionTrackingFailedListener != null) {
            logger.debug("Launching failed session tracking listener");
            // add it to the handler queue
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (adTraceConfig == null) {
                        return;
                    }
                    if (adTraceConfig.onSessionTrackingFailedListener == null) {
                        return;
                    }
                    adTraceConfig.onSessionTrackingFailedListener.onFinishedSessionTrackingFailed(sessionResponseData.getFailureResponseData());
                }
            };
            handler.post(runnable);

            return;
        }
    }

    private void launchAttributionResponseTasksI(AttributionResponseData attributionResponseData) {
        // try to update adid from response
        updateAdidI(attributionResponseData.adid);

        Handler handler = new Handler(adTraceConfig.context.getMainLooper());

        // try to update the attribution
        boolean attributionUpdated = updateAttributionI(attributionResponseData.attribution);

        // if attribution changed, launch attribution changed delegate
        if (attributionUpdated) {
            launchAttributionListenerI(handler);
        }

        // if there is any, try to launch the deeplink
        prepareDeeplinkI(attributionResponseData.deeplink, handler);
    }

    private void launchAttributionListenerI(Handler handler) {
        if (adTraceConfig.onAttributionChangedListener == null) {
            return;
        }
        // add it to the handler queue
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (adTraceConfig == null) {
                    return;
                }
                if (adTraceConfig.onAttributionChangedListener == null) {
                    return;
                }
                adTraceConfig.onAttributionChangedListener.onAttributionChanged(attribution);
            }
        };
        handler.post(runnable);
    }

    private void prepareDeeplinkI(final Uri deeplink, final Handler handler) {
        if (deeplink == null) {
            return;
        }

        logger.info("Deferred deeplink received (%s)", deeplink);

        final Intent deeplinkIntent = createDeeplinkIntentI(deeplink);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (adTraceConfig == null) {
                    return;
                }
                boolean toLaunchDeeplink = true;
                if (adTraceConfig.onDeeplinkResponseListener != null) {
                    toLaunchDeeplink = adTraceConfig.onDeeplinkResponseListener.launchReceivedDeeplink(deeplink);
                }
                if (toLaunchDeeplink) {
                    launchDeeplinkMain(deeplinkIntent, deeplink);
                }
            }
        };
        handler.post(runnable);
    }

    private Intent createDeeplinkIntentI(Uri deeplink) {
        Intent mapIntent;
        if (adTraceConfig.deepLinkComponent == null) {
            mapIntent = new Intent(Intent.ACTION_VIEW, deeplink);
        } else {
            mapIntent = new Intent(Intent.ACTION_VIEW, deeplink, adTraceConfig.context, adTraceConfig.deepLinkComponent);
        }
        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mapIntent.setPackage(adTraceConfig.context.getPackageName());

        return mapIntent;
    }

    private void launchDeeplinkMain(Intent deeplinkIntent, Uri deeplink) {
        // Verify it resolves
        PackageManager packageManager = adTraceConfig.context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(deeplinkIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (!isIntentSafe) {
            logger.error("Unable to open deferred deep link (%s)", deeplink);
            return;
        }

        // add it to the handler queue
        logger.info("Open deferred deep link (%s)", deeplink);
        adTraceConfig.context.startActivity(deeplinkIntent);
    }

    private void setEnabledI(boolean enabled) {
        // compare with the saved or internal state
        if (!hasChangedStateI(this.isEnabledI(), enabled,
                "AdTrace already enabled", "AdTrace already disabled")) {
            return;
        }

        if (enabled) {
            if (activityState.isGdprForgotten) {
                logger.error("Re-enabling SDK not possible for forgotten user");
                return;
            }
        }

        // save new enabled state in internal state
        internalState.enabled = enabled;

        if (internalState.hasFirstSdkStartNotOcurred()) {
            updateStatusI(!enabled,
                    "Handlers will start as paused due to the SDK being disabled",
                    "Handlers will still start as paused",
                    "Handlers will start as active due to the SDK being enabled");
            return;
        }

        activityState.enabled = enabled;
        writeActivityStateI();

        if (enabled) {
            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());

            if (sharedPreferencesManager.getGdprForgetMe()) {
                gdprForgetMeI();
            }

            // check if install was tracked
            if (!sharedPreferencesManager.getInstallTracked()) {
                long now = System.currentTimeMillis();
                trackNewSessionI(now);
            }
            checkAfterNewStartI(sharedPreferencesManager);
        }

        updateStatusI(!enabled,
                "Pausing handlers due to SDK being disabled",
                "Handlers remain paused",
                "Resuming handlers due to SDK being enabled");
    }


    private void checkAfterNewStartI() {
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
        checkAfterNewStartI(sharedPreferencesManager);
    }

    private void checkAfterNewStartI(SharedPreferencesManager sharedPreferencesManager) {
        // check if there is a saved push token to send
        String pushToken = sharedPreferencesManager.getPushToken();

        if (pushToken != null && !pushToken.equals(activityState.pushToken)) {
            // queue set push token
            setPushToken(pushToken, true);
        }

        // check if there are token to send
        Object referrers = sharedPreferencesManager.getRawReferrerArray();
        if (referrers != null) {
            // queue send referrer tag
            sendReftagReferrer();
        }

        // try to read and send the install referrer
        installReferrer.startConnection();
    }

    private void setOfflineModeI(boolean offline) {
        // compare with the internal state
        if (!hasChangedStateI(internalState.isOffline(), offline,
                "AdTrace already in offline mode",
                "AdTrace already in online mode")) {
            return;
        }

        internalState.offline = offline;

        if (internalState.hasFirstSdkStartNotOcurred()) {
            updateStatusI(offline,
                    "Handlers will start paused due to SDK being offline",
                    "Handlers will still start as paused",
                    "Handlers will start as active due to SDK being online");
            return;
        }

        updateStatusI(offline,
                "Pausing handlers to put SDK offline mode",
                "Handlers remain paused",
                "Resuming handlers to put SDK in online mode");

    }

    private void enableLocationI(boolean enabled) {
        // compare with the internal state
        if (!hasChangedStateI(internalState.isEnableLocation(), enabled,
                "AdTrace already in offline mode",
                "AdTrace already in online mode")) {
            return;
        }

        internalState.enableLocation = enabled;

        if (internalState.hasFirstSdkStartNotOcurred()) {
            updateStatusI(enabled,
                    "Handlers will start paused due to SDK being offline",
                    "Handlers will still start as paused",
                    "Handlers will start as active due to SDK being online");
            return;
        }

        updateStatusI(enabled,
                "Pausing handlers to put SDK offline mode",
                "Handlers remain paused",
                "Resuming handlers to put SDK in online mode");

    }

    private boolean hasChangedStateI(boolean previousState, boolean newState,
                                     String trueMessage, String falseMessage)
    {
        if (previousState != newState) {
            return true;
        }

        if (previousState) {
            logger.debug(trueMessage);
        } else {
            logger.debug(falseMessage);
        }

        return false;
    }

    private void updateStatusI(boolean pausingState, String pausingMessage,
                               String remainsPausedMessage, String unPausingMessage)
    {
        // it is changing from an active state to a pause state
        if (pausingState) {
            logger.info(pausingMessage);
        }
        // check if it's remaining in a pause state
        else if (pausedI(false)) {
            // including the sdk click handler
            if (pausedI(true)) {
                logger.info(remainsPausedMessage);
            } else {
                logger.info(remainsPausedMessage + ", except the Sdk Click Handler");
            }
        } else {
            // it is changing from a pause state to an active state
            logger.info(unPausingMessage);
        }

        updateHandlersStatusAndSendI();
    }

    private void setAskingAttributionI(boolean askingAttribution) {
        activityState.askingAttribution = askingAttribution;

        writeActivityStateI();
    }

    private void sendReftagReferrerI() {
        if (!isEnabledI()) {
            return;
        }
        if (internalState.hasFirstSdkStartNotOcurred()) {
            return;
        }

        sdkClickHandler.sendReftagReferrers();
    }

    private void sendInstallReferrerI(String installReferrer, long referrerClickTimestampSeconds, long installBeginTimestampSeconds) {
        if (!isEnabledI()) {
            return;
        }

        if (installReferrer == null) {
            return;
        }

        if (referrerClickTimestampSeconds == activityState.clickTime
                && installBeginTimestampSeconds == activityState.installBegin
                && installReferrer.equals(activityState.installReferrer)) {
            // Same click already sent before, nothing to be done.
            return;
        }

        // Create sdk click
        ActivityPackage sdkClickPackage = PackageFactory.buildInstallReferrerSdkClickPackage(
                installReferrer,
                referrerClickTimestampSeconds,
                installBeginTimestampSeconds,
                activityState,
                adTraceConfig,
                deviceInfo,
                internalState,
                sessionParameters);

        sdkClickHandler.sendSdkClick(sdkClickPackage);
    }

    private void readOpenUrlI(Uri url, long clickTime) {
        if (!isEnabledI()) {
            return;
        }

        if (Util.isUrlFilteredOut(url)) {
            logger.debug("Deep link (" + url.toString() + ") processing skipped");
            return;
        }

        ActivityPackage sdkClickPackage = PackageFactory.buildDeeplinkSdkClickPackage(
                url,
                clickTime,
                activityState,
                adTraceConfig,
                deviceInfo,
                internalState,
                sessionParameters);

        if (sdkClickPackage == null) {
            return;
        }

        sdkClickHandler.sendSdkClick(sdkClickPackage);
    }

    private void updateHandlersStatusAndSendI() {
        // check if it should stop sending
        if (!toSendI()) {
            pauseSendingI();
            return;
        }

        resumeSendingI();

        // if event buffering is not enabled
        if (!adTraceConfig.eventBufferingEnabled ||
                // or if it's the first launch and it hasn't received the session response
                (internalState.isFirstLaunch() && internalState.hasSessionResponseNotBeenProcessed()))
        {
            // try to send
            packageHandler.sendFirstPackage();
        }
    }

    private void pauseSendingI() {
        attributionHandler.pauseSending();
        packageHandler.pauseSending();
        // the conditions to pause the sdk click handler are less restrictive
        // it's possible for the sdk click handler to be active while others are paused
        if (!toSendI(true)) {
            sdkClickHandler.pauseSending();
        } else {
            sdkClickHandler.resumeSending();
        }
    }

    private void resumeSendingI() {
        attributionHandler.resumeSending();
        packageHandler.resumeSending();
        sdkClickHandler.resumeSending();
    }

    private boolean updateActivityStateI(long now) {
        if (!checkActivityStateI(activityState)) { return false; }

        long lastInterval = now - activityState.lastActivity;

        // ignore late updates
        if (lastInterval > SESSION_INTERVAL) {
            return false;
        }
        activityState.lastActivity = now;

        if (lastInterval < 0) {
            logger.error(TIME_TRAVEL);
        } else {
            activityState.sessionLength += lastInterval;
            activityState.timeSpent += lastInterval;
        }
        return true;
    }

    public static boolean deleteActivityState(Context context) {
        return context.deleteFile(ACTIVITY_STATE_FILENAME);
    }

    public static boolean deleteAttribution(Context context) {
        return context.deleteFile(ATTRIBUTION_FILENAME);
    }

    public static boolean deleteSessionCallbackParameters(Context context) {
        return context.deleteFile(SESSION_CALLBACK_PARAMETERS_FILENAME);
    }

    public static boolean deleteSessionPartnerParameters(Context context) {
        return context.deleteFile(SESSION_PARTNER_PARAMETERS_FILENAME);
    }

    private void transferSessionPackageI(long now) {
        PackageBuilder builder = new PackageBuilder(adTraceConfig, deviceInfo, activityState,
                sessionParameters, internalState, now);
        ActivityPackage sessionPackage = builder.buildSessionPackage(internalState.isInDelayedStart());
        packageHandler.addPackage(sessionPackage);
        packageHandler.sendFirstPackage();
    }

    private void startForegroundTimerI() {
        // don't start the timer if it's disabled
        if (!isEnabledI()) {
            return;
        }

        foregroundTimer.start();
    }

    private void stopForegroundTimerI() {
        foregroundTimer.suspend();
    }

    private void foregroundTimerFiredI() {
        // stop the timer cycle if it's disabled
        if (!isEnabledI()) {
            stopForegroundTimerI();
            return;
        }

        if (toSendI()) {
            packageHandler.sendFirstPackage();
        }

        if (updateActivityStateI(System.currentTimeMillis())) {
            writeActivityStateI();
        }
    }

    private void startBackgroundTimerI() {
        if (backgroundTimer == null) {
            return;
        }

        // check if it can send in the background
        if (!toSendI()) {
            return;
        }

        // background timer already started
        if (backgroundTimer.getFireIn() > 0) {
            return;
        }

        backgroundTimer.startIn(BACKGROUND_TIMER_INTERVAL);
    }

    private void stopBackgroundTimerI() {
        if (backgroundTimer == null) {
            return;
        }

        backgroundTimer.cancel();
    }

    private void backgroundTimerFiredI() {
        if (toSendI()) {
            packageHandler.sendFirstPackage();
        }
    }

    private void delayStartI() {
        // it's not configured to start delayed or already finished
        if (internalState.isNotInDelayedStart()) {
            return;
        }

        // the delay has already started
        if (isToUpdatePackagesI()) {
            return;
        }

        // check against max start delay
        double delayStartSeconds = adTraceConfig.delayStart != null ? adTraceConfig.delayStart : 0.0;
        long maxDelayStartMilli = AdTraceFactory.getMaxDelayStart();

        long delayStartMilli = (long)(delayStartSeconds * 1000);
        if (delayStartMilli > maxDelayStartMilli) {
            double maxDelayStartSeconds = maxDelayStartMilli / 1000;
            String delayStartFormatted = Util.SecondsDisplayFormat.format(delayStartSeconds);
            String maxDelayStartFormatted = Util.SecondsDisplayFormat.format(maxDelayStartSeconds);

            logger.warn("Delay start of %s seconds bigger than max allowed value of %s seconds", delayStartFormatted, maxDelayStartFormatted);
            delayStartMilli = maxDelayStartMilli;
            delayStartSeconds = maxDelayStartSeconds;
        }

        String delayStartFormatted = Util.SecondsDisplayFormat.format(delayStartSeconds);
        logger.info("Waiting %s seconds before starting first session", delayStartFormatted);

        delayStartTimer.startIn(delayStartMilli);

        internalState.updatePackages = true;

        if (activityState != null) {
            activityState.updatePackages = true;
            writeActivityStateI();
        }
    }

    private void sendFirstPackagesI() {
        if (internalState.isNotInDelayedStart()) {
            logger.info("Start delay expired or never configured");
            return;
        }

        // update packages in queue
        updatePackagesI();
        // no longer is in delay start
        internalState.delayStart = false;
        // cancel possible still running timer if it was called by user
        delayStartTimer.cancel();
        // and release timer
        delayStartTimer = null;
        // update the status and try to send first package
        updateHandlersStatusAndSendI();
    }

    private void updatePackagesI() {
        // update activity packages
        packageHandler.updatePackages(sessionParameters);
        // no longer needs to update packages
        internalState.updatePackages = false;
        if (activityState != null) {
            activityState.updatePackages = false;
            writeActivityStateI();
        }
    }

    private boolean isToUpdatePackagesI() {
        if (activityState != null) {
            return activityState.updatePackages;
        } else {
            return internalState.itHasToUpdatePackages();
        }
    }

    public void addSessionCallbackParameterI(String key, String value) {
        if (!Util.isValidParameter(key, "key", "Session Callback")) return;
        if (!Util.isValidParameter(value, "value", "Session Callback")) return;

        if (sessionParameters.callbackParameters == null) {
            sessionParameters.callbackParameters = new LinkedHashMap<String, String>();
        }

        String oldValue = sessionParameters.callbackParameters.get(key);

        if (value.equals(oldValue)) {
            logger.verbose("Key %s already present with the same value", key);
            return;
        }

        if (oldValue != null) {
            logger.warn("Key %s will be overwritten", key);
        }

        sessionParameters.callbackParameters.put(key, value);

        writeSessionCallbackParametersI();
    }

    public void addSessionPartnerParameterI(String key, String value) {
        if (!Util.isValidParameter(key, "key", "Session Partner")) return;
        if (!Util.isValidParameter(value, "value", "Session Partner")) return;

        if (sessionParameters.partnerParameters == null) {
            sessionParameters.partnerParameters = new LinkedHashMap<String, String>();
        }

        String oldValue = sessionParameters.partnerParameters.get(key);

        if (value.equals(oldValue)) {
            logger.verbose("Key %s already present with the same value", key);
            return;
        }

        if (oldValue != null) {
            logger.warn("Key %s will be overwritten", key);
        }

        sessionParameters.partnerParameters.put(key, value);

        writeSessionPartnerParametersI();
    }

    public void removeSessionCallbackParameterI(String key) {
        if (!Util.isValidParameter(key, "key", "Session Callback")) return;

        if (sessionParameters.callbackParameters == null) {
            logger.warn("Session Callback parameters are not set");
            return;
        }

        String oldValue = sessionParameters.callbackParameters.remove(key);

        if (oldValue == null) {
            logger.warn("Key %s does not exist", key);
            return;
        }

        logger.debug("Key %s will be removed", key);

        writeSessionCallbackParametersI();
    }

    public void removeSessionPartnerParameterI(String key) {
        if (!Util.isValidParameter(key, "key", "Session Partner")) return;

        if (sessionParameters.partnerParameters == null) {
            logger.warn("Session Partner parameters are not set");
            return;
        }

        String oldValue = sessionParameters.partnerParameters.remove(key);

        if (oldValue == null) {
            logger.warn("Key %s does not exist", key);
            return;
        }

        logger.debug("Key %s will be removed", key);

        writeSessionPartnerParametersI();
    }

    public void resetSessionCallbackParametersI() {
        if (sessionParameters.callbackParameters == null) {
            logger.warn("Session Callback parameters are not set");
        }

        sessionParameters.callbackParameters = null;

        writeSessionCallbackParametersI();
    }

    public void resetSessionPartnerParametersI() {
        if (sessionParameters.partnerParameters == null) {
            logger.warn("Session Partner parameters are not set");
        }

        sessionParameters.partnerParameters = null;

        writeSessionPartnerParametersI();
    }

    private void setPushTokenI(String token) {
        if (!checkActivityStateI(activityState)) { return; }
        if (!isEnabledI()) { return; }
        if (activityState.isGdprForgotten) { return; }

        if (token == null) { return; }
        if (token.equals(activityState.pushToken)) { return; }

        // save new push token
        activityState.pushToken = token;
        writeActivityStateI();

        long now = System.currentTimeMillis();
        PackageBuilder infoPackageBuilder = new PackageBuilder(adTraceConfig, deviceInfo, activityState, sessionParameters, internalState, now);

        ActivityPackage infoPackage = infoPackageBuilder.buildInfoPackage(Constants.PUSH);
        packageHandler.addPackage(infoPackage);

        // If push token was cached, remove it.
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
        sharedPreferencesManager.removePushToken();

        if (adTraceConfig.eventBufferingEnabled) {
            logger.info("Buffered event %s", infoPackage.getSuffix());
        } else {
            packageHandler.sendFirstPackage();
        }
    }

    private void gdprForgetMeI() {
        if (!checkActivityStateI(activityState)) { return; }
        if (!isEnabledI()) { return; }
        if (activityState.isGdprForgotten) { return; }

        activityState.isGdprForgotten = true;
        writeActivityStateI();

        long now = System.currentTimeMillis();
        PackageBuilder gdprPackageBuilder = new PackageBuilder(adTraceConfig, deviceInfo, activityState, sessionParameters, internalState, now);

        ActivityPackage gdprPackage = gdprPackageBuilder.buildGdprPackage();
        packageHandler.addPackage(gdprPackage);

        // If GDPR choice was cached, remove it.
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
        sharedPreferencesManager.removeGdprForgetMe();

        if (adTraceConfig.eventBufferingEnabled) {
            logger.info("Buffered event %s", gdprPackage.getSuffix());
        } else {
            packageHandler.sendFirstPackage();
        }
    }

    private void gotOptOutResponseI() {
        activityState.isGdprForgotten = true;
        writeActivityStateI();

        packageHandler.flush();
        setEnabledI(false);
    }

    private void readActivityStateI(Context context) {
        try {
            activityState = Util.readObject(context, ACTIVITY_STATE_FILENAME, ACTIVITY_STATE_NAME, ActivityState.class);
        } catch (Exception e) {
            logger.error("Failed to read %s file (%s)", ACTIVITY_STATE_NAME, e.getMessage());
            activityState = null;
        }
        if (activityState != null) {
            internalState.firstSdkStart = true;
        }
    }

    private void readAttributionI(Context context) {
        try {
            attribution = Util.readObject(context, ATTRIBUTION_FILENAME, ATTRIBUTION_NAME, AdTraceAttribution.class);
        } catch (Exception e) {
            logger.error("Failed to read %s file (%s)", ATTRIBUTION_NAME, e.getMessage());
            attribution = null;
        }
    }

    private void readSessionCallbackParametersI(Context context) {
        try {
            sessionParameters.callbackParameters = Util.readObject(context,
                    SESSION_CALLBACK_PARAMETERS_FILENAME,
                    SESSION_CALLBACK_PARAMETERS_NAME,
                    (Class<Map<String,String>>)(Class)Map.class);
        } catch (Exception e) {
            logger.error("Failed to read %s file (%s)", SESSION_CALLBACK_PARAMETERS_NAME, e.getMessage());
            sessionParameters.callbackParameters = null;
        }
    }

    private void readSessionPartnerParametersI(Context context) {
        try {
            sessionParameters.partnerParameters = Util.readObject(context,
                    SESSION_PARTNER_PARAMETERS_FILENAME,
                    SESSION_PARTNER_PARAMETERS_NAME,
                    (Class<Map<String,String>>)(Class)Map.class);
        } catch (Exception e) {
            logger.error("Failed to read %s file (%s)", SESSION_PARTNER_PARAMETERS_NAME, e.getMessage());
            sessionParameters.partnerParameters = null;
        }
    }

    private void writeActivityStateI() {
        synchronized (ActivityState.class) {
            if (activityState == null) {
                return;
            }
            Util.writeObject(activityState, adTraceConfig.context, ACTIVITY_STATE_FILENAME, ACTIVITY_STATE_NAME);
        }
    }

    private void teardownActivityStateS() {
        synchronized (ActivityState.class) {
            if (activityState == null) {
                return;
            }
            activityState = null;
        }
    }

    private void writeAttributionI() {
        synchronized (AdTraceAttribution.class) {
            if (attribution == null) {
                return;
            }
            Util.writeObject(attribution, adTraceConfig.context, ATTRIBUTION_FILENAME, ATTRIBUTION_NAME);
        }
    }

    private void teardownAttributionS() {
        synchronized (AdTraceAttribution.class) {
            if (attribution == null) {
                return;
            }
            attribution = null;
        }
    }

    private void writeSessionCallbackParametersI() {
        synchronized (SessionParameters.class) {
            if (sessionParameters == null) {
                return;
            }
            Util.writeObject(sessionParameters.callbackParameters, adTraceConfig.context, SESSION_CALLBACK_PARAMETERS_FILENAME, SESSION_CALLBACK_PARAMETERS_NAME);
        }
    }

    private void writeSessionPartnerParametersI() {
        synchronized (SessionParameters.class) {
            if (sessionParameters == null) {
                return;
            }
            Util.writeObject(sessionParameters.partnerParameters, adTraceConfig.context, SESSION_PARTNER_PARAMETERS_FILENAME, SESSION_PARTNER_PARAMETERS_NAME);
        }
    }

    private void teardownAllSessionParametersS() {
        synchronized (SessionParameters.class) {
            if (sessionParameters == null) {
                return;
            }
            sessionParameters = null;
        }
    }

    private boolean checkEventI(AdTraceEvent event) {
        if (event == null) {
            logger.error("Event missing");
            return false;
        }

        if (!event.isValid()) {
            logger.error("Event not initialized correctly");
            return false;
        }

        return true;
    }

    private boolean checkOrderIdI(String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            return true;  // no order ID given
        }

        if (activityState.findOrderId(orderId)) {
            logger.info("Skipping duplicated order ID '%s'", orderId);
            return false; // order ID found -> used already
        }

        activityState.addOrderId(orderId);
        logger.verbose("Added order ID '%s'", orderId);
        // activity state will get written by caller
        return true;
    }

    private boolean checkActivityStateI(ActivityState activityState) {
        if (internalState.hasFirstSdkStartNotOcurred()) {
            logger.error("Sdk did not yet start");
            return false;
        }
        return true;
    }

    private boolean pausedI() {
        return pausedI(false);
    }

    private boolean pausedI(boolean sdkClickHandlerOnly) {
        if (sdkClickHandlerOnly) {
            // sdk click handler is paused if either:
            return internalState.isOffline() ||     // it's offline
                    !isEnabledI();                  // is disabled
        }
        // other handlers are paused if either:
        return internalState.isOffline()    ||      // it's offline
                !isEnabledI()               ||      // is disabled
                internalState.isInDelayedStart();   // is in delayed start
    }

    private boolean toSendI() {
        return toSendI(false);
    }

    private boolean toSendI(boolean sdkClickHandlerOnly) {
        // don't send when it's paused
        if (pausedI(sdkClickHandlerOnly)) {
            return false;
        }

        // has the option to send in the background -> is to send
        if (adTraceConfig.sendInBackground) {
            return true;
        }

        // doesn't have the option -> depends on being on the background/foreground
        return internalState.isInForeground();
    }

    private void checkForInstallReferrerInfo(final SdkClickResponseData responseData) {
        if (!responseData.isInstallReferrer) {
            return;
        }

        activityState.clickTime = responseData.clickTime;
        activityState.installBegin = responseData.installBegin;
        activityState.installReferrer = responseData.installReferrer;

        writeActivityStateI();
    }
}
