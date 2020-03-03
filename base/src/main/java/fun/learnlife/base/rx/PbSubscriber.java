package fun.learnlife.base.rx;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import fun.learnlife.base.exception.ApiExceptionFactory;
import fun.learnlife.base.utils.SpUtils;
import fun.learnlife.base.utils.Utils;
import rx.Subscriber;

/**
 * Created by Ulez on 2017/7/26.
 * Email：lcy1532110757@gmail.com
 */


public abstract class PbSubscriber<T> extends Subscriber<T> {

    private ProgressBar progressBar;
    private Context context;

    protected PbSubscriber(Context context) {
        this(null,context);
    }

    protected PbSubscriber(View progressBar,Context context) {
        super(null, false);
        if (progressBar instanceof ProgressBar)
            this.progressBar = (ProgressBar) progressBar;
        this.context = context.getApplicationContext();
    }

    @Override
    public void onCompleted() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }

    private String TAG = "PbSubscriber";

    @Override
    public void onError(Throwable e) {
        Log.i(TAG, e.toString());
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        SpUtils.getInstance(context).t(ApiExceptionFactory.getApiException(e,context).getDisplayMessage());
    }

    @Override
    public void onNext(T t) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }
}
