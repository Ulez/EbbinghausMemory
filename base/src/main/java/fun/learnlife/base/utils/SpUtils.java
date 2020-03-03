package fun.learnlife.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SpUtils {
    private static SpUtils mInstance;
    private SharedPreferences sp;
    private Context context;
    private SpUtils(Context context){
        this.context = context.getApplicationContext();
        sp = context.getApplicationContext().getSharedPreferences("permissions", Context.MODE_PRIVATE);
    }
    public static SpUtils getInstance(Context context) {
        if(mInstance == null){
            synchronized (SpUtils.class){
                if(mInstance == null){
                    mInstance = new SpUtils(context);
                }
            }
        }
        return mInstance;
    }

    public void t(String m) {
        Toast.makeText(context, m, Toast.LENGTH_SHORT).show();
    }

    public void tL(String m) {
        Toast.makeText(context, m, Toast.LENGTH_LONG).show();
    }

    public void t(int sId) {
        Toast.makeText(context, context.getString(sId), Toast.LENGTH_LONG).show();
    }

    public boolean hasOverlayPermission() {
        return getBoolean(Constant.hasPermission, false);
    }

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public void putT(String key, Object value) {
        if (value instanceof String) {
            sp.edit().putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            sp.edit().putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Integer) {
            sp.edit().putInt(key, (Integer) value).commit();
        }
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public void remove(String key) {
        sp.edit().remove(key).commit();
    }

    public void removeAll() {
        sp.edit().clear().commit();
    }
}
