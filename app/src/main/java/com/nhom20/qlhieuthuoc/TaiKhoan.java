package com.nhom20.qlhieuthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TaiKhoan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan);
    }

    public void handBack(View view) {
        Intent intent = new Intent(TaiKhoan.this, Menu.class);
        startActivity(intent);
    }

    public void handleSave(View view) {
    }
}