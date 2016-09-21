package log.soondori.d11_firebase;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static int RC_SIGN_IN = 1025;

    private View btSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSignIn = findViewById(R.id.btSignin);
        btSignIn.setOnClickListener(this::checkSigninStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseUser firebaseUser = CloudUtils.getFirebaseUser();

        if(firebaseUser != null){
            String userName = firebaseUser.getDisplayName();
            Toast.makeText(this, userName + "(으)로 로그인 되었습니다.", Toast.LENGTH_LONG).show();

            jumpToChatting();
            finish();
        }
    }

    public void checkSigninStatus(View v){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> {
                    Log.d(TAG, "onConnectionFailed:"+ connectionResult);
                    Toast.makeText(this, "Google Play Sercives error.", Toast.LENGTH_LONG).show();
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }else{
                Log.e(TAG, "Google Sign In failed.");
                Toast.makeText(this, "Google Sign In 이 실패 했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void jumpToChatting() {
        Intent jumpToChatting = new Intent(this, ChattingActivity.class);
        String message = getIntent().getStringExtra(MyFMService.NOTI_MESSAGE);
        if (message != null ) {
            jumpToChatting.putExtra(MyFMService.NOTI_MESSAGE, message);
        }

        startActivity(jumpToChatting);
        finish();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithCredential", task.getException());

                        Snackbar.make(btSignIn, "로그인에 실패했습니다: " + task.getResult(),
                                Snackbar.LENGTH_SHORT).setAction("종료", v -> finish()).show();
                    } else {
                        Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();

                        jumpToChatting();

                        finish();
                    }
                });
    }
}
