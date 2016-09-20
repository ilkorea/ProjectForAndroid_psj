package log.soondori.d11_firebase;

/**
 * Created by Log on 2016-09-20.
 */

public class ChatData {

    // creator
    private String name;
    // 대화
    private String chat;

    public ChatData () {
    }

    public ChatData (String _chat) {
        name = CloudUtils.getUserName();
        chat = _chat;
    }

    public String getName () {
        return name;
    }

    public String getChat () {
        return chat;
    }
}
