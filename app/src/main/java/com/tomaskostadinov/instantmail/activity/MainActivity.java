package com.tomaskostadinov.instantmail.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tomaskostadinov.instantmail.R;

public class MainActivity extends AppCompatActivity {
    public Boolean muted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        muted = sharedPref.getBoolean("muted", true);

        FirebaseMessaging.getInstance().subscribeToTopic("mailing-service");
        FirebaseInstanceId.getInstance().getToken();
        TextView t = (TextView) findViewById(R.id.card_title);
        String text = t != null ? t.getText().toString() : null;
        Toast.makeText(MainActivity.this, FirebaseInstanceId.getInstance().getToken() ,Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, text ,Toast.LENGTH_SHORT).show();

        final ImageButton mute = (ImageButton) findViewById(R.id.mute_button);

        if (mute != null) {
            if (muted) {
                mute.setImageResource(R.drawable.ic_mute);
            } else {
                mute.setImageResource(R.drawable.ic_volume);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        Button button = (Button) findViewById(R.id.action_button);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Benachrichtigung gelöscht", Snackbar.LENGTH_SHORT).show();
                }
            });
        }

        if (mute != null) {
            mute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!muted){
                        mute.setImageResource(R.drawable.ic_mute);
                        muted  = true;
                        editor.putBoolean("muted", true);
                        editor.apply();

                    } else {
                        editor.putBoolean("muted", false);
                        editor.apply();
                        muted = false;
                        mute.setImageResource(R.drawable.ic_volume);
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            if (muted){
                return true;
            }
            Intent i = new Intent(this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("Sie haben Post!")
                    .setContentText("Ihr Briefkasten ist voll.")
                    .setSmallIcon(R.drawable.ic_mail)
                    .setContentIntent(pendingIntent)
                    .setSound(Uri.parse("android.resource://"
                            + getApplication().getPackageName() + "/"
                            + R.raw.alert))
                    .setPriority(Notification.PRIORITY_HIGH)
                    ;

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            manager.notify(0,builder.build());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
