package com.nhom20.qlhieuthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    public void chuyenTaiKhoan(View view) {
        Intent intent = new Intent(Menu.this, TaiKhoan.class);
        startActivity(intent);
    }
    public void logOut(View view) {
        SharedPreferences preferences = getSharedPreferences(Login.nameShare , MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("saveLogin", false);
        editor.commit();
        Intent intent = new Intent(Menu.this, Login.class);
        startActivity(intent);
    }
    public void chuyenHieuThuoc(View view) {
        Intent intent = new Intent(Menu.this, HieuThuoc.class);
        startActivity(intent);
    }

    public void chuyenThuoc(View view) {
        Intent intent = new Intent(Menu.this, Thuoc.class);
        startActivity(intent);
    }
    public void chuyenHoaDon(View view) {
        Intent intent = new Intent(Menu.this, HoaDon.class);
        startActivity(intent);
    }
    public void chuyenCTHD(View view) {
        Intent intent = new Intent(Menu.this, CTHoaDon.class);
        startActivity(intent);
    }

}