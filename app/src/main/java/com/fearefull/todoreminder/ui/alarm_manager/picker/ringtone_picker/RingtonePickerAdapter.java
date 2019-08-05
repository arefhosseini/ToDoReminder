package com.fearefull.todoreminder.ui.alarm_manager.picker.ringtone_picker;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.data.model.db.Ringtone;
import com.fearefull.todoreminder.data.model.other.item.RingtonePickerItem;
import com.fearefull.todoreminder.databinding.ItemRingtonePickerBinding;
import com.fearefull.todoreminder.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RingtonePickerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final List<RingtonePickerItem> ringtoneList;
    private RingtonePickerAdapterListener listener;
    private RingtonePickerItem defaultRingtone;

    public RingtonePickerAdapter() {
        this.ringtoneList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return ringtoneList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRingtonePickerBinding binding = ItemRingtonePickerBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);

        return new RingtonePickerViewHolder(binding);
    }

    public void addItems(List<RingtonePickerItem> list) {
        this.ringtoneList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearItems() {
        ringtoneList.clear();
    }

    public void setListener(RingtonePickerAdapterListener listener) {
        this.listener = listener;
    }

    public interface RingtonePickerAdapterListener {
        void onItemClick(Ringtone ringtone);
    }

    public class RingtonePickerViewHolder extends BaseViewHolder
            implements RingtonePickerItemViewModel.RingtonePickerItemViewModelListener {
        private final ItemRingtonePickerBinding binding;
        private RingtonePickerItemViewModel viewModel;

        public RingtonePickerViewHolder(ItemRingtonePickerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            RingtonePickerItem item = ringtoneList.get(position);
            viewModel = new RingtonePickerItemViewModel(item, this);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
            item.setBinding(binding);
            if (item.isDefault()) {
                defaultRingtone = item;
            }
        }

        @Override
        public void onItemClick(RingtonePickerItem item) {
            if (!defaultRingtone.getRingtone().isSame(item.getRingtone())) {
                defaultRingtone.setDefault(false);
                defaultRingtone.getBinding().getViewModel().changeIsSelected();
                defaultRingtone = item;
            }
            listener.onItemClick(item.getRingtone());
        }
    }
}
