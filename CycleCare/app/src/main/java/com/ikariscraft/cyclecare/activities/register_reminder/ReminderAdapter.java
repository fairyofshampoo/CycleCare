package com.ikariscraft.cyclecare.activities.register_reminder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ikariscraft.cyclecare.activities.view_content_medic_pov.InformativeContentAdapter;
import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.databinding.ContentListItemBinding;
import com.ikariscraft.cyclecare.databinding.ReminderListItemBinding;
import com.ikariscraft.cyclecare.model.Reminder;
import com.squareup.picasso.Picasso;

public class ReminderAdapter extends ListAdapter<Reminder, ReminderAdapter.ReminderViewHolder> {
    public static final DiffUtil.ItemCallback<Reminder> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Reminder>() {
                @Override
                public boolean areItemsTheSame(@NonNull Reminder oldItem, @NonNull Reminder newItem) {
                    String oldItemId = Integer.toString(oldItem.getReminderId());
                    String newItemId = Integer.toString(newItem.getReminderId());
                    return oldItemId.equals(newItemId);
                }

                @Override
                public boolean areContentsTheSame(@NonNull Reminder oldItem, @NonNull Reminder newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public ReminderAdapter() {
        super(DIFF_CALLBACK);
    }

    public ReminderAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(Reminder reminder);
    }

    public void setOnItemClickListener(ReminderAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    protected ReminderAdapter(@NonNull DiffUtil.ItemCallback<Reminder> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ReminderAdapter.ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReminderListItemBinding binding = ReminderListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ReminderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ReminderViewHolder holder, int position) {
        Reminder reminder = getItem(position);
        holder.bind(reminder);
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder {
        ReminderListItemBinding binding;
        public ReminderViewHolder(@NonNull ReminderListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Reminder reminder){
            binding.txtTitle.setText(reminder.getTitle());
            binding.txtDate.setText(reminder.getCreationDate());
            binding.getRoot().setOnClickListener( v ->  {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(reminder);
                }
            });
            binding.executePendingBindings();
        }

    }
}