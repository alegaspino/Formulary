package com.andrewlegaspino.formulary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.ArrayAdapter;


public class MathSubjectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_subjects);

        String[] mathsubjectsarray = { "Algebra 1",
                                       "Geometry",
                                       "Trigonometry",
                                       "Pre-Calculus",
                                       "Calculus" };

        ListView mathsubjects = (ListView) findViewById(R.id.math_subjects);

        ArrayAdapter arrayadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mathsubjectsarray);
        mathsubjects.setAdapter(arrayadapter);
    }
}
