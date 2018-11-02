package ipca.edjd.idomtest.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class Zone {

    public static final String ZONE_ID     = "id";
    public static final String ZONE_NAME   = "name";
    public static final String ZONE_PATH   = "path";
    public static final String ZONE_PARENT = "parent";

    @PrimaryKey
    @NonNull
    public String id      ;
    public String name    ;
    public String path    ;
    public String parent  ;

    public Zone(String id, String name, String path, String parent) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.parent = parent;
    }

    public Zone() {
        this.id = "";
        this.name = "";
        this.path = "";
        this.parent = "";
    }

}

