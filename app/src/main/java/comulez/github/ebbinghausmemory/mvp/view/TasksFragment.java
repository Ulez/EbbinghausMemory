package comulez.github.ebbinghausmemory.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comulez.github.ebbinghausmemory.MainActivity;
import comulez.github.ebbinghausmemory.R;
import comulez.github.ebbinghausmemory.beans.RecordInfo;
import comulez.github.ebbinghausmemory.beans.TaskContent;
import comulez.github.ebbinghausmemory.dao.RecordDao;
import comulez.github.ebbinghausmemory.utils.CalculateUtil;
import comulez.github.ebbinghausmemory.utils.Utils;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TasksFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MyItemRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TasksFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TasksFragment newInstance(int columnCount) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//            TaskContent content = new TaskContent("任务1");
            RecordDao recordDao = new RecordDao(getActivity());
            List<RecordInfo> records = getRecordInfos(recordDao.selectAllByPlanDate());
            adapter = new MyItemRecyclerViewAdapter(records, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private boolean show7Days = true;

    public void setShow7Days(boolean f) {
        show7Days = f;
    }

    public boolean getShow7Days() {
        return show7Days;
    }

    public void notifyDataSetChanged() {
        RecordDao recordDao = new RecordDao(getActivity());
        List<RecordInfo> results = getRecordInfos(recordDao.selectAllByPlanDate());
        adapter.setData(results);
        adapter.notifyDataSetChanged();
    }

    private List<RecordInfo> getRecordInfos(List<RecordInfo> records) {
        List<RecordInfo> results = new ArrayList<>();
        if (show7Days) {
            Date now = new Date();
            for (int i = 0; i < records.size(); i++) {
                if (!records.get(i).done && CalculateUtil.in7days(records.get(i).plandate, now)) {
                    results.add(records.get(i));
                }
            }
        } else {
            results = records;
        }
        return results;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLongClick(List<RecordInfo> records, int position);

        void onClick(List<RecordInfo> records, int position);
    }
}
