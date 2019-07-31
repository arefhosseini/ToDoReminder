package com.fearefull.todoreminder.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.databinding.ItemAlarmDisabledBinding;
import com.fearefull.todoreminder.databinding.ItemAlarmEnabledBinding;
import com.fearefull.todoreminder.databinding.ItemAlarmFirstBinding;
import com.fearefull.todoreminder.ui.base.BaseAlarmViewHolder;
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
    public int getItemViewType(int position) {
        if (position == 0 && alarmList.get(position).getIsEnable())
            return AlarmItemViewType.FIRST.value;
        else if (alarmList.get(position).getIsEnable())
            return AlarmItemViewType.ENABLED.value;
        else
            return AlarmItemViewType.DISABLED.value;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (AlarmItemViewType.getTypeByValue(viewType) == AlarmItemViewType.FIRST) {
            ItemAlarmFirstBinding binding = ItemAlarmFirstBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new AlarmFirstViewHolder(binding, listener);
        }
        if (AlarmItemViewType.getTypeByValue(viewType) == AlarmItemViewType.ENABLED) {
            ItemAlarmEnabledBinding binding = ItemAlarmEnabledBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new AlarmEnabledViewHolder(binding, listener);
        }
        else {
            ItemAlarmDisabledBinding binding = ItemAlarmDisabledBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new AlarmDisabledViewHolder(binding, listener);
        }
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
        void onAlarmLongClick(Alarm alarm);
        void onAlarmSwitchClick(Alarm alarm);
    }

    public class AlarmFirstViewHolder extends BaseAlarmViewHolder {
        private final ItemAlarmFirstBinding binding;
        private AlarmItemViewModel viewModel;

        AlarmFirstViewHolder(ItemAlarmFirstBinding binding, AlarmAdapter.AlarmAdapterListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        @Override
        public void onBind(int position) {
            final Alarm alarm = alarmList.get(position);
            viewModel = new AlarmItemViewModel(alarm, this);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }

    public class AlarmEnabledViewHolder extends BaseAlarmViewHolder {
        private final ItemAlarmEnabledBinding binding;
        private AlarmItemViewModel viewModel;

        AlarmEnabledViewHolder(ItemAlarmEnabledBinding binding, AlarmAdapter.AlarmAdapterListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        @Override
        public void onBind(int position) {
            final Alarm alarm = alarmList.get(position);
            viewModel = new AlarmItemViewModel(alarm, this);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }

    public class AlarmDisabledViewHolder extends BaseAlarmViewHolder {
        private final ItemAlarmDisabledBinding binding;
        private AlarmItemViewModel viewModel;

        AlarmDisabledViewHolder(ItemAlarmDisabledBinding binding, AlarmAdapter.AlarmAdapterListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        @Override
        public void onBind(int position) {
            final Alarm alarm = alarmList.get(position);
            viewModel = new AlarmItemViewModel(alarm, this);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }
}
