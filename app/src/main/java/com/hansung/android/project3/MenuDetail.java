package com.hansung.android.project3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import static android.support.v4.graphics.drawable.DrawableCompat.setTint;

public class MenuDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        //위로 돌아가기 버튼
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Drawable drawable = ContextCompat.getDrawable(this,R.drawable.ic_chevron_left_black_24dp);
            if (drawable != null) {
                setTint(drawable, Color.WHITE);
                actionBar.setHomeAsUpIndicator(drawable);
            }
        }


        //
        Intent intent =getIntent();
        String Name=intent.getStringExtra("Option1");
        String Phtot=intent.getStringExtra("Option2");
        String Price=intent.getStringExtra("Option3");

        //Image 설정
        ImageView image=(ImageView)findViewById(R.id.image1) ;
        Bitmap bitmap = BitmapFactory.decodeFile(Phtot);
        image.setImageBitmap(bitmap);

        //음식 이름 설정
        TextView name = (TextView)findViewById(R.id.text1);
        name.setText(Name);

        //가격 설정
        TextView name2 = (TextView)findViewById(R.id.text2);
        name2.setText(Price+"원");

    }

    //뒤로 돌아갈때 Intent로 정보 전달
    @Override
    protected void onStop() {
        Intent intent = new Intent(getApplicationContext(),RestaurantDetail.class);
        intent.putExtra("code",4);
        startActivity(intent);
        super.onStop();
    }
}
