package www.lixiangfei.top.audiorecordtest;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.lixiangfei.top.audiorecordtest.history.RecordHistory;

public class RecordHistoryAdapter extends BaseQuickAdapter<RecordHistory,BaseViewHolder> {
    public RecordHistoryAdapter(@Nullable List<RecordHistory> data) {
        super(R.layout.item_record,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordHistory item) {
        helper.setText(R.id.tv,item.name);
    }
}
