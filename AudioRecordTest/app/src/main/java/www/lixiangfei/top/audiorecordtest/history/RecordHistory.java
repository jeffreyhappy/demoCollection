package www.lixiangfei.top.audiorecordtest.history;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "records")
public class RecordHistory {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String path;
}
