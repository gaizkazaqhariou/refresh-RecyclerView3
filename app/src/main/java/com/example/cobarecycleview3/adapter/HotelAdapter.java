package com.example.cobarecycleview3.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cobarecycleview3.R;
import com.example.cobarecycleview3.model.Hotel;

import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    ArrayList<Hotel> hotelList;
    IHotelAdapter mIHotelAdapter;

    public interface IHotelAdapter {
        void doClick(int pos);

        void doEdit(int pos);

        void doDelete(int pos);

        void doFav(int pos);

        void doShare(int pos);
    }

    public HotelAdapter(Context context, ArrayList<Hotel> hotelList) {
        this.hotelList = hotelList;
        mIHotelAdapter = (IHotelAdapter) context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.tvJudul.setText(hotel.judul);
        holder.tvDeskripsi.setText(hotel.deskripsi);
        holder.ivFoto.setImageURI(Uri.parse(hotel.foto));
    }

//    public HotelAdapter(ArrayList<Hotel> hotelList) {
//        this.hotelList = hotelList;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

//    public interface IHotelAdapter {
//        void doClick(int pos);
//    }

    @Override
    public int getItemCount() {
        if (hotelList != null)
            return hotelList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView ivFoto;
//        TextView tvJudul;
//        TextView tvDeskripsi;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            ivFoto = itemView.findViewById(R.id.imageView);
//            tvJudul = itemView.findViewById(R.id.textViewJudul);
//            tvDeskripsi = itemView.findViewById(R.id.textViewDeskripsi);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mIHotelAdapter.doClick(getAdapterPosition());
//                }
//            });
//        }

        ImageView ivFoto;
        TextView tvJudul;
        TextView tvDeskripsi;
        Button bEdit;
        Button bDelete;
        ImageButton ibFav;
        ImageButton ibShare;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageView);
            tvJudul = itemView.findViewById(R.id.textViewJudul);
            tvDeskripsi = itemView.findViewById(R.id.textViewDeskripsi);
            bEdit = itemView.findViewById(R.id.buttonEdit);
            bDelete = itemView.findViewById(R.id.buttonDelete);
            ibFav = itemView.findViewById(R.id.buttonFavorite);
            ibShare = itemView.findViewById(R.id.buttonShare);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIHotelAdapter.doClick(getAdapterPosition());
                }
            });
            bEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIHotelAdapter.doEdit(getAdapterPosition());
                }
            });
            bDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIHotelAdapter.doDelete(getAdapterPosition());
                }
            });
            ibFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIHotelAdapter.doFav(getAdapterPosition());
                }
            });
            ibShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIHotelAdapter.doShare(getAdapterPosition());
                }
            });
        }
    }
}
