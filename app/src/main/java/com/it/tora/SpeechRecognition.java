package com.it.tora;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.util.Log;
import android.widget.ImageButton;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Visin on 18/04/2015.
 *
 * Per lanciarlo
 * sr = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
 * SpeechToCar listener = new SpeechToCar();
 * sr.setRecognitionListener(listener);
 * Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
 * intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "");
 * sr.startListening(intent);
 *
 */
public class SpeechRecognition implements RecognitionListener {

    private final String FDXON = "destra";
    private final String FDXOFF = "spegni";
    private final String FSXON = "sinistra";
    private final String FSXOFF = "spegni";
    private final String HORN = "suona";
    private final String HORNOFF = "spegni";
    private final String FPARKON = "parcheggia";
    private final String FPARKOFF = "spegni";

    private HomeFragment home;


    public SpeechRecognition(HomeFragment homeFragment){
        home=homeFragment;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.d("pronto", "");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("inizio","iinixio a paralere");

        home.startSpeak();

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
                /*Log.d("fine","fine a paralere");
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "");
                sr.startListening(intent);*/
        home.endSpeak();
    }

    @Override
    public void onError(int i) {

        home.endSpeak();


        switch (i){
            case 1:
                Log.d("error","Network operation timed out");
                break;
            case 2:
                Log.d("error","Other network related errors");
                break;
            case 3:
                Log.d("error","Audio recording error.");
                break;
            case 4:
                Log.d("error","Server sends error status.");
                break;
            case 5:
                Log.d("error","Other client side errors.");
                break;
            case 6:
                Log.d("error","Timeout - No speech input ");
                break;
            case 7:
                Log.d("error","No recognition result matched");
                break;
            case 8:
                Log.d("error","RecognitionService busy");
                break;
            case 9:
                Log.d("error","Insufficient permissions ");
                break;
        }


    }

    @Override
    public void onResults(Bundle bundle) {
        final ArrayList risultati=(ArrayList)bundle.get("results_recognition");
        Log.d("Risultato", risultati.toString());
        home.commandSent(filterResult(risultati.get(0).toString()));
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    try {



                        switch (filterResult(risultati.get(0).toString())){
                            case FDXON:
                                sendRequest("FSXON");
                                break;
                            case FSXON:
                                sendRequest("FDXON");
                                break;
                            case HORN:
                                sendRequest("HORN");
                                break;
                            case FPARKON:
                                sendRequest("FPARKON");
                                break;
                            default:
                                sendRequest("FPARKOFF");
                                break;

                        }

                    }catch (Exception ex){
                        Log.d("Risultato", ex.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    private String filterResult(String s) {



        if(s.indexOf(FDXON)!=-1){
            return FDXON;
        }else if(s.indexOf(FSXON)!=-1){
            return FSXON;
        }else if(s.indexOf(HORN)!=-1 || s.indexOf("pirla")!=-1|| s.indexOf("pirata")!=-1){
            return HORN;
        }else if(s.indexOf(FPARKON)!=-1|| s.indexOf("parcheggi")!=-1|| s.indexOf("vigil")!=-1){
            return FPARKON;
        }else {
            return "";
        }

    }

    private void sendRequest(String command) throws MalformedURLException {
        HttpURLConnection urlConnection=null;
        try {
            URL url = new URL("http://172.20.10.8/arduino/" + command + "/DO");
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.d("Comando Inviato", command);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //readStream(in);
        }catch(Exception e){
            Log.d("Errore Rete", e.toString());
        } finally {
            urlConnection.disconnect();
        }



    }

    @Override
    public void onPartialResults(Bundle bundle) {
        ArrayList risultati=(ArrayList)bundle.get("results_recognition");
        Log.d("Risultato Parziali", risultati.toString());
    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}

