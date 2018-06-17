package comulez.github.ebbinghausmemory.mvp.model;

import comulez.github.ebbinghausmemory.mvp.DataListener;

/**
 * Created by Ulez on 2017/7/28.
 * Email：lcy1532110757@gmail.com
 */


public interface ITranslateModel {
    void translate(String q, String from, String to, String appKey, int salt, String sign, DataListener listener);
}
