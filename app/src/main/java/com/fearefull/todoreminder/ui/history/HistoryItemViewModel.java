package com.fearefull.todoreminder.ui.history;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.db.History;

public class HistoryItemViewModel {
    private final History history;
    private final HistoryItemViewModelListener listener;
    private final ObservableField<String> title;
    private final ObservableField<String> time;
    private final ObservableField<Boolean> isDone;

    public HistoryItemViewModel(History history, HistoryItemViewModelListener listener) {
        this.history = history;
        this.listener = listener;

        title = new ObservableField<>();
        time = new ObservableField<>();
        isDone = new ObservableField<>();
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public History getHistory() {
        return history;
    }

    public ObservableField<Boolean> getIsDone() {
        return isDone;
    }

    public void onLongClick() {
        listener.onLongClick(history);
    }

    public interface HistoryItemViewModelListener {
        void onLongClick(History history);
    }
}
