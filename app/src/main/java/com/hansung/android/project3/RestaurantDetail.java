package com.hansung.android.project3;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RestaurantDetail extends AppCompatActivity {

    private DBHelper mDbHelper;
    static MyAdapter adapter;
    ArrayList<MyItem> data = new ArrayList<MyItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        //시작시 DB 부터 얻은 정보로 Restaurant정보 설정
        getContributes();
    }


    //옵션 메뉴 설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //옵션 메뉴에서 아이탬클릭시 작동방법 구현
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_menu:
                //Registration_Menu액티비티 실행
                startActivityForResult(new Intent(this,Registration_Menu.class),1);
        }
        return super.onOptionsItemSelected(item);
    }

    //다른 액티비티로 부터 돌아와 재시작시 작동되는 메소드 구현
    @Override
    protected void onResume() {
        Intent intent = getIntent();
        int i = intent.getIntExtra("code",1);

        //i==3일 경우에는 메뉴 등록으로부터
        //i==4일 경우에는 메뉴 상세로 부터
        if(i==3||i==4){
            //각 메뉴 정보를 DB로 부터 불러와 설정하는 메소드
            getContributes2();

        }
        super.onResume();
    }


    //Restaurant의 정보설정 메소드
    protected void getContributes(){
        Log.i("asd","contributes1");
        mDbHelper = new DBHelper(this);
        Cursor cursor_restaurant = mDbHelper.getAllRestaurantsByMethod();
        int i= cursor_restaurant.getCount();

        while(cursor_restaurant.moveToNext()){
            if(cursor_restaurant.getInt(0)==i){
                TextView textView1 = (TextView) findViewById(R.id.restaurant_name);
                TextView textView2 = (TextView) findViewById(R.id.restaurant_add);
                TextView textView3 = (TextView) findViewById(R.id.restaurant_tel);
                ImageView imageView = (ImageView) findViewById(R.id.restaurant_image);

                textView1.setText(cursor_restaurant.getString(1));
                textView2.setText(cursor_restaurant.getString(2));
                textView3.setText(cursor_restaurant.getString(3));
                Bitmap bitmap = BitmapFactory.decodeFile(cursor_restaurant.getString(4));
                imageView.setImageBitmap(bitmap);

            }
        }
    }

    //Restaurant의 메뉴설정 메소드
    public void getContributes2(){
        Cursor cursor_restaurant = mDbHelper.getAllRestaurantsByMethod();
        Cursor cursor_menu = mDbHelper.getAllMenusByMethod();
        int ID = cursor_restaurant.getCount();

        while(cursor_menu.moveToNext()){
            if(cursor_menu.getInt(1)==ID){
                String menu_name = cursor_menu.getString(2);
                String menu_price = cursor_menu.getString(3);
                String menu_photo = cursor_menu.getString(5);
                data.add(new MyItem(menu_photo, menu_name, menu_price));

            }
        }

        //어댑터 생성
        adapter = new MyAdapter(this, R.layout.item, data);

        //어댑터 연결
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);

        //Listner 부착
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View vClicked, int position, long id) {

                String name = ((MyItem)adapter.getItem(position)).nName;
                String Photo=((MyItem)adapter.getItem(position)).mphoto;
                String Price=((MyItem)adapter.getItem(position)).nPrice;

                //MenuDetail에 속성값 전달 그리고 액티비티 실행
                Intent intent=new Intent(getApplicationContext(),MenuDetail.class);
                intent.putExtra("Option1",name);
                intent.putExtra("Option2",Photo);
                intent.putExtra("Option3",Price);
                startActivity(intent);

            }
        });

    }

    //통화실행코드
    public void callnumber(View view){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:02-760-4499")));
    }
}
