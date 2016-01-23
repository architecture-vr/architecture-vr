package com.bradbain.architecturevr;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.FileHandler;


public class ProjectViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_project_view);

        ArrayList<VisualizationProject> projectArrayList = new ArrayList<VisualizationProject>();

        File projectDirectory = getDir("Projects", Context.MODE_PRIVATE);
        File[] visualizationProjectFiles = projectDirectory.listFiles();
        for(File visualizationProjectFile : visualizationProjectFiles) {
            projectArrayList.add(VisualizationProject.LoadFromProjectName(this, visualizationProjectFile.getName()));
        }


        // Bind to our new adapter.
        VisualizationProjectsAdapter adapter = new VisualizationProjectsAdapter(this, projectArrayList); // Parallel array of which template objects to bind to those columns.

        // Register event handler
        ListView projectListView = (ListView) findViewById(R.id.project_list);
        projectListView.setAdapter(adapter);
        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VisualizationProject selectedProject = (VisualizationProject) adapterView.getAdapter().getItem(i);

                navigateToRenderActivity(selectedProject);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_view, menu);
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
        if(id == R.id.action_add) {
            Intent i = new Intent(getApplicationContext(), CreateVisualizationProjectActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigateToRenderActivity(VisualizationProject project) {
        Intent i = new Intent(getApplicationContext(), ArchitectureRenderActivity.class);
        i.putExtra("visualizationFilePath", project.getModelFile().getAbsolutePath());

        startActivity(i);
    }
}
