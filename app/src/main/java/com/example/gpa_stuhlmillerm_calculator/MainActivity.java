package com.example.gpa_stuhlmillerm_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Boolean to keep track of whether we should reset the form or not on button click.
    private boolean needsReset = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public float parseScore(View view) {
        EditText grade = (EditText) view;
        float gradeValue;
        try {
            gradeValue = Float.parseFloat(grade.getText().toString());
        }
        catch (Exception ex) {
            gradeValue = -1;
        }
        return gradeValue;

    }

    public float validateGrade(View view) {
        float grade = parseScore(view);

        // Initialize a new GradientDrawable instance
        GradientDrawable gd = new GradientDrawable();
        // Set the gradient drawable background to transparent
        gd.setColor(Color.parseColor("#00ffffff"));
        // Set a border for the gradient drawable
        gd.setStroke(2,getResources().getColor(R.color.colorError));

        view.setBackground(gd);
        if (grade < 0 || grade > 100) {
            view.setBackground(gd);
            return -1;
        }
        view.setBackground(null);
        return grade;
    }

    public void buttonClick(View view) {
        Button buttonView = (Button) view;
        View root = buttonView.getRootView();
        TextView resultView = findViewById(R.id.textViewResult);

        EditText course1 = findViewById(R.id.editTextNumberDecimalCourse1);
        EditText course2 = findViewById(R.id.editTextNumberDecimalCourse2);
        EditText course3 = findViewById(R.id.editTextNumberDecimalCourse3);
        EditText course4 = findViewById(R.id.editTextNumberDecimalCourse4);
        EditText course5 = findViewById(R.id.editTextNumberDecimalCourse5);


        if (needsReset) {
            needsReset = false;
            buttonView.setText(R.string.button_default);
            resultView.setText(R.string.result_start);
            course1.setText("");
            course2.setText("");
            course3.setText("");
            course4.setText("");
            course5.setText("");
            root.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
            return;
        }

        boolean inputValid = true;
        float grade1 = validateGrade(course1);
        float grade2 = validateGrade(course2);
        float grade3 = validateGrade(course3);
        float grade4 = validateGrade(course4);
        float grade5 = validateGrade(course5);


        if (grade1 == -1 || grade2 == -1 || grade3 == -1 || grade4 == -1 || grade5 == -1) {
            resultView.setText(R.string.result_error);
        }
        else {
            needsReset = true;
            buttonView.setText(R.string.button_reset);

            double gpa = (grade1 + grade2 + grade3 + grade4 + grade5) / 5.0;
            resultView.setText(String.format("%.2f", gpa));
            if (gpa >= 80) {
                root.setBackgroundColor(getResources().getColor(R.color.colorHigh));
            }
            else if (gpa >= 60) {
                root.setBackgroundColor(getResources().getColor(R.color.colorMed));
            }
            else {
                root.setBackgroundColor(getResources().getColor(R.color.colorLow));
            }
        }
    }
}