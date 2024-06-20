package com.ikariscraft.cyclecare.activities.cycle_log;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikariscraft.cyclecare.databinding.CalendarItemBinding;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private LocalDate currentDate;
    private OnItemClickListener onItemClickListener;

    public CalendarAdapter(ArrayList<String> daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
        this.currentDate = LocalDate.now();
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
            if (isToday(text)) {
                dayOfMonth.setPaintFlags(dayOfMonth.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                binding.cellDayText.setTypeface(null, Typeface.BOLD);
            } else {
                dayOfMonth.setPaintFlags(dayOfMonth.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                binding.cellDayText.setTypeface(null, Typeface.NORMAL);
            }
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

        private boolean isToday(String dayText) {
            try {
                int day = Integer.parseInt(dayText);
                LocalDate itemDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), day);
                return itemDate.equals(currentDate);
            } catch (NumberFormatException | DateTimeException e) {
                return false;
            }
        }
    }
}