package com.saicmotor.facekittest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pixtalks.detect.DetectResult;
import com.pixtalks.facekitsdk.FaceKit;
import com.pixtalks.facekitsdk.PConfig;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public FaceKit faceKit=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 示例代码
         */

        faceKit = new FaceKit(MainActivity.this);
        // 设置验证账号
        faceKit.setAuth("", "");
        // 初始化模型
        long begin = System.currentTimeMillis();
        int ret = faceKit.initModel();
        Log.e(PConfig.projectLogTag, "Load model use time " + (System.currentTimeMillis() - begin));
        if (ret != PConfig.okCode) {
            Log.e(PConfig.projectLogTag, "Fail to load model with " + ret);
            return;
        }

        try {
            begin = System.currentTimeMillis();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(PConfig.getImg1Path(), options);

            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap2 = BitmapFactory.decodeFile(PConfig.getImg2Path(), options2);

            BitmapFactory.Options options3 = new BitmapFactory.Options();
            options2.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap3 = BitmapFactory.decodeFile(PConfig.getImg3Path(), options3);
            Log.e(PConfig.projectLogTag, "read image use time " + (System.currentTimeMillis() - begin));

            begin = System.currentTimeMillis();
            // 检测是否有人脸
            ArrayList<DetectResult> detectResults = faceKit.detectFace(bitmap);
            Log.e(PConfig.projectLogTag, "detect image 1 ues time " + (System.currentTimeMillis() - begin));

            begin = System.currentTimeMillis();
            ArrayList<DetectResult> detectResults2 = faceKit.detectFace(bitmap2);
            Log.e(PConfig.projectLogTag, "detect image 2 ues time " + (System.currentTimeMillis() - begin));

            begin = System.currentTimeMillis();
            Log.i(PConfig.projectLogTag, "Detect result size " + detectResults.size());
            Log.i(PConfig.projectLogTag, "Detect result 2 size " + detectResults2.size());
            // 提取特征值
            float[] fea = faceKit.getFeatureByDetectResult(bitmap, detectResults.get(0));
            Log.e(PConfig.projectLogTag, "get feature image 1 use time " + (System.currentTimeMillis() - begin));

            begin = System.currentTimeMillis();
            float[] fea2 = faceKit.getFeatureByDetectResult(bitmap2, detectResults.get(0));
            Log.e(PConfig.projectLogTag, "get feature image 2 use time " + (System.currentTimeMillis() - begin));

            Log.e(PConfig.projectLogTag, "Feature dim " + fea.length + " " + fea2.length);

            begin = System.currentTimeMillis();
            // 得分比对
            float score = faceKit.compareScore(fea, fea2);
            Log.e(PConfig.projectLogTag, "compare feature use time " + (System.currentTimeMillis() - begin));

            begin = System.currentTimeMillis();
            Log.e("Score ", score + "");
            Log.e(PConfig.projectLogTag, "two image use time " + (System.currentTimeMillis() - begin));

            // 活体识别
            boolean isLiveness = faceKit.isLive(bitmap3);
            Log.e(PConfig.projectLogTag, "Image 1 is " + isLiveness + " Use time " + (System.currentTimeMillis() - begin));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
