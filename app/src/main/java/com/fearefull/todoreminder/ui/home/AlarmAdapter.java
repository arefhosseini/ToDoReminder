package com.fearefull.todoreminder.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.databinding.ItemAlarmDefaultBinding;
import com.fearefull.todoreminder.databinding.ItemAlarmDisabledBinding;
import com.fearefull.todoreminder.databinding.ItemAlarmDoneBinding;
import com.fearefull.todoreminder.databinding.ItemAlarmNotDoneBinding;
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
        return alarmList.get(position).getAlarmType().value;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (AlarmType.getTypeByValue(viewType) == AlarmType.DEFAULT) {
            ItemAlarmDefaultBinding binding = ItemAlarmDefaultBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new AlarmDefaultViewHolder(binding, listener);
        }
        else if (AlarmType.getTypeByValue(viewType) == AlarmType.DONE) {
            ItemAlarmDoneBinding binding = ItemAlarmDoneBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new AlarmDoneViewHolder(binding, listener);
        }
        else if (AlarmType.getTypeByValue(viewType) == AlarmType.NOT_DONE) {
            ItemAlarmNotDoneBinding binding = ItemAlarmNotDoneBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new AlarmNotDoneViewHolder(binding, listener);
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

    public class AlarmDefaultViewHolder extends BaseAlarmViewHolder {
        private final ItemAlarmDefaultBinding binding;
        private AlarmItemViewModel viewModel;

        AlarmDefaultViewHolder(ItemAlarmDefaultBinding binding, AlarmAdapter.AlarmAdapterListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        @Override
        public void onBind(int position) {
            final Alarm alarm = alarmList.get(position);
            viewModel = new AlarmItemViewModel(alarm, this,
                    position == 0, position == alarmList.size() - 1);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }

    public class AlarmDoneViewHolder extends BaseAlarmViewHolder {
        private final ItemAlarmDoneBinding binding;
        private AlarmItemViewModel viewModel;

        AlarmDoneViewHolder(ItemAlarmDoneBinding binding, AlarmAdapter.AlarmAdapterListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        @Override
        public void onBind(int position) {
            final Alarm alarm = alarmList.get(position);
            viewModel = new AlarmItemViewModel(alarm, this,
                    position == 0, position == alarmList.size() - 1);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }

    public class AlarmNotDoneViewHolder extends BaseAlarmViewHolder {
        private final ItemAlarmNotDoneBinding binding;
        private AlarmItemViewModel viewModel;

        AlarmNotDoneViewHolder(ItemAlarmNotDoneBinding binding, AlarmAdapter.AlarmAdapterListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        @Override
        public void onBind(int position) {
            final Alarm alarm = alarmList.get(position);
            viewModel = new AlarmItemViewModel(alarm, this,
                    position == 0, position == alarmList.size() - 1);

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
            viewModel = new AlarmItemViewModel(alarm, this,
                    position == 0, position == alarmList.size() - 1);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }
}
