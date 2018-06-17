package comulez.github.ebbinghausmemory.mvp.view;

import comulez.github.ebbinghausmemory.beans.YouDaoBean;

/**
 * Created by Ulez on 2017/7/26.
 * Email：lcy1532110757@gmail.com
 */


public interface ITranslateView {

    void showResult(YouDaoBean youDaoBean);

    void resetText();

    void showLoading();

    void onError(String msg);
}
