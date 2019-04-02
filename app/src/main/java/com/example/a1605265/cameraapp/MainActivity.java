package com.example.a1605265.cameraapp;

import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    android.hardware.Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;
    CameraManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        //open the camera

        camera = android.hardware.Camera.open();

        showCamera = new ShowCamera(this, camera);

        frameLayout.addView(showCamera);

    }

    android.hardware.Camera.PictureCallback mPictureCallback= new android.hardware.Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera)
        {

            File picture_file = getOutputMediaFile();

            if(picture_file!=null){
                try {
                    FileOutputStream fos = new FileOutputStream(picture_file);
                        fos.write(data);
                        fos.close();

                        camera.startPreview();
                }catch(IOException e){
                    e.printStackTrace();
                }

            }
        }


    };
    private File getOutputMediaFile()
    {
        String state = Environment.getExternalStorageState();
        if(!state.equals(Environment.MEDIA_MOUNTED))
        {
            return  null;
        }
        else
        {
            File folder_gui = new File(
                    Environment.getExternalStorageDirectory() + File.separator + "GUI");
            if(!folder_gui.exists())
            {
                folder_gui.mkdirs();
            }

            File output = new File(folder_gui, "temp.jpg");
            return output;
        }
    }

        public void captureImage(View view)
        {
            if (camera!=null)
            {
                camera.takePicture(null,null,mPictureCallback);
            }

        }




}
