package com.bradbain.architecturevr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nononsenseapps.filepicker.FilePickerActivity;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

import java.io.File;


public class CreateVisualizationProjectActivity extends Activity {
    final static int FILE_CODE = 4242;

    String projectName;
    File modelFile;
    float modelScaleFactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_visualization_project);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_visualization_project, menu);
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

    public void onCreateProjectPressed(View View) {
        projectName = "Bridge";
        if(projectName != null && modelFile != null) {
            VisualizationProject project = new VisualizationProject(projectName, modelFile);
            VisualizationProject.SaveProject(this, project);
        }

        Intent i = new Intent(this, ProjectViewActivity.class);
        startActivity(i);
    }

    public void onOpenFileDialogPressed(View view) {
        Intent i = new Intent(this, FilePickerActivity.class);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(i, FILE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            Uri fileUri = data.getData();
            this.modelFile = new File(fileUri.getPath());

            /*ModelPreviewRenderer previewRenderer = new ModelPreviewRenderer(this, modelFile);

            RajawaliSurfaceView previewView = new RajawaliSurfaceView(this);
            previewView.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);
            previewView.setFrameRate(10);
            addContentView(previewView, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));
            previewView.setSurfaceRenderer(previewRenderer);*/
        }
    }


}
