package com.gejiahui.easyrecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initData();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        MyAdapter adapter = new MyAdapter(mDatas);
        recyclerView.setAdapter(adapter);
        View header = LayoutInflater.from(this).inflate(R.layout.header, recyclerView, false);
        adapter.setHeaderView(header);
        adapter.setFooterView(header);
    }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 20; i++)
        {
            mDatas.add(""+ i);
        }
    }

    class MyAdapter extends EasyRecyclerViewAdapter<String>{

        public MyAdapter(List<String> mDatas) {
            super(mDatas);
        }

        @Override
        public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main,parent,false);
            return new MyHolder(view);
        }

        @Override
        public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, String data) {
            if(viewHolder instanceof MyHolder){
                ((MyHolder)viewHolder).text.setText(data);
            }

        }

        class MyHolder extends EasyRecyclerViewAdapter.EasyViewHolder {
            TextView text;
            public MyHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.tv);
            }
        }
    }

}
