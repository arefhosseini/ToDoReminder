package com.fearefull.todoreminder.ui.alarm_manager;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.other.RepeatTypeItem;
import com.fearefull.todoreminder.databinding.ItemRepeatBinding;
import com.fearefull.todoreminder.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepeatAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<RepeatTypeItem> repeatTypeItemList;
    private final Map<RepeatTypeItem, ItemRepeatBinding> repeatBindingMap;
    private RepeatAdapterListener listener;


    public RepeatAdapter() {
        this.repeatTypeItemList = new ArrayList<>();
        repeatBindingMap = new HashMap<>();
    }

    @Override
    public int getItemCount() {
        return repeatTypeItemList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRepeatBinding itemRepeatBinding = ItemRepeatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new RepeatViewHolder(itemRepeatBinding);
    }

    public void addItems(List<RepeatTypeItem> repeatTypeItemList) {
        this.repeatTypeItemList.addAll(repeatTypeItemList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        repeatTypeItemList.clear();
    }

    private void unselectOtherRepeatTypeItem() {
        RepeatTypeItem selectedRepeatTypeItem = null;
        for (RepeatTypeItem repeatTypeItem: repeatTypeItemList) {
            if (repeatTypeItem.isSelected())
                selectedRepeatTypeItem = repeatTypeItem;
        }
        if (selectedRepeatTypeItem != null && repeatBindingMap.get(selectedRepeatTypeItem) != null) {
            ItemRepeatBinding binding = repeatBindingMap.get(selectedRepeatTypeItem);
            binding.title.setBackground(binding.getRoot().getContext().getDrawable(R.drawable.item_repeat_unselected_bg));
            binding.title.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.primaryColorLightTheme));
            selectedRepeatTypeItem.setSelected(false);
        }
    }

    public void setListener(RepeatAdapterListener listener) {
        this.listener = listener;
    }

    public interface RepeatAdapterListener {
        void onRepeatItemClick(RepeatTypeItem repeatTypeItem);
    }

    public class RepeatViewHolder extends BaseViewHolder implements RepeatItemViewModel.RepeatItemViewModelListener {
        private final ItemRepeatBinding binding;
        private RepeatItemViewModel repeatItemViewModel;

        public  RepeatViewHolder(ItemRepeatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            final RepeatTypeItem repeatTypeItem = repeatTypeItemList.get(position);
            repeatItemViewModel = new RepeatItemViewModel(repeatTypeItem, this);
            binding.setViewModel(repeatItemViewModel);
            binding.executePendingBindings();

            if (repeatTypeItem.isSelected()) {
                binding.title.setBackground(itemView.getContext().getDrawable(R.drawable.item_repeat_selected_bg));
                binding.title.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryTextColorDarkTheme));
            }

            repeatBindingMap.put(repeatTypeItem, binding);
        }

        @Override
        public void onItemClick(RepeatTypeItem repeatType) {
            if (!repeatType.isSelected()) {
                unselectOtherRepeatTypeItem();

                binding.title.setBackground(itemView.getContext().getDrawable(R.drawable.item_repeat_selected_bg));
                binding.title.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryTextColorDarkTheme));
                repeatType.setSelected(true);

                listener.onRepeatItemClick(repeatType);
            }
        }

        public ItemRepeatBinding getBinding() {
            return binding;
        }
    }
}
