package com.hansung.android.project3;

import android.provider.BaseColumns;

/***********************************************************************************
 *                                                                                  *
 *   출처:https://github.com/kwanulee/Android/blob/master/examples/SQLiteDBTest     *
 *                                                                                  *
 ***********************************************************************************/

public final class RestaurantContract {
    public static final String DB_NAME="user.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private RestaurantContract() {}


    //Restaurants테이블
    public static class Restaurants implements BaseColumns {
        public static final String TABLE_NAME="Restaurant";
        public static final String KEY_NAME = "Name";
        public static final String KEY_ADD = "Ad2d";
        public static final String KEY_TEL = "Tel";
        public static final String KEY_PHOTO = "Photo";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_ADD + TEXT_TYPE + COMMA_SEP +
                KEY_TEL + TEXT_TYPE + COMMA_SEP +
                KEY_PHOTO + TEXT_TYPE +  " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    //Menus테이블
    public static class Menus implements BaseColumns {
        public static final String TABLE_NAME="Menus";
        public static final String KEY_ID2 = "ID";
        public static final String KEY_NAME2 = "Menu_Name";
        public static final String KEY_PRICE = "Menu_Price";
        public static final String KEY_DTAIL = "Menu_Detail";
        public static final String KEY_PHOTO2 = "Photo";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                 _ID + INTEGER_TYPE + " auto_increment " + COMMA_SEP +
                KEY_ID2 + INTEGER_TYPE + COMMA_SEP +
                KEY_NAME2 + TEXT_TYPE + COMMA_SEP +
                KEY_PRICE + TEXT_TYPE + COMMA_SEP +
                KEY_DTAIL + TEXT_TYPE + COMMA_SEP +
                KEY_PHOTO2 + TEXT_TYPE +  COMMA_SEP +
                "primary key(" + _ID + COMMA_SEP + KEY_ID2 + " )" + " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}