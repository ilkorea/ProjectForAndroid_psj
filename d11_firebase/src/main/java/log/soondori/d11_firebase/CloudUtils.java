package log.soondori.d11_firebase;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

/**
 * Created by Log on 2016-09-20.
 */

public class CloudUtils {
    private static final StorageReference storageImagesRef;
    private static final DatabaseReference refChatDb;
    private static String userName;
    private static File chatImageDir;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled( true );
        refChatDb = database.getReference( "fastchat" );
        storageImagesRef = FirebaseStorage.getInstance().getReference( "images" );
        File picturesDir = Environment.getExternalStoragePublicDirectory(
                Environment. DIRECTORY_PICTURES );
        chatImageDir = new File(picturesDir , "fastchat" );
        chatImageDir .mkdirs();
    }

    public static void init () {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userName = String.format( "%s (%s)" , firebaseUser.getDisplayName() , firebaseUser.getEmail());
    }
    public static DatabaseReference getRefChatDb () {
        return refChatDb;
    }
    public static String getUserName () {
        return userName;
    }
    public static FirebaseUser getFirebaseUser () {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static File getChatImageDir () {
        return chatImageDir;
    }
    public static void fetchImage (ChatData chatData , ImageView imageView) {
        File imageFile = new File( chatImageDir , chatData.getChat());
        storageImagesRef .child(chatData.getChat()).getFile(imageFile)
                .addOnFailureListener(e ->
                        Snackbar.make(imageView , e.getMessage() ,
                                Snackbar. LENGTH_LONG ).show())
                .addOnSuccessListener(result ->
                        imageView.setImageDrawable(
                                Drawable.createFromPath(imageFile.getAbsolutePath()))
                );
    }

    public static void putFile (Uri imageUri , ChatData chatData , View view) {
        Snackbar snackbar = Snackbar.make(view , "이미지 파일 업로드 준비중입니다: " + chatData.getChat() ,
                Snackbar. LENGTH_INDEFINITE );
                snackbar.show();

        storageImagesRef.child(chatData.getChat()).putFile(imageUri)
                .addOnProgressListener(snapshot -> {
                    double ratio = snapshot.getBytesTransferred() / (double)snapshot.getTotalByteCount() * 100;
            String ratioStr = String.format("이미지 파일 업로드 (%.2f%%)" , ratio);
            snackbar.setText(ratioStr);
        })
        .addOnFailureListener(e ->{
                    snackbar.setText( "예외 발생: " + e.getMessage())
                            .setAction( "확인" , v -> snackbar.dismiss());
                    FirebaseCrash.report(e);
        })
        .addOnSuccessListener(result -> {
            DatabaseReference refNewChat = refChatDb .push();
            refNewChat.setValue(chatData);
            snackbar.dismiss();
        });
    }
}
