package com.saicmotor.facekittest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pixtalks.detect.DetectResult;
import com.pixtalks.facekitsdk.FaceKit;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        FaceKit faceKit = new FaceKit(MainActivity.this);
        faceKit.setAuth("ShangHai_Inner_Test_74543234", "T20_73452342344343");
        faceKit.initModel();

        long begin = System.currentTimeMillis();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/facekitsdk/abc.jpeg", options);
//        BitmapFactory.Options options2 = new BitmapFactory.Options();
//        options2.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap2 = BitmapFactory.decodeFile(PConfig.getImg2Path(), options);
        Log.e("zzz", "read image use time " + (System.currentTimeMillis() - begin));
        for(int i=0;i<100;i++){
            begin = System.currentTimeMillis();
            ArrayList<DetectResult> detectResults = faceKit.detectFace(bitmap);
            Log.e("zzz", "detect image 1 ues time " + (System.currentTimeMillis() - begin));
            if(detectResults!=null){
                Log.e("zzz", "has face..");
            }else{
                Log.e("zzz", "has no face..");
            }
        }
    }
}
