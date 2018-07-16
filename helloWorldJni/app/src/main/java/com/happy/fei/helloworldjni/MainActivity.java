package com.happy.fei.helloworldjni;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    TextView tv ;

    private String TAG = MainActivity.class.getSimpleName();
    private String strLog="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv =  findViewById(R.id.sample_text);
        String result = helloClass("fei");
        tv.setText(result);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    strLog = "";
                    doTestOnlyExt();
                    tv.setText(strLog);
                }else {
                    tv.setText("请允许读写权限");
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
            }
        });

        findViewById(R.id.btn_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testCreateFile("text/plain","hello.txt");
            }
        });

        findViewById(R.id.btn_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("/storage/0CFE-1006/java_write.txt","write by java");
//                alterDocument(Uri.parse("content://com.android.externalstorage.documents/document/0CFE-1006%3Ahello.txt"));
            }
        });

        findViewById(R.id.btn_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = readByJavaFile("/storage/0CFE-1006/2.txt");
                strLog = result;
                tv.setText(strLog);
//                testReadFileContent(Uri.parse("content://com.android.externalstorage.documents/document/0CFE-1006%3Ahello.txt"));
            }
        });

        findViewById(R.id.btn_read_from_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result  = readFile("/storage/0CFE-1006/2.txt");
                strLog = result;
                tv.setText(strLog);
            }
        });


        findViewById(R.id.btn_write_from_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               String result =  writeFile("/storage/0CFE-1006/3.txt","this is c","/storage/0CFE-1006/2.txt");
//               String result =  writeFile("/storage/0CFE-1006/Android/data/com.happy.fei.helloworldjni.test1/3.txt","this is c","/storage/0CFE-1006/2.txt");
               String result =  writeFile("/storage/0CFE-1006/Android/data/com.happy.fei.testjava/3.txt","this is c","/storage/0CFE-1006/2.txt");
               tv.setText(result);
            }
        });

        findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRequest();
            }
        });
        printLog();


    }

    public  String write(String filePath,String info){
        Log.d(TAG,"write start " + filePath);
        File file = new File(filePath);
        if (!file.exists()){
            try {
                Log.d(TAG,"write createNewFile " + filePath);
                file.createNewFile();
            } catch (IOException e) {
                Log.d(TAG,"write createNewFile IOException " + e);
                e.printStackTrace();
            }
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(info);
            bw.close();
            Log.d(TAG,"write success " + filePath);
            return "write success";
        } catch (IOException e) {
            Log.d(TAG,"write IOException " + e);
            e.printStackTrace();
        }
        return "write fail";
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printLog(){
        Log.d("feifeifei"," printLog ");
        File[] file = getExternalFilesDirs(Environment.DIRECTORY_DOCUMENTS);
        for(File item : file){
            Log.d("feifeifei"," item " + item.getPath().toString());
        }
    }


    private void doTestMySelfDoc(){
//        ArrayList<String> allFilePath = testExternal();
        File[] files = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
             files = getExternalFilesDirs(Environment.DIRECTORY_DOCUMENTS);
        }
        for (File root : files){
            Log.d("feifeifei","start " + root);
            strLog = strLog +  " start " + root + "\n";
            createFile(root ,"from" + root);
            String result  = openFromJNI(root.toString());
            strLog = strLog +  " result " + result + "\n";
            Log.d("feifeifei","result  " + result);
            strLog = strLog +  " end start " + root + "\n \n \n";
            Log.d("feifeifei","end start " + root);
            Log.d("feifeifei","\n \n");
        }
    }

    private void doTest(){
        ArrayList<String> allFilePath = testExternal();
        for (String root : allFilePath){
            Log.d("feifeifei","start " + root);
            strLog = strLog +  " start " + root + "\n";
            createFile(new File(root) ,"from" + root);
            String result  = openFromJNI(new File(root).toString());
            strLog = strLog +  " result " + result + "\n";
            Log.d("feifeifei","result  " + result);
            strLog = strLog +  " end start " + root + "\n \n \n";
            Log.d("feifeifei","end start " + root);
            Log.d("feifeifei","\n \n");
        }
    }

    private void doTestOnlyExt(){
            String root = "/storage/0CFE-1006/Android";
            Log.d("feifeifei","start " + root);
            strLog = strLog +  " start " + root + "\n";
            createFile(new File(root),root);
            String result  = openFromJNI(root);
            strLog = strLog +  " result " + result + "\n";
            Log.d("feifeifei","result  " + result);
            strLog = strLog +  " end start " + root + "\n";
            Log.d("feifeifei","end start " + root);
    }

    private ArrayList<String> testExternal(){
            ArrayList<String> allFilePath = new ArrayList<>();
           StorageHack.RefStorageVolume[] list =  StorageHack.getVolumeList(getApplication());
           if (list != null){
               for (StorageHack.RefStorageVolume item : list){
                   try {
                       String path =  item.getPath();
//                       Log.d("feifeifei"," path " + path);
                       allFilePath.add(path);
                   } catch (Exception e) {
                       e.printStackTrace();
                       strLog = strLog +  " e " + e + "\n";
                       Log.d("feifeifei"," e " + e);
                   }
               }
           }
           return allFilePath;
    }


//    public File[] getExternalDirs() {
//        final StorageVolume[] volumes = StorageManager.getVolumeList(mUserId,StorageManager.FLAG_FOR_WRITE);
//        final File[] files = new File[volumes.length];
//        for (int i = 0; i < volumes.length; i++) {
//            files[i] = volumes[i].getPathFile();
//        }
//        return files;
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        doTest();

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native String helloWorld(String s1,String s2);
    public native String helloClass(String name);
    public native String openFromJNI(String rootDir);
    public native String readFile(String fullPath);
    public native String writeFile(String fullPath,String info,String testPath);


    private void createFile(File rootDir,String more){
//        File DownloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//        Log.d("feifeifei", Environment.getExternalStorageDirectory().toString());
//        File rootDir = Environment.getExternalStorageDirectory();
        File newFile = new File(rootDir,"hello.txt");
        strLog = strLog +  " newFile " + newFile.toString() + "\n";
        Log.d("feifeifei","newFile " + newFile.toString());
        if (!newFile.exists()){
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(newFile);
            fileWriter.write("hello jni file " + more);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static final int WRITE_REQUEST_CODE = 43;
    private static final int READ_REQUEST_CODE = 42;
    private void testCreateFile(String mimeType, String fileName) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

        // Filter to only show results that can be "opened", such as
        // a file (as opposed to a list of contacts or timezones).
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Create a file with the requested MIME type.
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        startActivityForResult(intent, WRITE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK){
            return;
        }

            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.i("feifeifei", "Uri: " + uri.toString());
            }
    }

    private void alterDocument(Uri uri) {
        try {
            ParcelFileDescriptor pfd = getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(("Overwritten by MyCloud at " +
                    System.currentTimeMillis() + "\n").getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        public  String readByJavaFile(String filePath){
            Log.d(TAG,"read start " + filePath);
            File file = new File(filePath);
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String line ;
                while ((line = br.readLine()) != null ){
                    sb.append(line);
                }
                br.close();
                Log.d(TAG,"read success " + sb.toString());
                return sb.toString();
            } catch (FileNotFoundException e) {
                Log.d(TAG,"read FileNotFoundException " + e);
                e.printStackTrace();
            } catch (IOException e) {
                Log.d(TAG,"read IOException " + e);
                e.printStackTrace();
            }
            return "read fail";
        }


    private void testReadFileContent(Uri uri) {
        try {
            ParcelFileDescriptor pfd = getContentResolver().
                    openFileDescriptor(uri, "r");
            FileInputStream fileInputStream=
                    new FileInputStream(pfd.getFileDescriptor());
            byte[] info = new byte[1024];
            fileInputStream.read(info);
            Log.d("feifeifei","getFileContent " + new String(info));
            // Let the document provider know you're done by closing the stream.
            fileInputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void performRequest(){
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
//        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
//        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }
}
