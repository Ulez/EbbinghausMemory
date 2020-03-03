package comulez.github.ebbinghausmemory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import fun.learnlife.base.utils.Utils;
import okhttp3.internal.Util;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.tv_time).setOnClickListener(this);
        findViewById(R.id.tv_translate).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_time:
                Toast.makeText(this,"time",Toast.LENGTH_LONG).show();
                ARouter.getInstance().build("/account/time").navigation();
                break;
            case R.id.tv_translate:
                Toast.makeText(this,"transtate",Toast.LENGTH_LONG).show();
                ARouter.getInstance().build("/translate/translate").navigation();
                break;
        }
    }
}
