package kr.mindwing.d04_smsbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import java.util.ArrayList;

/**
 * Fast campus 안드로이드 앱 프로젝트 CAMP
 * Basic for Android (by mindwing)
 * 예제 - SMS Broadcast Receiver Test
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Telephony.Sms.Intents.SMS_DELIVER_ACTION:
                break;

            case Telephony.Sms.Intents.SMS_RECEIVED_ACTION:
                ArrayList<String> smsData = getSmsData(intent);
                startMainActivity(context, smsData);
                break;

            default:
                break;
        }
    }

    private void startMainActivity(Context context, ArrayList<String> smsData) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putStringArrayListExtra("sms", smsData);

        context.startActivity(intent);
    }

    public ArrayList<String> getSmsData(Intent smsIntent) {
        Object[] messages = (Object[]) smsIntent.getSerializableExtra("pdus");

        int pduCount = messages.length;
        SmsMessage[] smsMessages = new SmsMessage[pduCount];
        ArrayList<String> smsData = new ArrayList<>();

        for (int i = 0; i < pduCount; i++) {
            byte[] pdu = (byte[]) messages[i];
            // createFromPdu() 메서드는 API 23 에서 deprecated 되었음.
            smsMessages[i] = SmsMessage.createFromPdu(pdu);
        }

        for (SmsMessage smsMessage : smsMessages) {
            String address = smsMessage.getOriginatingAddress();
            String body = smsMessage.getMessageBody();
            long timestamp = smsMessage.getTimestampMillis();

            smsData.add(address + ", " + body + ", " + timestamp);
        }

        return smsData;
    }
}
