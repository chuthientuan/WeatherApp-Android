package com.example.weatherapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.entities.FutureDomain;

import java.util.ArrayList;

public class FutureAdapter extends RecyclerView.Adapter<FutureAdapter.FutureViewHolder> {
    ArrayList<FutureDomain> items;
    Context context;

    public FutureAdapter(ArrayList<FutureDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public FutureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_future, parent, false);
        return new FutureViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FutureViewHolder holder, int position) {
        FutureDomain item = items.get(position);
        context = holder.itemView.getContext();
        holder.textDay.setText(item.getDay());
        holder.textStatus.setText(item.getStatus());
        holder.textHigh.setText(item.getHighTemp()+ "°C");
        holder.textLow.setText(item.getLowTemp()+ "°C");
        holder.imgPicNext.setImageResource(context.getResources().getIdentifier(item.getPicPath(), "drawable", context.getPackageName()));
        int iconResId = getCustomIcon(item.getPicPath());
        holder.imgPicNext.setImageResource(iconResId);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class FutureViewHolder extends RecyclerView.ViewHolder {
        private TextView textDay, textStatus, textHigh, textLow;
        private ImageView imgPicNext;
        public FutureViewHolder(@NonNull View itemView) {
            super(itemView);
            textDay = itemView.findViewById(R.id.textDay);
            textStatus = itemView.findViewById(R.id.textStatus);
            textHigh = itemView.findViewById(R.id.textHigh);
            textLow = itemView.findViewById(R.id.textLow);
            imgPicNext = itemView.findViewById(R.id.imgPicNext);
        }
    }

    private int getCustomIcon(String icon) {
        switch (icon) {
            case "01d":
                return R.drawable.cloudy_sunny;
            case "01n":
                return R.drawable.sunny;
            case "02d":
                return R.drawable.cloudy;
            case "02n":
                return R.drawable.cloudy;
            case "03d":
            case "03n":
                return R.drawable.cloudy_sunny;
            case "04d":
            case "04n":
                return R.drawable.cloudy;
            case "09d":
            case "09n":
                return R.drawable.rainy;
            case "10d":
                return R.drawable.rainy;
            case "10n":
                return R.drawable.rainy;
            case "11d":
            case "11n":
                return R.drawable.storm;
            case "13d":
            case "13n":
                return R.drawable.snowy;
            case "50d":
            case "50n":
                return R.drawable.windy;
            default:
                return R.drawable.wind; // Biểu tượng mặc định nếu không có giá trị ánh xạ
        }
    }

}
