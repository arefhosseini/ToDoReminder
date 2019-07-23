package com.fearefull.todoreminder.ui.alarm_manager.repeat.weekly_repeat;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.other.item.DayWeekItem;
import com.fearefull.todoreminder.data.model.other.type.DayWeekType;
import com.fearefull.todoreminder.databinding.ItemDayWeekBinding;
import com.fearefull.todoreminder.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayWeekAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<DayWeekItem> dayWeekItemList;
    private DayWeekAdapterListener listener;

    public DayWeekAdapter() {
        this.dayWeekItemList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return dayWeekItemList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDayWeekBinding binding = ItemDayWeekBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new DayWeekViewHolder(binding);
    }

    public void addItems(List<DayWeekItem> dayWeekItemList) {
        this.dayWeekItemList.addAll(dayWeekItemList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        dayWeekItemList.clear();
    }

    public void setListener(DayWeekAdapterListener listener) {
        this.listener = listener;
    }

    public interface DayWeekAdapterListener {
        void onDayWeekItemClick(DayWeekItem dayWeekItem);
    }

    public class DayWeekViewHolder extends BaseViewHolder implements DayWeekItemViewModel.WeekItemViewModelListener {
        private final ItemDayWeekBinding binding;
        private DayWeekItemViewModel itemViewModel;

        public DayWeekViewHolder(ItemDayWeekBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            final DayWeekItem item = dayWeekItemList.get(position);
            itemViewModel = new DayWeekItemViewModel(item, this);
            binding.setViewModel(itemViewModel);
            binding.executePendingBindings();

            if (item.isSelected()) {
                binding.title.setBackground(itemView.getContext().getDrawable(R.drawable.item_repeat_selected_bg));
                binding.title.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryTextColorDarkTheme));
            }
        }

        @Override
        public void onItemClick(DayWeekItem dayWeekItem) {
            if (!dayWeekItem.isSelected()) {
                binding.title.setBackground(itemView.getContext().getDrawable(R.drawable.item_repeat_selected_bg));
                binding.title.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryTextColorDarkTheme));
                dayWeekItem.setSelected(true);
            }
            else {
                binding.title.setBackground(itemView.getContext().getDrawable(R.drawable.item_repeat_unselected_bg));
                binding.title.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryColorLightTheme));
                dayWeekItem.setSelected(false);
            }

            listener.onDayWeekItemClick(dayWeekItem);
        }
    }
}
