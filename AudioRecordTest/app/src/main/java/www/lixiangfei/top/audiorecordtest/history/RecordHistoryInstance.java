package www.lixiangfei.top.audiorecordtest.history;

import android.arch.persistence.room.Room;
import android.content.Context;

public class RecordHistoryInstance {
    private static RecordDatabase recordDatabase ;
    private static final String DB_NAME = "audio.db";

    public static RecordDatabase get(Context context){
        if (recordDatabase == null){
            synchronized (RecordHistoryInstance.class){
                if (recordDatabase == null){
                    recordDatabase = Room.databaseBuilder(context, RecordDatabase.class, DB_NAME).build();
                }
            }
        }
        return recordDatabase;
    }
}
