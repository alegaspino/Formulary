package com.lego.formulary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class SearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText et = getView().findViewById(R.id.search_edittext);
        final Button button_read = getView().findViewById(R.id.search_buttonread);
        final Button button_write = getView().findViewById(R.id.search_buttonwrite);
        final TextView tv = getView().findViewById(R.id.search_textview);

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
                writeFile(et);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_searchbar, menu);
    }

    public String readFile() {
        String text = "";

        try {
            FileInputStream fis = getActivity().openFileInput("file.txt");
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

    public void writeFile(EditText et) {
        try {
            String all = readFile();
            String piece = et.getText().toString();
            if (!"".equalsIgnoreCase(piece)) {
                all += piece + "~";
            }

            FileOutputStream fos = getActivity().openFileOutput("file.txt", Context.MODE_PRIVATE);
            fos.write(all.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
