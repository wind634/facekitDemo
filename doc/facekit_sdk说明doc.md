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
        compile(name: 'facekit_v1.9', ext: 'aar')
      ```

#### 三. 使用前先调用faceKit.setAuth()设置账号,具体见示例代码MainActivity.java


