package fun.learnlife.base;

import android.app.Application;
import android.content.Context;

public abstract class BaseApp extends Application {
    protected static Context context;
    /**
     * Application 初始化
     */
    public abstract void initModuleApp(Application application);

    /**
     * 所有 Application 初始化后的自定义操作
     */
    public abstract void initModuleData(Application application);
}
