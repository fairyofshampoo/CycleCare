package com.ikariscraft.cyclecare.activities.cycle_log;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikariscraft.cyclecare.api.responses.PredictionJSONResponse;
import com.ikariscraft.cyclecare.databinding.CalendarItemBinding;
import com.ikariscraft.cyclecare.model.CycleLog;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private LocalDate currentDate;
    private List<CycleLog> cycleLogs;
    private OnItemClickListener onItemClickListener;
    private LocalDate selectedDate;
    private final DateTimeFormatter dateFormatter;
    private final PredictionJSONResponse predictionJSONResponse;

    public CalendarAdapter(ArrayList<String> daysOfMonth, List<CycleLog> cycleLogs, LocalDate selectedDate, PredictionJSONResponse predictionJSONResponse) {
        this.daysOfMonth = daysOfMonth;
        this.cycleLogs = cycleLogs;
        this.selectedDate = selectedDate;
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        this.currentDate = LocalDate.now();
        this.predictionJSONResponse = predictionJSONResponse;
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
            }
            if (isCycleCreationDate(text)) {
                binding.getRoot().setBackgroundColor(Color.parseColor("#FF0065"));
                binding.cellDayText.setTextColor(Color.WHITE);
            }

            if(isPrediction(text)) {
                binding.getRoot().setBackgroundColor(Color.parseColor("#FFE9BA"));
                binding.cellDayText.setTextColor(Color.DKGRAY);
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

        private boolean isPrediction(String text) {
            try {
                int day = Integer.parseInt(text);
                if (predictionJSONResponse != null) {
                    LocalDate nextPeriodStartDate = LocalDate.parse(predictionJSONResponse.getNextPeriodStartDate(), dateFormatter);
                    LocalDate nextPeriodEndDate = LocalDate.parse(predictionJSONResponse.getNextPeriodEndDate(), dateFormatter);
                    LocalDate date = LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), day);

                    return !date.isBefore(nextPeriodStartDate) && !date.isAfter(nextPeriodEndDate);
                }
            } catch (NumberFormatException | DateTimeException e) {
                return false;
            }
            return false;
        }


        private boolean isCycleCreationDate(String dayText) {
            try {
                int day = Integer.parseInt(dayText);
                LocalDate itemDate = LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), day);
                for (CycleLog cycleLog : cycleLogs) {
                    LocalDate creationDate = LocalDate.parse(cycleLog.getCreationDate(), dateFormatter);
                    if (creationDate.equals(itemDate)) {
                        return true;
                    }
                }
                return false;
            } catch (NumberFormatException | DateTimeException e) {
                return false;
            }
        }

        private boolean isToday(String dayText) {
            try {
                int day = Integer.parseInt(dayText);
                LocalDate itemDate = LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), day);
                return itemDate.equals(currentDate);
            } catch (NumberFormatException | DateTimeException e) {
                return false;
            }
        }
    }
}