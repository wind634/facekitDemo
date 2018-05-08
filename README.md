# facekitDemo

使用sdk步骤:
## 一. 加载jniLibs下so库
## 二. 加载 facekit_v1.6.2.aar

    1. gradle android下新增配置

        ```
            repositories {
                flatDir {
                    dirs 'libs'
                }
            }
        ```

    2. dependencies新增配置
        ```compile(name: 'facekit_v1.6.2', ext: 'aar')```
## 三. 先调用faceKit.setAuth()设置账号,具体见示例代码MainActivity.java
