package huce.nhom15.mobileapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.databinding.ItemTcBinding;
import huce.nhom15.mobileapp.viewmodel.SanPhamViewModel;

public class ProductCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM=1;
    private static final int TYPE_LOADING=2;


    private List<SanPhamViewModel> SanPhams;
    private boolean isLoadingAdd;
    private Context ct;
    public ProductCategoryAdapter(List<SanPhamViewModel> sanPhams, Context ct) {
        SanPhams = sanPhams;
        this.ct = ct;
    }

    public ProductCategoryAdapter() {
        SanPhams=null;
    }

    @Override
    public int getItemViewType(int position) {
        if(SanPhams != null && position == SanPhams.size()-1 && isLoadingAdd){
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(TYPE_ITEM == viewType){
            ItemTcBinding itemTcBinding = ItemTcBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

            return new SanPhamViewHodel(itemTcBinding);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loadmore, parent, false);
            return  new LoadingViewHodel(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == TYPE_ITEM){
            SanPhamViewModel sanPhamViewModel = SanPhams.get(position);
            SanPhamViewHodel sanPhamViewHodel = (SanPhamViewHodel) holder;
            sanPhamViewHodel.itemTcBinding.setSanPhamViewModel(sanPhamViewModel);
        }
    }



    @Override
    public int getItemCount() {
        if(SanPhams != null){
            return SanPhams.size();
        }
        return 0;
    }

    public class SanPhamViewHodel extends RecyclerView.ViewHolder{
        private ItemTcBinding itemTcBinding;
        public SanPhamViewHodel(@NonNull ItemTcBinding itemTcBinding) {
            super(itemTcBinding.getRoot());
            this.itemTcBinding = itemTcBinding;
        }
    }
    public class LoadingViewHodel extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;

        public LoadingViewHodel(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }
    public void addFooterLoading(){
        isLoadingAdd = true;
        SanPhams.add(new SanPhamViewModel());
        notifyItemInserted(SanPhams.size()-1);
    }
    public void removeFooterLoading(){
        isLoadingAdd=false;
        int position = SanPhams.size()-1;
        SanPhamViewModel sanPhamViewModel = SanPhams.get(position);
        if(sanPhamViewModel != null){
            SanPhams.remove(position);
            notifyItemRemoved(position);
        }
    }
}
