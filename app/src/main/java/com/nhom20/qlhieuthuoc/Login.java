package com.nhom20.qlhieuthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Login extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView tvSignup ;
    String DATABASE_NAME = "DBHIEUTHUOC.db";
    String DB_PATH_SUFFIX = "/databases/";
    public static String nameShare = "thongtindangnhap";
    CheckBox chkSave, chkLogin ;
    public static SQLiteDatabase database = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        processCopy();
        addControls();
        addEvents();

    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        if(edtUsername.getText().length() > 0 && edtPassword.getText().length() > 0 ){
            database= openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            Cursor cursor = database.query("USER", null, "USERNAME =? and PASSWORD =?",
                    new String[]{edtUsername.getText().toString(), edtPassword.getText().toString()}, null, null, null);
            if(cursor.moveToNext()){
                SharedPreferences preferences = getSharedPreferences(nameShare , MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putBoolean("saveLogin", chkLogin.isChecked());
                editor.commit();
                Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, Menu.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Đăng nhập thất bại, sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addControls() {
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        tvSignup = (TextView) findViewById(R.id.tvSignup);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        chkSave = (CheckBox) findViewById(R.id.chkSave);
        chkLogin = (CheckBox) findViewById(R.id.chkLogin);
    }

    public void handleSignup(View view) {
        Intent signUpView = new Intent(Login.this, SignUpActivity.class);
        
        startActivity(signUpView);
    }
    private void processCopy(){
        try {
            File dbFile = getDatabasePath(DATABASE_NAME);
            if(!dbFile.exists()){
                copyDatabaseFromAssets();
                Toast.makeText(Login.this,"Copy database thành công", Toast.LENGTH_LONG ).show();
                
            }
        }catch (Exception ex){
            Log.e("Loi", ex.toString());
        }
    }
    private String getDatabasePath(){
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }
    private void copyDatabaseFromAssets() {
        try {
            InputStream myInput =getAssets().open(DATABASE_NAME);
            String outFileName =getDatabasePath();
            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists()) f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte [] buffer = new byte[1024];
            int length;
            while ((length= myInput.read(buffer))>0){
                myOutput.write(buffer,0,length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        }
        catch (Exception ex){
            Log.e("Loi", ex.toString());
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences(nameShare , MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", edtUsername.getText().toString());
        editor.putString("password", edtPassword.getText().toString());
        editor.putBoolean("save", chkSave.isChecked());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(nameShare , MODE_PRIVATE);
        String user = preferences.getString("username","");
        String password = preferences.getString("password","");
        Boolean save = preferences.getBoolean("save",false);
        Boolean saveLogin = preferences.getBoolean("saveLogin",false);
        if(saveLogin){
            Intent intent = new Intent(Login.this, Menu.class);
            startActivity(intent);

        }
        if(save){
            edtUsername.setText(user);
            edtPassword.setText(password);

        }
        chkSave.setChecked(save);
    }
}