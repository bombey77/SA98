package bombey77.sa98;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ромашка on 13.06.2017.
 */

public class MyService extends Service {

    MyBinder myBinder = new MyBinder();

    Timer timer;

    TimerTask tTask;

    int result;

    private static final String LOG = "myLogs";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG, "onCreateMyService");
        timer = new Timer();
        schedule();
    }

    void schedule () {
        if (tTask != null) tTask.cancel();
        if (result > 0) {
            tTask = new TimerTask() {
                @Override
                public void run() {
                    Log.d(LOG, "run");
                }
            };
            timer.schedule(tTask, 1000, result);
        }
    }

    int upResult(int gap) {
        result = result + gap;
        schedule();
        return result;
    }

    int downResult(int gap) {
        if (result < 0) result = 0;
        result = result - gap;
        schedule();
        return result;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
