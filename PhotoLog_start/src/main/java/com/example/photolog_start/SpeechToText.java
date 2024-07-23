package com.example.photolog_start;

import static android.Manifest.permission.RECORD_AUDIO;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;

import java.util.ArrayList;
import java.util.Locale;

public final class SpeechToText {
    public static void startListening(Activity activity, String prompt) {
        // Intent to listen to user vocal input and return the result to the same activity.
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Use a language model based on free-form speech recognition.
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        //Message to display in dialog box
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, prompt);
        try {
            activity.startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "SPEECH NOT SUPPORTED", Toast.LENGTH_SHORT).show();
        }
    }

//    @RequiresPermission(allOf = {"android.permission.MANAGE_SPEECH_RECOGNITION", "android.permission.RECORD_AUDIO"})
//    @RequiresPermission(RECORD_AUDIO)
    public static void startListeningWithoutDialog(Context appContext) {
        // Intent to listen to user vocal input and return the result to the same activity.
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Use a language model based on free-form speech recognition.
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        //Message to display in dialog box
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, appContext.getPackageName());
        // Add custom listeners.
        CustomRecognitionListener listener = new CustomRecognitionListener() {
            @Override
            public void onError(int error) {
                super.onError(error);
            }

            @Override
            public void onResults(Bundle results) {
                super.onResults(results);
                // TODO
            }
        };
        SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(appContext);
        sr.setRecognitionListener(listener);
        sr.startListening(intent);
    }

    static class CustomRecognitionListener implements RecognitionListener {
        private static final String TAG = "RecognitionListener";

        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "onEndOfSpeech");
        }

        @Override
        public void onError(int error) {
            Log.e(TAG, "onError " + error);
//                SpeechRecognizer.ERROR_NETWORK_TIMEOUT
//                SpeechRecognizer.ERROR_NETWORK
//                SpeechRecognizer.ERROR_AUDIO
//                SpeechRecognizer.ERROR_SERVER
//                SpeechRecognizer.ERROR_CLIENT
//                SpeechRecognizer.ERROR_SPEECH_TIMEOUT
//                SpeechRecognizer.ERROR_NO_MATCH
//                SpeechRecognizer.ERROR_RECOGNIZER_BUSY
//                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS
//                SpeechRecognizer.ERROR_TOO_MANY_REQUESTS
//                SpeechRecognizer.ERROR_SERVER_DISCONNECTED
//                SpeechRecognizer.ERROR_LANGUAGE_NOT_SUPPORTED
//                SpeechRecognizer.ERROR_LANGUAGE_UNAVAILABLE
//                SpeechRecognizer.ERROR_CANNOT_CHECK_SUPPORT
        }

        @Override
        public void onResults(Bundle results) {
            Log.d(TAG, "onResults");
            ArrayList<String> data = results.getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
            if (data != null) {
                for (String result : data) {
                    Log.d(TAG, result);
                }
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
}
