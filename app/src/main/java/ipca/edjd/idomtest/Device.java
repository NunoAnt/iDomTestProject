package ipca.edjd.idomtest;

public class Device {

    public static String DEVICE_ID     = "id";
    public static String DEVICE_IDNAME = "idName";
    public static String DEVICE_NAME   = "name";
    public static String DEVICE_ZONE   = "zone";

    String id;
    String idName;
    String name;
    String zone;

    public Device() {
        this.id         = "";
        this.idName     = "";
        this.name       = "";
        this.zone       = "";
    }

    public Device(String id, String idName, String name, String zone) {
        this.id = id;
        this.idName = idName;
        this.name = name;
        this.zone = zone;
    }
}
