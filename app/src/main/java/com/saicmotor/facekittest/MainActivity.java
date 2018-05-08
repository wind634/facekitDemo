package com.saicmotor.facekittest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pixtalks.detect.DetectResult;
import com.pixtalks.facekitsdk.FaceKit;
import com.pixtalks.facekitsdk.PConfig;
import com.pixtalks.facekitsdk.utils.FileUtils;

import java.io.File;
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
        faceKit.setAuth("ShangHai_Inner_Test_74543234", "T20_73452342344343");
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


            // isLive是单张静默活体的。若启用了动作活体，可以去掉。
            begin = System.currentTimeMillis();
            // 活体识别
            boolean isLiveness = faceKit.isLive(bitmap, detectResults.get(0));
            Log.e(PConfig.projectLogTag, "Image 1 is " + isLiveness + " Use time " + (System.currentTimeMillis() - begin));

            begin = System.currentTimeMillis();
            isLiveness = faceKit.isLive(bitmap2, detectResults2.get(0));
            Log.e(PConfig.projectLogTag, "Image 2 is " + isLiveness + " Use time " + (System.currentTimeMillis() - begin));

            begin = System.currentTimeMillis();
            isLiveness = faceKit.isLive(bitmap3, detectResults2.get(0));
            Log.e(PConfig.projectLogTag, "Image 3 is " + isLiveness + " Use time " + (System.currentTimeMillis() - begin));


            // 动作活体
            // 调用动作活体参考流程：
            // 先通过faceKit.detectFace 检测人脸（因为每次动作活体验证过程中，会保存当前次的全部验证图片，如果第一张不先进行检测，可能会保存大量没必要的非人脸图片）
            // 检测到人脸后才调用动作活体接口（参考说明1）并提示用户眨眼（眨眼过程中头部不要大幅度晃动）
            // 送去faceKit.addImage的图片不需要先调用faceKit.detectFace接口进行人脸检测。可以调用当faceKit.addImage内部会根据需要自动进行人脸检测。

            // 说明1
            // 眨眼动作活体使用流程（先保证faceKit初始化成功）：
            // 1 调用faceKit.beginActionDetect（每次开始一次活体动作验证，都需要调用一次）
            // 2 连续调用faceKit.addImage 直至返回 PConfig.okCode 或业务逻辑端控制一次验证传递的最大帧数
            // 3 当faceKit.addImage 返回 PConfig.okCode后，可以调用当faceKit.getDailyImage 获取活体验证通过的图片

            // 每次开始动作活体验证都要调用
            faceKit.beginActionDetect();
            for (int i = 0; i< 30; ++i) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("/storage/emulated/0/pixtalks_facekit_3/frame/ff_" + i + ".jpg");
                Bitmap frame = BitmapFactory.decodeFile(stringBuffer.toString(), options);
                ret = faceKit.addImage(frame);
                if (PConfig.keepAddImage == ret) {
                    continue;
                }

                if (ret == PConfig.okCode) {
                    Log.e(PConfig.projectLogTag, "Action pass");
                    byte[] selectImg = faceKit.getDailyImage();
                    FileUtils.saveFile(selectImg, "/storage/emulated/0/pixtalks_facekit_3/select_frame.jpg");
                    flushFileDisplay("/storage/emulated/0/pixtalks_facekit_3/select_frame.jpg");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void flushFileDisplay(String path){
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(new File(path)));
        this.sendBroadcast(intent);
    }

}
