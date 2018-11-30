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
    public static String DEVICE_IDNAME = "idname";
    public static String DEVICE_NAME   = "name";
    public static String DEVICE_ZONE   = "zone";
    public static String DEVICE_TYPE   = "devtype";
    public static String DEVICE_DATA   = "data";

    @PrimaryKey
    @NonNull
    public String id;
    public String idname;
    public String name;
    public String idZone;
    public String devType;
    public int data;

    public Device() {
        this.id         = "";
        this.idname     = "";
        this.name       = "";
        this.idZone     = "";
        this.devType    = "";
        this.data       = 0;
    }

    public Device(String id, String idname, String name, String zone, String devType, int data) {
        this.id = id;
        this.idname = idname;
        this.name = name;
        this.idZone = zone;
        this.devType = devType;
        this.data = data;
    }

}

