package com.andrewlegaspino.formulary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.*;

public class FormularyOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulary_options);

        String[] formularyoptions = { "Math", "Physics", "Statistics", "Chemistry", "Electrical Engineering" };
        LinearLayout linearlayout = (LinearLayout) findViewById(R.id.layout_linear);

        for (String option : formularyoptions) {
            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
           // need to figure out how to use getResources.getString(<strings.xml resource>)
           // dont know if getString() takes a string as parameter or R.string.*
            button.setText(option);
           // button.setId();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FormularyOptionsActivity.this, MathSubjectsActivity.class));
                }
            });
            linearlayout.addView(button);
        }

    }
}
