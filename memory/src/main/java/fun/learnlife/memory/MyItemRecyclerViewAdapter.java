package fun.learnlife.memory;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import fun.learnlife.base.beans.RecordInfo;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private List<RecordInfo> mValues;
    private final TasksFragment.OnListFragmentInteractionListener mListener;

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm  -  yyyy年");// HH:mm:ss

    public MyItemRecyclerViewAdapter(List<RecordInfo> items, TasksFragment.OnListFragmentInteractionListener listener) {
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mTimeView.setText(mValues.get(position).no + "、" + simpleDateFormat.format(mValues.get(position).plandate));
        holder.mTitle.setText(mValues.get(position).title);
        holder.imageView.setImageResource(holder.mItem.isDone() ? R.drawable.done : R.drawable.un);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("", "onClick：");
                if (null != mListener) {
                    mListener.onClick(mValues, position);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("", "onLongClick：");
                if (null != mListener) {
                    mListener.onLongClick(mValues, position);
                    return true;
                }
                return false;
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
        public final ImageView imageView;
        public RecordInfo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTimeView = (TextView) view.findViewById(R.id.time);
            imageView = view.findViewById(R.id.cb_done);
            mTitle = view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTimeView.getText() + "'";
        }
    }
}
