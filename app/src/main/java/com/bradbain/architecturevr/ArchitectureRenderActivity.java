package com.bradbain.architecturevr;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import org.rajawali3d.loader.LoaderAWD;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.util.RajLog;
import org.rajawali3d.vr.RajawaliVRActivity;

import java.io.File;


public class ArchitectureRenderActivity extends RajawaliVRActivity {
    private ArchitectureVRRenderer mRenderer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Bundle intentBundle = getIntent().getExtras();
        String visualizationFilePath = intentBundle.getString("visualizationFilePath");
        if(visualizationFilePath != null) {
            mRenderer = new ArchitectureVRRenderer(this, new File(visualizationFilePath));
            setRenderer(mRenderer);
            setConvertTapIntoTrigger(true);
        }
    }

    @Override
    public void onCardboardTrigger() {
        RajLog.i("onCardboardTrigger");
        mRenderer.onCardboardTrigger();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        RajLog.i("onTouchEvent");

        //mRenderer.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
