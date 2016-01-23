package com.bradbain.architecturevr;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderAWD;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;
import org.rajawali3d.util.RajLog;
import org.rajawali3d.vr.renderer.RajawaliVRRenderer;

import java.io.File;

/**
 * Created by bradbain on 8/14/15.
 */
public class ArchitectureVRRenderer extends RajawaliVRRenderer {
    LoaderOBJ loader;

    public ArchitectureVRRenderer(Context context, File modelFile) {
        super(context);

        loader = new LoaderOBJ(this, modelFile);
    }

    @Override
    public void initScene() {
        DirectionalLight light = new DirectionalLight(0.2f, -1f, 0f);
        light.setPower(.7f);
        getCurrentScene().addLight(light);

        light = new DirectionalLight(0.2f, 1f, 0f);
        light.setPower(1f);
        getCurrentScene().addLight(light);

        getCurrentCamera().setFarPlane(1000);

        getCurrentScene().setBackgroundColor(0xdddddd);


        try {
            getCurrentScene().setSkybox(R.drawable.posx, R.drawable.negx, R.drawable.posy, R.drawable.negy, R.drawable.posz, R.drawable.negz);

            RajLog.i("Loading model");
            loader.parse();

            Material cruiserMaterial = new Material();
            cruiserMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
            cruiserMaterial.setColorInfluence(0);
            cruiserMaterial.enableLighting(true);
            //cruiserMaterial.addTexture(new Texture("spaceCruiserTex", R.drawable.space_cruiser_4_color_1));

            Object3D mVisualizationModel;
            mVisualizationModel = loader.getParsedObject();
            mVisualizationModel.setMaterial(cruiserMaterial);
            mVisualizationModel.setZ(0);
            mVisualizationModel.setY(0);
            //mVisualizationModel.setScale(0.05);
            getCurrentScene().addChild(mVisualizationModel);

            RajLog.i("Done Loading model");
        }
        catch (Exception e) {

        }

        super.initScene();

    }

    @Override
    public void onRender(long elapsedTime, double deltaTime) {
        super.onRender(elapsedTime, deltaTime);

        //getCurrentCamera().rotate(Vector3.Axis.Y, 1);
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);

        //getCurrentCamera().rotate(Vector3.Axis.Y, 20);
    }

    public void onCardboardTrigger() {
        getCurrentCamera().moveForward(-1.0);
    }
}
