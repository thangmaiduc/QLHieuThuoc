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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.HoaDonModel;
import model.ThuocModel;

public class ThemHoaDon extends AppCompatActivity {
    Spinner spThuoc;
    Button addThuoc,saveThuoc;
    EditText editTextNumber;
    ListView lvHoaDon;
    ArrayList<String> dataThuoc = new ArrayList<>();
    HashMap <String,ThuocModel> map = new  HashMap<String,ThuocModel>();
    ArrayAdapter<HoaDonModel> adapter;
    ArrayList <HoaDonModel> listThuoc;
    private String maThuoc = "";
    private Integer ma = 0;
    private String ten = "",dvt ="";
    private Double donGia = 0.0;
    ThuocModel tempThuoc = new ThuocModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoa_don);
        addControll();
        hienThiThuoc();
        ArrayAdapter stapter  = new ArrayAdapter(this, android.R.layout.simple_list_item_1,dataThuoc);
        listThuoc = new ArrayList<HoaDonModel>();
        spThuoc.setAdapter(stapter);
        spThuoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(ThemHoaDon.this,"Bạn Chọn" + dataThuoc.get(position),Toast.LENGTH_SHORT).show();
                    tempThuoc = map.get(dataThuoc.get(position));
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ThemHoaDon.this,"Bạn Chọn" + dataThuoc.get(i),Toast.LENGTH_SHORT).show();
                maThuoc = (String) map.get(dataThuoc.get(i));
            }
        });
    }
    private void hienThiThuoc(){

        Login.database= openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = Login.database.query("THUOC", null, null,
                null, null, null, null);
        dataThuoc.clear();
        map.clear();
        while(cursor.moveToNext()){
             ma = cursor.getInt(0);
             ten = cursor.getString(1);
             dvt = cursor.getString(2);
             donGia = cursor.getDouble(3);
             ThuocModel thuoc = new ThuocModel(ma, ten, dvt, donGia);
             dataThuoc.add(ten);
             map.put(ten,thuoc);
        }
        cursor.close();
    }
    private  void addControll(){
        spThuoc = (Spinner) findViewById(R.id.spThuoc);
        addThuoc = (Button) findViewById(R.id.addThuoc);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        lvHoaDon = (ListView) findViewById(R.id.lvHoaDon);
        saveThuoc = (Button) findViewById(R.id.saveThuoc);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvHoaDon.setAdapter(adapter);
        registerForContextMenu(lvHoaDon);
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
            HoaDonModel hoaDon = new HoaDonModel(tempThuoc.getMaThuoc(), tempThuoc.getTenThuoc(), tempThuoc.getDVT(), tempThuoc.getDonGia(),sl);
            adapter.add(hoaDon);
            listThuoc.add(hoaDon);
        }
    }
    public void saveHoaDon(View view) {
        long millis=System.currentTimeMillis();
        Random rand = new Random();
        int soHD = rand.nextInt(100)+1;
        java.sql.Date date=new java.sql.Date(millis);
        ContentValues values = new ContentValues();
        values.put("SOHD",soHD);
        values.put("NGAYHD", date.toString());
        values.put("MANT",getIntent().getStringExtra("MANT"));
        ContentValues values2 = new ContentValues();
        for (int counter = 0; counter < listThuoc.size(); counter++) {
            HoaDonModel xtemp  = new HoaDonModel();
            xtemp  = listThuoc.get(counter);
            values2.put("SOHD",soHD);
            values2.put("MATHUOC", xtemp.getMaThuoc());
            values2.put("SOLUONG",xtemp.getSL());
            long kq2 = Login.database.insert("CHITIETBANLE", null, values2);
        }
        long kq =  Login.database.insert("HOADON", null, values);
        if(kq > 0){
            Toast.makeText(this, "Thêm nhà thuốc thành công ",Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Số HD là "+ soHD,Toast.LENGTH_SHORT).show();
            Intent intent   =  new Intent(ThemHoaDon.this,HoaDon.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Thêm nhà thuốc không thành công",Toast.LENGTH_SHORT).show();
        }

    }
}