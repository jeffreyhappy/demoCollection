---
title: 可移除sd卡的读写
date: 2018/7/12 10:51
---
# 可移除的sd卡的读写
领导让我测试下外置sd卡各版本的读写有没有问题.我自信的一比,这个怎么可能出问题.一顿操作猛如虎,果然出了问题

### 误区
安卓手机一般都会内置存储卡,开发人员只需要通过 Environment.getExternalStorageDirectory() 就能获取到内置存储卡的根地址,我就噼里啪啦在根地址读写文件,只需要声明下读写权限,并且在6.0以上动态的申请文件读写权限.创建读写删除都是没有问题的.然而我们讨论的是外置sd卡,可以移除的那种.测试下了果然报错了,permission
```
 W/System.err: java.io.FileNotFoundException: /storage/0CFE-1006/java_write.txt (Permission denied)
```
测试了一轮,读是可以的,写和创建就报Permission denied
所以,通过Environment.getExternalStorageDirectory()获取的是内置存储卡,虽然英文名翻译过来是外置的.内置存储卡的读写是有权限的
经过各种百度.外置可移除的sd卡在4.4之后已经限制了.原因是谷歌认为外置可移除的sd卡是不安全的,文件说没就没了.但是给开发人员开了个口子,就是通过官方提供的存储访问框架来读写
[点击查看存储访问框架文档](https://developer.android.google.cn/guide/topics/providers/document-provider)

这个文档里面演示了创建,读写,删除等等操作.但是每次操作一下就要跳转文档访问界面,这个对于文件读写功能来说太麻烦了,例如:
创建文档
```
private static final int WRITE_REQUEST_CODE = 43;
...
private void createFile(String mimeType, String fileName) {
    Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

    // Filter to only show results that can be "opened", such as
    // a file (as opposed to a list of contacts or timezones).
    intent.addCategory(Intent.CATEGORY_OPENABLE);

    // Create a file with the requested MIME type.
    intent.setType(mimeType);
    intent.putExtra(Intent.EXTRA_TITLE, fileName);
    startActivityForResult(intent, WRITE_REQUEST_CODE);
}
```
调用完createFile后在onAcitivtyResult会拿到创建好的文件的uri.但是在哪创建你是控制不了.这可不行,继续百度
看到了[这篇文章](https://blog.csdn.net/u010784887/article/details/53560025)
```
Android 5.0 起不能通过流的形式直接往外置SDCard目标路径url里面写入数据了，必须通过support.v4.provider.DocumentFile来实现，可以通过发送Intent.ACTION_OPEN_DOCUMENT_TREE，动态授予SDCard目录树的读写权限。
```
可以授予目录树的读写权限,如果授予的权限是外置sd卡的根目录,那么不是随便我操作.剩下的就是百度Intent.ACTION_OPEN_DOCUMENT_TREE相关用法,这就很简单了

请求根目录的读写权限
调用了requestRootDirPermission后,用户选择完目录会回调onActivity来告诉结果.我们保存下来对应的目录就可以了
```
    /**
     *
     */
    public void requestRootDirPermission(){
        Toast.makeText(mContext,"请选择外置sd卡根目录",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        mContext.startActivityForResult(intent, ROOT_DIR_REQUEST_CODE);
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

```

接下来就是创建,写,读的操作了,用这套框架不能直接用文件流读写,得先通过contentResolver来获取对应的ParcelFileDescriptor来操作
```
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
```


[demo地址](https://github.com/jeffreyhappy/demoCollection/tree/master/SDCardWrite)
