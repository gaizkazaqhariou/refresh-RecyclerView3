package com.example.cobarecycleview3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cobarecycleview3.R;
import com.example.cobarecycleview3.model.Hotel;

import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    ArrayList<Hotel> hotelList;

    public HotelAdapter(ArrayList<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.tvJudul.setText(hotel.judul);
        holder.tvDeskripsi.setText(hotel.deskripsi);
        holder.ivFoto.setImageDrawable(hotel.foto);
    }

    @Override
    public int getItemCount() {
        if (hotelList != null)
            return hotelList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul;
        TextView tvDeskripsi;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageView);
            tvJudul = itemView.findViewById(R.id.textViewJudul);
            tvDeskripsi = itemView.findViewById(R.id.textViewDeskripsi);
        }
    }
}
