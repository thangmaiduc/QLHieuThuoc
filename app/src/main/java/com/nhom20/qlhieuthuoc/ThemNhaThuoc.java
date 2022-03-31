package com.nhom20.qlhieuthuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThemNhaThuoc extends AppCompatActivity {
    EditText edtTenNT, edtDiaChi;
    Button btnAdd, btnSave, btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nha_thuoc);
        addControls();

    }
    private void addControls() {
        edtTenNT = (EditText) findViewById(R.id.edtTenNT);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if(type.equalsIgnoreCase("sua")){
            btnAdd.setEnabled(false);
            btnContinue.setEnabled(false);
            edtTenNT.setText(HieuThuoc.selectedNhaThuoc.getTenNT());
            edtDiaChi.setText(HieuThuoc.selectedNhaThuoc.getDiaChi());
        }

        else  btnSave.setEnabled(false);


    }

    public void handBack(View view) {
        Intent intent = new Intent(this, HieuThuoc.class);
        startActivity(intent);
    }


    public void handleContinue(View view) {
        edtTenNT.setText("");

        edtDiaChi.setText("");
        edtTenNT.requestFocus();
    }

    public void handleSave(View view) {
        String TENNT = edtTenNT.getText().toString();
        String DIACHI = edtDiaChi.getText().toString();
        if(TENNT.length() ==0 ||  DIACHI.length() == 0 ){
            Toast.makeText(this, "Vui lòng nhập thông tin",Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("TENNT" , TENNT);
        values.put("DIACHI" , DIACHI);
        int kq = Login.database.update("NHATHUOC", values, "MANT = ?", new String[]{HieuThuoc.selectedNhaThuoc.getMaNT().toString()});
        if(kq>0){
            Toast.makeText(this, "Lưu thông tin thành công",Toast.LENGTH_SHORT).show();
            finish();
        }else Toast.makeText(this, "Lưu thông tin thất bại",Toast.LENGTH_SHORT).show();
    }


    public void handleAdd(View view) {

        String TENNT = edtTenNT.getText().toString();
        String DIACHI = edtDiaChi.getText().toString();
        if(TENNT.length() ==0 ||  DIACHI.length() == 0 ){
            Toast.makeText(this, "Vui lòng nhập thông tin",Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("TENNT" , TENNT);
        values.put("DIACHI" , DIACHI);
        long kq =  Login.database.insert("NHATHUOC", null, values);
        if(kq>0){
            Toast.makeText(this, "Thêm nhà thuốc thành công",Toast.LENGTH_SHORT).show();

        }else Toast.makeText(this, "Thêm nhà thuốc thất bại",Toast.LENGTH_SHORT).show();
    }
}