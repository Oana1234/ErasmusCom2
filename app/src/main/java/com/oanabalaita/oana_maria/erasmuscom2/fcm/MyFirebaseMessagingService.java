package com.oanabalaita.oana_maria.erasmuscom2.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.oanabalaita.oana_maria.erasmuscom2.ErasmusCom2MainApp;
import com.oanabalaita.oana_maria.erasmuscom2.R;
import com.oanabalaita.oana_maria.erasmuscom2.events.PushNotificationEvent;
import com.oanabalaita.oana_maria.erasmuscom2.models.UserChat;
import com.oanabalaita.oana_maria.erasmuscom2.ui.activities.ChatActivity;
import com.oanabalaita.oana_maria.erasmuscom2.utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Oana-Maria on 04/06/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {



        Log.d(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


            final String[] title = new String[1];
            final String[] username = new String[1];

            title[0] = null;
            username[0] = null;


            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference ref = database.getReference("users");
            ref.orderByChild("email").equalTo(remoteMessage.getData().get("username")).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    UserChat userChat = dataSnapshot.getValue(UserChat.class);
                    title[0] =  userChat.nameAndSurname.toString();
                    username[0] =  userChat.nameAndSurname.toString();

                    if(title[0].equals("")||username[0].equals(null)){
                        title[0] =  remoteMessage.getData().get("title");
                        username[0] =remoteMessage.getData().get("username");
                    }
                    String message = remoteMessage.getData().get("text");

                    String uid = remoteMessage.getData().get("uid");
                    String fcmToken = remoteMessage.getData().get("fcm_token");

                    // Don't show notification if chat activity is open.
                    if (!ErasmusCom2MainApp.isChatActivityOpen()) {
                        sendNotification(title[0],
                                message,
                                username[0],
                                uid,
                                fcmToken);
                    } else {
                        EventBus.getDefault().post(new PushNotificationEvent(title[0],
                                message,
                                username[0],
                                uid,
                                fcmToken));
                    }
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
    }


    private void sendNotification(String title,
                                  String message,
                                  String receiver,
                                  String receiverUid,
                                  String firebaseToken) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(Constants.ARG_RECEIVER, receiver);
        String receiverUsername;

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        intent.putExtra(Constants.ARG_RECEIVER_UID, receiverUid);
        intent.putExtra(Constants.ARG_FIREBASE_TOKEN, firebaseToken);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.hisl)
                //    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.mipmap.ic_launcher))

                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}

