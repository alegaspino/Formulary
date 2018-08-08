package com.lego.formulary;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

public class SearchActivity extends MainActivity {

    private EditText et;
    private Button button_read, button_write;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        et = findViewById(R.id.search_edittext);
        button_read = findViewById(R.id.search_buttonread);
        button_write = findViewById(R.id.search_buttonwrite);
        tv = findViewById(R.id.search_textview);

        button_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SEARCHTEST", "readFile()="+readFile());
                tv.setText(readFile());
            }
        });

        button_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeFile();
            }
        });
    }

    public String readFile() {
        String text = "";

        try {
            FileInputStream fis = openFileInput("file.txt");
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return text;
    }

    public void writeFile() {
        try {
            String all = readFile();
            String piece = et.getText().toString();
            if (!"".equalsIgnoreCase(piece)) {
                all += piece + "~";
            }

            FileOutputStream fos = openFileOutput("file.txt", Context.MODE_PRIVATE);
            fos.write(all.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }
}