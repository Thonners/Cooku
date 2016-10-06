package com.thonners.kooku;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thonners.kooku.MyOrdersFragment.OnMyOrdersFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display an {@link Order} and makes a call to the
 * specified {@link OnMyOrdersFragmentInteractionListener}.
 *
 * @author M Thomas
 * @since 04/10/16.
 */
public class MyOrderRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder> {

    private final List<Order> orders;
    private final OnMyOrdersFragmentInteractionListener mListener;

    public MyOrderRecyclerViewAdapter(List<Order> orders, MyOrdersFragment.OnMyOrdersFragmentInteractionListener listener) {
        this.orders = orders;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.order = orders.get(position);
        holder.orderID.setText(Integer.toString(position));
        holder.orderChefName.setText(orders.get(position).getChef().getChefName());
        holder.orderDate.setText(orders.get(position).getDateString());
        holder.orderPrice.setText("£" + orders.get(position).getPrice());
        holder.orderAddress.setText(orders.get(position).getDateString());
        holder.orderPrompt.setText(orders.get(position).getDateString());


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onMyOrdersFragmentInteraction(holder.order);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView orderID;
        public final TextView orderChefName;
        public final TextView orderDate;
        public final TextView orderPrice;
        public final TextView orderAddress;
        public final TextView orderPrompt;
        public Order order;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            orderID = (TextView) view.findViewById(R.id.order_id);
            orderChefName = (TextView) view.findViewById(R.id.order_chef_name);
            orderDate = (TextView) view.findViewById(R.id.order_date);
            orderPrice = (TextView) view.findViewById(R.id.order_price);
            orderAddress = (TextView) view.findViewById(R.id.order_address);
            orderPrompt = (TextView) view.findViewById(R.id.order_footer_prompt);
        }

        @Override
        public String toString() {
            return super.toString() + " 'OrderID: " + orderID.getText() + "'";
        }
    }
}
