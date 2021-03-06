package fun.learnlife.translate.view;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import fun.learnlife.base.beans.YouDaoBean;
import fun.learnlife.base.utils.Constant;
import fun.learnlife.base.utils.SpUtils;
import fun.learnlife.base.utils.Utils;
import fun.learnlife.translate.TransActivity;
import fun.learnlife.translate.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TranslateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TranslateFragment extends Fragment implements ITranslateView, View.OnClickListener {
    private static final String TAG = "TranslateFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    CheckBox etCb;
    EditText etWord;
    TextView tvPronounce;
    TextView tvResult;
    TextView tvExplains;
    private String[] PERMISSIONS = {
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public TranslateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TranslateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TranslateFragment newInstance(String param1, String param2) {
        TranslateFragment fragment = new TranslateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "tasksFragment,onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);
        etCb = view.findViewById(R.id.cb);
        etWord = view.findViewById(R.id.et_word);
        tvPronounce = view.findViewById(R.id.tv_pronounce);
        tvResult = view.findViewById(R.id.tv_result);
        tvExplains = view.findViewById(R.id.tv_Explains);

        view.findViewById(R.id.et_word).setOnClickListener(this);
        view.findViewById(R.id.iv_trans).setOnClickListener(this);
        view.findViewById(R.id.button).setOnClickListener(this);
        view.findViewById(R.id.button_per).setOnClickListener(this);
        view.findViewById(R.id.button_clean).setOnClickListener(this);

//        R.id.et_word, R.id.iv_trans, R.id.button, R.id.button_per, R.id.button_clean

        setTransType();
        etCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtils.getInstance(getContext()).putT(Constant.youdao, !SpUtils.getInstance(getContext()).getBoolean(Constant.youdao, true));
                setTransType();
            }
        });
        mListener = (OnFragmentInteractionListener) getActivity();
        return view;
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setTransType() {
        if (SpUtils.getInstance(getContext()).getBoolean(Constant.youdao, true)) {
            etCb.setChecked(true);
            etCb.setText("有道翻译");
        } else {
            etCb.setChecked(true);
            etCb.setText("百度翻译");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        Utils.hideSoftKeyboard(this.getActivity());
        tvResult.setText(getString(R.string.loading));
        tvExplains.setText("");
        tvPronounce.setText("");
    }

    @Override
    public void onResume() {
        SpUtils.getInstance(getContext()).putT(Constant.showPop, false);
        super.onResume();
    }

    @Override
    public void onPause() {
        SpUtils.getInstance(getContext()).putT(Constant.showPop, true);
        super.onPause();
    }

    @Override
    public void onError(String msg) {
        tvResult.setText(msg);
    }

    @Override
    public void showResult(YouDaoBean youDaoBean) {
        try {
            if (youDaoBean != null) {
                resetText();
                if (SpUtils.getInstance(getContext()).getBoolean(Constant.youdao, true)) {
                    etWord.setText(youDaoBean.getQuery());
                    Utils.setEditTextSelectionToEnd(etWord);
                    tvResult.setText(youDaoBean.getTranslation().get(0));
                    String phonetic = youDaoBean.getBasic().getPhonetic();
                    if (!TextUtils.isEmpty(phonetic))
                        tvPronounce.setText("[" + phonetic + "]");
                    tvExplains.setText(Utils.getALl(youDaoBean.getBasic().getExplains(), youDaoBean.getWeb()));
                }
                {
                    Utils.setEditTextSelectionToEnd(etWord);
                    tvResult.setText(youDaoBean.getTrans_result().get(0).getDst());
                }
            } else {
                tvResult.setText(getString(R.string.noresult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetText() {
        tvExplains.setText("");
        tvResult.setText("");
        tvPronounce.setText("");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.et_word) {
        } else if (id == R.id.iv_trans) {
            String q = etWord.getText().toString();
            Log.e(TAG, "onClick translate");
            mListener.translate(q, "auto", "zh_CHS", Constant.appkey, 2, Utils.md5(Constant.appkey + q + 2 + Constant.miyao));
        } else if (id == R.id.button) {
            mListener.stopService();
            ((TransActivity) mListener).finish();
        } else if (id == R.id.button_per) {
            mListener.requestPermission();
        } else if (id == R.id.button_clean) {
            etWord.setText("");
            resetText();
        }
    }

    public interface OnFragmentInteractionListener {
        void translate(String q, String from, String to, String appKey, int salt, String sign);

        void requestPermission();

        void stopService();
    }
}
