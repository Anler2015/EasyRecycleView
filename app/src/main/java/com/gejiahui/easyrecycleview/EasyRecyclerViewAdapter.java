package com.gejiahui.easyrecycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by gejiahui on 2016/2/24.
 */
public abstract class EasyRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_BODY = 1;
    private static final int TYPE_FOOT = 2;

    private View mHeaderView;
    private View mFooterView;

    private List<T> mDatas;

    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mItemClickListener = listener;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getFooterPosition());
    }

    public EasyRecyclerViewAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public int getItemViewType(int position) {
        if(mFooterView == null && mHeaderView == null){
            return TYPE_BODY;
        }
        if(mHeaderView != null && position == 0){
            return TYPE_HEAD;
        }
        if(mFooterView != null && mHeaderView == null && position == mDatas.size())
        {
            return  TYPE_FOOT;
        }
        if(mFooterView != null && mHeaderView != null && position == mDatas.size()+1 )
        {
            return  TYPE_FOOT;
        }
        return TYPE_BODY;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEAD){
            return new EasyViewHolder(mHeaderView);
        }
        if(viewType == TYPE_FOOT){
            return new EasyViewHolder(mFooterView);
        }
        return onCreate(parent,viewType);//获取资源文件
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEAD || getItemViewType(position) ==TYPE_FOOT){
            return;
        }

        final int realPosition = getRealPosition(holder);
        final T data = mDatas.get(realPosition);
        onBind(holder,realPosition,data);

        if(mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.OnItemClick(v,realPosition,data);
                }
            });
        }

    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    private int getFooterPosition() {
        if(mFooterView == null){
            return -1;
        }
        if( mHeaderView == null )
        {
            return  mDatas.size();
        }
        if( mHeaderView != null  )
        {
            return   mDatas.size()+1;
        }
        return -1;
    }

    @Override
    public  int getItemCount(){
      if(mFooterView !=null && mHeaderView != null){
          return mDatas.size()+2;
      }
      else if(mFooterView !=null || mHeaderView != null){
            return mDatas.size()+1;
        }
        return mDatas.size();
    }

    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, final int viewType);
    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);

    class EasyViewHolder extends RecyclerView.ViewHolder{

        public EasyViewHolder(View itemView) {
            super(itemView);
        }
    }

    interface OnItemClickListener<T>{
        public void OnItemClick(View view, int position,T data);
    }

}
