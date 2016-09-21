package log.soondori.d11_firebase;

import android.content.Intent;
import android.text.TextUtils;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Log on 2016-09-21.
 */

public class MyFMService extends FirebaseMessagingService{
    private static final String TAG = "MyFMService";
    public static final String NOTI_MESSAGE = "noti_message";

    @Override
    public void onMessageReceived (RemoteMessage remoteMessage) {
        String noti = remoteMessage.getData().get( NOTI_MESSAGE);
        Intent newNoti = new Intent(getApplicationContext() , MainActivity. class );
        if (!TextUtils. isEmpty(noti)) {
            newNoti.putExtra( NOTI_MESSAGE, noti);
        }
        startActivity(newNoti);
    }
}
