package www.lixiangfei.top.audiorecordtest.history;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface RecordHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(RecordHistory  history);

    @Query("SELECT * FROM records order by id desc")
    Flowable<List<RecordHistory>> load();


}
