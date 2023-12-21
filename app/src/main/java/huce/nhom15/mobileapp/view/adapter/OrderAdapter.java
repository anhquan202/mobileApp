package huce.nhom15.mobileapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import huce.nhom15.mobileapp.databinding.ItemOrderBinding;
import huce.nhom15.mobileapp.viewmodel.OrderViewModel;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<OrderViewModel> orderViewModels;
    private Context context;
    private OrderViewModel orderViewModel;

    public OrderAdapter(List<OrderViewModel> orderViewModels, Context context) {
        this.orderViewModels = orderViewModels;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding itemOrderBinding = ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderViewHolder(itemOrderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        if(orderViewModels == null) return;
        orderViewModel = orderViewModels.get(position);
        holder.itemOrderBinding.setOrderViewModel(orderViewModel);
    }

    @Override
    public int getItemCount() {
        if(orderViewModels != null){
            return orderViewModels.size();
        }
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private ItemOrderBinding itemOrderBinding;

        public OrderViewHolder(@NonNull ItemOrderBinding itemOrderBinding) {
            super(itemOrderBinding.getRoot());
            this.itemOrderBinding = itemOrderBinding;
        }
    }
}
