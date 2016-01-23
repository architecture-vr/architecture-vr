package com.bradbain.architecturevr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bradbain on 8/25/15.
 */
public class VisualizationProjectsAdapter extends ArrayAdapter<VisualizationProject> {
    public VisualizationProjectsAdapter(Context context, ArrayList<VisualizationProject> projects) {
        super(context, 0, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VisualizationProject project = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_project, parent, false);
        }

        TextView projectNameView = (TextView) convertView.findViewById(R.id.projectName);
        projectNameView.setText(project.getName());

        return convertView;
    }
}
