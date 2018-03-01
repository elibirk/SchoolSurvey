package com.example.spec.cis410_hw5;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ViewFragment extends Fragment {

    int position;
    int ans = 0;
    ViewGroup rootView;
    String q;
    SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(
                R.layout.q1, container, false);

        //access preferences
        prefs = getContext().getSharedPreferences(
                "com.example.spec", getContext().MODE_PRIVATE);

        //get info about the section & question
        if (getArguments().containsKey("title")) {
            String strtext = getArguments().getString("title");
            TextView title = (TextView) rootView.findViewById(R.id.survey_sectionTitle);
            title.setText(strtext);
        }

        if (getArguments().containsKey("position")) {
            position = getArguments().getInt("position");
        }

        if (getArguments().containsKey("question")) {
            String strtext = getArguments().getString("question");
            TextView question = (TextView) rootView.findViewById(R.id.survey_question);
            question.setText(strtext);
            q = strtext;

            //get answer if it's available
            int rbid = prefs.getInt(q, -1);
            RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.survey_answer);
            radioGroup.check(rbid);
            ans = getNumericAns();
        }

        //based on section, decide what to do
        if (getArguments().getString("endSec") != null) {
            String strtext = getArguments().getString("endSec");
            if (strtext.equals("s1")) {

                RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.survey_answer);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                        {
                            @Override //listener detects button choice, auto-saves
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                // checkedId is the RadioButton selected
                                ans = getNumericAns();

                                //save the answer
                                TakeSurvey.addAnswer1(position, ans);
                                RadioGroup radioGroup = (RadioGroup) getView().findViewById(R.id.survey_answer);
                                int rbid = radioGroup.getCheckedRadioButtonId();
                                prefs.edit().remove(q);
                                prefs.edit().putInt(q, rbid).commit();
                            }
                        });
                        TakeSurvey.addAnswer1(position, ans);
            }
            else if (strtext.equals("s2")) {

                RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.survey_answer);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                        {
                            @Override //listener detects button choice, auto-saves
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                // checkedId is the RadioButton selected
                                ans = getNumericAns();

                                //save the answer
                                TakeSurvey.addAnswer2(position, ans);
                                RadioGroup radioGroup = (RadioGroup) getView().findViewById(R.id.survey_answer);
                                int rbid = radioGroup.getCheckedRadioButtonId();
                                prefs.edit().remove(q);
                                prefs.edit().putInt(q, rbid).commit();
                            }
                        });//end oncheckedchange listener
                TakeSurvey.addAnswer2(position, ans);
            }//end else if
        }//end if

        return rootView;
    }//end onCreate


    public int getNumericAns (){

        //convert answer to numbers for averages
        RadioButton rb = (RadioButton) rootView.findViewById(R.id.rb_sa);
        if(rb.isChecked()) {
            ans = 5;}
        rb = (RadioButton) rootView.findViewById(R.id.rb_a);
        if(rb.isChecked()) {
            ans = 4;}
        rb = (RadioButton) rootView.findViewById(R.id.rb_n);
        if(rb.isChecked()) {
            ans = 3;}
        rb = (RadioButton) rootView.findViewById(R.id.rb_d);
        if(rb.isChecked()) {
            ans = 2;}
        rb = (RadioButton) rootView.findViewById(R.id.rb_sd);
        if(rb.isChecked()) {
            ans = 1;}

        return ans;
    }//end getNumericAns

}//end class

