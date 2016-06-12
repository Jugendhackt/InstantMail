package com.tomaskostadinov.instantmail.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

        FirebaseMessaging.getInstance().subscribeToTopic("mailing-service");
        FirebaseInstanceId.getInstance().getToken();
        TextView t = (TextView) findViewById(R.id.card_title);
        String text = t != null ? t.getText().toString() : null;
        Toast.makeText(MainActivity.this, FirebaseInstanceId.getInstance().getToken() ,Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, text ,Toast.LENGTH_SHORT).show();

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

        final ImageButton mute = (ImageButton) findViewById(R.id.mute_button);
        if (mute != null) {
            mute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (muted == false){
                        mute.setImageResource(R.drawable.ic_mute);
                        muted  = true;
                    } else {
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
