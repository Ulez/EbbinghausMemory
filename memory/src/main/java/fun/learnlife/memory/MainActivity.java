package fun.learnlife.memory;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Date;
import java.util.List;

import fun.learnlife.EApplication;
import fun.learnlife.base.beans.RecordInfo;
import fun.learnlife.base.beans.TaskContent;
import fun.learnlife.base.dao.RecordDao;
import fun.learnlife.base.dao.TaskDao;
import fun.learnlife.base.utils.CalculateUtil;

public class MainActivity extends AppCompatActivity implements TasksFragment.OnListFragmentInteractionListener{

    private static final String TAG = "MainActivity";
    private FragmentManager fManager;
    private FloatingActionButton fab;
    private TasksFragment tasksFragment;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        fManager = getSupportFragmentManager();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(activity)
                        .title(R.string.title)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                if (!TextUtils.isEmpty(input.toString())) {
                                    TaskContent.oldTask(input.toString());
                                    tasksFragment.notifyDataSetChanged();
                                }
                            }
                        }).show();
            }
        });
        tasksFragment = TasksFragment.newInstance(10);
        fManager.beginTransaction().replace(R.id.content_frame, tasksFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLongClick(final List<RecordInfo> records, final int position) {
        final int taskId = records.get(position).getTask().getId();
        new MaterialDialog.Builder(this)
                .title(R.string.delete)
                .positiveText(R.string.allow)
                .negativeText(R.string.deny)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Snackbar.make(fab, "已取消！ヾ(◍°∇°◍)ﾉﾞ ", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        new TaskDao(EApplication.getContext()).delete(records.get(position).getTask());
                        Snackbar.make(fab, "已删除！୧(๑•̀◡•́๑)૭ ", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        tasksFragment.notifyDataSetChanged();
                    }
                })
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_today) {
            tasksFragment.setShowDays(1);
            tasksFragment.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.action_3days) {
            tasksFragment.setShowDays(3);
            tasksFragment.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.action_7days) {
            tasksFragment.setShowDays(7);
            tasksFragment.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.action_all) {
            tasksFragment.setShowDays(-1);
            tasksFragment.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(final List<RecordInfo> records, final int position) {
        final int taskId = records.get(position).getTask().getId();
        new MaterialDialog.Builder(this)
                .title(R.string.confirm)
                .positiveText(R.string.allow)
                .negativeText(R.string.deny)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Snackbar.make(fab, "继续加油！ヾ(◍°∇°◍)ﾉﾞ ", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        CalculateUtil calculateUtil = new CalculateUtil(new Date().getTime());
                        for (int i = position; i < records.size(); i++) {
                            RecordInfo item = records.get(i);
                            if (item.getTask().getId() == taskId) {
                                if (i == position) item.setDone(true);
                                item.setPlandate(calculateUtil.getDate(item.getNo(), records.get(position).getNo()));
                                new RecordDao(EApplication.getContext()).update(item);
                            }
                        }
                        Snackbar.make(fab, "୧(๑•̀◡•́๑)૭ ", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        tasksFragment.notifyDataSetChanged();
                    }
                })
                .show();
    }
}
