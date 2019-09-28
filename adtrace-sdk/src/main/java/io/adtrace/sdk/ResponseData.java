package io.adtrace.sdk;

import org.json.JSONObject;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public class ResponseData {
    public boolean success;
    public boolean willRetry;
    public String adid;
    public String message;
    public String timestamp;
    public JSONObject jsonResponse;
    public ActivityKind activityKind;
    public TrackingState trackingState;
    public AdTraceAttribution attribution;

    protected ResponseData() {}

    public static ResponseData buildResponseData(ActivityPackage activityPackage) {
        ResponseData responseData;
        ActivityKind activityKind = activityPackage.getActivityKind();
        switch (activityKind) {
            case SESSION:
                responseData = new SessionResponseData(activityPackage);
                break;
            case CLICK:
                responseData = new SdkClickResponseData();
                break;
            case ATTRIBUTION:
                responseData = new AttributionResponseData();
                break;
            case EVENT:
                responseData = new EventResponseData(activityPackage);
                break;
            default:
                responseData = new ResponseData();
                break;
        }
        responseData.activityKind = activityKind;

        return responseData;
    }

    @Override
    public String toString() {
        return Util.formatString("message:%s timestamp:%s json:%s", message, timestamp, jsonResponse);
    }
}