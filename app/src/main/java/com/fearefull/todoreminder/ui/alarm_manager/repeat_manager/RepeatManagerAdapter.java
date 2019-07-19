package com.fearefull.todoreminder.ui.alarm_manager.repeat_manager;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.data.model.other.item.RepeatManagerItem;
import com.fearefull.todoreminder.databinding.ItemRepeatManagerBinding;
import com.fearefull.todoreminder.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RepeatManagerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final List<RepeatManagerItem> repeatManagerItemList;
    private RepeatManagerAdapterListener listener;

    public RepeatManagerAdapter() {
        this.repeatManagerItemList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return repeatManagerItemList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRepeatManagerBinding binding = ItemRepeatManagerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new RepeatManagerViewHolder(binding);
    }

    public void addItems(List<RepeatManagerItem> list) {
        this.repeatManagerItemList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearItems() {
        repeatManagerItemList.clear();
    }

    public void setListener(RepeatManagerAdapterListener listener) {
        this.listener = listener;
    }

    public interface RepeatManagerAdapterListener {
        void onRemoveClick(RepeatManagerItem repeatManagerItem);
    }

    public class RepeatManagerViewHolder extends BaseViewHolder implements RepeatManagerItemViewModel.RepeatManagerItemViewModelListener {
        private final ItemRepeatManagerBinding binding;
        private RepeatManagerItemViewModel itemViewModel;

        public RepeatManagerViewHolder(ItemRepeatManagerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            final RepeatManagerItem item = repeatManagerItemList.get(position);
            itemViewModel = new RepeatManagerItemViewModel(item, this);
            binding.setViewModel(itemViewModel);
            binding.executePendingBindings();

        }

        @Override
        public void onRemoveClick(RepeatManagerItem item) {
            listener.onRemoveClick(item);
        }
    }
}
