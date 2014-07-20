package frusso.squarewatchface;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import jfabrix101.wear.lib.WatchFaceLifecycle;

public class MyActivity extends Activity
implements WatchFaceLifecycle.Listener {

    private static final String LOG_TAG = MyActivity.class.getName();

    private TextView mDateTimeTextView;
    private boolean mScreenDim = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_face);
        mDateTimeTextView = (TextView) findViewById(R.id.dateTime);

        registerReceiver(this.mIntentTimeTickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
        WatchFaceLifecycle.attach(this, savedInstanceState, this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mIntentTimeTickReceiver);
    }

    private final BroadcastReceiver mIntentTimeTickReceiver = new BroadcastReceiver() {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent) {
            if ("android.intent.action.TIME_TICK".equals(paramAnonymousIntent.getAction())) {
                Log.d(LOG_TAG, "Received TIME_TICK event");
                updateUI();
            }
        }
    };

    @Override
    public void onScreenDim() {
        mScreenDim = true;
        updateUI();

    }

    @Override
    public void onScreenAwake() {
        mScreenDim = false;
        updateUI();
    }

    @Override
    public void onWatchFaceRemoved() {
        mScreenDim = true;
    }

    @Override
    public void onScreenOff() {
        mScreenDim = true;
        updateUI();
    }

    private void updateUI() {
        if (mScreenDim) return;
        Date now = new Date();
        String str = new SimpleDateFormat("EEE, MMM d").format(now);
        mDateTimeTextView.setText(str);
    }
}
