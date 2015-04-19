package com.it.tora;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.speech.RecognitionListener;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private SpeechRecognizer sr;
    private ToggleButton toggle;
    private ImageButton imgButton;

    final Handler handler = new Handler();
    private HomeFragment home;
    Timer timer;
    TimerTask timerTask;


    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        home=this;


        imgButton = (ImageButton) rootView.findViewById(R.id.imageButton);

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecog();
            }
        });


        return rootView;
    }

    int status=0;

    public void startRecog(){

        //set a new Timer
        /*
        if(status==0) {

            timer = new Timer();

            //initialize the TimerTask's job

            initializeTimerTask();

            //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
            status=1;
            timer.schedule(timerTask, 0, 8000); //
        }else{
            status=0;
            timer.cancel();
        }
        */



        sr = SpeechRecognizer.createSpeechRecognizer(getActivity().getApplicationContext());
                        SpeechRecognition listener = new SpeechRecognition(home);
                        sr.setRecognitionListener(listener);
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "");
                        sr.startListening(intent);




    }

    private void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        sr = SpeechRecognizer.createSpeechRecognizer(getActivity().getApplicationContext());
                        SpeechRecognition listener = new SpeechRecognition(home);
                        sr.setRecognitionListener(listener);
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "");
                        sr.startListening(intent);
                    }
                });
            }
        };

    }

    public void startSpeak() {
        TextView text = (TextView) getView().findViewById(R.id.status);
        text.setText("Parla!");
    }

    public void commandSent(String s) {
        TextView text = (TextView) getView().findViewById(R.id.comando);
        text.setText("Comando "+s);
    }

    public void endSpeak() {
        TextView text = (TextView) getView().findViewById(R.id.status);
        text.setText("Fine Ascolto");
    }
}