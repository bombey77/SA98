package bombey77.sa98;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean statusBind;

    Intent intent;

    ServiceConnection serviceConnection;

    private static final String LOG = "myLogs";

    TextView tvResult;

    MyService myService;

    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tvResult);

        intent = new Intent(this, MyService.class);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myService = ((MyService.MyBinder)service).getService();
                Log.d(LOG, "onServiceConnected");
                statusBind = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                statusBind = false;
                Log.d(LOG, "onServiceDisconnected");
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(intent, serviceConnection, 0);
        Log.d(LOG, "bindService");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!statusBind)return;
        unbindService(serviceConnection);
        statusBind = false;
        Log.d(LOG, "onStopService");
    }

    public void onStart(View view) {
        startService(intent);
    }

    public void onUp(View view) {
        if (!statusBind) return;
        result = myService.upResult(500);
        tvResult.setText(Integer.toString(result));
    }

    public void onDown(View view) {
        if (!statusBind) return;
        result = myService.downResult(500);
        tvResult.setText(Integer.toString(result));
    }
}
