package com.fearefull.todoreminder.ui.history;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.db.History;

public class HistoryItemViewModel {
    private final History history;
    private final HistoryItemViewModelListener listener;
    private final ObservableField<String> title;
    private final ObservableField<String> time;
    private final ObservableField<String> isDone;

    public HistoryItemViewModel(History history, HistoryItemViewModelListener listener) {
        this.history = history;
        this.listener = listener;

        title = new ObservableField<>();
        time = new ObservableField<>();
        isDone = new ObservableField<>();

        title.set(history.getTitle());
        time.set(history.timeToString());
        if (history.getDone()) {
            isDone.set("انجام شده");
        }
        else {
            isDone.set("انجام نشده");
        }
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public History getHistory() {
        return history;
    }

    public ObservableField<String> getIsDone() {
        return isDone;
    }

    public boolean onLongClick() {
        listener.onLongClick(history);
        return false;
    }

    public void onIsDoneClick() {
        if (!history.getDone())
            listener.onIsDoneClick(history);
    }

    public interface HistoryItemViewModelListener {
        void onLongClick(History history);
        void onIsDoneClick(History history);
    }
}
