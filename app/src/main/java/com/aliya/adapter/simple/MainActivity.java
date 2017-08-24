package com.aliya.adapter.simple;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aliya.adapter.BaseRecyclerAdapter;
import com.aliya.adapter.DecorAdapter;
import com.aliya.adapter.divider.ListDivider;
import com.aliya.adapter.simple.adapter.DemoAdapter;
import com.aliya.adapter.simple.adapter.DiffDataSimpleAdapter;
import com.aliya.adapter.simple.callback.LoadMoreListener;
import com.aliya.adapter.simple.callback.LoadingCallBack;
import com.aliya.adapter.simple.holder.FooterLoadMore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycle;
    private DecorAdapter mAdapter;

    private int count;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycle = (RecyclerView) findViewById(R.id.recycler);

        recycle.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            mList.add(String.valueOf(i));
//            switch (i % 3 + 5) {
//                case 0:
//                    mList.add(String.valueOf(i));
//                    break;
//                case 1:
//                    mList.add(Integer.valueOf(i));
//                    break;
//                case 2:
//                    mList.add(null);
//                    break;
//                default:
//                    mList.add(String.valueOf(i));
//                    break;
//            }
        }

        mAdapter = new DecorAdapter(new DemoAdapter(mList));

        recycle.setAdapter(mAdapter);

        recycle.addItemDecoration(new ListDivider(5, Color.BLUE, 0, 0, true, false, false));

        View inflate = getLayoutInflater().inflate(R.layout.item_header_layout, recycle, false);
        ((TextView) inflate.findViewById(R.id.tv)).setText("第1个header");
        mAdapter.addHeaderView(inflate);

        View inflate1 = getLayoutInflater().inflate(R.layout.item_header_layout, recycle, false);
        ((TextView) inflate1.findViewById(R.id.tv)).setText("第2个header");
        mAdapter.addHeaderView(inflate1);

        View inflate2 = getLayoutInflater().inflate(R.layout.item_header_layout, recycle, false);
        ((TextView) inflate2.findViewById(R.id.tv)).setText("第3个header");
        mAdapter.addHeaderView(inflate2);

        mAdapter.setFooterLoadMore(new FooterLoadMore(recycle, new LoadMoreListener<String>() {

            @Override
            public void onLoadMoreSuccess(String data) {
//                List datas = mAdapter.getDatas();
                mList.add(data);
                mAdapter.notifyDataSetChanged();
                Log.e("TAG", "notifyDataSetChanged");
            }

            @Override
            public void onLoadMore(final LoadingCallBack<String> callback) {
                Log.e("TAG", "onLoadMore " + count);
                recycle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        count++;
                        if (count == 8) {
                            callback.onEmpty();
                        } else if (count % 3 == 0) {
                            callback.onError("", 1);
                        } else {
                            callback.onSuccess("成功 " + count);
                        }
                    }
                }, 2000);
            }

        }).getView());

        View footer1 = getLayoutInflater().inflate(R.layout.item_header_layout, recycle, false);
        ((TextView) footer1.findViewById(R.id.tv)).setText("第1个footer");
        mAdapter.addFooterView(footer1);

        View footer2 = getLayoutInflater().inflate(R.layout.item_header_layout, recycle, false);
        ((TextView) footer2.findViewById(R.id.tv)).setText("第2个footer");
        mAdapter.addFooterView(footer2);

        View refresh = getLayoutInflater().inflate(R.layout.item_header_layout, recycle, false);
        ((TextView) refresh.findViewById(R.id.tv)).setText("我是下拉刷新");
        mAdapter.setHeaderRefresh(refresh);

    }
}
