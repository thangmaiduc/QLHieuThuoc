package com.nhom20.qlhieuthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    EditText edtUsername, edtEmail, edtPassword, edtConfirmPassword;
    Button btnSignUp;
    TextView tvGoToLogin;

    private static String DATABASE_NAME = "DBHIEUTHUOC.db";
    private static int MIN_PASSWORD_LENGTH = 6;
    private static SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        addLayouts();
        addEvents();
    }

    public static boolean isEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();
    }

    private void onSignup() {
        String username = edtUsername.getText().toString().toLowerCase(Locale.ROOT).trim();
        String email = edtEmail.getText().toString().toLowerCase(Locale.ROOT).trim();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "H??y nh???p ?????y ????? c??c th??ng tin!", Toast.LENGTH_SHORT).show();
        } else if (!isEmail(email)) {
            Toast.makeText(this, "Email kh??ng h???p l???!", Toast.LENGTH_SHORT).show();
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            Toast.makeText(this, "M???t kh???u ph???i d??i ??t nh???t " + MIN_PASSWORD_LENGTH + " k?? t???!", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Nh???p l???i m???t kh???u kh??ng ????ng!", Toast.LENGTH_SHORT).show();
        } else {
            database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            Cursor cursor = database.query("USER", null, "USERNAME = ?",
                    new String[]{username}, null, null, null);

            if (cursor.moveToNext()) {
                Toast.makeText(this, "Username ???? t???n t???i!", Toast.LENGTH_SHORT).show();
            } else {
                ContentValues cv = new ContentValues();
                cv.put("USERNAME", username);
                cv.put("EMAIL", email);
                cv.put("PASSWORD", password);
                database.insert("USER", null, cv);
                Toast.makeText(this, "????ng k?? th??nh c??ng, h??y ????ng nh???p ????? ti???p t???c s??? d???ng!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);
                this.finish();
            }
            cursor.close();
        }
    }

    private void addEvents() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignup();
            }
        });
    }

    private void addLayouts() {
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvGoToLogin = findViewById(R.id.tvGoToLogin);
    }
}