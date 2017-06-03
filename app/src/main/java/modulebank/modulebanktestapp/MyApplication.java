package modulebank.modulebanktestapp;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by user on 03.06.2017.
 */

public class MyApplication extends Application {
    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);

    }
}
