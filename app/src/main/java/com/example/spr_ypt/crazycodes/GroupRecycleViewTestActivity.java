package com.example.spr_ypt.crazycodes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.spr_ypt.crazycodes.pkenterV3.EnterGroupView;

/**
 * Created by Spr_ypt on 2018/2/8.
 */

public class GroupRecycleViewTestActivity extends Activity {

    private RecyclerView mGroupRecycler;

    private GroupAdapter mAdapter;

    private int[] mData = new int[]{2, 0, 1, 1, 1, 1, 1, 2, 0, 1, 1, 2, 0, 1, 2, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 0, 1, 1};

    private int[] mTestItemData = new int[]{1, 1, 1, 1, 1};

    private EnterGroupView mEnterGroupView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_test);
        initView();
        initEvent();
    }

    private void initEvent() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mData[position]) {
                    case 0:
                        return 3;
                    case 1:
                        return 1;
                    default:
                        return 3;
                }
            }
        });
        mGroupRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new GroupAdapter(this);
        mGroupRecycler.setAdapter(mAdapter);
        mAdapter.setData(mTestItemData);
        mEnterGroupView.setData(mTestItemData);
    }

    private void initView() {
        mGroupRecycler = (RecyclerView) findViewById(R.id.group_recycler);
        mEnterGroupView = (EnterGroupView) findViewById(R.id.enter_group_view);
    }

    private static class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {
        private Context mContext;

        private int[] data;

        private int[] testData1 = new int[]{1};
        private int[] testData2 = new int[]{1, 1, 1, 1, 1};
        private int[] testData3 = new int[]{1, 1};

        public GroupAdapter(Context context) {
            mContext = context;
        }

        public void setData(int[] data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            EnterGroupView itemView = new EnterGroupView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(layoutParams);
            return new GroupViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(GroupViewHolder holder, final int position) {
            switch (position % 3) {
                case 0:
                    holder.mGroup.setData(testData1);
                    break;
                case 1:
                    holder.mGroup.setData(testData2);
                    break;
                default:
                    holder.mGroup.setData(testData3);
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

    private static class GroupViewHolder extends RecyclerView.ViewHolder {

        private EnterGroupView mGroup;

        public GroupViewHolder(EnterGroupView itemView) {
            super(itemView);
            this.mGroup = itemView;
        }
    }
}

