package com.nhom20.qlhieuthuoc;

import androidx.appcompat.app.AppCompatActivity;

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
import model.ThuocModel;

import android.os.Bundle;

public class activity_thuoc extends AppCompatActivity {
    ListView lvThuoc ;
    ArrayAdapter<ThuocModel> adapter;
    public static ThuocModel selectedThuoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuoc);

        addControls();
        addEvents();
        hienThiThuoc();
    }

    private void addEvents() {
        lvThuoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedThuoc = adapter.getItem(i);
            }
        });
        lvThuoc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedThuoc = adapter.getItem(i);
                return false;
            }
        });
    }
    private void addControls() {
        lvThuoc= (ListView) findViewById(R.id.lvThuoc);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvThuoc.setAdapter(adapter);
        registerForContextMenu(lvThuoc);
    }
    private void hienThiThuoc() {
        Login.database= openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = Login.database.query("THUOC", null, null,
                null, null, null, null);
        adapter.clear();
        while(cursor.moveToNext()){
            Integer ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            String  dvt = cursor.getString(2);
            Double donGia = cursor.getDouble(3);
            ThuocModel thuoc = new ThuocModel(ma, ten, dvt, donGia) ;
            adapter.add(thuoc);

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
        hienThiThuoc();

    }

    public void handAdd(View view) {
        Intent intent = new Intent(this, ThemHieuThuoc.class);
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
            if(selectedThuoc!=null){
                Intent intent = new Intent(this, ThemHieuThuoc.class);
                intent.putExtra("type", "sua");
                startActivity(intent);
            }
        }
        if(item.getItemId() == R.id.menuDelete){
            if(selectedThuoc!=null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa?: \n " + selectedThuoc);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Login.database= openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
                        int kq = Login.database.delete("THUOC", "MATHUOC = ?", new String[]{selectedThuoc.getMaThuoc().toString()});
                        if(kq>0){
                            Toast.makeText(activity_thuoc.this, "Xóa thuốc thành công",Toast.LENGTH_SHORT).show();
                            hienThiThuoc();
                        }else{
                            Toast.makeText(activity_thuoc.this, "Xóa thuốc thất bại vì đã có hóa đơn",Toast.LENGTH_SHORT).show();
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