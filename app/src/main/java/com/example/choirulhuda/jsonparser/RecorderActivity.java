package com.example.choirulhuda.jsonparser;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioRecord;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by choirul.huda on 03/03/2016.
 */
public class RecorderActivity extends Activity implements View.OnClickListener {

    ToggleButton btnVoiceNotes;
    ImageButton btnPlay;
    ImageButton btnStop;
    Button btnSave;
    LinearLayout playerLayout;
    private com.example.choirulhuda.AudioRecord record;
    private Bundle mArguments;
    public int SURVEY_MODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);

        playerLayout = (LinearLayout)findViewById(R.id.recorderLayout);
        btnVoiceNotes = (ToggleButton) findViewById(R.id.btnVoiceNotes);
        TextView noVoiceNote = (TextView)findViewById(R.id.txtNoVoiceNote);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnStop = (ImageButton) findViewById(R.id.btnStop);
        btnSave= (Button) findViewById(R.id.buttonSave);



        /*btnVoiceNotes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(btnVoiceNotes.isChecked()){
                    //start record
                    record.startRecording(v);
                    playerLayout.setVisibility(View.GONE);
                    btnSave.setVisibility(View.GONE);
                }else{
                    //stop record
                    record.stop(v);
                    header.setVoice_note(null);
                    playerLayout.setVisibility(View.VISIBLE);
                    btnSave.setVisibility(View.VISIBLE);
                }
            }
        });*/

        /*btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                byte[] voiceNotes = null;
                try {
                    voiceNotes = record.saveAudioToByte();
                    header.setVoice_note(record.saveAudioToByte());
                } catch (Exception e) {
                    header.setVoice_note(header.getVoice_note());
                }
                if(voiceNotes!=null&&voiceNotes.length>0)
                    CustomerFragment.header.setVoice_note(voiceNotes);
                Intent intent = new Intent();
                intent.putExtra(Global.BUND_KEY_DETAIL_DATA, voiceNotes);
                setResult(Global.REQUEST_VOICE_NOTES, intent);
                finish();
            }
        });*/
        /*btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (SURVEY_MODE == Global.MODE_SURVEY_TASK||
                        SURVEY_MODE == Global.MODE_VIEW_SENT_SURVEY) {//
                    if(header.getVoice_note()!=null){
                        AudioRecord.playAudio(getApplicationContext(), header.getVoice_note());
                    }else{
                        record.play(v);
                    }
                }else{
                    if(header.getVoice_note()!=null){
                        AudioRecord.playAudio(getApplicationContext(), header.getVoice_note());
                    }else{
                        record.play(v);
                    }
                }
                btnSave.setVisibility(View.GONE);
            }
        });*/
        /*btnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                record.stopPlay(v);
                btnSave.setVisibility(View.VISIBLE);
                try {
                    if(DynamicFormActivity.isVerified||DynamicFormActivity.isApproval){
                        btnSave.setVisibility(View.GONE);
                    }
                } catch (Exception e) {}
                try {
                    AudioRecord.stopPlay(v);
                } catch (Exception e) {}
            }
        });*/

    }


    @Override
    public void onClick(View v) {
        int i = 0;
        if (v == btnVoiceNotes){

            for (i=0; i<30000; i++){

            }
        }
    }
}
