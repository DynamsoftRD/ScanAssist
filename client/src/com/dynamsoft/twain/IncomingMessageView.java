package com.dynamsoft.twain;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.TextView;

public class IncomingMessageView extends Activity {

    public static final String KEY_FROM = "from";
    public static final String KEY_MESSAGE = "message";
    public static final int NOTIFICATION_ID = R.layout.activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView view = new TextView(this);
        view.setText(getIntent().getCharSequenceExtra(KEY_FROM) + ": " + getIntent().getCharSequenceExtra(KEY_MESSAGE));
        
        setContentView(view);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(NOTIFICATION_ID);
    }
}