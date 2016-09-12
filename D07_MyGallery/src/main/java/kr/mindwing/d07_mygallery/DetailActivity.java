package kr.mindwing.d07_mygallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Fast campus 안드로이드 앱 개발 프로젝트 CAMP
 * MyGallery (1/3) (by mindwing)
 */
public class DetailActivity extends AppCompatActivity {

    public static final String PATH = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String path = intent.getStringExtra(PATH);
        Bitmap bm = Utils.getBitmap(path, false);

        TextView tvPath = (TextView) findViewById(R.id.path);
        tvPath.setText(path);

        ImageView ivImage = (ImageView) findViewById(R.id.image);

        ViewCompat.setTransitionName(ivImage, Utils.TRANSITION_NAME);
        ivImage.setImageBitmap(bm);
    }
}
