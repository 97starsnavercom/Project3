package com.hansung.android.project3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/***********************************************************************************
 *                                                                                  *
 *   출처:https://github.com/kwanulee/Android/blob/master/examples/SQLiteDBTest     *
 *                                                                                  *
 ***********************************************************************************/

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest";

    public DBHelper(Context context) {
        super(context, RestaurantContract.DB_NAME, null, RestaurantContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Restaurants, Menus 테이블 생성
        db.execSQL(RestaurantContract.Restaurants.CREATE_TABLE);
        db.execSQL(RestaurantContract.Menus.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(RestaurantContract.Restaurants.DELETE_TABLE);
        onCreate(db);
    }


    //Restaurant추가시 작동되는 메소드
    public long insertRestaurantByMethod(String Name, String Add,  String Tel,  String Photo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RestaurantContract.Restaurants.KEY_NAME, Name);
        values.put(RestaurantContract.Restaurants.KEY_ADD, Add);
        values.put(RestaurantContract.Restaurants.KEY_TEL, Tel);
        values.put(RestaurantContract.Restaurants.KEY_PHOTO, Photo);

        return db.insert(RestaurantContract.Restaurants.TABLE_NAME,null,values);
    }

    //Menu추가시 작동되는 메소드
    public long insertMenuByMethod(Integer restaurant_id, String name,  String price,  String detail, String photo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RestaurantContract.Menus.KEY_ID2, restaurant_id);
        values.put(RestaurantContract.Menus.KEY_NAME2, name);
        values.put(RestaurantContract.Menus.KEY_PRICE, price);
        values.put(RestaurantContract.Menus.KEY_DTAIL, detail);
        values.put(RestaurantContract.Menus.KEY_PHOTO2, photo);

        return db.insert(RestaurantContract.Menus.TABLE_NAME,null,values);
    }

    //Restaurant정보를 가져올때 사용
    public Cursor getAllRestaurantsByMethod() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(RestaurantContract.Restaurants.TABLE_NAME,null,null,null,null,null,null);
    }

    //Menu정보를 가져올때 사용
    public Cursor getAllMenusByMethod() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(RestaurantContract.Menus.TABLE_NAME,null,null,null,null,null,null);
    }
}