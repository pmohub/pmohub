package com.edulandsoft.mysentence.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edulandsoft.mysentence.R;
import com.edulandsoft.mysentence.common.g;
import com.edulandsoft.mysentence.db.Dbo;
import com.edulandsoft.mysentence.db.Dbow;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import android.support.v4.app.FragmentActivity;

public class HomeActivity extends FragmentActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;

    TextView mTodayLearnCntText;
    EditText mMySentenceEdit, mTranSentenceEdit;
    Button mNextBtn;

    Dbo dbo; Cursor rs; Dbow dbow;
    String TAG = "psh HomeActivity";

    MediaPlayer startWav;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Log.d(TAG, "onCreate");

        //-----------------------------------------------
        // Google Login
        //-----------------------------------------------
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Create a GoogleApiClient instance
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

        Log.d(TAG, "mGoogleApiClient 1");

        //---------------------------------------------------------
        // Dbow,Dbo
        //---------------------------------------------------------
        dbow = new Dbow(this); dbo = new Dbo(this);
        Log.d(TAG, "mGoogleApiClient 2");

        //-----------------------------------------------
        // Variable
        //-----------------------------------------------
        startWav=MediaPlayer.create(this, R.raw.start);
        //---------------------------------------------------------
        // Learn
        //---------------------------------------------------------
        mTodayLearnCntText = (TextView) findViewById(R.id.todayLearnCntText);
        mMySentenceEdit = (EditText) findViewById(R.id.mySentenceEdit);
        mTranSentenceEdit = (EditText) findViewById(R.id.tranSentenceEdit); mTranSentenceEdit.setOnClickListener(this);
        mNextBtn = (Button) findViewById(R.id.nextBtn); mNextBtn.setOnClickListener(this);

        //test01();

        //--------------------------------------
        // State
        //--------------------------------------
        mMySentenceEdit.setFocusableInTouchMode(false);
        mTranSentenceEdit.setFocusableInTouchMode(false);

        mMySentenceEdit.setText("잠시만 기다려주세요!!");


        //HomeFirstStart();
    }

    //-----------------------------------------------
    // Google Login Callback
    //-----------------------------------------------
    public void onConnectionFailed(ConnectionResult connectionResult) {}
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d(TAG, "acct.getDisplayName()=" + acct.getDisplayName());
            Log.d(TAG, "acct.getEmail()=" + acct.getEmail());
            Log.d(TAG, "acct.getId()=" + acct.getId());

            g.googleId = acct.getId();
            g.googleName = acct.getDisplayName();
            g.googleEmail = acct.getEmail();

            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
            dbow.SelUserInfo();
            dbow.SelSentence();
            HomeStart();


        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }


    void HomeFirstStart() {

        //--------------------------------------
        // Check Google Login
        //--------------------------------------
        g.googleId = "113424592816649022426";
        g.googleName = "edulandsoft";

        //------------------------------------------------------------
        // www SelUserInfo(or InsUserInfo)
        //------------------------------------------------------------
        //--------------------------------------
        // Check Patch
        //--------------------------------------

        //--------------------------------------
        // In local, insert dbow.SelSentence
        //--------------------------------------
        //dbow.SelSentence();
        //HomeStart();
    }

    public void HomeStart() {

        //--------------------------------------
        // Learn
        //--------------------------------------
        dbow.SelUserLearnHis2();

        mTodayLearnCntText.setText(String.valueOf(g.todayLearnCnt));
        //Dictionary.SetActive(false);

        //--------------------------------------
        // Set Learn
        //--------------------------------------
        ShowNextSentence();
    }

    void ShowNextSentence() {

        Log.d(TAG, "ShowNextSentence");

        //if(g.curIdx > g.lastIdx) dbo.InsSentenceT();
        dbo = Dbo.getInstance(this); dbo.open();
        dbo.InsSentenceT();

        rs = dbo.SelSentenceT(g.curIdx);
        Log.d(TAG, "ShowNextSentence111111111");

        rs.moveToFirst();

        Log.d(TAG, "ShowNextSentence222222222");


        g.userId       = rs.getString(rs.getColumnIndex("userId"));
        g.registNum    = rs.getInt(rs.getColumnIndex("registNum"));
        g.keyword      = rs.getString(rs.getColumnIndex("keyword"));
        g.mySentence   = rs.getString(rs.getColumnIndex("mySentence"));
        g.tranSentence = rs.getString(rs.getColumnIndex("tranSentence"));
        g.learnCnt     = rs.getInt(rs.getColumnIndex("learnCnt"));
        g.masterYn     = rs.getString(rs.getColumnIndex("masterYn"));
        g.delYn        = rs.getString(rs.getColumnIndex("delYn"));
        g.createDate   = rs.getString(rs.getColumnIndex("createDate"));
        g.createTime   = rs.getString(rs.getColumnIndex("createTime"));

        mMySentenceEdit.setText(g.mySentence);
        mTranSentenceEdit.setText("Click");
        startWav.start();

        Animation scaleAni;
        scaleAni = AnimationUtils.loadAnimation(this, R.anim.left_middle_move);
        mMySentenceEdit.startAnimation(scaleAni);
    }

    //---------------------------------------------------------------
    //                            Event
    //---------------------------------------------------------------
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mySentenceEdit:
                break;
            case R.id.nextBtn:
                EventNextOnRelease();
                break;
        }
    }

    public void EventNextOnRelease() {
        g.todayLearnCnt++;
        //dbo.UptSentenceAddLearnCnt();
        //StartCoroutine ( dbow.UptSentenceAddLearnCnt() );
        //HomeStart();
        g.curIdx++;
        /*
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

        */
    }



    void test01() {

        //Toast.makeText(getApplicationContext(), "mTodayLearnCntEdit=" + mTodayLearnCntEdit, Toast.LENGTH_LONG).show();

        Button button2 = (Button) findViewById(R.id.registBtn);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "두 번째 버튼이 클릭되었습니다.", Toast.LENGTH_LONG).show();

                setContentView(R.layout.home);
            }

        });

        //Test01 tt = new Test01();
        //tt.test001();

        //dbow.SelSentence();
        dbo.InsCnt();
        rs = dbo.SelCnt();
        rs.moveToFirst();
        while (!rs.isAfterLast()) {
            Log.d(TAG, "ppppppppppppppppppppp=" + rs.getString(0));
            rs.moveToNext();
        }
    }
}



