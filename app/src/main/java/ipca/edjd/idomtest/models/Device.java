package ipca.edjd.idomtest.models;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import java.util.List;

@Entity
public class Device{

    public static String DEVICE_ID     = "id";
    public static String DEVICE_IDNAME = "idName";
    public static String DEVICE_NAME   = "name";
    public static String DEVICE_ZONE   = "zone";

    @PrimaryKey
    @NonNull
    public String id;
    public String idName;
    public String name;
    public String idZone;

    public Device() {
        this.id         = "";
        this.idName     = "";
        this.name       = "";
        this.idZone     = "";
    }

    public Device(String id, String idName, String name, String zone) {
        this.id = id;
        this.idName = idName;
        this.name = name;
        this.idZone = zone;
    }

}

