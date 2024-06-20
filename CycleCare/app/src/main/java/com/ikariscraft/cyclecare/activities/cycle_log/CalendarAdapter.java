package com.ikariscraft.cyclecare.activities.cycle_log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikariscraft.cyclecare.databinding.CalendarItemBinding;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private OnItemClickListener onItemClickListener;

    public CalendarAdapter(ArrayList<String> daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String dayText);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CalendarItemBinding binding = CalendarItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CalendarViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.bind(daysOfMonth.get(position));
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        private final CalendarItemBinding binding;
        public final TextView dayOfMonth;

        public CalendarViewHolder(@NonNull CalendarItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            dayOfMonth = binding.cellDayText;
        }

        public void bind(String text) {
            binding.cellDayText.setText(text);
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position, text);
                    }
                }
            });
            binding.executePendingBindings();
        }
    }
}