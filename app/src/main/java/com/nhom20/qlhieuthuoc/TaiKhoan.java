package com.nhom20.qlhieuthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TaiKhoan extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtEmail;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan);
        addControls();

    }
    private void addControls() {
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnSave = (Button) findViewById(R.id.btnSaveInfoAccount);
    }

    public void handBack(View view) {
        Intent intent = new Intent(TaiKhoan.this, Menu.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtUsername.setEnabled(false);
        SharedPreferences preferences = getSharedPreferences(Login.nameShare , MODE_PRIVATE);
        String user = preferences.getString("username","");
        Login.database= openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = Login.database.query("USER", null, "USERNAME =?",
                new String[]{user}, null, null, null);
        String email="",password="";
        if(cursor.moveToNext()){
            email = cursor.getString(1);
            password = cursor.getString(2);


        }
        cursor.close();

        edtUsername.setText(user);
        edtPassword.setText(password);
        edtEmail.setText(email);


    }

    public void handleSave(View view) {
        String user = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String email = edtEmail.getText().toString();
        ContentValues values = new ContentValues();
        values.put("EMAIL" , email);
        values.put("PASSWORD" , password);
        int kq = Login.database.update("USER", values, "USERNAME = ?", new String[]{user});
        if(kq>0){
            Toast.makeText(this, "Lưu thông tin thành công",Toast.LENGTH_SHORT).show();
            finish();
        }else Toast.makeText(this, "Lưu thông tin thất bại",Toast.LENGTH_SHORT).show();
    }
}