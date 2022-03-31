package com.nhom20.qlhieuthuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import model.HieuThuocModel;

public class HieuThuoc extends AppCompatActivity {
    ListView lvHieuThuoc ;
    ArrayAdapter<HieuThuocModel> adapter;
    public static HieuThuocModel selectedNhaThuoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hieu_thuoc);
        addControls();
        addEvents();
        hienThiNhaThuoc();
    }

    private void addEvents() {
        lvHieuThuoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedNhaThuoc = adapter.getItem(i);
            }
        });
        lvHieuThuoc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedNhaThuoc = adapter.getItem(i);
                return false;
            }
        });
    }
    private void addControls() {
        lvHieuThuoc= (ListView) findViewById(R.id.lvHieuThuoc);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvHieuThuoc.setAdapter(adapter);
        registerForContextMenu(lvHieuThuoc);
    }
    private void hienThiNhaThuoc() {
        Login.database= openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = Login.database.query("NHATHUOC", null, null,
                null, null, null, null);
        adapter.clear();
        while(cursor.moveToNext()){
            Integer ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            String  diachi= cursor.getString(2);
            HieuThuocModel nhaThuoc = new HieuThuocModel(ma, ten, diachi) ;
            adapter.add(nhaThuoc);

        }
        cursor.close();
    }


    public void handBack(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hienThiNhaThuoc();

    }

    public void handAdd(View view) {
        Intent intent = new Intent(this, ThemNhaThuoc.class);
        intent.putExtra("type", "them");
        startActivity(intent);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuEdit){
            if(selectedNhaThuoc!=null){
                Intent intent = new Intent(this, ThemNhaThuoc.class);
                intent.putExtra("type", "sua");
                startActivity(intent);
            }
        }
        if(item.getItemId() == R.id.menuDelete){
            if(selectedNhaThuoc!=null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa?: \n " + selectedNhaThuoc);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Login.database= openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
                        int kq = Login.database.delete("NHATHUOC", "MANT =?", new String[]{selectedNhaThuoc.getMaNT().toString()});
                        if(kq>0){
                            Toast.makeText(HieuThuoc.this, "Xóa nhà thuốc thành công",Toast.LENGTH_SHORT).show();
                            hienThiNhaThuoc();
                        }else{
                            Toast.makeText(HieuThuoc.this, "Xóa nhà thuốc thất bại vì đã có hóa đơn",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        }
        return super.onContextItemSelected(item);
    }

}