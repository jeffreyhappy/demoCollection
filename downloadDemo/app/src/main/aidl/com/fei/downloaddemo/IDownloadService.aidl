package com.fei.downloaddemo;

import com.fei.downloaddemo.IDownloadCallback;

/**
 * 创建日期：2018/6/4 on 16:04
 * 描述:
 * 作者:Li
 */

interface IDownloadService {
//   void onDownloadChange();
   void addListener(IDownloadCallback callback);
   void removeListener(IDownloadCallback callback);
}
