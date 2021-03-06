package fun.learnlife.base.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fun.learnlife.base.beans.RecordInfo;
import fun.learnlife.base.beans.TaskBean;
import fun.learnlife.base.utils.DatabaseHelper;

/**
 * 操作Record表的DAO类
 */
public class RecordDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<RecordInfo, Integer> dao;

    public RecordDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(RecordInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(RecordInfo data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            Log.e("SQLException", e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteByTask(TaskBean data) {
        try {
            DeleteBuilder<RecordInfo, Integer> deleteBuilder = dao.deleteBuilder();
            deleteBuilder.where().eq(RecordInfo.COLUMNNAME_TASK, data.getId());
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(RecordInfo data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改数据
    public void update(RecordInfo data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 通过ID查询一条数据
    public RecordInfo queryById(int id) {
        RecordInfo Record = null;
        try {
            Record = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Record;
    }

    // 通过条件查询文章集合（通过任务ID查找）
    public List<RecordInfo> queryByTaskId(int task_id) {
        try {
            return dao.queryBuilder().where().eq(RecordInfo.COLUMNNAME_TASK, task_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查询task表中的所有数据
    public List<RecordInfo> selectAll() {
        List<RecordInfo> recordInfos = null;
        try {
            recordInfos = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordInfos;
    }

    public List<RecordInfo> selectAllByPlanDate() {
        List<RecordInfo> recordInfos = selectAll();
        Collections.sort(recordInfos, new Comparator<RecordInfo>() {
            @Override
            public int compare(RecordInfo o1, RecordInfo o2) {
                return o1.getPlandate().compareTo(o2.getPlandate());
            }
        });
        return recordInfos;
    }
}
