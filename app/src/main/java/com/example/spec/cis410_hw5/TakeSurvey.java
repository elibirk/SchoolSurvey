package com.example.spec.cis410_hw5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;

public class TakeSurvey extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    //arrays to store info
    private final String[] STitles = {"1. About The Instructor", "2. About The Course",
            "3. Student Information", "4. Comments About Instructor", "5. Comments About Course"};

    private static final String[] S1questions = {"1.1 created an academic environment that supported the learning process.",
            "1.2 modeled respect to others and the values of Fontbonne University.",
            "1.3 demonstrated enthusiasm for teaching and learning.",
            "1.4 followed the policies outlined in the syllabus.",
            "1.5 presented the content in a logical and organizaed manner.",
            "1.6 challenged me to think in new and different ways.",
            "1.7 addressed the objectives stated in the syllabus.",
            "1.8 encouraged my interest in the course.", "1.9 provided timely feedback.",
            "1.10 provided useful feedback.", "1.11 make her/his availability known.",
            "1.12 Overall, te instructor effectively facilitated my learning."};

    private static final String[] S2questions = {"2.1 Syllabus clearly stated the learning objectives.",
            "2.2 Syllabus clearly stated the overall grading policy",
            "2.3 Assignments were clearly stated.",
            "2.4 Assignments/activities/tests helped me attain the learning objectives.",
            "2.5 Grading criteria for assignments were clearly stated.",
            "2.6 Textbook and/or materials were helpful in attaining the learning objectives.",
            "2.7 Overall, I rate this course as excellent."};

    private static final String[] S3questions = {"3.1 For me this course was:",
            "3.2 My student classification is:", "3.3 I regularly attended class",
            "3.4 I submitted homework on time:", "3.5 I asked questions when I did not understand:",
            "4. Comments About Instructor", "5. Comments About Course"};

    static Integer[] S1answers = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static Integer[] S2answers = {0, 0, 0, 0, 0, 0, 0};
    static String[] S3answers = {"", "", "", "", "", "", ""}; //includes space for Sections 4 and 5

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_survey);

        //if preferences are full, don't allow a retake
        if (ViewResult.isFinished()) {
            TextView warning = (TextView) findViewById(R.id.warning);
            warning.setText("You've already taken this survey. You can go back and view results" +
                    " or contact an admin if this is an error.");

            //Secret button to clear preferences for retesting. Would be removed in production
            RelativeLayout layout = (RelativeLayout)findViewById(R.id.survey);
            Button clear = new Button(this);

            clear.setText("");
            clear.setBackgroundColor(Color.TRANSPARENT);//invisible to ward off unsuspecting users
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prefs = getSharedPreferences(
                            "com.example.spec", MODE_PRIVATE);
                    editor = prefs.edit();
                    editor.clear();
                    editor.commit();
                    ViewResult.adminReset();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    }
                });
                clear.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                );
                layout.addView(clear);

        } else {
            // Instantiate a ViewPager and a PagerAdapter.
            mPager = (ViewPager) findViewById(R.id.questions);
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
        }
    }//onCreate


    public void onBackPressed() {//deal with backbutton

        if(ViewResult.isFinished()){
            super.onBackPressed();//if we have no pages, just let us return like normal
        }else {

            if (mPager.getCurrentItem() == 0) {
                // If the user is currently looking at the first step, allow the system to handle the
                // Back button.
                super.onBackPressed();
            } else {
                // Otherwise, select the previous question
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        }
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ViewFragment fragment;
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);

            //based on the position (Thus the question), pass on needed info
            if(position < Array.getLength(S1questions)) {
                fragment = new ViewFragment();
                bundle.putString("title", STitles[0]);
                bundle.putString("question", S1questions[position]);
                    bundle.putString("endSec", "s1");
                fragment.setArguments(bundle);
                return fragment;
            }
            else if(position < Array.getLength(S2questions) + Array.getLength(S1questions)){
                fragment = new ViewFragment();
                bundle.putString("title", STitles[1]);
                bundle.putString("question", S2questions[position - Array.getLength(S1questions)]);
                    bundle.putString("endSec", "s2");
                fragment.setArguments(bundle);
                return fragment;
            }
            else if(position == Array.getLength(S2questions) + Array.getLength(S1questions)){
               ViewFragment3_1 fragment3_1 = new ViewFragment3_1();
                bundle.putString("title", STitles[2]);
                bundle.putString("question", S3questions[position - Array.getLength(S2questions) -
                        Array.getLength(S1questions)]);
                    bundle.putString("endSec", "s3");
                fragment3_1.setArguments(bundle);
                return fragment3_1;
            }
            else if(position == Array.getLength(S2questions) + Array.getLength(S1questions) +1 ){
                ViewFragment3_2 fragment3_2 = new ViewFragment3_2();
                bundle.putString("title", STitles[2]);
                bundle.putString("question", S3questions[position - Array.getLength(S2questions) -
                        Array.getLength(S1questions)]);
                    bundle.putString("endSec", "s3");
                fragment3_2.setArguments(bundle);
                return fragment3_2;
            }
            else if(position > Array.getLength(S2questions) + Array.getLength(S1questions) +1 &&
                    position < Array.getLength(S2questions) + Array.getLength(S1questions) + 5){
                ViewFragment3_456 fragment3_456 = new ViewFragment3_456();
                bundle.putString("title", STitles[2]);
                bundle.putString("question", S3questions[position - Array.getLength(S2questions) -
                        Array.getLength(S1questions)]);
                    bundle.putString("endSec", "s3");
                fragment3_456.setArguments(bundle);
                return fragment3_456;
            }
            else {
                ViewFragment45 fragment45 = new ViewFragment45();
                bundle.putString("endSec", "s4");
                bundle.putString("question", S3questions[position - Array.getLength(S2questions) -
                        Array.getLength(S1questions)]);
                if (position == 25) {
                    bundle.putString("endSec", "final");
                    bundle.putString("title", "5. Comments About Course");
                }
                fragment45.setArguments(bundle);
                return fragment45;
            }//end else
        }//end getItem

        @Override
        public int getCount() {
            return 26;
        }
    }//end slideAdapter class


    //functions to add answers to our arrays
    public static void addAnswer1(int position, int ans){
        S1answers[position] = ans;
    }

    public static void addAnswer2(int position, int ans){
        S2answers[position - 12] = ans;
    }

    public static void addAnswer345(int position, String ans){
        S3answers[position - 12 - 7] = ans;
    }


    //functions to get info from arrays as needed
    public static String[] getQuestions345(){
        return S3questions;
    }
    public static Integer[] getAnswers1(){
        return S1answers;
    }
    public static Integer[] getAnswers2(){
        return S2answers;
    }
    public static String[] getAnswers345(){
        return S3answers;
    }

}//end class
