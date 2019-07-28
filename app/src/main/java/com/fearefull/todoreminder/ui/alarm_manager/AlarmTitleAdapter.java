package com.fearefull.todoreminder.ui.alarm_manager;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.other.item.AlarmTitleItem;
import com.fearefull.todoreminder.databinding.ItemAlarmTitleBinding;
import com.fearefull.todoreminder.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AlarmTitleAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<AlarmTitleItem> alarmTitleItemList;
    private AlarmTitleAdapterListener listener;
    private AlarmTitleItem selectedItem;

    public AlarmTitleAdapter() {
        this.alarmTitleItemList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return alarmTitleItemList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAlarmTitleBinding binding = ItemAlarmTitleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new AlarmItemViewHolder(binding);
    }

    public void addItems(List<AlarmTitleItem> alarmTitleItemList) {
        this.alarmTitleItemList.addAll(alarmTitleItemList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        alarmTitleItemList.clear();
    }

    public void setListener(AlarmTitleAdapterListener listener) {
        this.listener = listener;
    }

    private void changeSelected(AlarmTitleItem oldItem, AlarmTitleItem newItem) {
        newItem.getBinding().titleLayout.setBackground(ContextCompat.getDrawable(newItem.getBinding().titleLayout.getContext(), R.drawable.item_alarm_selected));
        oldItem.getBinding().titleLayout.setBackground(ContextCompat.getDrawable(oldItem.getBinding().titleLayout.getContext(), R.drawable.item_alarm_unselected));

        selectedItem = newItem;
    }

    public interface AlarmTitleAdapterListener {
        void onAlarmTitleItemClick(AlarmTitleItem item);
    }

    public class AlarmItemViewHolder extends BaseViewHolder implements AlarmTitleItemViewModel.AlarmTitleItemViewModelListener {
        private final ItemAlarmTitleBinding binding;
        private AlarmTitleItemViewModel viewModel;

        public AlarmItemViewHolder(ItemAlarmTitleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            final AlarmTitleItem item = alarmTitleItemList.get(position);
            viewModel = new AlarmTitleItemViewModel(item, this);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
            item.setBinding(binding);

            if (item.isSelected()) {
                selectedItem = item;
                binding.titleLayout.setBackground(ContextCompat.getDrawable(binding.titleLayout.getContext(), R.drawable.item_alarm_selected));
            }
            else {
                binding.titleLayout.setBackground(ContextCompat.getDrawable(binding.titleLayout.getContext(), R.drawable.item_alarm_unselected));
            }
        }

        @Override
        public void onItemClick(AlarmTitleItem item) {
            if (item != selectedItem && !item.isSelected()) {
                item.setSelected(true);
                selectedItem.setSelected(false);
                changeSelected(selectedItem, item);

            }

            listener.onAlarmTitleItemClick(item);
        }
    }
}
