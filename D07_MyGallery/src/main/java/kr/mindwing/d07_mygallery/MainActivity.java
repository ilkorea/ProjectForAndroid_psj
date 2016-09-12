package kr.mindwing.d07_mygallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Fast campus 안드로이드 앱 개발 프로젝트 CAMP
 * MyGallery (1/3) (by mindwing)
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUI();
    }

    private void setupUI() {
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        // TODO RecyclerView 에 Layout Manager 와 Adapter 를 설정해주세요.
        recyclerView.setLayoutManager(sglm);
        recyclerView.setAdapter(new GalleryAdapter());
    }

}
