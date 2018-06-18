package comulez.github.ebbinghausmemory.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import comulez.github.ebbinghausmemory.R;
import comulez.github.ebbinghausmemory.beans.RecordInfo;
import comulez.github.ebbinghausmemory.mvp.view.TasksFragment.OnListFragmentInteractionListener;


/**
 * {@link RecyclerView.Adapter} that can display a {@link RecordInfo} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private List<RecordInfo> mValues;
    private final OnListFragmentInteractionListener mListener;

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");// HH:mm:ss

    public MyItemRecyclerViewAdapter(List<RecordInfo> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTimeView.setText(mValues.get(position).no + ". " + simpleDateFormat.format(mValues.get(position).plandate));
        holder.mTitle.setText(mValues.get(position).title);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setData(List<RecordInfo> records) {
        mValues = records;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTimeView;
        public final TextView mTitle;
        public RecordInfo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTimeView = (TextView) view.findViewById(R.id.time);
            mTitle = view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTimeView.getText() + "'";
        }
    }
}
