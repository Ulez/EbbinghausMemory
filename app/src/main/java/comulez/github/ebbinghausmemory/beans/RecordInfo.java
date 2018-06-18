package comulez.github.ebbinghausmemory.beans;

import android.support.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "record")
public class RecordInfo{
    public static final String COLUMNNAME_ID = "id";
    public static final String COLUMNNAME_NO = "no";
    public static final String COLUMNNAME_TITLE = "title";
    public static final String COLUMNNAME_PLANDATE = "plandate";
    public static final String COLUMNNAME_DONE = "done";
    public static final String COLUMNNAME_TASK = "task_id";

    @DatabaseField(columnName = COLUMNNAME_DONE, useGetSet = true)
    public boolean done = false;
    @DatabaseField(columnName = COLUMNNAME_PLANDATE, useGetSet = true)
    public Date plandate;
    @DatabaseField(columnName = COLUMNNAME_TASK, foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, foreignColumnName = TaskBean.COLUMNNAME_ID)
    private TaskBean task_id;
    @DatabaseField(columnName = COLUMNNAME_TITLE, useGetSet = true, canBeNull = false)
    public String title;

    @DatabaseField(generatedId = true, columnName = COLUMNNAME_ID, useGetSet = true)
    public int id;

    @DatabaseField(columnName = COLUMNNAME_NO, useGetSet = true)
    public int no;

    public RecordInfo() {
    }

    public RecordInfo(int id, String title, Date plandate, TaskBean task, int no) {
        this.id = id;
        this.title = title;
        this.plandate = plandate;
        this.task_id = task;
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPlandate() {
        return plandate;
    }

    public void setPlandate(Date plandate) {
        this.plandate = plandate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "RecordInfo{" +
                "id='" + id + '\'' +
                ", plandate=" + plandate +
                ", title='" + title + '\'' +
                ", done=" + done +
                '}';
    }

    public TaskBean getTask() {
        return task_id;
    }
}
