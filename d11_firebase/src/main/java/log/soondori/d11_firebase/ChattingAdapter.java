package log.soondori.d11_firebase;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.io.File;

/**
 * Created by Log on 2016-09-20.
 */

public class ChattingAdapter extends FirebaseRecyclerAdapter<ChatData, ChatViewHolder> {

    private static final String TAG = ChattingAdapter.class.getSimpleName();
    private RecyclerView recyclerView;

    public ChattingAdapter(DatabaseReference rootRef) {
        super(ChatData.class,
                R.layout.viewholder_chat,
                ChatViewHolder.class,
                rootRef);

        rootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView (RecyclerView _recyclerView){
        recyclerView = _recyclerView;
    }

    @Override
    protected void populateViewHolder(ChatViewHolder viewHolder, ChatData chatData, int position) {
        viewHolder.chatData = chatData;
        viewHolder.creator.setText(chatData.getName());
        viewHolder.chat.setText(chatData.getChat());

        if (chatData.getIsImageChat()) {
            File imageFile = new File(CloudUtils.getChatImageDir() , chatData.getChat());
            if (imageFile.exists()) {
                viewHolder.image .setImageDrawable(Drawable.createFromPath(
                        imageFile.getAbsolutePath()));
            } else {
                viewHolder.image .setImageResource(R.color. colorPrimary );
                CloudUtils.fetchImage(chatData , viewHolder.image );
            }
            viewHolder.image .setVisibility(View. VISIBLE );
        } else {
            viewHolder.image .setVisibility(View. GONE );
        }
    }
}