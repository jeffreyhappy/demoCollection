---
title: helloWorld JNI
date: 2018/7/12 10:51
---
## hello world JNI
##### 创建
我使用的是android studio 3.1.3.直接新建一个项目.在Create Android Project界面下面勾选Include C++ support.然后一路next就可以了
##### 对比
与普通的安卓项目相比
1.  main文件夹下多了个cpp文件夹,cpp文件夹下有个native-lib.cpp文件
2.  app/build.gradle中多了externalNativeBuild配置
```
android{
  defaultConfig{
    ....
    externalNativeBuild {
            cmake {
                cppFlags ""
            }
        }
  }

  externalNativeBuild {
            cmake {
                CMakeLists.txt
            }
  }
}
```
3. app文件夹下多了个CMakeLists.txt
```

//第一个参数是将要打包的库的名字
//第二个参数是共享链接库
//第三个参数是打包的文件
add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/native-lib.cpp
              )


//找到内置的库
find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )


//把内置的库链接进native-lib 然而并不知道有啥用
target_link_libraries( # Specifies the target library.
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
```

4. 在activity里,声明下就可以使用了
```
   static {
        System.loadLibrary("native-lib");
   }
   public native String stringFromJNI();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       ....
       TextView tv = (TextView) findViewById(R.id.sample_text);
       tv.setText(stringFromJNI());
   }
```

对于native-lib.cpp文件来说
```
//引入jni.h头文件
#include <jni.h>
//引入string头文件
#include <string>

// extern "C" 标识后面的用C编译 ,这里的意思是这个函数用C编译,C++为了函数重载会把参数带上,而c没有函数重载
// JNICALL表示调用约定，相当于C++的stdcall，说明调用的是本地方法
// JNIEXPORT表示函数的链接方式，当程序执行的时候从本地库文件中找函数
// 中间的jstring就是返回类型,是c++中对应java中String的类型
// JNIEnv 是对java环境的引用,并且提供了一些类型转换方法
// jobject 是调用这个函数的java对象.没发现如何使用
extern "C" JNIEXPORT jstring JNICALL Java_com_happy_fei_helloworldjni_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

```

extern "C" 告诉编译器后面的按照C的风格编译.我测试下如果函数前不加extern "C"会报错
```
java.lang.UnsatisfiedLinkError: No implementation found for java.lang.String com.happy.fei.helloworldjni.MainActivity.helloWorld(java.lang.String, java.lang.String) (tried Java_com_happy_fei_helloworldjni_MainActivity_helloWorld and Java_com_happy_fei_helloworldjni_MainActivity_helloWorld__Ljava_lang_String_2Ljava_lang_String_2)
```
错误的信息是没有找到helloWorld函数也没有找到helloWorld(String,String)函数.
问题:什么时候可以不使用extern "C". 我这个文件是CPP啊,不过留待以后熟练了发现吧.现在还不太溜

###### 稍微难一点

上面这个stringFromJNI函数是返回一个字符串.现在进阶一下,输入两个字符串连起来之后再返回
```
//jstring 对应的java中的String
extern "C" JNIEXPORT jstring JNICALL Java_com_happy_fei_helloworldjni_MainActivity_helloWorld(
        JNIEnv *env,
        jobject /* this */ ,jstring s1 ,jstring s2) {
          //将jstring转为std::string
          std::string cs1 = env->GetStringUTFChars(s1, NULL);
          std::string cs2 = env->GetStringUTFChars(s2, NULL);
          std::string result = cs1 + cs2 ;
          //再将std::string转为jstring 返回给java
          return env->NewStringUTF(result.c_str());
}
```
GetStringUTFChars返回const char* 对于C++来说就是std::string
在MainActivity里使用
```
public native String helloWorld(String s1,String s2);
.....
protected void onCreate(Bundle savedInstanceState){
   ....
   //str为helloworld
   String str = helloWorld("hello","world"));
}
.....
```

##### 添加新的类
上面都是在编辑器给我们生成的文件里操作.真写起来肯定需要新建多个类文件的.尝试新增一个C++类
1. 右键cpp文件目录 new - class - c++ class .输入名字hello.点击ok.会在目录下生成hello.cpp和hello.h
2. 编辑hello.h
```

//如果没有定义过  HELLOWORLDJNI_HELLO_H就定义下
//HELLOWORLDJNI_HELLO_H 是指hello.h这个头文件
//再往下就不会了
#ifndef HELLOWORLDJNI_HELLO_H
#define HELLOWORLDJNI_HELLO_H

#include <string>

//定义一个hello类,声明一个public方法 say
class hello {
    public:
     std::string say(std::string name);
};


#endif //HELLOWORLDJNI_HELLO_H

```
3. 根据.h来实现cpp
```
//引入hello.h
#include "hello.h"

//头文件中类的方法没有实现,只是定义了下.需要在cpp文件里实现
std::string hello::say(std::string name) {
    return "hello " + name;
}
```

4. .cpp和.h文件编辑完了就可以使用了

在native-lib.cpp中
```
//引入头文件,""的意思是在项目目录下找 <>是到系统库里去找
#include "hello.h"

//对应的java方法是helloClass(String name)
extern "C" JNIEXPORT jstring JNICALL Java_com_happy_fei_helloworldjni_MainActivity_helloClass(
        JNIEnv *env,
        jobject /* this */ ,jstring name) {
    //c++中的类就是这么奔放,声明了就可以用
    //查了下new.new是创建个对象并返回指向该对象的指针,这里的话就不需要new了      
    hello helloObj;
    //将jstring转为std::string
    std::string strName = env->GetStringUTFChars(name, NULL);
    //调用类的方法
    std::string result = helloObj.say(strName);
    //再将std::string转为jstring并返回回去
    return env->NewStringUTF(result.c_str());
}

```

5. 在MainActivity中使用
```
//声明原生方法
public native String helloClass(String name);

@Override
protected void onCreate(Bundle savedInstanceState) {
  .....
  //result 为 hello fei
  String result= helloClass("fei");
  .....
}      

```

6. 运行...然后报错..... 没有找到hello::say方法
```
error: undefined reference to 'hello::say(std::__ndk1::basic_string<char, std::__ndk1::char_traits<char>, std::__ndk1::allocator<char> >)'
```

7. 想起之前有个打包相关的,就是CMakelist.txt.将hello.cpp加入编译就可以了
```
//加了个hello.cpp
add_library( # Sets the name of the library.
             native-lib
             SHARED
             # Provides a relative path to your source file(s).
             src/main/cpp/native-lib.cpp
             src/main/cpp/hello.cpp
              )
```
8. 在运行就可以了





#### 感想
c/c++真是博大精深.好歹之前干过半年,然而复习了几天还是一脸蒙蔽,这个demo肯定有很多错误.希望自己能继续看下去.发现并修改这些错误

### 相关资料
[jni常用函数大全](https://blog.csdn.net/qinjuning/article/details/7595104)\
