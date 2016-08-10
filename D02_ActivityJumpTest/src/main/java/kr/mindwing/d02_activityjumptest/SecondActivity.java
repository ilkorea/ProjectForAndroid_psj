package kr.mindwing.d02_activityjumptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {
    public static final String TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle("두 번째 액티비티!!!");

        Log.d(TAG, "LifeCycle: onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "LifeCycle: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "LifeCycle: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "LifeCycle: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "LifeCycle: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "LifeCycle: onDestroy()");
    }
}
