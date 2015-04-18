package com.it.tora;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.speech.RecognitionListener;

public class HomeFragment extends Fragment {

    private SpeechRecognizer sr;
    private ToggleButton toggle;
    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ToggleButton toggle;
        toggle = (ToggleButton) rootView.findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    startRecog();


                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });


        return rootView;
    }



    public void startRecog(){

        sr = SpeechRecognizer.createSpeechRecognizer(getActivity().getApplicationContext());
        SpeechRecognition listener = new SpeechRecognition();
        sr.setRecognitionListener(listener);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "");
        sr.startListening(intent);

    }
}