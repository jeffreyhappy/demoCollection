package com.fei.downloaddemo.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fei.downloaddemo.R;

/**
 * 创建日期：2018/6/4 on 11:44
 * 描述:
 * 作者:Li
 */

public class HomeItemVH extends RecyclerView.ViewHolder {
    public TextView tvName;
    public TextView tvSize;
    public Button btn;
    public HomeItemVH(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_name);
        tvSize = itemView.findViewById(R.id.tv_size);
        btn  = itemView.findViewById(R.id.btn);
    }
}
