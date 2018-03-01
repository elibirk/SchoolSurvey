package com.example.spec.cis410_hw5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewResult extends AppCompatActivity {

    //get answers and questions
    Integer[] S1answers = TakeSurvey.getAnswers1();
    Integer[] S2answers = TakeSurvey.getAnswers2();
    String[] S345answers = TakeSurvey.getAnswers345();

    String[] s345q = TakeSurvey.getQuestions345();

    static String last = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);

        //get textviews
        TextView S1results = (TextView) findViewById(R.id.tv_result1);
        TextView S2results = (TextView) findViewById(R.id.tv_result2);
        TextView S345results = (TextView) findViewById(R.id.tv_result345);

        //floats to hold average scores
        float instructorAVG = 0;
        float courseAVG = 0;

        //get answers if saved
        SharedPreferences prefs = getSharedPreferences(
                "com.example.spec", MODE_PRIVATE);
        float iAVG = prefs.getFloat("iAVG", -1);
        float cAVG = prefs.getFloat("cAVG", -1);

        S1results.setText("Instructor score average:\n" + iAVG + "\n");
        S2results.setText("Course score average:\n" + cAVG + "\n");

        //if answers sections are full, display the answers
            if (!isEmptyIntArray(S1answers)) {
                for (int i = 0; i < S1answers.length; i++) {
                    instructorAVG = instructorAVG + S1answers[i];
                }
                instructorAVG = instructorAVG / 12;
                S1results.setText("Instructor score average:\n" + instructorAVG);
                Log.d("iAVG", Float.toString(instructorAVG));
                prefs.edit().remove("iAVG");
                prefs.edit().putFloat("iAVG", instructorAVG).commit();
            }
            if (!isEmptyIntArray(S2answers)) {
                for (int i = 0; i < S2answers.length - 1; i++) {
                    courseAVG = courseAVG + S2answers[i];
                }
                courseAVG = courseAVG / 7;
                S2results.setText("Course score average:\n" + courseAVG);
                prefs.edit().remove("cAVG");
                prefs.edit().putFloat("cAVG", instructorAVG).commit();
            }


                S345results.setText("");
                for (int i = 0; i < S345answers.length; i++) {
                    Log.d("ans", S345answers[i]);
                    if(S345answers[i].equals("")){
                        if(prefs.getString("345" + Integer.toString(i), "").equals("")){
                            last = ""; //if it's not saved either, then the survey isn't finished
                        }
                    } else {
                        prefs.edit().remove("345" + Integer.toString(i));
                        prefs.edit().putString("345" + Integer.toString(i), S345answers[i]).commit();
                    }
                }//end for
                for (int i = 0; i < s345q.length; i++) {
                    S345results.append(s345q[i] + "\n");
                    String str = prefs.getString("345" + Integer.toString(i), "");
                    S345results.append(str + "\n\n");
                    if (i == 6) {
                        last = str;
                    }
                }//end for


            //if any sections are partially empty, give a warning
            if (isEmptyStringArray(S345answers) || isEmptyIntArray(S1answers) || isEmptyIntArray(S2answers)) {
                String temp = "";
                for (int i = 0; i < S1answers.length; i++) {
                    temp = temp + S1answers[i].toString();
                    if (S1answers[i].toString().equals("")) {
                        temp = temp + "+";
                    }
                }
                for (int i = 0; i < S2answers.length; i++) {
                    temp = temp + S2answers[i].toString();
                    if (S2answers[i].toString().equals("")) {
                        temp = temp + "+";
                    }
                }
                for (int i = 0; i < S345answers.length; i++) {
                    temp = temp + S345answers[i];
                    if (S345answers[i].equals("")) {
                        temp = temp + "+";
                    }
                }
            }//if no results, display error

    }//onCreate

    //functions for testing if the arrays are partially empty
    public static boolean isEmptyStringArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == "") {
                return true;
            }
        }
        return false;
    }

    public boolean isEmptyIntArray(Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFinished(){
        if(last !=""){
            return true;
        }
        return false;
    }

    public static void adminReset(){
        last = "";
    }

    public void goToMain(View v){
        //finish(); would take you back to the questions
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}//end class
