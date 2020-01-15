package fun.learnlife.memory;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fun.learnlife.base.BaseApp;

/**
 * Created by Ulez on 2017/7/25.
 * Email：lcy1532110757@gmail.com
 */


public class EApplication extends BaseApp {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initModuleApp(this);
        initModuleData(this);
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void initModuleApp(Application application) {
        //初始化Memory组件的服务，对外提供；
    }

    @Override
    public void initModuleData(Application application) {

    }
}
