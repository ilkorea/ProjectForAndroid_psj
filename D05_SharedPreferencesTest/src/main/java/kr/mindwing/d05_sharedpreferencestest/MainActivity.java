package kr.mindwing.d05_sharedpreferencestest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Fast campus 안드로이드 앱 프로젝트 CAMP
 * Basic for Android (by mindwing)
 * 예제 - Shared Preferences Test
 */
public class MainActivity extends AppCompatActivity {

    public static final String COUNT_NUMBER = "count_number";
    public static final String PREF_NAME = "count";
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = (TextView) findViewById(R.id.count);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        int count = prefs.getInt(COUNT_NUMBER, 0);

        tvCount.setText(Integer.toString(count));

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(COUNT_NUMBER, ++count);
        editor.apply();
    }
}
