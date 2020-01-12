package fun.learnlife.base.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import fun.learnlife.EApplication;
import fun.learnlife.base.beans.TaskBean;
import fun.learnlife.base.utils.DatabaseHelper;

/**
 * 操作Task数据表的Dao类，封装这操作task表的所有操作
 * 通过DatabaseHelper类中的方法获取ORMLite内置的DAO类进行数据库中数据的操作
 * <p>
 * 调用dao的create()方法向表中添加数据
 * 调用dao的delete()方法删除表中的数据
 * 调用dao的update()方法修改表中的数据
 * 调用dao的queryForAll()方法查询表中的所有数据
 */
public class TaskDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<TaskBean, Integer> dao;

    public TaskDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(TaskBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 向task表中添加一条数据
    public void insert(TaskBean data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除task表中的一条数据
    public void delete(TaskBean data) {
        try {
            dao.delete(data);
            new RecordDao(EApplication.getContext()).deleteByTask(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改task表中的一条数据
    public void update(TaskBean data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询task表中的所有数据
    public List<TaskBean> selectAll() {
        List<TaskBean> tasks = null;
        try {
            tasks = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // 根据ID取出用户信息
    public TaskBean queryById(int id) {
        TaskBean task = null;
        try {
            task = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }
}
