package com.lyy.copysmscode;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putBoolean("first_open", true);
        editor.apply();

        obtainPermission();
    }

    private void obtainPermission() {

        boolean first_open = getSharedPreferences("data", MODE_PRIVATE).getBoolean("first_open", false);

        RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
        rxPermissions
                .request(Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS)
                .subscribe(granted -> {
                    if (granted) {
                        if (first_open) {
                            editor.putBoolean("first_open", false);
                            Toast.makeText(MainActivity.this, "您已获得读取短信权限", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "未获取读取短信的权限", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
