package com.happy.fei.sdcardwrite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.v4.provider.DocumentFile;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SDCardRW {
    private String SP_NAME = "sp";
    private String SP_KEY = "rootDir";
    private String TAG = SDCardRW.class.getSimpleName();
    private static final int ROOT_DIR_REQUEST_CODE = 42;
    public Activity mContext;

    /**
     * 外置sd卡的根目录
     */
    private Uri   mRootUri;


    public SDCardRW(Activity activity){
        this.mContext = activity;
        this.mRootUri = getUriFromSP();
    }


    /**
     * 是否已经有权限了
     * @return false 没有
     *         true  有权限了
     */
    public Boolean havePermission(){
        if (mRootUri == null){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 请求根目录的读写权限
     */
    public void requestRootDirPermission(){
        Toast.makeText(mContext,"请选择外置sd卡根目录",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        mContext.startActivityForResult(intent, ROOT_DIR_REQUEST_CODE);
    }


    /**
     * 将会在授权访问的目录下创建文件
     * @param fileName    文件名字 只是单独的文件名 例如 hello.txt
     */
    public Boolean createFileIfNotExist(String fileName){
        if (mRootUri == null){
            requestRootDirPermission();
            return false;
        }
        DocumentFile treeDocumentFile = DocumentFile.fromTreeUri(mContext,mRootUri);
        DocumentFile findFile = treeDocumentFile.findFile(fileName);
        if (findFile != null){
            Log.d(TAG,"create file but file exist");
            return true;
        }
        DocumentFile documentFile = treeDocumentFile.createFile("text/plain",fileName);
        if (documentFile == null){
            Log.d(TAG,"create file fail");
            return false;
        }else {
            Log.d(TAG,"create success " + documentFile.getUri().toString());
            return true;

        }
    }

    public void printInfo(){
        Log.d(TAG,"printInfo " + getRootUri().toString());
        DocumentFile treeDocumentFile = DocumentFile.fromTreeUri(mContext,getRootUri());
        DocumentFile[] files  = treeDocumentFile.listFiles();
        for (DocumentFile oneFile : files){
            boolean canRead = oneFile.canRead();
            boolean canWrite = oneFile.canWrite();
            Log.d(TAG,oneFile.getUri().toString() + " canRead " + canRead + " canWrite " + canWrite);
        }
    }



    /**
     * 写入内容到文件里,擦除之前的内容
     * @param fileName  文件名 只是单独的文件名 例如 hello.txt
     * @param writeInfo 写入的内容
     */
    public Boolean writeFile(String fileName,String writeInfo){
        if (mRootUri == null){
            requestRootDirPermission();
            return false;
        }
        DocumentFile treeDocumentFile = DocumentFile.fromTreeUri(mContext,getRootUri());
        DocumentFile targetFile = treeDocumentFile.findFile(fileName);
        if (targetFile == null){
            Log.d(TAG,"writeFile  fail not find " + fileName);
            return false;
        }

        Uri uri = targetFile.getUri();

        try {
            // mode    r:只读  ,rw 读写, rwt 读写并截断已存在的文件
            ParcelFileDescriptor pfd = mContext.getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(writeInfo.getBytes());
            fileOutputStream.close();
            pfd.close();
            Log.d(TAG,"writeFile  success " +  uri.toString() +  " " + writeInfo);
            return true;
        } catch (FileNotFoundException e) {
            Log.d(TAG,"writeFile  FileNotFoundException " +  uri.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG,"writeFile  IOException " +  uri.toString() + " "+ e);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取文件的内容
     * @param fileName  只是单独的文件名 例如 hello.txt
     * @return
     */
    public String readFile(String fileName){
        if (mRootUri == null){
            Log.d(TAG,"readFile mRootUri null" + fileName);
            requestRootDirPermission();
            return "";
        }

        DocumentFile treeDocumentFile = DocumentFile.fromTreeUri(mContext,getRootUri());
        DocumentFile targetFile = treeDocumentFile.findFile(fileName);
        if (targetFile == null){
            Log.d(TAG,"writeFile  fail not find " + fileName);
            return "";
        }

        Uri uri = targetFile.getUri();



        try {
            // mode    r:只读  ,rw 读写, rwt 读写并截断已存在的文件
            ParcelFileDescriptor pfd = mContext.getContentResolver().
                    openFileDescriptor(uri, "r");
            FileInputStream fileInputStream =
                    new FileInputStream(pfd.getFileDescriptor());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            fileInputStream.close();
            pfd.close();
            Log.d(TAG,"readFile  success uri " +  uri.toString() );
            Log.d(TAG,"readFile  success content " +  stringBuilder.toString() );
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            Log.d(TAG,"readFile  FileNotFoundException " +  uri.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG,"readFile  IOException " +  uri.toString() + " "+ e);
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 用户选中后,会把授权过的uri返回回来
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void handleActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        Uri uri = null;
        if (data != null) {
            uri = data.getData();
            Log.i(TAG, "Uri: " + uri.toString());
        }
        if (requestCode == ROOT_DIR_REQUEST_CODE){
            mRootUri = uri;
            saveToSP(mRootUri.toString());
            final int takeFlags = data.getFlags()
                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Log.d(TAG,"takeFlags " + takeFlags);

            //是在4.4后才对sd卡读写进行限制的,大于4.4判断就行
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //授权将一直持续到用户设备重启时,调用下面一句可以更持久的使用授权
                // Check for the freshest data.
                mContext.getContentResolver().takePersistableUriPermission(uri, takeFlags);
            }
        }
    }




    private Uri getRootUri() {
        return mRootUri;
    }


    private void saveToSP(String str){
       SharedPreferences sp =  mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
       sp.edit().putString(SP_KEY,str).apply();
    }

    private Uri getUriFromSP(){
        SharedPreferences sp =  mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String strUri =  sp.getString(SP_KEY,"");
        if (TextUtils.isEmpty(strUri)){
            return null;
        }else {
            return Uri.parse(strUri);
        }
    }
}
