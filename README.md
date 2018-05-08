# facekitDemo

使用sdk步骤:
## 一. 加载jniLibs下so库
## 二. 加载 facekit_v1.6.1.aar
    1. gradle android下新增配置

        ```
            repositories {
                flatDir {
                    dirs 'libs'
                }
            }
        ```

    2. dependencies新增配置
        ```compile(name: 'facekit_v1.6.1', ext: 'aar')```
## 三. 示例代码见MainActivity.java