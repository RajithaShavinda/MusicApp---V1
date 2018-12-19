package srilanka.lasith.android.musicapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Play_Activity extends Activity {

    ImageView img_view, play, stop, pause;
    TextView tv1, tv2;

    String url;
    final MediaPlayer mediaPlayer = new MediaPlayer();

    boolean notStopped = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_play);

        img_view = findViewById(R.id.img_View);
        tv1 = findViewById(R.id.txt_play_name);
        tv2 = findViewById(R.id.txt_play_des);

        play = findViewById(R.id.img_play);
        stop = findViewById(R.id.img_stop);
        pause = findViewById(R.id.img_pause);

        byte[] bytes = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        img_view.setImageBitmap(bitmap);

        tv1.setText(getIntent().getStringExtra("name"));
        tv2.setText(getIntent().getStringExtra("des"));
        url = getIntent().getStringExtra("url");


        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            Toast.makeText(Play_Activity.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();

        }
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            Toast.makeText(Play_Activity.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        mediaPlayer.start();


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notStopped) {
                    mediaPlayer.start();
                } else {

                    try {

                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                    } catch (IOException e) {
                        Toast.makeText(Play_Activity.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                Toast.makeText(Play_Activity.this, "PLAY", Toast.LENGTH_SHORT).show();
                notStopped = true;
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();

                Toast.makeText(Play_Activity.this, "PAUSE", Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                notStopped = false;
                Toast.makeText(Play_Activity.this, "STOP", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        mediaPlayer.stop();

        Toast.makeText(Play_Activity.this, "Media Stopped", Toast.LENGTH_LONG).show();

        this.finish();
    }
}
