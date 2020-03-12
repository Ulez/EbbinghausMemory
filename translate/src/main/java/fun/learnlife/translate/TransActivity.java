package fun.learnlife.translate;

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
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;

import com.alibaba.android.arouter.facade.annotation.Route;

import fun.learnlife.base.beans.YouDaoBean;
import fun.learnlife.base.utils.Constant;
import fun.learnlife.base.utils.SpUtils;
import fun.learnlife.base.utils.Utils;
import fun.learnlife.translate.view.ITranslateView;
import fun.learnlife.translate.view.TranslateFragment;

@Route(path = "/translate/translate")
public class TransActivity extends AppCompatActivity
        implements TranslateFragment.OnFragmentInteractionListener, ITranslateView {
    private static final String TAG = "TransActivity";
    private FragmentManager fManager;
    private Intent intent;
    private int OVERLAY_PERMISSION_REQ_CODE = 45;
    private TranslateFragment translateFragment;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tran);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("翻译组件");
        intent = new Intent(this, ListenClipboardService.class);
        fManager = getSupportFragmentManager();
        askForPermission();
        translateFragment = TranslateFragment.newInstance("aaaa", "bbbb");
        fManager.beginTransaction().replace(R.id.content_frame, translateFragment).commit();
        if (intent != null) {
            startService(intent);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
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
                SpUtils.getInstance(TransActivity.this).t("权限授予失败，无法开启悬浮窗");
            } else {
                SpUtils.getInstance(TransActivity.this).t("权限授予成功！");
                SpUtils.getInstance(TransActivity.this).putT(Constant.hasPermission, true);
                startService(intent);
            }
        }
    }

    private void askForPermission() {
        if (Utils.isM() && !SpUtils.getInstance(TransActivity.this).getBoolean(Constant.hasPermission, false)) {
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
            Log.e("lcy", "onServiceConnected");
            ListenClipboardService.LocalBinder binder = (ListenClipboardService.LocalBinder) service;
            clipboardService = binder.getServiceInstance();
            clipboardService.attachAct(TransActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("lcy", "onServiceDisconnected");
        }
    };

    @Override
    public void translate(String q, String from, String to, String appKey, int salt, String sign) {
        clipboardService.translate(q, "auto", "zh_CHS", Constant.appkey, 2, Utils.md5(Constant.appkey + q + 2 + Constant.miyao));
    }

    @Override
    public void requestPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
    }

    @Override
    public void stopService() {
        if (clipboardService != null) {
            unbindService(mConnection);
            stopService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy -- unbindService");
        if (clipboardService != null)
            unbindService(mConnection);
    }

    @Override
    public void onResume() {
        SpUtils.getInstance(TransActivity.this).putT(Constant.showPop, false);
        super.onResume();
    }

    @Override
    public void onPause() {
        SpUtils.getInstance(TransActivity.this).putT(Constant.showPop, true);
        super.onPause();
    }

    @Override
    public void showResult(YouDaoBean youDaoBean) {
        if (!SpUtils.getInstance(TransActivity.this).getBoolean(Constant.showPop, true))
            translateFragment.showResult(youDaoBean);
    }

    @Override
    public void resetText() {
        if (!SpUtils.getInstance(TransActivity.this).getBoolean(Constant.showPop, true))
            translateFragment.resetText();
    }

    @Override
    public void showLoading() {
        if (!SpUtils.getInstance(TransActivity.this).getBoolean(Constant.showPop, true))
            translateFragment.showLoading();
    }

    @Override
    public void onError(String msg) {
        if (!SpUtils.getInstance(TransActivity.this).getBoolean(Constant.showPop, true))
            translateFragment.onError(msg);
    }
}
