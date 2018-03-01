package com.example.spec.cis410_hw5;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ViewFragment45 extends Fragment {

    int position;
    EditText comments;
    String q;
    SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.q45, container, false);

        //get the edit text box
        comments = (EditText) rootView.findViewById(R.id.et_45);

        //access preferences
        prefs = getContext().getSharedPreferences(
                "com.example.spec", getContext().MODE_PRIVATE);

        if (getArguments().containsKey("position")) {
            int strtext = getArguments().getInt("position");
            position = strtext;
        }

        if (getArguments().getString("title") != null) {
            String strtext = getArguments().getString("title");
            TextView title = (TextView) rootView.findViewById(R.id.survey_sectionTitle);
            title.setText(strtext);
        }

        if (getArguments().getString("question") != null) {
            q = getArguments().getString("question");
            String ans = prefs.getString(q, "");
            comments.setText(ans);
            TakeSurvey.addAnswer345(position, ans);
        }


        //final save info here
        comments.addTextChangedListener(textWatcher);//save text


        //if last page
        if(position == 25) {
            Button submitButton = new Button(rootView.getContext());

            submitButton.setText("Save and Submit");
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                // FINAL SUBMIT, take to view results
                    Intent intent = new Intent(getContext(), ViewResult.class);
                    startActivity(intent);
                }
            });
            submitButton.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            );

                rootView.addView(submitButton);
        }//end if

        return rootView;
    }//end onCreate


    private TextWatcher textWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        //save text as it's being changed instead of after bc user may swipe while still clicked
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            String ans = comments.getText().toString();
            //set numeric value of answer
            TakeSurvey.addAnswer345(position, ans);

            //save answer to preferences
            prefs.edit().remove(q);
            prefs.edit().putString(q, ans).commit();
        }

    };
}

