package log.soondori.d11_firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Log on 2016-09-20.
 */

public class ChattingActivity extends AppCompatActivity {
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
        findViewById(R.id. btChat ).setOnClickListener( this ::sendChat);
    }
    private void sendChat (View view) {
        String chat = etChat .getText().toString();
        etChat .setText( "" );
        ChatData chatData = new ChatData(chat);
        DatabaseReference refNewChat = refChatDb .push();
        refNewChat.setValue(chatData);
    }
}