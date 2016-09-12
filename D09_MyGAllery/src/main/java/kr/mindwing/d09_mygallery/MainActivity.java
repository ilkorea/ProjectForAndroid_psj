package kr.mindwing.d09_mygallery;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

/**
 * Fast campus 안드로이드 앱 개발 프로젝트 CAMP
 * MyGallery (2/3) (by mindwing)
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean hasPermissions = Utils.requestStoragePermission(this);

        if (hasPermissions) {
            setupUI();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utils.REQUEST_CODE_EXTERNAL_STORAGE:
                if (grantResults.length == 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "'저장소 읽기' 가 승인되었습니다.", Toast.LENGTH_SHORT).show();

                    setupUI();
                } else {
                    Toast.makeText(this, "'저장소 읽기' 권한요청을 거부하셨습니다.", Toast.LENGTH_SHORT).show();

                    Utils.requestStoragePermission(this);
                }
                break;

            default:
                break;
        }
    }

    private void setupUI() {
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
        recyclerView.setAdapter(new GalleryAdapter());
    }

}
