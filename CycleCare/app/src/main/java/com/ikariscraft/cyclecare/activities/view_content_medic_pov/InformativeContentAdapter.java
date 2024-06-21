package com.ikariscraft.cyclecare.activities.view_content_medic_pov;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.responses.InformativeContentJSONResponse;
import com.ikariscraft.cyclecare.databinding.ActivityMyContentBinding;
import com.ikariscraft.cyclecare.databinding.ContentListItemBinding;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;

public class InformativeContentAdapter extends ListAdapter<InformativeContentJSONResponse, InformativeContentAdapter.ContentViewHolder> {

    public static final DiffUtil.ItemCallback<InformativeContentJSONResponse> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<InformativeContentJSONResponse>() {
                @Override
                public boolean areItemsTheSame(@NonNull InformativeContentJSONResponse oldItem, @NonNull InformativeContentJSONResponse newItem) {
                    String oldItemId = Integer.toString(oldItem.getContentId());
                    String newItemId = Integer.toString(newItem.getContentId());
                    return oldItemId.equals(newItemId);
                }

                @Override
                public boolean areContentsTheSame(@NonNull InformativeContentJSONResponse oldItem, @NonNull InformativeContentJSONResponse newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public InformativeContentAdapter() {
        super(DIFF_CALLBACK);
    }

    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(InformativeContentJSONResponse informativeContent);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    protected InformativeContentAdapter(@NonNull DiffUtil.ItemCallback<InformativeContentJSONResponse> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public InformativeContentAdapter.ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContentListItemBinding binding = ContentListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ContentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InformativeContentAdapter.ContentViewHolder holder, int position) {
        InformativeContentJSONResponse informativeContet = getItem(position);
        holder.bind(informativeContet);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        ContentListItemBinding binding;
        public ContentViewHolder(@NonNull ContentListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(InformativeContentJSONResponse informativeContentJSONResponse){
            binding.contentTitleLabel.setText(informativeContentJSONResponse.getTitle());
            binding.dateLabel.setText(informativeContentJSONResponse.getCreationDate());
            ApiClient apiClient = ApiClient.getInstance();
            String baseIP = apiClient.getBaseIp();
            Picasso.get().load(baseIP + "/images/"+ informativeContentJSONResponse.getImage()).into(binding.imageContent);
            binding.getRoot().setOnClickListener( v ->  {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(informativeContentJSONResponse);
                }
            });
            binding.executePendingBindings();
        }

    }
}
