package comulez.github.ebbinghausmemory.beans;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comulez.github.ebbinghausmemory.EApplication;
import comulez.github.ebbinghausmemory.dao.RecordDao;
import comulez.github.ebbinghausmemory.dao.TaskDao;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TaskContent {

    private static final String TAG = "TaskContent";
    private static final int minute = 1000 * 60;//分钟换毫秒；
    private static final int hour = 60 * 1000 * 60;//小时换毫秒；
    private static final int day = 24 * 1000 * 60 * 60;//天换毫秒；

    public static final int TASK_TYPE_NEW = 0;
    public static final int TASK_TYPE_OLD = 1;

    public int task_type;
    /**
     * An array of sample (dummy) items.
     */
    public List<RecordInfo> ITEMS = new ArrayList<>();
    public long currentTimeMillis;
    TaskBean task;
    public static final Map<Integer, RecordInfo> ITEM_MAP = new HashMap<Integer, RecordInfo>();
    private static final int COUNT = 10;

    private TaskContent(String title, int task_type) {
        currentTimeMillis = System.currentTimeMillis();
        task = new TaskBean(title, '1', new Date(), title);
        new TaskDao(EApplication.getContext()).insert(task);
        int start = 0;
        switch (task_type) {
            case TASK_TYPE_NEW:
                start = 0;
                break;
            case TASK_TYPE_OLD:
                start = 4;
                break;
        }
        for (int i = start; i <= COUNT; i++) {
            addItem(createRecordItem(i, title));
        }
    }

    public static TaskContent newTask(String title) {
        return new TaskContent(title, TASK_TYPE_NEW);
    }

    public static TaskContent oldTask(String title) {
        return new TaskContent(title, TASK_TYPE_OLD);
    }


    private void addItem(RecordInfo item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private RecordInfo createRecordItem(int position, String title) {
        Date date;
        RecordInfo recordInfo;
        switch (task_type) {
            case TASK_TYPE_OLD:
                switch (position) {
                    case 4:
                        currentTimeMillis += 0;
                        break;
                    case 5:
                        currentTimeMillis += day * 1;
                        break;
                    case 6:
                        currentTimeMillis += day * 2;
                        break;
                    case 7:
                        currentTimeMillis += day * 4;
                        break;
                    case 8:
                        currentTimeMillis += day * 7;
                        break;
                    default:
                        currentTimeMillis += day * 15;
                        break;
                }
                date = new Date(currentTimeMillis);
                recordInfo = new RecordInfo(position, title, date, task, position);
                new RecordDao(EApplication.getContext()).insert(recordInfo);
                return recordInfo;
            default:
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
                date = new Date(currentTimeMillis);
                recordInfo = new RecordInfo(position, title, date, task, position);
                new RecordDao(EApplication.getContext()).insert(recordInfo);
                return recordInfo;
        }

    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

}
