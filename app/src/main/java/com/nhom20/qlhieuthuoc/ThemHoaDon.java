package com.nhom20.qlhieuthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.ThuocModel;

public class ThemHoaDon extends AppCompatActivity {
    Spinner spThuoc;
    Button addThuoc;
    ArrayList<String> dataThuoc = new ArrayList<>();
    HashMap map = new HashMap();
    EditText editTextNumber;
    String maThuoc = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoa_don);
        addControll();
        hienThiThuoc();
        ArrayAdapter stapter  = new ArrayAdapter(this, android.R.layout.simple_list_item_1,dataThuoc);
        spThuoc.setAdapter(stapter);
        spThuoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(ThemHoaDon.this,"Bạn Chọn" + dataThuoc.get(position),Toast.LENGTH_SHORT).show();
                maThuoc = String.valueOf(map.get(dataThuoc.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void hienThiThuoc(){

        Login.database= openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = Login.database.query("THUOC", null, null,
                null, null, null, null);
        dataThuoc.clear();
        while(cursor.moveToNext()){
            Integer ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            String  dvt = cursor.getString(2);
            Double donGia = cursor.getDouble(3);
            dataThuoc.add(ten);
            map.put(ten,ma);
        }
        cursor.close();
    }
    private  void addControll(){
        spThuoc = findViewById(R.id.spThuoc);
        addThuoc = (Button) findViewById(R.id.addThuoc);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
    }
    public void handBack(View view) {
        Intent intent = new Intent(ThemHoaDon.this, Menu.class);
        startActivity(intent);
    }

    public void addHoaDon(View view) {
        Integer sl = Integer.valueOf(editTextNumber.getText().toString());
        if(sl < 0){
            Toast.makeText(ThemHoaDon.this,"Bạn Chưa chọn số lượng",Toast.LENGTH_SHORT).show();
        }
        else{
            long millis=System.currentTimeMillis();
            Random rand = new Random();
            int soHD = rand.nextInt(100)+1;
            java.sql.Date date=new java.sql.Date(millis);
            ContentValues values = new ContentValues();
            values.put("SOHD",soHD);
            values.put("NGAYHD", date.toString());
            values.put("MANT",getIntent().getStringExtra("MANT"));
            ContentValues values2 = new ContentValues();
            values2.put("SOHD",soHD);
            values2.put("MATHUOC", Integer.valueOf(maThuoc));
            values2.put("SOLUONG",sl);
            long kq2 = Login.database.insert("CHITIETBANLE", null, values2);
            long kq =  Login.database.insert("HOADON", null, values);
            if(kq > 0){
                Toast.makeText(this, "Thêm nhà thuốc thành công",Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(this, "Thêm nhà thuốc không thành công",Toast.LENGTH_SHORT).show();
            }

        }
    }
}