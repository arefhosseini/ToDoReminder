package com.fearefull.todoreminder.data.model.other.item;

import com.fearefull.todoreminder.data.model.db.History;

public class HistoryItem {
    private final History history;

    public HistoryItem(History history) {
        this.history = history;
    }

    public History getHistory() {
        return history;
    }
}
