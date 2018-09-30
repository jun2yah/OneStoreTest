package com.jun2yah.codingtest.onestoretest.view.servicelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jun2yah.codingtest.onestoretest.R;
import com.jun2yah.codingtest.onestoretest.model.ServiceItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<ServiceItem> mItems;
    Context mContext;

    private ItemView itemView;

    public RecyclerViewAdapter(ArrayList itemList) {
        mItems = itemList;
    }


    public void setItemClick(ItemView itemView) {
        this.itemView = itemView;
    }

    /**
     * View 생성
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        mContext = parent.getContext();

        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;
    }

    /**
     * 재활용되는 View 가 호출, Adapter 가 해당 position 에 해당하는 데이터를 결합
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        // 해당 position에 해당하는 데이터 결합
        holder.textViewServiceName.setText(mItems.get(position).getServiceName());

        // 생성된 List 중 선택된 목록번호를 Toast로 출력
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, String.format("%d 선택", position + 1), Toast.LENGTH_SHORT).show();
                if (itemView != null) {
                    itemView.onItemClick(v, position);
                }

            }
        });
    }

    /**
     * 데이터 갯수
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface ItemView {
        void onItemClick(View view, int position);
    }
}
