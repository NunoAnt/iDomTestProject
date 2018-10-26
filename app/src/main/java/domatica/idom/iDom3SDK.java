package domatica.idom;

public class iDom3SDK
{
	public static native boolean	Init(int cycletime);
	public static native boolean	IsProDistribution();
	public static native void		Free();
	public static native int		GatewaySerial();
	public static native void		PumpEvents();
	public static native int		ThreadsBaseSleep(int time);
	public static native int		Version();
	
	public static native boolean	Connect(String hostAddr, int hostPort);
	public static native boolean	ChangeServer(int id, String remoteHostAddress, int remoteHostPort, boolean setAsDefault);
    public static native boolean	DisableObjectFilter(int id);
    public static native void		Disconnect();
    public static native String 	GetConnectionStats(int id);
    public static native boolean	Listen(int port);
    public static native String		SetObjectFilter(int id, String csv_object_list);
    public static native boolean	SetServiceKey(byte[] keyData, int keySize);
	public static native int		SystemCount();
    
	public static native boolean	HWOEnable(int id);
	public static native int		HWOExists(int id, String hwobjname);
	public static native int		HWOGetValue(int id, String hwobjname);
	public static native boolean 	HWOSetValue(int id, String hwobjname, int value);
	
	public static native String 	CompileUserProgram(int id, String data);
	public static native boolean	DeleteUserFile(int id, int fileid);
	public static native boolean	DeleteUserProgram(int id);
	public static native boolean	DisableLogFilter(int id);
	public static native boolean	EmitString(int id, String str);
	public static native String		GetAttributes(int id, String attributes);
	public static native String		GetData(int id, String idname);
	public static native String		GetUserFileMap(int id);
	public static native int		GetValue(int id, String idname);
	public static native String		IDName2ID(int id, String idnamestr);
    public static native String		ID2IDName(int id, String idstr);
    public static native boolean 	ObjectExists(int id, String idname);
    public static native boolean	RequestGatewayInfo(int id);
    public static native boolean	RequestLogRecords(int id, int itemsCount);
	public static native boolean	RequestLogSinceTimestamp(int id, int timestamp);
	public static native boolean	RequestMainSystemFile(int id);
	public static native boolean	RequestUserFile(int id, int fileid);
    public static native boolean	RequestUserFileInfo(int id, int fileid);
    public static native boolean	RequestUserProgramFile(int id);
    public static native boolean	SendUserFile(int id, int fileid, String filename, byte[] data, int size, boolean compress);
    public static native String		SendUserProgram(int id, String data);
    public static native boolean	SetData(int id, String idname, String data);
    public static native String		SetLogFilter(int id, String csv_object_list);
	public static native boolean 	SetTimeZone(int id, int default_tz_offset, String tzNameString);
	public static native boolean 	SetTimeStamp(int id, int timestamp);
	public static native boolean	SetValue(int id, String idname, int value);
	public static native boolean	SyncClock(int id, String datetimestr);
	
    public static native void		RegConnectionStateCB(String class_path, String cb_fname);
    public static native void		RegConnectionStateExCB(String class_path, String cb_fname);
    public static native void		RegEmittedStringCB(String classPath, String fname);
    public static native void		RegFileEventCB(String class_path, String cb_fname);
    public static native void 		RegHWObjectValueCB(String classPath, String cb_fname);
    public static native void 		RegInfoEventCB(String classPath, String fname);
    public static native void		RegLogFileCB(String class_path, String cb_fname);
    public static native void		RegObjectDataCB(String class_path, String cb_fname);
    public static native void		RegObjectValueCB(String class_path, String cb_fname);
    public static native void		RegSystemFileCB(String class_path, String cb_fname);
    public static native void		RegUserProgramFileCB(String class_path, String cb_fname);
    public static native void		RegUserFileCB(String class_path, String cb_fname);
    public static native void		RegUserFileInfoCB(String class_path, String cb_fname);
    
    private static String loadlibresult;
    
    public static String LoadResult()
	{
		return loadlibresult;
	}
	
	public static void RegisterCallBacks(String classPath)
	{
		RegConnectionStateCB(classPath, "iDomConnectionStateEvent");		// class_path, member_func_name (must be static)
		//RegConnectionStateExCB(classPath, "iDomConnectionStateExEvent");	// class_path, member_func_name (must be static)
		RegEmittedStringCB(classPath, "iDomEmittedStringEvent");			// class_path, member_func_name (must be static)
		RegFileEventCB(classPath, "iDomFileEvent");							// class_path, member_func_name (must be static)
		RegInfoEventCB(classPath, "iDomInfoEvent");							// class_path, member_func_name (must be static)
		RegLogFileCB(classPath, "iDomLogFileEvent");						// class_path, member_func_name (must be static)
		RegObjectDataCB(classPath, "iDomObjectDataEvent");					// class_path, member_func_name (must be static)
		//RegObjectValueCB(classPath, "iDomObjectValueEvent");				// class_path, member_func_name (must be static)
		RegSystemFileCB(classPath, "iDomSystemFileEvent");					// class_path, member_func_name (must be static) 
		RegUserFileCB(classPath, "iDomUserFileEvent");						// class_path, member_func_name (must be static)
		RegUserFileInfoCB(classPath, "idomUserFileInfoEvent");				// class_path, member_func_name (must be static)
		RegUserProgramFileCB(classPath, "iDomUserProgramFileEvent");		// class_path, member_func_name (must be static)
	}
    
    static 
	{
        try
        {
        	System.loadLibrary("iDom3SDKLiteAndroidARM_32");
        	//System.loadLibrary("iDom3SDKProAndroidARM_32");
        	
        	loadlibresult = "iDom3SDK Lib Loaded";
        }
        catch (UnsatisfiedLinkError ule) 
        {
        	loadlibresult = ule.toString();
        }
    }
}
