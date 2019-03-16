# 人脸SDK相关使用说明


## 一.使用sdk步骤:
#### 1. 加载jniLibs下so库
#### 2. 加载 facekit_v1.9.aar
  * a. gradle android下新增配置
     ```
               repositories {
                   flatDir {
                       dirs 'libs'
                   }
               }
     ```

  * b. dependencies新增配置
      ```
         implementation(name: 'auth-1.0.2', ext: 'aar')
         implementation(name: 'comparator-1.0.2', ext: 'aar')
         implementation(name: 'facekit_v2.8', ext: 'aar')
         implementation(name: 'fakeFace-1.2.0', ext: 'aar')
      ```

#### 三. 使用前先调用faceKit.setAuth()设置账号,具体见示例代码MainActivity.java


## 二.sdk接口详细说明:

#### 1. 创建FaceKit对象, 建议单例或者全局唯一
   ```
      FaceKit facekit = new FaceKit(context);
   ```

#### 2. 设置权限认证
   * 方法定义
        * void setAuth(String userName, String authCode)
   * 方法参数
        * userName 和 authCode是由我司分配的权限认证账号。
   * 方法返回
        * 无


#### 3. 设置证书下载位置。初次认证需要设置证书,可自定义证书下载位置。
   * 方法定义
        * void setLicencePath(String licencePath)
   * 方法参数
        * licencePath 自定义的证书路径。
   * 方法返回
        * 无

#### 4. 初始化模型。
   * 方法定义
        * int initModel()
   * 方法参数
        * 无
   * 方法返回
        * 初始化结果, 为0表示初始化成功

#### 5. 判断是否有人脸。
   * 方法定义
        * ArrayList<DetectResult>  detectFace(Bitmap bitmap)
   * 方法参数
        * bitmap类型
   * 方法返回
        * 如果无人脸, 返回的是null。如果有人脸, 返回的是人脸的相关特征点位置信息。

#### 6. 获取指定人脸的特征值。
   * 方法定义
        * float[]  getFeatureByDetectResult(Bitmap bitmap, DetectResult detectResult)
   * 方法参数
        * bitmap类型的图片, 以及DetectResult类型的相关人脸特征点位置信息
   * 方法返回
        * float[] 类型的人脸特征值

#### 7. 获取指定人脸的特征值(只传图片)。
   * 方法定义
        * ArrayList<float[]> getFeature(Bitmap bitmap, boolean isOnlyFirst)
   * 方法参数
        * bitmap类型的图片, 如果bitmap中有多张人脸, isOnlyFirst 为true表示只获取主要一张人脸特征值
   * 方法返回
        * ArrayList<float[]> 多个float[]人脸特征值集合(与截获的人脸数量相对应)

#### 8. 人脸比对得分(只传两张图片)。
   * 方法定义
        * float compareScore(Bitmap bitmap1, Bitmap bitmap2)
   * 方法参数
        * 两张bitmap类型的图片
   * 方法返回
        * float 比对得分。 如果有一张以上图片无人脸都返回0.0f

#### 9. 人脸比对得分(只传特征值)。
   * 方法定义
        * float compareScore(float[] fea1, float[] fea2)
   * 方法参数
        * 两张图片的特征值
   * 方法返回
        * float 比对得分

#### 10. 静态活体检测接口
   * 方法定义
        * boolean isLive(Bitmap bitmap, DetectResult detectResult)
   * 方法参数
        * bitmap图片 以及 detectFace(Bitmap bitmap)提取的人脸特征点位置信息
   * 方法返回
        * 是或否

#### 11. 眨眼活体检测接口
   * 眨眼活体分三个接口。眨眼动作活体使用流程如下, 具体参考MainActivity代码（先保证faceKit初始化成功）：
     *  1.调用faceKit.beginActionDetect（每次开始一次活体动作验证，都需要调用一次）
     *  2.连续调用faceKit.addImage 直至返回 PConfig.okCode 或业务逻辑端控制一次验证传递的最大帧数
     *  3.当faceKit.addImage 返回 PConfig.okCode后，可以调用当faceKit.getDailyImage 获取活体验证通过的图片




