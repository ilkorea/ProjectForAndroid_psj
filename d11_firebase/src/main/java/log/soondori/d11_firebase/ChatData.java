package log.soondori.d11_firebase;

/**
 * Created by Log on 2016-09-20.
 */

public class ChatData {

    // creator
    private String name;
    // 대화
    private String chat;

    private  boolean isImageChat;

    public ChatData () {
    }

    public ChatData (String _chat) {
        this (_chat , false );
    }

    public ChatData (String _chat, boolean _isImageChat) {
        name = CloudUtils.getUserName();
        chat = _chat;
        isImageChat = _isImageChat;
    }

    public String getName () {
        return name;
    }

    public String getChat () {
        return chat;
    }

    public boolean getIsImageChat () {

        return isImageChat;
    }
}
