package com.fearefull.todoreminder.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.databinding.ItemAlarmBinding;
import com.fearefull.todoreminder.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final List<Alarm> alarmList;
    private AlarmAdapterListener listener;

    public AlarmAdapter() {
        this.alarmList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAlarmBinding itemAlarmBinding = ItemAlarmBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new AlarmViewHolder(itemAlarmBinding);
    }

    public void addItems(List<Alarm> alarmList) {
        this.alarmList.addAll(alarmList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        alarmList.clear();
    }

    public void setListener(AlarmAdapterListener listener) {
        this.listener = listener;
    }

    public interface AlarmAdapterListener {
        void onAlarmClick(Alarm alarm);
    }

    public class AlarmViewHolder extends BaseViewHolder implements AlarmItemViewModel.AlarmItemViewModelListener {
        private final ItemAlarmBinding binding;
        private AlarmItemViewModel alarmItemViewModel;

        public AlarmViewHolder(ItemAlarmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            final Alarm alarm = alarmList.get(position);
            alarmItemViewModel = new AlarmItemViewModel(alarm, this);
            binding.setViewModel(alarmItemViewModel);
            binding.executePendingBindings();

        }

        @Override
        public void onItemClick(Alarm alarm) {
            listener.onAlarmClick(alarm);
        }
    }
}
