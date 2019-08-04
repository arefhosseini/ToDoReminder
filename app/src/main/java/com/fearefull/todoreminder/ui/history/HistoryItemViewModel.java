package com.fearefull.todoreminder.ui.history;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.model.db.History;
import com.fearefull.todoreminder.data.model.db.Settings;

public class HistoryItemViewModel {
    private final History history;
    private final HistoryItemViewModelListener listener;
    private final ObservableField<String> title;
    private final ObservableField<Integer> imageRes;
    private final ObservableField<String> time;
    private final ObservableField<String> isDone;
    private final ObservableBoolean isFirst;

    public HistoryItemViewModel(History history, HistoryItemViewModelListener listener,
                                Settings settings, boolean isFirst) {
        this.history = history;
        this.listener = listener;

        this.title = new ObservableField<>();
        this.imageRes = new ObservableField<>();
        this.time = new ObservableField<>();
        this.isDone = new ObservableField<>();
        this.isFirst = new ObservableBoolean(isFirst);

        title.set(history.getTitle());
        imageRes.set(history.getTitleType().getImageRes());
        time.set(history.timeToString(settings.getHourType()));
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

    public ObservableField<Integer> getImageRes() {
        return imageRes;
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

    public ObservableBoolean getIsFirst() {
        return isFirst;
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
