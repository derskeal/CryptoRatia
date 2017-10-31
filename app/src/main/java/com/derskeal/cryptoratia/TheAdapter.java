package com.derskeal.cryptoratia;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Derskeal on 20/10/2017.
 */

public class TheAdapter extends RecyclerView.Adapter<TheAdapter.ViewHolder> {
    private String[] mDataset;
    private String[] vDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public TextView mTextView;
        public LinearLayout mLinearLayout;



        public ViewHolder(LinearLayout v) {
            super(v);
            mLinearLayout = v;
        }
    }

    public TheAdapter(String[] myDataset, String[] myDatasetValue) {

        mDataset = myDataset;
        vDataset = myDatasetValue;
    }

    @Override
    public TheAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_linear_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView uctext = (TextView) holder.mLinearLayout.findViewById(R.id.usecardtext);
        uctext.setText(mDataset[position]);

        TextView ucval =  (TextView) holder.mLinearLayout.findViewById(R.id.usecardvalue);
        ucval.setText(vDataset[position]);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mDataset.length;
    }

}
