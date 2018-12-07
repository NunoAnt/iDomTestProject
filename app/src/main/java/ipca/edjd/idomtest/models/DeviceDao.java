package ipca.edjd.idomtest.models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DeviceDao {

    @Query("SELECT * FROM device")
    LiveData<List<Device>> getAll();

    @Query("SELECT * FROM device WHERE id IN (:devicesIds)")
    LiveData<List<Device>> loadAllByIds(String[] devicesIds);

    @Query("SELECT * FROM device WHERE id = :id")
    Device get(String id );

    @Query("SELECT * FROM device WHERE idname = :idname")
    Device getByIdName(String idname );

    @Query("SELECT * FROM device WHERE idname = :idname")
    LiveData<Device> getByIdNameLive(String idname );

    @Query("DELETE FROM device")
    void deleteAll();

    @Insert
    void insertAll(Device... devices);

    @Insert
    void insert(Device device);

    @Update
    void update(Device device);

    @Delete
    void delete(Device device);

}
