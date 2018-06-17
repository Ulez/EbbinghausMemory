package comulez.github.ebbinghausmemory.mvp.view.dummy;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /**
     * An array of sample (dummy) items.
     */
    public List<RecordInfo> ITEMS = new ArrayList<RecordInfo>();
    public long currentTimeMillis;

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, RecordInfo> ITEM_MAP = new HashMap<String, RecordInfo>();

    private static final int COUNT = 8;
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");// HH:mm:ss

    public TaskContent(String title) {
        // Add some sample items.
        currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i <= COUNT; i++) {
            addItem(createRecordItem(i, title));
        }
    }


    private void addItem(RecordInfo item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private RecordInfo createRecordItem(int position, String title) {
        Log.e(TAG, "createRecordItem position=" + position);
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
            case 8:
                currentTimeMillis += day * 15;
                break;
        }
        Date date = new Date(currentTimeMillis);
        return new RecordInfo(String.valueOf(position), simpleDateFormat.format(date), makeDetails(position), title);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class RecordInfo {
        public final String id;
        public Date date;
        public boolean done = false;
        public String content;
        public String title;
        public String details;

        public RecordInfo(String id, String title, Date date) {
            this.id = id;
            this.title = title;
            this.date = date;
        }


        public RecordInfo(String id, String content, String details, String title) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.title = title;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
