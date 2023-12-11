package huce.nhom15.mobileapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import huce.nhom15.mobileapp.databinding.ItemTcBinding;
import huce.nhom15.mobileapp.viewmodel.SanPhamViewModel;

public class SanPhamTCAdapter extends RecyclerView.Adapter<SanPhamTCAdapter.SanPhamTCViewHodel> {
    private List<SanPhamViewModel> SanPhams;
    private Context ct;
    private SanPhamViewModel sanPhamViewModel;
    public SanPhamTCAdapter(List<SanPhamViewModel> sanPhams, Context ct) {
        SanPhams = sanPhams;
        this.ct = ct;
    }
    @NonNull
    @Override
    public SanPhamTCViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTcBinding itemTcBinding = ItemTcBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new SanPhamTCViewHodel(itemTcBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamTCViewHodel holder, int position) {
        if(SanPhams == null) return;
        sanPhamViewModel = SanPhams.get(position);
        holder.itemTcBinding.setSanPhamViewModel(sanPhamViewModel);
    }

    @Override
    public int getItemCount() {
        if(SanPhams != null){
            return SanPhams.size();
        }
        return 0;
    }

    public class SanPhamTCViewHodel extends RecyclerView.ViewHolder{
        private ItemTcBinding itemTcBinding;
        public SanPhamTCViewHodel(@NonNull ItemTcBinding itemTcBinding) {
            super(itemTcBinding.getRoot());
            this.itemTcBinding = itemTcBinding;
        }
    }
}
