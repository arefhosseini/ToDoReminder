package com.fearefull.todoreminder.ui.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.History;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.databinding.ItemHistoryBinding;
import com.fearefull.todoreminder.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final List<History> historyList;
    private HistoryAdapterListener listener;
    private Settings settings;

    public HistoryAdapter() {
        this.historyList = new ArrayList<>();
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoryViewHolder(binding);
    }

    public void addItems(List<History> historyItemList) {
        this.historyList.addAll(historyItemList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        historyList.clear();
    }

    public void setDoneHistory(History history) {
        if (history.getDone()) {
             history.getBinding().isDoneLayout.setBackground(
                     ContextCompat.getDrawable(history.getBinding().isDoneLayout.getContext(), R.color.greenDarkColor));
        }
    }

    public void setListener(HistoryAdapterListener listener) {
        this.listener = listener;
    }

    public interface HistoryAdapterListener {
        void onHistoryLongClick(History history);
        void onHistoryIsDoneClick(History history);
    }

    public class HistoryViewHolder extends BaseViewHolder implements HistoryItemViewModel.HistoryItemViewModelListener {
        private final ItemHistoryBinding binding;
        private HistoryItemViewModel viewModel;

        public HistoryViewHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            final History item = historyList.get(position);
            viewModel = new HistoryItemViewModel(item, this, settings);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
            item.setBinding(binding);

            if (item.getDone()) {
                binding.isDoneLayout.setBackground(ContextCompat.getDrawable(binding.isDoneLayout.getContext(), R.color.greenDarkColor));
            }
            else {
                binding.isDoneLayout.setBackground(ContextCompat.getDrawable(binding.isDoneLayout.getContext(), R.color.redDark2Color));
            }
        }

        @Override
        public void onLongClick(History history) {
            listener.onHistoryLongClick(history);
        }

        @Override
        public void onIsDoneClick(History history) {
            listener.onHistoryIsDoneClick(history);
        }
    }
}
