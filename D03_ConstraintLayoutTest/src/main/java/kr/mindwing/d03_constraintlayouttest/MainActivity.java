package kr.mindwing.d03_constraintlayouttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Fast campus 안드로이드 앱 프로젝트 CAMP
 * Basic for Android (by mindwing)
 * 예제 - Constraint Layout Test
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLabel = (TextView) findViewById(R.id.tvLabel);

        findViewById(R.id.btHello).setOnClickListener(v -> tvLabel.setText("Hello~"));
        findViewById(R.id.btWorld).setOnClickListener(v -> tvLabel.setText("World!"));
    }
}