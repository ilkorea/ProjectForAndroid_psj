package kr.mindwing.d05_handlertest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Fast campus 안드로이드 앱 프로젝트 CAMP
 * Basic for Android (by mindwing)
 * 예제 - Handler Test
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyHandler";
    private static final int EVENT_COUNT = 1;

    private int count;
    private TextView tvCount;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_COUNT:
                    Log.d(TAG, toString() + ", " + count);
                    tvCount.setText(Integer.toString(count++));
                    sendEmptyMessageDelayed(EVENT_COUNT, 1000);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = (TextView) findViewById(R.id.count);
    }

    @Override
    protected void onResume() {
        super.onResume();

        handler.sendEmptyMessage(EVENT_COUNT);
    }

    @Override
    protected void onPause() {
        super.onPause();

        handler.removeMessages(EVENT_COUNT);
    }
}
