package comulez.github.ebbinghausmemory;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import comulez.github.ebbinghausmemory.beans.RecordInfo;
import comulez.github.ebbinghausmemory.beans.TaskContent;
import comulez.github.ebbinghausmemory.beans.YouDaoBean;
import comulez.github.ebbinghausmemory.mvp.ListenClipboardService;
import comulez.github.ebbinghausmemory.mvp.view.ITranslateView;
import comulez.github.ebbinghausmemory.mvp.view.TasksFragment;
import comulez.github.ebbinghausmemory.mvp.view.TranslateFragment;
import comulez.github.ebbinghausmemory.utils.Constant;
import comulez.github.ebbinghausmemory.utils.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TasksFragment.OnListFragmentInteractionListener, TranslateFragment.OnFragmentInteractionListener, ITranslateView {

    private static final String TAG = "MainActivity";
    private FragmentManager fManager;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private Intent intent;
    private int OVERLAY_PERMISSION_REQ_CODE = 45;
    private TranslateFragment translateFragment;
    int i = 0;
    private TasksFragment tasksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.t("新建任务");
                i++;
                new TaskContent("任务" + i);
                tasksFragment.notifyDataSetChanged();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tasksFragment = TasksFragment.newInstance(10);
        fManager.beginTransaction().replace(R.id.content_frame, tasksFragment).commit();
        Log.e(TAG, "add TasksFragment");
    }

    /**
     * task返回
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                Utils.t("权限授予失败，无法开启悬浮窗");
            } else {
                Utils.t("权限授予成功！");
                Utils.putT(Constant.hasPermission, true);
                startService(intent);
            }
        }
    }

    private void askForPermission() {
        if (Utils.isM() && !Utils.getBoolean(Constant.hasPermission, false)) {
            new AlertDialog.Builder(this).setMessage(getString(R.string.tip1))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermission();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        } else {
            startService(intent);
        }
    }

    public ListenClipboardService clipboardService;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.i("lcy", "onServiceConnected");
            ListenClipboardService.LocalBinder binder = (ListenClipboardService.LocalBinder) service;
            clipboardService = binder.getServiceInstance();
            clipboardService.attachAct(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("lcy", "onServiceDisconnected");
        }
    };

    @Override
    public void translate(String q, String from, String to, String appKey, int salt, String sign) {
        Log.e(TAG, "translate");
        clipboardService.translate(q, "auto", "zh_CHS", Constant.appkey, 2, Utils.md5(Constant.appkey + q + 2 + Constant.miyao));
    }

    @Override
    public void requestPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
    }

    @Override
    public void stopService() {
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy -- unbindService");
        if (clipboardService != null)
            unbindService(mConnection);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        fab.setVisibility(View.VISIBLE);
        int id = item.getItemId();
        if (id == R.id.nav_tasks) {
            tasksFragment = TasksFragment.newInstance(10);
            fManager.beginTransaction().replace(R.id.content_frame, tasksFragment).commit();
            Log.e("lcy", "tasksFragment,fly_content");
        } else if (id == R.id.nav_translate) {
            if (intent == null) {
                askForPermission();
                intent = new Intent(this, ListenClipboardService.class);
                startService(intent);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            }
            translateFragment = TranslateFragment.newInstance("aaaa", "bbbb");
            fManager.beginTransaction().replace(R.id.content_frame, translateFragment).commit();
            Log.e("lcy", "tasksFragment,tasksFragment");
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onResume() {
        Utils.putT(Constant.showPop, false);
        super.onResume();
    }

    @Override
    public void onPause() {
        Utils.putT(Constant.showPop, true);
        super.onPause();
    }

    @Override
    public void onListFragmentInteraction(RecordInfo item) {
        Log.e("lcy", item.toString());
    }

    @Override
    public void showResult(YouDaoBean youDaoBean) {
        if (!Utils.getBoolean(Constant.showPop, true))
            translateFragment.showResult(youDaoBean);
    }

    @Override
    public void resetText() {
        if (!Utils.getBoolean(Constant.showPop, true))
            translateFragment.resetText();
    }

    @Override
    public void showLoading() {
        if (!Utils.getBoolean(Constant.showPop, true))
            translateFragment.showLoading();
    }

    @Override
    public void onError(String msg) {
        if (!Utils.getBoolean(Constant.showPop, true))
            translateFragment.onError(msg);
    }
}
