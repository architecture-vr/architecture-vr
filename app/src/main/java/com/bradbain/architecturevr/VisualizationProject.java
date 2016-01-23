package com.bradbain.architecturevr;

import android.content.Context;

import org.rajawali3d.Object3D;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.channels.FileChannel;

/**
 * Created by bradbain on 8/25/15.
 */
public class VisualizationProject implements Serializable {
    private String name;
    private transient File modelFile;
    private float scaleFactor = 1;

    public VisualizationProject(String projectName, File modelFile) {
        this.name = projectName;
        this.modelFile = modelFile;
    }

    public String getName() {
        return name;
    }

    public File getModelFile() {
        return modelFile;
    }

    public float getScaleFactor() { return scaleFactor; }

    public static void SaveProject(Context c, VisualizationProject project) {
        File projectDir = c.getDir("Projects", Context.MODE_PRIVATE);
        File projectFile = new File(projectDir, project.getName());

        File modelDir = c.getDir("Models", Context.MODE_PRIVATE);
        File modelFile = new File(modelDir, project.getName()+"_model");

        // Ensure the project can be saved
        try(FileOutputStream projectFileStream = new FileOutputStream(projectFile)) {
            // Copy the model over
            copyFile(project.modelFile, modelFile);

            // Actually save the VisualizationProject
            try (ObjectOutputStream oos = new ObjectOutputStream(projectFileStream)) {
                oos.writeObject(project);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

    public static VisualizationProject LoadFromProjectName(Context c, String projectName) {
        VisualizationProject project = null;

        File projectDir = c.getDir("Projects", Context.MODE_PRIVATE);
        File projectFile = new File(projectDir, projectName);

        // Ensure the project can be loaded
        try(FileInputStream projectFileStream = new FileInputStream(projectFile)) {
            try (ObjectInputStream ois = new ObjectInputStream(projectFileStream)) {
                // Inflate the serialized object
                project = (VisualizationProject) ois.readObject();

                // Set the modelFile
                File modelDir = c.getDir("Models", Context.MODE_PRIVATE);
                File modelFile = new File(modelDir, projectName+"_model");
                project.modelFile = modelFile;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return project;
    }
}
