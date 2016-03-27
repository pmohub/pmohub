package com.edulandsoft.mysentence.common;

/**
 * Created by Administrator on 2016-03-05.
 */
public final class g {

    public static int curVersion = 16;
    public static int patchVersion = 16;

    public static String sqliteName = "me0100100400015kr1.db";
    public static String TAG = "psh";

    public static String packageNm = "com.edulandsoft.mysentence";
    //---------------------------------------------------------
    // State
    //---------------------------------------------------------
    public static boolean isFirstHome = true;

    //---------------------------------------------------------
    // Game Control
    //---------------------------------------------------------
    public static String internetConnectTestUrl = "http://www.google.com";
    public static boolean isInternetReachability = true;
    public static boolean isPurchase;

    public static boolean isPurchaseSucceed = false;
    public static String purchaseOrderId;
    public static String purchaseProductId;


    public static int curIdx = 0, lastIdx = 0;

    //---------------------------------------------------------
    // User Option
    //---------------------------------------------------------
    public static boolean isVibrate = true;
    public static String userLanguage = "Korean";

    //---------------------------------------------------------
    // User Info
    //---------------------------------------------------------
    public static String userId;
    public static String googleId;
    public static String googleName;
    public static String googleEmail;
    public static String buyYn = "N";
    public static String fbId;

    public static String wwwDate;

    //---------------------------------------------------------
    // Learn
    //---------------------------------------------------------
    public static int todayLearnCnt = 1;
    public static int weekLearnCnt = 1;
    public static int monthLearnCnt = 1;

    //---------------------------------------------------------
    // Regist
    //---------------------------------------------------------
    public static boolean isModify = false;

    public static String registDate;
    public static int    registNum;
    public static String keyword;
    public static String mySentence;
    public static String tranSentence;
    public static int    learnCnt;
    public static String masterYn;
    public static String delYn;
    public static String createDate;
    public static String createTime;

    //---------------------------------------------------------
    // Commom
    //---------------------------------------------------------
    public static float backgroudColorR;
    public static float backgroudColorG;
    public static float backgroudColorB;

}
