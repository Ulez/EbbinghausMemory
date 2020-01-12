package fun.learnlife.translate.presenter;


import android.util.Log;

import fun.learnlife.base.beans.YouDaoBean;
import fun.learnlife.base.mvp.base.BasePresenter;
import fun.learnlife.translate.DataListener;
import fun.learnlife.translate.model.TranslateModelIml;
import fun.learnlife.translate.view.ITranslateView;

/**
 * Created by Ulez on 2017/7/28.
 * Email：lcy1532110757@gmail.com
 */


public class TranslatePresenter extends BasePresenter<ITranslateView> {
    private final TranslateModelIml modelIml;

    public TranslatePresenter() {
        modelIml = new TranslateModelIml();
    }

    public void translate(String q, String from, String to, String appKey, int salt, String sign) {
        getView().showLoading();
        Log.e("TranslatePresenter","translate");
        modelIml.translate(q, from, to, appKey, salt, sign, new DataListener() {
            @Override
            public void onResult(YouDaoBean youDaoBean) {
                getView().showResult(youDaoBean);
            }

            @Override
            public void onError(String displayMessage) {
                getView().onError(displayMessage);
            }
        });
    }
}
