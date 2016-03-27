public class MainMenu extends Activity 
implements OnClickListener, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, GameHelperListener{

@Override
public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.main_menu);
  gameHelper = new GameHelper(this);
}

@Override
public void onClick(View v) {
  if(v.equals(loadData)) {
    if(gameHelper.isSignedIn()) {
      gameHelper.setup(this, GameHelper.CLIENT_GAMES, Scopes.GAMES);
      startActivityForResult(gameHelper.getGamesClient().getAllLeaderboardsIntent(), RC_UNUSED);
    }
  }
  else if(v.equals(loginButton)) {
    Intent googlePicker = AccountPicker.newChooseAccountIntent(null,null,new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE},true,null,null,null,null) ;
    startActivityForResult(googlePicker, REQUEST_CODE_PICK_ACCOUNT);
  }
}

@Override
protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
  if(requestCode==REQUEST_CODE_RECOVER_PLAY_SERVICES) {
    if (resultCode == RESULT_CANCELED) {
      Toast.makeText(this, "Google Play Services must be installed.", Toast.LENGTH_SHORT).show();
      finish();
    }
    return;
  }
  else if(requestCode==REQUEST_CODE_PICK_ACCOUNT) {
    if (resultCode == RESULT_OK) {
      String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
      gameHelper.beginUserInitiatedSignIn();
    }
    else if (resultCode == RESULT_CANCELED) {
      Toast.makeText(this, "This application requires a Google account.", Toast.LENGTH_SHORT).show();
      finish();
    }
    return;
  }
  super.onActivityResult(requestCode, resultCode, data);
}

// this 2 methods not called, is this also because my code is wrong?
@Override
public void onSignInFailed() {
  Log.d("rush", "on sign in failed");
}

@Override
public void onSignInSucceeded() {
  Log.d("rush", "on sign in succeed");
}

}