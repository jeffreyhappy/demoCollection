#include <jni.h>
#include <string>
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include <pthread.h>
#include <chrono>
#include <thread>
#include "android/log.h"
extern "C" JNIEXPORT jstring JNICALL
Java_www_lixiangfei_top_audiorecordtest_NativeAudioPlayActivity_hello(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

static SLObjectItf engineObject = NULL;
static SLEngineItf engineEngine;
static SLObjectItf outputMixObject = NULL;
static SLEnvironmentalReverbItf outputMixEnvironmentalReverb = NULL;

// buffer queue player interfaces
static SLObjectItf bqPlayerObject = NULL;
static SLPlayItf bqPlayerPlay;
static SLAndroidSimpleBufferQueueItf bqPlayerBufferQueue;
static SLEffectSendItf bqPlayerEffectSend;
static SLVolumeItf bqPlayerVolume;

static pthread_mutex_t  audioEngineLock = PTHREAD_MUTEX_INITIALIZER;

static long havePlayCount ;
extern "C" JNIEXPORT void JNICALL
Java_www_lixiangfei_top_audiorecordtest_NativeAudioPlayActivity_initEngine(
        JNIEnv* env,
        jobject /* this */) {

    /*创建*/
    SLresult result;
    // create engine
    result = slCreateEngine(&engineObject, 0, NULL, 0, NULL, NULL);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;

    // realize the engine
    result = (*engineObject)->Realize(engineObject, SL_BOOLEAN_FALSE);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;

    // get the engine interface, which is needed in order to create other objects
    result = (*engineObject)->GetInterface(engineObject, SL_IID_ENGINE, &engineEngine);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;

    // create output mix, with environmental reverb specified as a non-required interface
    const SLInterfaceID ids[1] = {SL_IID_ENVIRONMENTALREVERB};
    const SLboolean req[1] = {SL_BOOLEAN_FALSE};
    result = (*engineEngine)->CreateOutputMix(engineEngine, &outputMixObject, 1, ids, req);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;

    // realize the output mix
    result = (*outputMixObject)->Realize(outputMixObject, SL_BOOLEAN_FALSE);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;



}


extern "C" JNIEXPORT void JNICALL
Java_www_lixiangfei_top_audiorecordtest_NativeAudioPlayActivity_testOpenFile(
        JNIEnv* env,
        jobject /* this */) {
    FILE * fp = fopen("/storage/sdcard0/Android/data/www.lixiangfei.top.audiorecordtest/files/audio201015021015.pcm", "r");
    int  BUFFER_SIZE = 44100*2*2;
    int buff[BUFFER_SIZE];
    int readCount;
    __android_log_print(ANDROID_LOG_INFO, "native-play", "testOpen file  start ");
    while ((readCount = fread(buff,sizeof(int),BUFFER_SIZE,fp)) != 0) {
        // enqueue another buffer
        __android_log_print(ANDROID_LOG_INFO, "native-play", "testOpen file  readCount %d ", readCount);
    }
    __android_log_print(ANDROID_LOG_INFO, "native-play", "testOpen file  end ");
    fclose(fp);

}
// this callback handler is called every time a buffer finishes playing
void bqPlayerCallback(SLAndroidSimpleBufferQueueItf bq, void *context)
{
    assert(bq == bqPlayerBufferQueue);
    assert(NULL == context);

//    FILE * fp = fopen("/storage/sdcard0/Android/data/www.lixiangfei.top.audiorecordtest/files/audio201027020927.pcm", "rb");
    FILE * fp = fopen("/storage/sdcard0/Android/data/www.lixiangfei.top.audiorecordtest/files/audio201015021015.pcm", "r");
    //使用这个会只输出一个
    int  BUFFER_SIZE = 44100*2;
//    int  BUFFER_SIZE = 1024*4;
    int buff[BUFFER_SIZE];
    size_t readCount ;
    SLresult result;
    __android_log_print(ANDROID_LOG_INFO, "native-play", "bqPlayerCallback file  start ");
    fseek(fp,havePlayCount,SEEK_SET);
    if ((readCount = fread(buff,sizeof(int),BUFFER_SIZE,fp)) == BUFFER_SIZE){
        havePlayCount += readCount;
        result = (*bqPlayerBufferQueue)->Enqueue(bqPlayerBufferQueue, buff, BUFFER_SIZE);

        __android_log_print(ANDROID_LOG_INFO, "native-play", "bqPlayerCallback file  readCount %d current pos %ld ", readCount,havePlayCount);

        (void)result;
    }

    __android_log_print(ANDROID_LOG_INFO, "native-play", "bqPlayerCallback file  end ");
//    free(buffer);
    fclose(fp);
    pthread_mutex_unlock(&audioEngineLock);
}

void createPlayer(){


    //创建audioPlayer

    SLuint32 bqPlayerSampleRate = SL_SAMPLINGRATE_44_1;

    // configure audio source
    SLDataLocator_AndroidSimpleBufferQueue loc_bufq = {SL_DATALOCATOR_ANDROIDSIMPLEBUFFERQUEUE, 2};
    SLDataFormat_PCM format_pcm = {SL_DATAFORMAT_PCM, 1, SL_SAMPLINGRATE_44_1,
                                   SL_PCMSAMPLEFORMAT_FIXED_16, SL_PCMSAMPLEFORMAT_FIXED_16,
                                   SL_SPEAKER_FRONT_CENTER, SL_BYTEORDER_LITTLEENDIAN};
    /*
     * Enable Fast Audio when possible:  once we set the same rate to be the native, fast audio path
     * will be triggered
     */
    if(bqPlayerSampleRate) {
        format_pcm.samplesPerSec = bqPlayerSampleRate;       //sample rate in mili second
    }
    SLDataSource audioSrc = {&loc_bufq, &format_pcm};

    // configure audio sink
    SLDataLocator_OutputMix loc_outmix = {SL_DATALOCATOR_OUTPUTMIX, outputMixObject};
    SLDataSink audioSnk = {&loc_outmix, NULL};

    /*
     * create audio player:
     *     fast audio does not support when SL_IID_EFFECTSEND is required, skip it
     *     for fast audio case
     */
    const SLInterfaceID ids[3] = {SL_IID_BUFFERQUEUE, SL_IID_VOLUME, SL_IID_EFFECTSEND,
            /*SL_IID_MUTESOLO,*/};
    const SLboolean req[3] = {SL_BOOLEAN_TRUE, SL_BOOLEAN_TRUE, SL_BOOLEAN_TRUE,
            /*SL_BOOLEAN_TRUE,*/ };

    SLresult result;
    result = (*engineEngine)->CreateAudioPlayer(engineEngine, &bqPlayerObject, &audioSrc, &audioSnk,
                                                bqPlayerSampleRate? 2 : 3, ids, req);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;

    // realize the player
    result = (*bqPlayerObject)->Realize(bqPlayerObject, SL_BOOLEAN_FALSE);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;

    // get the play interface
    result = (*bqPlayerObject)->GetInterface(bqPlayerObject, SL_IID_PLAY, &bqPlayerPlay);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;

    // get the buffer queue interface
    result = (*bqPlayerObject)->GetInterface(bqPlayerObject, SL_IID_BUFFERQUEUE,
                                             &bqPlayerBufferQueue);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;

    // register callback on the buffer queue
    result = (*bqPlayerBufferQueue)->RegisterCallback(bqPlayerBufferQueue, bqPlayerCallback, NULL);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;

    // get the effect send interface
    bqPlayerEffectSend = NULL;
    if( 0 == bqPlayerSampleRate) {
        result = (*bqPlayerObject)->GetInterface(bqPlayerObject, SL_IID_EFFECTSEND,
                                                 &bqPlayerEffectSend);
        assert(SL_RESULT_SUCCESS == result);
        (void)result;
    }

#if 0   // mute/solo is not supported for sources that are known to be mono, as this is
    // get the mute/solo interface
    result = (*bqPlayerObject)->GetInterface(bqPlayerObject, SL_IID_MUTESOLO, &bqPlayerMuteSolo);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;
#endif

    // get the volume interface
    result = (*bqPlayerObject)->GetInterface(bqPlayerObject, SL_IID_VOLUME, &bqPlayerVolume);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;

    // set the player's state to playing
    result = (*bqPlayerPlay)->SetPlayState(bqPlayerPlay, SL_PLAYSTATE_PLAYING);
    assert(SL_RESULT_SUCCESS == result);
    (void)result;
    havePlayCount = 0;
    bqPlayerCallback(bqPlayerBufferQueue,NULL);
}


extern "C" JNIEXPORT void JNICALL
        Java_www_lixiangfei_top_audiorecordtest_NativeAudioPlayActivity_startPlay(        JNIEnv* env,
                                                                                          jobject /* this */) {
//    if (pthread_mutex_trylock(&audioEngineLock)) {
//        // If we could not acquire audio engine lock, reject this request and client should re-try
//        return ;
//    }
    createPlayer();
}


extern "C" JNIEXPORT void JNICALL
Java_www_lixiangfei_top_audiorecordtest_NativeAudioPlayActivity_destory(        JNIEnv* env,
                                                                                  jobject /* this */) {
//    if (pthread_mutex_trylock(&audioEngineLock)) {
//        // If we could not acquire audio engine lock, reject this request and client should re-try
//        return ;
//    }
    if (bqPlayerObject != NULL) {
        (*bqPlayerObject)->Destroy(bqPlayerObject);
        bqPlayerObject = NULL;
        bqPlayerPlay = NULL;
        bqPlayerBufferQueue = NULL;
        bqPlayerEffectSend = NULL;
        bqPlayerVolume = NULL;
    }

    // destroy engine object, and invalidate all associated interfaces
    if (engineObject != NULL) {
        (*engineObject)->Destroy(engineObject);
        engineObject = NULL;
        engineEngine = NULL;
    }

}
