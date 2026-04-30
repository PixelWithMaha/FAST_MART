package com.example.assignment3.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.assignment3.R;
import com.example.assignment3.data.model.Order;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) { this.orderList = orderList; }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvId.setText("#ORD-" + (position + 9000)); // Simulating ID
        holder.tvTotal.setText(String.format("$%.2f", order.getTotalPrice()));

        // Requirement 4: Setting the summary string to the TextView
        holder.tvSummary.setText(order.getProductSummary());
    }

    @Override
    public int getItemCount() { return orderList.size(); }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvTotal, tvSummary;
        OrderViewHolder(View v) {
            super(v);
            tvId = v.findViewById(R.id.tv_order_id);
            tvTotal = v.findViewById(R.id.tv_order_total);
            tvSummary = v.findViewById(R.id.tv_items_summary);
        }
    }
}