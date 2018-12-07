package ipca.edjd.idomtest.models;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class ZoneRepository {

    enum TaskType{ INSERT, UPDATE, DELETE}

    private ZoneDao zoneDao;

    public ZoneRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        zoneDao = db.zoneDao();
    }

    public LiveData<List<Zone>> getAll(){
        return zoneDao.getAll();
    }

    public Zone get(String id){
        return zoneDao.get(id);
    }

    public void insert(Zone zone){
        new DbAsyncTask(zoneDao, TaskType.INSERT).execute(zone);
    }

    public void update(Zone zone){
        new DbAsyncTask(zoneDao, TaskType.UPDATE).execute(zone);
    }

    public void delete(Zone zone){
        new DbAsyncTask(zoneDao, TaskType.DELETE).execute(zone);
    }

    public void deleteAll(){
        zoneDao.deleteAll();
    }


    private class DbAsyncTask extends AsyncTask<Zone, Void, Void> {

        private ZoneDao mAsyncTaskDao;
        private TaskType taskType;

        public DbAsyncTask(ZoneDao zoneDao, TaskType taskType) {
            mAsyncTaskDao = zoneDao;
            this.taskType = taskType;
        }

        @Override
        protected Void doInBackground(Zone... zones) {
            switch (taskType){
                case INSERT:
                    mAsyncTaskDao.insert(zones[0]);
                    break;
                case UPDATE:
                    mAsyncTaskDao.update(zones[0]);
                    break;
                case DELETE:
                    mAsyncTaskDao.delete(zones[0]);
                    break;
            }

            return null;
        }
    }
}
