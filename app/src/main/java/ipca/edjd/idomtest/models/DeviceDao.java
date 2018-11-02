package ipca.edjd.idomtest.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DeviceDao {

    @Query("SELECT * FROM device")
    List<Device> getAll();

    @Query("SELECT * FROM device WHERE id IN (:devicesIds)")
    List<Device> loadAllByIds(String[] devicesIds);

    @Query("SELECT * FROM device WHERE id = :id")
    Device get(String id );


    @Insert
    void insertAll(Device... devices);

    @Insert
    void insert(Device device);

    @Update
    void update(Device device);

    @Delete
    void delete(Device device);

}
