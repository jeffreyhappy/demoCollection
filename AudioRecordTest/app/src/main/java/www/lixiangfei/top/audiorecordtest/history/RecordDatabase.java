package www.lixiangfei.top.audiorecordtest.history;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {RecordHistory.class}, version = 1)
public abstract class RecordDatabase extends RoomDatabase {
    public abstract RecordHistoryDao recordHistoryDao();
}
