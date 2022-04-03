package com.nhom20.qlhieuthuoc;

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

import android.os.Bundle;

public class ThemHieuThuoc extends AppCompatActivity {
    EditText edtTenThuoc, edtDVT, edtDonGia;
    Button btnAdd, btnSave, btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hieu_thuoc);
        addControls();
    }

    private void addControls() {
        edtTenThuoc = (EditText) findViewById(R.id.edtTenThuoc);
        edtDVT = (EditText) findViewById(R.id.edtDVT);
        edtDonGia = (EditText) findViewById(R.id.edtDonGia);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if(type.equalsIgnoreCase("sua")){
            btnAdd.setEnabled(false);
            btnContinue.setEnabled(false);
            edtTenThuoc.setText(activity_thuoc.selectedThuoc.getTenThuoc());
            edtDVT.setText(activity_thuoc.selectedThuoc.getDVT());
            edtDonGia.setText(activity_thuoc.selectedThuoc.getDonGia().toString());
        }

        else  btnSave.setEnabled(false);


    }

    public void handBack(View view) {
        Intent intent = new Intent(this, activity_thuoc.class);
        startActivity(intent);
    }


    public void handleContinue(View view) {
        edtTenThuoc.setText("");
        edtDonGia.setText("");
        edtDVT.setText("");
        edtTenThuoc.requestFocus();
    }

    public void handleSave(View view) {
        String TENTHUOC = edtTenThuoc.getText().toString().trim();
        String DVT = edtDVT.getText().toString().trim();
        String DONGIA = edtDonGia.getText().toString().trim();
        if(TENTHUOC.length() ==0 ||  DVT.length() == 0 || DONGIA.length() == 0 ){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("TENTHUOC" , TENTHUOC);
        values.put("DVT" , DVT);
        values.put("DONGIA", DONGIA);
        int kq = Login.database.update("THUOC", values, "MATHUOC = ?", new String[]{activity_thuoc.selectedThuoc.getMaThuoc().toString()});
        if(kq>0){
            Toast.makeText(this, "Lưu thông tin thành công",Toast.LENGTH_SHORT).show();
            finish();
        }else Toast.makeText(this, "Lưu thông tin thất bại",Toast.LENGTH_SHORT).show();
    }


    public void handleAdd(View view) {

        String TENTHUOC = edtTenThuoc.getText().toString().trim();
        String DVT = edtDVT.getText().toString().trim();
        String DONGIA = edtDonGia.getText().toString().trim();
        if(TENTHUOC.length() ==0 ||  DVT.length() == 0 || DONGIA.length() == 0){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("TENTHUOC" , TENTHUOC);
        values.put("DVT" , DVT);
        values.put("DONGIA", DONGIA);
        long kq =  Login.database.insert("THUOC", null, values);
        if(kq>0){
            Toast.makeText(this, "Thêm nhà thuốc thành công",Toast.LENGTH_SHORT).show();

        }else Toast.makeText(this, "Thêm nhà thuốc thất bại",Toast.LENGTH_SHORT).show();
    }
}