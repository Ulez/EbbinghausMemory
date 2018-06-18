package comulez.github.ebbinghausmemory.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

import comulez.github.ebbinghausmemory.beans.RecordInfo;
import comulez.github.ebbinghausmemory.beans.TaskBean;
import comulez.github.ebbinghausmemory.utils.DatabaseHelper;

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
            Log.e("SQLException",e.getMessage());
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
}
