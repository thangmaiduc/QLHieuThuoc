package com.nhom20.qlhieuthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import model.HoaDonModel;
import model.ThuocModel;

public class ShowHoaDon extends AppCompatActivity {
    ListView lvHoaDon ;
    ArrayAdapter<HoaDonModel> adapter;
    String MaChiNhanh,MaHD;
    TextView textViewHD,textViewHD1,textViewCN,textViewCN1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hoa_don);
        addControll();
        layData();
        hienThiData();
    }
    private void layData(){
            MaChiNhanh = getIntent().getStringExtra("MaChiNhanh");
            MaHD = getIntent().getStringExtra("MaHD");
    }
    private void  addControll(){
        lvHoaDon= (ListView) findViewById(R.id.lvThuoc);
        textViewHD = (TextView) findViewById(R.id.textViewHD);
        textViewHD1 = (TextView) findViewById(R.id.textViewHD1);
        textViewCN = (TextView) findViewById(R.id.textViewCN);
        textViewCN1 = (TextView) findViewById(R.id.textViewCN1);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvHoaDon.setAdapter(adapter);
        registerForContextMenu(lvHoaDon);
    }
    private void hienThiData(){
        Login.database= openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = Login.database.rawQuery("select HD.NGAYHD,NHATHUOC.TENNT FROM HOADON as HD INNER JOIN NHATHUOC ON (HD.SOHD = MaHD and HD.MANT = NHATHUOC.MANT )",null);
        if (cursor.moveToNext()) {
            textViewHD.setText(textViewHD.getText().toString() + MaHD);
            textViewHD1.setText(textViewHD1.getText().toString() + cursor.getString(0));
            textViewCN.setText(textViewCN.getText().toString() + MaChiNhanh);
            textViewCN1.setText(textViewCN1.getText().toString() + cursor.getString(1));
        }
        else{
            Toast.makeText(ShowHoaDon.this,"Hóa Đơn không tồn tại",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ShowHoaDon.this,HoaDon.class);
            startActivity(intent);
        }
        cursor = Login.database.rawQuery("select CT.SOLUONG,CT.MATHUOC,THUOC.TENTHUOC,THUOC.DVT,THUOC.DONGIA FROM CHITIETBANLE as CT INNER JOIN THUOC ON (CT.SOHD = MaHD and CT.MATHUOC = THUOC.MATHUOC) ",null);
        while (cursor.moveToNext()){
            Integer SL = cursor.getInt(0);
            Integer ma = cursor.getInt(1);
            String ten = cursor.getString(2);
            String  dvt = cursor.getString(3);
            Double donGia = cursor.getDouble(4);
            HoaDonModel hoaDon = new HoaDonModel(ma, ten, dvt, donGia,SL);
            adapter.add(hoaDon);
        }
        cursor.close();
    }
    @Override
    protected void onResume() {
        super.onResume();
        hienThiData();
    }
}