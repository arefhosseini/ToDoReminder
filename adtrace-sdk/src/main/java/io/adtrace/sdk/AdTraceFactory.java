package io.adtrace.sdk;

import android.content.Context;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public class AdTraceFactory {
    private static IPackageHandler packageHandler = null;
    private static IRequestHandler requestHandler = null;
    private static IAttributionHandler attributionHandler = null;
    private static IActivityHandler activityHandler = null;
    private static ILogger logger = null;
    private static HttpsURLConnection httpsURLConnection = null;
    private static ISdkClickHandler sdkClickHandler = null;

    private static long timerInterval = -1;
    private static long timerStart = -1;
    private static long sessionInterval = -1;
    private static long subsessionInterval = -1;
    private static BackoffStrategy sdkClickBackoffStrategy = null;
    private static BackoffStrategy packageHandlerBackoffStrategy = null;
    private static long maxDelayStart = -1;
    private static String baseUrl = Constants.BASE_URL;
    private static String gdprUrl = Constants.GDPR_URL;
    private static UtilNetworking.IConnectionOptions connectionOptions = null;
    private static boolean tryInstallReferrer = true;

    public static class URLGetConnection {
        HttpsURLConnection httpsURLConnection;
        URL url;
        URLGetConnection(HttpsURLConnection httpsURLConnection, URL url) {
            this.httpsURLConnection = httpsURLConnection;
            this.url = url;
        }
    }

    public static IPackageHandler getPackageHandler(IActivityHandler activityHandler,
                                                    Context context,
                                                    boolean startsSending) {
        if (packageHandler == null) {
            return new PackageHandler(activityHandler, context, startsSending);
        }
        packageHandler.init(activityHandler, context, startsSending);
        return packageHandler;
    }

    public static IRequestHandler getRequestHandler(IActivityHandler activityHandler,
                                                    IPackageHandler packageHandler) {
        if (requestHandler == null) {
            return new RequestHandler(activityHandler, packageHandler);
        }
        requestHandler.init(activityHandler, packageHandler);
        return requestHandler;
    }

    public static ILogger getLogger() {
        if (logger == null) {
            // Logger needs to be "static" to retain the configuration throughout the app
            logger = new Logger();
        }
        return logger;
    }

    public static long getTimerInterval() {
        if (timerInterval == -1) {
            return Constants.ONE_MINUTE;
        }
        return timerInterval;
    }

    public static long getTimerStart() {
        if (timerStart == -1) {
            return Constants.ONE_MINUTE;
        }
        return timerStart;
    }

    public static long getSessionInterval() {
        if (sessionInterval == -1) {
            return Constants.THIRTY_MINUTES;
        }
        return sessionInterval;
    }

    public static long getSubsessionInterval() {
        if (subsessionInterval == -1) {
            return Constants.ONE_SECOND;
        }
        return subsessionInterval;
    }

    public static BackoffStrategy getSdkClickBackoffStrategy() {
        if (sdkClickBackoffStrategy == null) {
            return BackoffStrategy.SHORT_WAIT;
        }
        return sdkClickBackoffStrategy;
    }

    public static BackoffStrategy getPackageHandlerBackoffStrategy() {
        if (packageHandlerBackoffStrategy == null) {
            return BackoffStrategy.LONG_WAIT;
        }
        return packageHandlerBackoffStrategy;
    }

    public static IActivityHandler getActivityHandler(AdTraceConfig config) {
        if (activityHandler == null) {
            return ActivityHandler.getInstance(config);
        }
        activityHandler.init(config);
        return activityHandler;
    }

    public static IAttributionHandler getAttributionHandler(IActivityHandler activityHandler,
                                                            boolean startsSending) {
        if (attributionHandler == null) {
            return new AttributionHandler(activityHandler, startsSending);
        }
        attributionHandler.init(activityHandler, startsSending);
        return attributionHandler;
    }

    public static HttpsURLConnection getHttpsURLConnection(URL url) throws IOException {
        if (AdTraceFactory.httpsURLConnection == null) {
            return (HttpsURLConnection)url.openConnection();
        }

        return AdTraceFactory.httpsURLConnection;
    }

    public static ISdkClickHandler getSdkClickHandler(IActivityHandler activityHandler, boolean startsSending) {
        if (sdkClickHandler == null) {
            return new SdkClickHandler(activityHandler, startsSending);
        }

        sdkClickHandler.init(activityHandler, startsSending);
        return sdkClickHandler;
    }

    public static long getMaxDelayStart() {
        if (maxDelayStart == -1) {
            return Constants.ONE_SECOND * 10; // 10 seconds
        }
        return maxDelayStart;
    }

    public static String getBaseUrl() {
        if (AdTraceFactory.baseUrl == null) {
            return Constants.BASE_URL;
        }
        return AdTraceFactory.baseUrl;
    }

    public static String getGdprUrl() {
        if (AdTraceFactory.gdprUrl == null) {
            return Constants.GDPR_URL;
        }
        return AdTraceFactory.gdprUrl;
    }

    public static UtilNetworking.IConnectionOptions getConnectionOptions() {
        if (connectionOptions == null) {
            return new UtilNetworking.ConnectionOptions();
        }
        return connectionOptions;
    }

    public static boolean getTryInstallReferrer() {
        return tryInstallReferrer;
    }

    public static void setPackageHandler(IPackageHandler packageHandler) {
        AdTraceFactory.packageHandler = packageHandler;
    }

    public static void setRequestHandler(IRequestHandler requestHandler) {
        AdTraceFactory.requestHandler = requestHandler;
    }

    public static void setLogger(ILogger logger) {
        AdTraceFactory.logger = logger;
    }

    public static void setTimerInterval(long timerInterval) {
        AdTraceFactory.timerInterval = timerInterval;
    }

    public static void setTimerStart(long timerStart) {
        AdTraceFactory.timerStart = timerStart;
    }

    public static void setSessionInterval(long sessionInterval) {
        AdTraceFactory.sessionInterval = sessionInterval;
    }

    public static void setSubsessionInterval(long subsessionInterval) {
        AdTraceFactory.subsessionInterval = subsessionInterval;
    }

    public static void setSdkClickBackoffStrategy(BackoffStrategy sdkClickBackoffStrategy) {
        AdTraceFactory.sdkClickBackoffStrategy = sdkClickBackoffStrategy;
    }

    public static void setPackageHandlerBackoffStrategy(BackoffStrategy packageHandlerBackoffStrategy) {
        AdTraceFactory.packageHandlerBackoffStrategy = packageHandlerBackoffStrategy;
    }

    public static void setActivityHandler(IActivityHandler activityHandler) {
        AdTraceFactory.activityHandler = activityHandler;
    }

    public static void setAttributionHandler(IAttributionHandler attributionHandler) {
        AdTraceFactory.attributionHandler = attributionHandler;
    }

    public static void setHttpsURLConnection(HttpsURLConnection httpsURLConnection) {
        AdTraceFactory.httpsURLConnection = httpsURLConnection;
    }

    public static void setSdkClickHandler(ISdkClickHandler sdkClickHandler) {
        AdTraceFactory.sdkClickHandler = sdkClickHandler;
    }

    public static void setBaseUrl(String baseUrl) {
        AdTraceFactory.baseUrl = baseUrl;
    }

    public static void setGdprUrl(String gdprUrl) {
        AdTraceFactory.gdprUrl = gdprUrl;
    }

    public static void useTestConnectionOptions() {
        AdTraceFactory.connectionOptions = new UtilNetworking.IConnectionOptions() {
            @Override
            public void applyConnectionOptions(HttpsURLConnection connection, String clientSdk) {
                UtilNetworking.ConnectionOptions defaultConnectionOption = new UtilNetworking.ConnectionOptions();
                defaultConnectionOption.applyConnectionOptions(connection, clientSdk);
                try {
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, new TrustManager[]{
                            new X509TrustManager() {
                                public X509Certificate[] getAcceptedIssuers() {
                                    getLogger().verbose("getAcceptedIssuers");
                                    return null;
                                }
                                public void checkClientTrusted(
                                        X509Certificate[] certs, String authType) {
                                    getLogger().verbose("checkClientTrusted ");
                                }
                                public void checkServerTrusted(
                                        X509Certificate[] certs, String authType) throws CertificateException {
                                    getLogger().verbose("checkServerTrusted ");

                                    String serverThumbprint = "7BCFF44099A35BC093BB48C5A6B9A516CDFDA0D1";
                                    X509Certificate certificate = certs[0];

                                    MessageDigest md = null;
                                    try {
                                        md = MessageDigest.getInstance("SHA1");
                                        byte[] publicKey = md.digest(certificate.getEncoded());
                                        String hexString = byte2HexFormatted(publicKey);

                                        if (!hexString.equalsIgnoreCase(serverThumbprint)) {
                                            throw new CertificateException();
                                        }
                                    } catch (NoSuchAlgorithmException e) {
                                        getLogger().error("testingMode error %s", e.getMessage());
                                    } catch (CertificateEncodingException e) {
                                        getLogger().error("testingMode error %s", e.getMessage());
                                    }
                                }
                            }
                    }, new java.security.SecureRandom());
                    connection.setSSLSocketFactory(sc.getSocketFactory());

                    connection.setHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            getLogger().verbose("verify hostname ");
                            return true;
                        }
                    });
                } catch (Exception e) {
                    getLogger().error("testingMode error %s", e.getMessage());
                }
            }
        };

    }

    public static void setTryInstallReferrer(boolean tryInstallReferrer) {
        AdTraceFactory.tryInstallReferrer = tryInstallReferrer;
    }

    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);

        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();

            if (l == 1) {
                h = "0" + h;
            }

            if (l > 2) {
                h = h.substring(l - 2, l);
            }

            str.append(h.toUpperCase());

            // if (i < (arr.length - 1)) str.append(':');
        }
        return str.toString();
    }

    public static void teardown(Context context) {
        if(context != null) {
            ActivityHandler.deleteState(context);
            PackageHandler.deleteState(context);
        }
        packageHandler = null;
        requestHandler = null;
        attributionHandler = null;
        activityHandler = null;
        logger = null;
        httpsURLConnection = null;
        sdkClickHandler = null;

        timerInterval = -1;
        timerStart = -1;
        sessionInterval = -1;
        subsessionInterval = -1;
        sdkClickBackoffStrategy = null;
        packageHandlerBackoffStrategy = null;
        maxDelayStart = -1;
        baseUrl = Constants.BASE_URL;
        gdprUrl = Constants.GDPR_URL;
        connectionOptions = null;
        tryInstallReferrer = true;
    }
}
