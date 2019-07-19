package com.fearefull.todoreminder.ui.alarm_manager;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.other.item.RepeatItem;
import com.fearefull.todoreminder.databinding.ItemRepeatBinding;
import com.fearefull.todoreminder.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepeatAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<RepeatItem> repeatItemList;
    private final Map<RepeatItem, ItemRepeatBinding> repeatBindingMap;
    private RepeatAdapterListener listener;


    public RepeatAdapter() {
        this.repeatItemList = new ArrayList<>();
        repeatBindingMap = new HashMap<>();
    }

    @Override
    public int getItemCount() {
        return repeatItemList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRepeatBinding binding = ItemRepeatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new RepeatViewHolder(binding);
    }

    public void addItems(List<RepeatItem> repeatItemList) {
        this.repeatItemList.addAll(repeatItemList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        repeatItemList.clear();
    }

    private void unselectOtherRepeatTypeItem() {
        RepeatItem selectedRepeatItem = null;
        for (RepeatItem repeatItem : repeatItemList) {
            if (repeatItem.isSelected())
                selectedRepeatItem = repeatItem;
        }
        if (selectedRepeatItem != null && repeatBindingMap.get(selectedRepeatItem) != null) {
            ItemRepeatBinding binding = repeatBindingMap.get(selectedRepeatItem);
            binding.title.setBackground(binding.getRoot().getContext().getDrawable(R.drawable.item_repeat_unselected_bg));
            binding.title.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.primaryColorLightTheme));
            selectedRepeatItem.setSelected(false);
        }
    }

    public void setListener(RepeatAdapterListener listener) {
        this.listener = listener;
    }

    public interface RepeatAdapterListener {
        void onRepeatItemClick(RepeatItem repeatItem);
    }

    public class RepeatViewHolder extends BaseViewHolder implements RepeatItemViewModel.RepeatItemViewModelListener {
        private final ItemRepeatBinding binding;
        private RepeatItemViewModel repeatItemViewModel;

        public RepeatViewHolder(ItemRepeatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            final RepeatItem repeatItem = repeatItemList.get(position);
            repeatItemViewModel = new RepeatItemViewModel(repeatItem, this);
            binding.setViewModel(repeatItemViewModel);
            binding.executePendingBindings();

            if (repeatItem.isSelected()) {
                binding.title.setBackground(itemView.getContext().getDrawable(R.drawable.item_repeat_selected_bg));
                binding.title.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryTextColorDarkTheme));
            }

            repeatBindingMap.put(repeatItem, binding);
        }

        @Override
        public void onItemClick(RepeatItem repeatType) {
            if (!repeatType.isSelected()) {
                unselectOtherRepeatTypeItem();

                binding.title.setBackground(itemView.getContext().getDrawable(R.drawable.item_repeat_selected_bg));
                binding.title.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryTextColorDarkTheme));
                repeatType.setSelected(true);

                listener.onRepeatItemClick(repeatType);
            }
        }
    }
}
