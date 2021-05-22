package com.app.shopifyuser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shopifyuser.R;
import com.app.shopifyuser.Utils.TimeFormatter;
import com.app.shopifyuser.model.DeliveryOrder;

import java.util.ArrayList;

public class ActiveOrdersAdapter extends RecyclerView.Adapter<ActiveOrdersAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<DeliveryOrder> deliveryOrders;
    private final CancelOrderListener cancelListener;

    public interface CancelOrderListener {
        void removeCartItem(int itemId, int position);
    }

    public ActiveOrdersAdapter(Context context, ArrayList<DeliveryOrder> deliveryOrders, CancelOrderListener cancelListener) {
        this.context = context;
        this.deliveryOrders = deliveryOrders;
        this.cancelListener = cancelListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_active_orders, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final DeliveryOrder deliveryOrder = deliveryOrders.get(position);
        holder.bind(deliveryOrder);

    }

    @Override
    public int getItemCount() {
        return deliveryOrders.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView orderedAtTv, scheduledAtTv, totalCostTv;
        private final Button cancelOrderBtn;
        private final ImageView viewLocationIv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderedAtTv = itemView.findViewById(R.id.orderedAtTv);
            scheduledAtTv = itemView.findViewById(R.id.scheduledAtTv);
            totalCostTv = itemView.findViewById(R.id.totalCostTv);
            cancelOrderBtn = itemView.findViewById(R.id.cancelOrderBtn);
            viewLocationIv = itemView.findViewById(R.id.viewLocationIv);
        }

        private void bind(DeliveryOrder deliveryOrder) {

            orderedAtTv.setText(TimeFormatter.formatTime(deliveryOrder.getOrderedAt()));
            scheduledAtTv.setText(TimeFormatter.formatTime(deliveryOrder.getScheduledTime()));
            totalCostTv.setText("Total cost: " + deliveryOrder.getTotalPrice() + "$");


            viewLocationIv.setOnClickListener(this);
            cancelOrderBtn.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == viewLocationIv.getId()) {

                cancelListener.removeCartItem(0, 0);

            } else if (v.getId() == cancelOrderBtn.getId()) {


            } else {

//                context.startActivity(new Intent(context, OrderActivity.class)
//                        .putExtra("position", position));


            }

        }
    }

}
