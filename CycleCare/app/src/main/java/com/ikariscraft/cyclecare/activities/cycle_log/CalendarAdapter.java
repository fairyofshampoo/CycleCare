package com.ikariscraft.cyclecare.activities.cycle_log;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikariscraft.cyclecare.databinding.CalendarItemBinding;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener)
    {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        CalendarItemBinding binding;
        public CalendarViewHolder(@NonNull CalendarItemBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final CalendarItemBinding binding;
        public final TextView dayOfMonth;
        public final OnItemListener onItemListener;

        public CalendarViewHolder(@NonNull CalendarItemBinding binding, OnItemListener onItemListener)
        {
            super(binding.getRoot());
            this.binding = binding;
            dayOfMonth = binding.dayOfMonth;
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            onItemListener.onItemClick(getAdapterPosition(), dayOfMonth.getText().toString());
        }
    }
}
