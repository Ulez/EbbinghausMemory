package comulez.github.ebbinghausmemory.utils;

import android.util.Log;

import java.util.Date;


public class CalculateUtil {
    private Long currentTimeMillis;
    private static final int minute = 1000 * 60;//分钟换毫秒；
    private static final int hour = 60 * 1000 * 60;//小时换毫秒；
    private static final int day = 24 * 1000 * 60 * 60;//天换毫秒；

    public CalculateUtil(Long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }


    public Date getDate(int position, int cu) {
//        Log.e("lcy", "更新第" + position + "条,点击记录的第" + cu + "条");
        if (cu == position) return new Date(currentTimeMillis);
        switch (position) {
            case 0:
                currentTimeMillis += 0;
                break;
            case 1:
                currentTimeMillis += minute * 5;
                break;
            case 2:
                currentTimeMillis += minute * 30;
                break;
            case 3:
                currentTimeMillis += hour * 12;
                break;
            case 4:
                currentTimeMillis += day * 1;
                break;
            case 5:
                currentTimeMillis += day * 2;
                break;
            case 6:
                currentTimeMillis += day * 4;
                break;
            case 7:
                currentTimeMillis += day * 7;
                break;
            default:
                currentTimeMillis += day * 15;
                break;
        }
        return new Date(currentTimeMillis);
    }
}
