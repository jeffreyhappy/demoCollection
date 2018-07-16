#include <jni.h>
#include <string>
#include "hello.h"
#include "FileAction.h"



extern "C" JNIEXPORT jstring JNICALL Java_com_happy_fei_helloworldjni_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL Java_com_happy_fei_helloworldjni_MainActivity_helloWorld(
        JNIEnv *env,
        jobject /* this */ ,jstring s1 ,jstring s2) {
    std::string cs1 = env->GetStringUTFChars(s1, NULL);
    std::string cs2 = env->GetStringUTFChars(s2, NULL);
    std::string result = cs1 + cs2 ;
    return env->NewStringUTF(result.c_str());
}

extern "C" JNIEXPORT jstring JNICALL Java_com_happy_fei_helloworldjni_MainActivity_helloClass(
        JNIEnv *env,
        jobject /* this */ ,jstring name) {
    hello helloObj;
    std::string strName = env->GetStringUTFChars(name, NULL);
    std::string result = helloObj.say(strName);
    return env->NewStringUTF(result.c_str());
}

extern "C" JNIEXPORT jstring  JNICALL
Java_com_happy_fei_helloworldjni_MainActivity_openFromJNI(JNIEnv *env, jobject instance,jstring rootDir) {
    std::string strRootDir = env->GetStringUTFChars(rootDir,NULL);
    std::string  path =  strRootDir +   "/hello.txt";
    FILE* file = fopen(path.c_str(),"r+");
    jstring result;
    if (file == NULL){
        result =  env->NewStringUTF("file null");
    } else{
        result =  env->NewStringUTF("find path ");
        fclose(file);
    }


    FILE *fp = NULL;
    fp = fopen(path.c_str(),"r");
    if (fp != NULL){
        char buffer[2560];
        fread(buffer,sizeof(buffer),1,fp);
        result = env->NewStringUTF(buffer);
        fclose(fp);
    } else{
        result = env->NewStringUTF("read not find file");
    }


    return result;
}


extern "C" JNIEXPORT jstring  JNICALL
Java_com_happy_fei_helloworldjni_MainActivity_readFile(JNIEnv *env, jobject instance,jstring filePath) {
    std::string  path =  env->GetStringUTFChars(filePath,NULL);
    FILE* file = fopen(path.c_str(),"r");
    jstring result;
    if (file == NULL){
        result =  env->NewStringUTF("file null");
    } else{
        result =  env->NewStringUTF("find path ");
        fclose(file);
    }


    FILE *fp = NULL;
    fp = fopen(path.c_str(),"r");
    if (fp != NULL){
        char buffer[2560];
        fread(buffer,sizeof(buffer),1,fp);
        result = env->NewStringUTF(buffer);
        fclose(fp);
    } else{
        result = env->NewStringUTF("read not find file");
    }


    return result;
}


extern "C" JNIEXPORT jstring  JNICALL
Java_com_happy_fei_helloworldjni_MainActivity_writeFile(JNIEnv *env, jobject instance,jstring filePath,jstring info,jstring testFile) {
    std::string  path =  env->GetStringUTFChars(filePath,NULL);
    std::string  testpath =  env->GetStringUTFChars(testFile,NULL);
    FILE* file = fopen(testpath.c_str(),"r");
    jstring result;

    if (file != NULL){
        fclose(file);

//        result =  env->NewStringUTF("not find path ");
    } else{
//        result =  env->NewStringUTF("find path ");
    }


    FILE *fp = NULL;
    fp = fopen(path.c_str(),"w+");
    if (fp != NULL){
        const char* cinfo = env->GetStringUTFChars(info,NULL);
        fwrite(cinfo, sizeof(cinfo),1,fp);
        fclose(fp);
        result =  env->NewStringUTF("write  success");
    } else{
        result = env->NewStringUTF("write not success");
    }


    return result;
}


extern "C"
JNIEXPORT jboolean JNICALL
Java_com_happy_fei_helloworldjni_MainActivity_createByFileAction(JNIEnv *env, jobject instance,
                                                               jstring fullPath_) {
    const char *fullPath = env->GetStringUTFChars(fullPath_, 0);
    FileAction fileAction(fullPath);
    bool  bResult = fileAction.create();
    return bResult;
//    env->ReleaseStringUTFChars(fullPath_, fullPath);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_happy_fei_helloworldjni_MainActivity_readByFileAction(JNIEnv *env, jobject instance,
                                                               jstring fullPath_) {
    const char *fullPath = env->GetStringUTFChars(fullPath_, 0);

    FileAction fileAction(fullPath);
    std::string result = fileAction.read();

    return env->NewStringUTF(result.c_str());
}


extern "C"
JNIEXPORT void JNICALL
Java_com_happy_fei_helloworldjni_MainActivity_writeByFileAction(JNIEnv *env, jobject instance,
                                                                jstring fullPath_, jstring info_) {
    const char *fullPath = env->GetStringUTFChars(fullPath_, 0);
    const char *info = env->GetStringUTFChars(info_, 0);

    FileAction fileAction(fullPath);
    fileAction.write(info);
}