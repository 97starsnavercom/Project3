package com.hansung.android.project3;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registration_Menu extends AppCompatActivity {

    private File mPhotoFile =null;
    private String mPhotoFileName = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private DBHelper mDbHelper;
    final String TAG="location";
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__menu);
        getID();
    }

    //메뉴사진을 찍기 위한 메소드
    public void menu_image(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //1. 카메라 앱으로 찍은 이미지를 저장할 파일 객체 생성
            mPhotoFileName = "IMG"+currentDateFormat()+".jpg";
            mPhotoFile = new File(Environment.getExternalStorageDirectory().getPath()+"/Pictures/", mPhotoFileName);

            if (mPhotoFile !=null) {
                //2. 생성된 파일 객체에 대한 Uri 객체를 얻기
                Uri imageUri = FileProvider.getUriForFile(this, "com.hansung.android.project3", mPhotoFile);

                //3. Uri 객체를 Extras를 통해 카메라 앱으로 전달
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else
                Toast.makeText(getApplicationContext(), "file null", Toast.LENGTH_SHORT).show();
        }

    }

    //그 결과값을 받아오는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (mPhotoFileName != null) {
                mPhotoFile = new File(Environment.getExternalStorageDirectory().getPath()+"/Pictures/", mPhotoFileName);
                ImageButton imageButton = (ImageButton) findViewById(R.id.imageBtn2);
                Bitmap bitmap = BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath());
                imageButton.setImageBitmap(bitmap);

            } else
                Toast.makeText(getApplicationContext(), "mPhotoFile is null", Toast.LENGTH_SHORT).show();

        }
    }

    //Menus테이블에 정보저장
    public void registration_menu(View view){
        mDbHelper = new DBHelper(this);
        EditText editText1 = (EditText)findViewById(R.id.Menu_Name);
        EditText editText2 = (EditText)findViewById(R.id.Menu_Price);
        EditText editText3 = (EditText)findViewById(R.id.Menu_Detail);

        Log.i(TAG,"Menu regi: "+ID);

        String name = editText1.getText().toString();
        String price = editText2.getText().toString();
        String detail = editText3.getText().toString();
        String photo =mPhotoFile.getAbsolutePath().toString();

        long nOfRows = mDbHelper.insertMenuByMethod(ID,name,price, detail,photo);
        if (nOfRows >0)
            Toast.makeText(this,nOfRows+" Record Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"No Record Inserted", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),RestaurantDetail.class);
        intent.putExtra("code",3);
        startActivity(intent);

    }

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    public void getID(){

        Cursor cursor_restaurant = mDbHelper.getAllRestaurantsByMethod();
        Cursor cursor_menu = mDbHelper.getAllMenusByMethod();

        Intent intent = getIntent();
        if(intent.getIntExtra("Restaurant ID",0)>=0){
            ID=intent.getIntExtra("Restaurant ID",0);
            Log.i(TAG,"Menu"+ID);
        }
        else{
            Log.i(TAG,"Error ID !!!!!!!!!!!!!!!!!! in Registration_Menu");
        }

    }

}
