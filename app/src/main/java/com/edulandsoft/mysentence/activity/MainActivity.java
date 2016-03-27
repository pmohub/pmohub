package com.edulandsoft.mysentence.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.edulandsoft.mysentence.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "psh MainActivity";

    Animation scaleAni;

    ImageView imgLogo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //-----------------------------------------------
        // Variable
        //-----------------------------------------------

        //-----------------------------------------------
        // Intro Animation
        //-----------------------------------------------
        Log.d(TAG, "Intro Animation");

        setContentView(R.layout.intro);
        imgLogo = (ImageView) findViewById(R.id.imageView);

        /*
        Animator anim = AnimatorInflater.loadAnimator(this, R.anim.scale4);
        anim.setTarget(imgLogo);
        anim.setDuration(1000);
        anim.setStartDelay(10);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                Toast.makeText(MainActivity.this, "Started...111", Toast.LENGTH_SHORT).show();
            }

            ;
        });
        anim.start();
        */


        scaleAni = AnimationUtils.loadAnimation(this, R.anim.small_large_scale);
        imgLogo.startAnimation(scaleAni);

        scaleAni.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation arg0) {
                //SystemClock.sleep(500);
                imgLogo.setVisibility(imgLogo.INVISIBLE);
                //-----------------------------------------------
                // intent HomeActivity
                //-----------------------------------------------
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


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
