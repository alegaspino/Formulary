package com.andrewlegaspino.formulary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.*;
import android.widget.LinearLayout.*;

public class FormularyOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulary_options);

        final String[] formularyoptions = getResources().getStringArray(R.array.formularyoptions);
        LinearLayout verticalscreen = (LinearLayout) findViewById(R.id.layout_verticalscreen);

        for (int j=0; j<formularyoptions.length; j++) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setWeightSum(3.0f);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));

            for (int i = 0; i < 3; i++) {
                Button button = new Button(this);
                button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 250, 1.0f));
                button.setText(formularyoptions[j]);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FormularyOptionsActivity.this, FormulasActivity.class);
                       // intent.putExtra("BUTTON_OPTION", formularyoptions[j]);
                        startActivity(intent);
                    }
                });
                row.addView(button);
                j++;
                if (j>= formularyoptions.length) { break; }
            }
            j--;

            verticalscreen.addView(row);
        }

    }
}
