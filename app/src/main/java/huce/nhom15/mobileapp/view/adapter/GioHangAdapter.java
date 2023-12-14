package huce.nhom15.mobileapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import huce.nhom15.mobileapp.databinding.ItemGiohangBinding;
import huce.nhom15.mobileapp.view.activity.GioHangActivity;
import huce.nhom15.mobileapp.viewmodel.GioHangViewModel;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHodel> {
    private List<GioHangViewModel> gioHangViewModels;
    private Context ct;
    private GioHangViewModel gioHangViewModel;
    private static onClickItem onClickItem;
    public GioHangAdapter(List<GioHangViewModel> gioHangViewModels, Context ct, onClickItem onClickItem) {
        this.gioHangViewModels = gioHangViewModels;
        this.ct = ct;
        this.onClickItem = onClickItem;
    }
    @NonNull
    @Override
    public GioHangViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGiohangBinding itemGiohangBinding = ItemGiohangBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new GioHangViewHodel(itemGiohangBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHodel holder, int position) {
        if(gioHangViewModels == null) return;

        gioHangViewModel = gioHangViewModels.get(position);
        holder.itemGiohangBinding.setGioHangViewModel(gioHangViewModel);
        if(Integer.parseInt(gioHangViewModel.getSoluong()) <= 1){
            holder.itemGiohangBinding.tvGiamSoLuongMuaGioHang.setVisibility(View.INVISIBLE);
        }
        else if(Integer.parseInt(gioHangViewModel.getSoluong()) >= Integer.parseInt(gioHangViewModel.getSoluongban())){
            holder.itemGiohangBinding.tvTangSoLuongMuaGioHang.setVisibility(View.INVISIBLE);
        }
        else{
            holder.itemGiohangBinding.tvGiamSoLuongMuaGioHang.setVisibility(View.VISIBLE);
            holder.itemGiohangBinding.tvTangSoLuongMuaGioHang.setVisibility(View.VISIBLE);
        }
        holder.itemGiohangBinding.tvGiamSoLuongMuaGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemGiohangBinding.tvTangSoLuongMuaGioHang.setVisibility(View.VISIBLE);
                int soLuongMoiNhat = Integer.parseInt(holder.itemGiohangBinding.tvSoLuongMuaGioHang.getText().toString()) - 1;
                if(soLuongMoiNhat <= 1){
                    holder.itemGiohangBinding.tvGiamSoLuongMuaGioHang.setVisibility(View.INVISIBLE);
                }
                holder.itemGiohangBinding.tvSoLuongMuaGioHang.setText(soLuongMoiNhat+"");
                int soLuongHienTai = Integer.parseInt(gioHangViewModels.get(holder.getAdapterPosition()).getSoluong());
                long giaHienTai = Long.parseLong(gioHangViewModels.get(holder.getAdapterPosition()).getGiasp().replace(",", ""));
                long giaMoiNhat = (giaHienTai * soLuongMoiNhat)/soLuongHienTai;
                gioHangViewModels.get(holder.getAdapterPosition()).setSoluong(soLuongMoiNhat+"");
                gioHangViewModels.get(holder.getAdapterPosition()).setGiasp(giaMoiNhat+"");
                notifyDataSetChanged();
                GioHangActivity.ganThongTin();
            }
        });
        holder.itemGiohangBinding.tvTangSoLuongMuaGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemGiohangBinding.tvGiamSoLuongMuaGioHang.setVisibility(View.VISIBLE);
                int soLuongMoiNhat = Integer.parseInt(holder.itemGiohangBinding.tvSoLuongMuaGioHang.getText().toString()) + 1;
                int soLuongSPHienCo = Integer.parseInt(gioHangViewModels.get(holder.getAdapterPosition()).getSoluongban());
                if(soLuongMoiNhat >= soLuongSPHienCo){
                    holder.itemGiohangBinding.tvTangSoLuongMuaGioHang.setVisibility(View.INVISIBLE);
                }
                holder.itemGiohangBinding.tvSoLuongMuaGioHang.setText(soLuongMoiNhat+"");
                int soLuongHienTai = Integer.parseInt(gioHangViewModels.get(holder.getAdapterPosition()).getSoluong());
                long giaHienTai = Long.parseLong(gioHangViewModels.get(holder.getAdapterPosition()).getGiasp().replace(",", ""));
                long giaMoiNhat = (giaHienTai * soLuongMoiNhat)/soLuongHienTai;
                gioHangViewModels.get(holder.getAdapterPosition()).setSoluong(soLuongMoiNhat+"");
                gioHangViewModels.get(holder.getAdapterPosition()).setGiasp(giaMoiNhat+"");
                notifyDataSetChanged();
                GioHangActivity.ganThongTin();
            }
        });
        holder.itemGiohangBinding.layoutItemGioHang.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                huce.nhom15.mobileapp.view.adapter.GioHangAdapter.onClickItem.onLongClickItemGioHang(holder.getAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(gioHangViewModels != null){
            return gioHangViewModels.size();
        }
        return 0;
    }

    public class GioHangViewHodel extends RecyclerView.ViewHolder{
        private ItemGiohangBinding itemGiohangBinding;
        public GioHangViewHodel(@NonNull ItemGiohangBinding itemGiohangBinding) {
            super(itemGiohangBinding.getRoot());
            this.itemGiohangBinding = itemGiohangBinding;

        }
    }
    public interface onClickItem{
        void onLongClickItemGioHang(int position);
    }
}

