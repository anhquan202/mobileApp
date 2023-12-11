package huce.nhom15.mobileapp.view.adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import huce.nhom15.mobileapp.databinding.ItemCategoryBinding;
import huce.nhom15.mobileapp.databinding.ItemTcBinding;
import huce.nhom15.mobileapp.viewmodel.CategoryViewModel;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHodel> {
    private List<CategoryViewModel> categoryViewModels;
    private Context ct;
    private CategoryViewModel categoryViewModel;

    public CategoryAdapter() {
    }

    public CategoryAdapter(List<CategoryViewModel> categoryViewModels, Context ct) {
        this.categoryViewModels = categoryViewModels;
        this.ct = ct;
    }

    @NonNull
    @Override
    public CategoryViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding itemCategoryBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CategoryViewHodel(itemCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHodel holder, int position) {
        if(categoryViewModels == null) return;
        categoryViewModel = categoryViewModels.get(position);
        holder.itemCategoryBinding.setCategoryViewModel(categoryViewModel);
    }

    @Override
    public int getItemCount() {
        if(categoryViewModels != null){
            return categoryViewModels.size();
        }
        return 0;
    }

    public class CategoryViewHodel extends RecyclerView.ViewHolder{
        private ItemCategoryBinding itemCategoryBinding;
        public CategoryViewHodel(@NonNull ItemCategoryBinding itemCategoryBinding) {
            super(itemCategoryBinding.getRoot());
            this.itemCategoryBinding = itemCategoryBinding;
        }
    }
}
