package log.soondori.d11_firebase;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;

import java.io.File;

/**
 * Created by Log on 2016-09-20.
 */

public class ChattingActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PICK_PHOTO_FROM_GALLERY = 2501;
    private static final String[] MEDIA_PROVIDER_COLUMN = {
            MediaStore.Images.Media. DATA ,
            MediaStore.Images.Media. DISPLAY_NAME ,
            MediaStore.Images.Media. TITLE
    };

    private EditText etChat;
    private DatabaseReference refChatDb = CloudUtils.getRefChatDb();
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        setContentView(R.layout. activity_chatting );

        CloudUtils.init();

        RecyclerView rvChat = (RecyclerView) findViewById(R.id.rvChat);
        ChattingAdapter chattingAdapter = new ChattingAdapter(CloudUtils.getRefChatDb());
        rvChat.setAdapter(chattingAdapter);
        rvChat.setLayoutManager( new LinearLayoutManager( this ));

        etChat = (EditText) findViewById(R.id. etChat );

        findViewById(R.id.ibGallery).setOnClickListener( this::jumpToGallery);
        findViewById(R.id.ibChat).setOnClickListener( this::sendChat);
    }

    @Override
    protected void onResume () {
        super .onResume();
        checkNotiMessage();
    }
    @Override
    protected void onNewIntent (Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void checkNotiMessage () {
        Intent intent = getIntent();
        String message = intent.getStringExtra(MyFMService.NOTI_MESSAGE);
        if (!TextUtils. isEmpty(message)) {
            intent.putExtra(MyFMService. NOTI_MESSAGE, "" );
            etChat .post(() ->{
                                    ChatData chatData = new ChatData(message);
                    DatabaseReference refNewChat = refChatDb .push();
                    refNewChat.setValue(chatData);
            });
        }
    }

    private void jumpToGallery (View view) {
        Intent intent = new Intent(Intent. ACTION_PICK ,
                android.provider.MediaStore.Images.Media. EXTERNAL_CONTENT_URI );
        intent.setType("image/jpeg, image/png");
        startActivityForResult(intent , REQUEST_CODE_PICK_PHOTO_FROM_GALLERY );
    }

    private void sendChat (View view) {
        String chat = etChat .getText().toString();
        etChat .setText( "" );
        ChatData chatData = new ChatData(chat);
        DatabaseReference refNewChat = refChatDb .push();
        refNewChat.setValue(chatData);
    }

    @Override
    protected void onActivityResult ( int requestCode , int resultCode , Intent data) {
        if (resultCode != RESULT_OK ) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_PICK_PHOTO_FROM_GALLERY :
                processPickedImage(data);
                break;
            default :
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void processPickedImage (Intent data) {
        Uri selectedImage = data.getData();
        File picturePath = null;
        Uri imageUri = null;
        String pictureName = "이름 알 수 없음";
        switch (selectedImage.getScheme()) {
            case "file" :
                FirebaseCrash.log( "picked image: file" );

                imageUri = selectedImage ;
                break;
            case "content" :
                FirebaseCrash.log( "picked image: content" );

                try (Cursor cursor = getContentResolver().query( selectedImage ,
                        MEDIA_PROVIDER_COLUMN , null, null, null )) {
                    if (cursor == null ) {
                        FirebaseCrash.log( "picked image: cursor is null" );
                        return;
                    }
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(
                            MEDIA_PROVIDER_COLUMN [ 0 ]);
                    picturePath = new File(cursor.getString(columnIndex));
                    FirebaseCrash.log( "picked image: picturePath: " + picturePath);

                    imageUri = Uri.fromFile(picturePath);
                    columnIndex = cursor.getColumnIndex(
                            MEDIA_PROVIDER_COLUMN [ 1 ]);
                    pictureName = cursor.getString(columnIndex);
                    FirebaseCrash.log( "picked image: pictureName: " + pictureName);

                    columnIndex = cursor.getColumnIndex(
                            MEDIA_PROVIDER_COLUMN [ 2 ]);
                    String title = cursor.getString(columnIndex);
                    FirebaseCrash.log( "picked image: title: " + title);

                    if (title != null ) {
                        pictureName = title;
                    }
                } catch (Exception e) {
                    Toast.makeText( this, "예외: " + e , Toast. LENGTH_SHORT ).show();
                    FirebaseCrash.report(e);
                }
                break;
            default :
                break;
        }
        if (imageUri != null ) {
            if (picturePath != null && !picturePath.exists()) {
                Toast.makeText( this, "파일이 존재하지 않습니다: " + picturePath ,
                        Toast. LENGTH_SHORT ).show();
                FirebaseCrash.log( "picked image: no file exists" );
                return;
            }
            ChatData chatData = new ChatData(pictureName , true );
            CloudUtils.putFile(imageUri , chatData , etChat );
        }
    }
}