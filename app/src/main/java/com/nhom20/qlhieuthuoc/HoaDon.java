package com.nhom20.qlhieuthuoc;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import model.HieuThuocModel;

public class HoaDon extends AppCompatActivity {
    Button timKiem;
    ImageView imageButton2;
    Spinner chiNhanh;
    EditText maHoaDon;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> data1 = new ArrayList<>();
    HashMap map = new HashMap();
    String maChiNhanh = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        chiNhanh = (Spinner) findViewById(R.id.chiNhanh);
        timKiem = (Button)findViewById(R.id.timKiem);
        maHoaDon = (EditText)findViewById(R.id.maHoaDon);
        imageButton2 = (ImageView) findViewById(R.id.imageButton2);
        setControll();

    }
    private void setControll(){
        KhoiTao();
        ArrayAdapter stapter  = new ArrayAdapter(this, android.R.layout.simple_list_item_1,data);
        stapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chiNhanh.setAdapter(stapter);
        chiNhanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HoaDon.this,"Bạn Chọn " + data.get(position),Toast.LENGTH_SHORT).show();
                maChiNhanh = String.valueOf(map.get(data1.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void KhoiTao(){
        Login.database= openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = Login.database.query("NHATHUOC", null, null,
                null, null, null, null);
        data.clear();
        map.clear();
        while(cursor.moveToNext()){
            Integer ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            String di = cursor.getString(2);
            data.add(ten+" "+di);
            data1.add(ten);
            map.put(ten,ma);
        }
    }
    public void handBack(View view) {
        Intent intent = new Intent(HoaDon.this, Menu.class);
        startActivity(intent);
    }
    public void timKiemHoaDon(View view){
        String maHD = maHoaDon.getText().toString();
        Intent intent = new Intent(HoaDon.this,ShowHoaDon.class);
        intent.putExtra("MaChiNhanh",maChiNhanh);
        intent.putExtra("MaHD",maHD);
        startActivity(intent);
    }
    public void handAdd(View view){
            if(maChiNhanh.length() < 0){
                Toast.makeText(HoaDon.this,"Bạn Chưa chọn Chi nhánh" ,Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(HoaDon.this, ThemHoaDon.class);
                intent.putExtra("MANT",maChiNhanh);
                startActivity(intent);
            }
    }
}