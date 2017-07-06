package com.sdj_jewellers.utility;

/**
 * Created by bhanwar on 27/02/2016.
 */
public class Connection {

    public static String BASE_URL="http://prithvirajjewels.com/API/DJdemo/";

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
}
