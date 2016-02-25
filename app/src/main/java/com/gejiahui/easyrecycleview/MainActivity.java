package com.gejiahui.easyrecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        MyAdapter adapter = new MyAdapter(mDatas);
        recyclerView.setAdapter(adapter);
        View header = LayoutInflater.from(this).inflate(R.layout.header, recyclerView, false);
        adapter.setHeaderView(header);
        View footer = LayoutInflater.from(this).inflate(R.layout.foot, recyclerView, false);
        adapter.setFooterView(footer);
        adapter.setOnItemClickListener(new EasyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position, Object data) {
                Toast.makeText(MainActivity.this, "itemClick + " + (String) data, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemLongClickListener(new EasyRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position, Object data) {
                Toast.makeText(MainActivity.this, "itemLongClick + " + (String) data, Toast.LENGTH_SHORT).show();
            }
        });


    }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 25; i++)
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
                ((MyHolder)viewHolder).text.setHeight((new Random().nextInt(200))+100);
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
