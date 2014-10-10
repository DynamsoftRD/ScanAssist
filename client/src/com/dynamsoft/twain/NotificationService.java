package com.dynamsoft.twain;


import com.dynamsoft.io.NotificationSocket;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class NotificationService extends Service {
 
    private NotificationSocket mNotificationThread;
 
    @Override
    public void onCreate() {
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotificationThread = new NotificationSocket(this);
        mNotificationThread.start();
    }

    @Override
    public void onDestroy() {
        mNM.cancel(IncomingMessageView.NOTIFICATION_ID);
        mNotificationThread.onClose();
        mNotificationThread.interrupt();
        try {
			mNotificationThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    
    @SuppressLint("NewApi")
	public void showAppNotification(String from, String message) {

        Intent notifyIntent = new Intent(this, IncomingMessageView.class);
        notifyIntent.putExtra(IncomingMessageView.KEY_FROM, from);
        notifyIntent.putExtra(IncomingMessageView.KEY_MESSAGE, message);
        
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                this,
                0,
                notifyIntent,
                PendingIntent.FLAG_ONE_SHOT
        );
        
        Notification notif = new Notification.Builder(this)
        .setContentTitle("TWAIN Scanner Status ")
        .setContentText(message)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentIntent(pendingIntent)
        .setTicker(message)
        .build();
        
        notif.defaults = Notification.DEFAULT_ALL;
        
        mNM.notify(IncomingMessageView.NOTIFICATION_ID, notif);
    }

    private final IBinder mBinder = new Binder() {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply,
                int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };

    private NotificationManager mNM;
}
