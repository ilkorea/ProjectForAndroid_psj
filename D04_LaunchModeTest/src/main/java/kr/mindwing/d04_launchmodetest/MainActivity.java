package kr.mindwing.d04_launchmodetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fast campus 안드로이드 앱 프로젝트 CAMP
 * Basic for Android (by mindwing)
 * 예제 - Launch Mode Test
 */
public abstract class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentStandard = new Intent(this, StandardActivity.class);
        Intent intentSingleTop = new Intent(this, SingleTopActivity.class);
        Intent intentSingleTask = new Intent(this, SingleTaskActivity.class);
        Intent intentSingleInstance = new Intent(this, SingleInstanceActivity.class);

        TextView tvActivityLaunchMode = (TextView) findViewById(R.id.tvActivityLaunchMode);
        tvActivityLaunchMode.setText(getClass().getSimpleName());

        findViewById(R.id.btStandard).setOnClickListener(v -> startActivity(intentStandard));
        findViewById(R.id.btSingleTop).setOnClickListener(v -> startActivity(intentSingleTop));
        findViewById(R.id.btSingleTask).setOnClickListener(v -> startActivity(intentSingleTask));
        findViewById(R.id.btSingleInstance).setOnClickListener(v -> startActivity(intentSingleInstance));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Toast.makeText(this, "onNewIntent() called!!!", Toast.LENGTH_SHORT).show();
    }
}
