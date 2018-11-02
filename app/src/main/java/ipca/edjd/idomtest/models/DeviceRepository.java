package ipca.edjd.idomtest.models;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Switch;

import java.util.List;

public class DeviceRepository {

    enum TaskType{ INSERT, UPDATE, DELETE}

    private DeviceDao deviceDao;

    public DeviceRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        deviceDao = db.deviceDao();
    }

    public List<Device> getAll(){
        return deviceDao.getAll();
    }

    public Device get(String id){
        return deviceDao.get(id);
    }

    public void insert(Device device){
        new DbAsyncTask(deviceDao, TaskType.INSERT).execute(device);
    }

    public void update(Device device){
        new DbAsyncTask(deviceDao, TaskType.UPDATE).execute(device);
    }

    public void delete(Device device){
        new DbAsyncTask(deviceDao, TaskType.DELETE).execute(device);
    }

    private class DbAsyncTask extends AsyncTask<Device, Void, Void> {

        private DeviceDao mAsyncTaskDao;
        private TaskType taskType;

        public DbAsyncTask(DeviceDao deviceDao, TaskType taskType) {
            mAsyncTaskDao = deviceDao;
            this.taskType = taskType;
        }

        @Override
        protected Void doInBackground(Device... devices) {
            switch (taskType){
                case INSERT:
                    mAsyncTaskDao.insert(devices[0]);
                    break;
                case UPDATE:
                    mAsyncTaskDao.update(devices[0]);
                    break;
                case DELETE:
                    mAsyncTaskDao.delete(devices[0]);
                    break;
            }

            return null;
        }
    }
}
