package com.indeema.microphonetest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.indeema.microphonetest.AMR.ThreeGPHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 2000;

    private MediaRecorder mMediaRecorder;
    private MediaPlayer mMediaPlayer;

    private String mFileName;

    private String[] permissions = new String[] { Manifest.permission.RECORD_AUDIO };

    private TextView mActionLabel;
    private Button mStartRecordButton;
    private Button mPlayButton;
    private Button mConvert;

    private boolean isRecord = false;
    private boolean isPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionLabel = (TextView)findViewById(R.id.tv_action_label);
        mStartRecordButton = (Button) findViewById(R.id.bt_start_record);
        mPlayButton = (Button)findViewById(R.id.bt_play);
        mConvert = (Button)findViewById(R.id.bt_convert);

        mStartRecordButton.setOnClickListener(this);
        mPlayButton.setOnClickListener(this);
        mConvert.setOnClickListener(this);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        mFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        mFileName += "/test_record.3gp";
        Log.d(TAG, "Output audio file -> " + mFileName);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Microphone record permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    setResult(Activity.RESULT_CANCELED, new Intent());
                }
                break;
            }
        }
    }

    /**
     * Start voice recording from device microphone.
     */
    private void startRecording() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mMediaRecorder.setOutputFile(mFileName);
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) { e.printStackTrace(); }
        mMediaRecorder.start();
        mStartRecordButton.setText("Stop Record");
        isRecord = true;
        mPlayButton.setClickable(false);
        Log.d(TAG,"Start recording");
    }

    /**
     * Stop voice recording from device microphone.
     */
    private void stopRecording() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mStartRecordButton.setText("Start Record");
        isRecord = false;
        mPlayButton.setClickable(true);
        Log.d(TAG,"Stop recording");
    }

    private void startPlay() {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(mFileName);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) { e.printStackTrace(); }
        isPlay = true;
        mPlayButton.setText("Stop");
        mStartRecordButton.setClickable(false);
        Log.d(TAG,"Start play");
    }

    private void stopPlay() {
        mMediaPlayer.release();
        isPlay = false;
        mPlayButton.setText("Play");
        mStartRecordButton.setClickable(true);
        Log.d(TAG,"Stop play");
    }

    private void convert() {
        File file = new File(mFileName);
        if (file == null)
            return;
        try {

            byte[] byteArray = new byte[(int)file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(byteArray);

            ThreeGPHelper.ParseThreeGPFile(byteArray);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            Log.d(TAG, "Convert : bytes -> " + byteArray.length);

        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bt_start_record: {
                if (!isRecord) {
                    startRecording();
                } else {
                    stopRecording();
                }
                break;
            }
            case R.id.bt_play: {
                if (!isPlay) {
                    startPlay();
                } else {
                    stopPlay();
                }
                break;
            }
            case R.id.bt_convert: {
                convert();
                break;
            }
            default: break;
        }
    }
}
