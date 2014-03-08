package com.example.musicplayer;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.TextView;

	public class MainActivity extends Activity implements OnPreparedListener, MediaController.MediaPlayerControl {
		private static final String TAG = "AudioPlayer";

		  public static final String AUDIO_FILE_NAME = Environment.getExternalStorageDirectory()+"/Samsung/Music/Song.mp3";

		  private MediaPlayer mediaPlayer;
		  private MediaController mediaController;
		  private String audioFile;

		  private Handler handler = new Handler();

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			this.getIntent().putExtra(AUDIO_FILE_NAME,AUDIO_FILE_NAME);
			audioFile = this.getIntent().getStringExtra(AUDIO_FILE_NAME);
		    ((TextView)findViewById(R.id.now_playing_text)).setText(audioFile);

		    mediaPlayer = new MediaPlayer();
		    mediaPlayer.setOnPreparedListener(this);

		    mediaController = new MediaController(this);

		    try {
		      mediaPlayer.setDataSource(audioFile);
		      mediaPlayer.prepare();
		      mediaPlayer.start();
		    } catch (IOException e) {
		      Log.e(TAG, "Could not open file " + audioFile + " for playback.", e);
		    }

		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		@Override
		  protected void onStop() {
		    super.onStop();
		    mediaController.hide();
		    mediaPlayer.stop();
		    mediaPlayer.release();
		  }

		  @Override
		  public boolean onTouchEvent(MotionEvent event) {
		    //the MediaController will hide after 3 seconds - tap the screen to make it appear again
		    mediaController.show();
		    return false;
		  }

		  //--MediaPlayerControl methods----------------------------------------------------
		  public void start() {
		    mediaPlayer.start();
		  }

		  public void pause() {
		    mediaPlayer.pause();
		  }

		  public int getDuration() {
		    return mediaPlayer.getDuration();
		  }

		  public int getCurrentPosition() {
		    return mediaPlayer.getCurrentPosition();
		  }

		  public void seekTo(int i) {
		    mediaPlayer.seekTo(i);
		  }

		  public boolean isPlaying() {
		    return mediaPlayer.isPlaying();
		  }

		  public int getBufferPercentage() {
		    return 0;
		  }

		  public boolean canPause() {
		    return true;
		  }

		  public boolean canSeekBackward() {
		    return true;
		  }

		  public boolean canSeekForward() {
		    return true;
		  }
		  //--------------------------------------------------------------------------------

		  public void onPrepared(MediaPlayer mediaPlayer) {
		    Log.d(TAG, "onPrepared");
		    mediaController.setMediaPlayer(this);
		    mediaController.setAnchorView(findViewById(R.id.main_audio_view));

		    handler.post(new Runnable() {
		      public void run() {
		        mediaController.setEnabled(true);
		        mediaController.show();
		      }
		    });
		  }

		@Override
		public int getAudioSessionId() {
			// TODO Auto-generated method stub
			return 0;
		}

	}

