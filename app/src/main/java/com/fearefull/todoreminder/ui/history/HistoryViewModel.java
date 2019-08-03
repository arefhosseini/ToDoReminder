package com.fearefull.todoreminder.ui.history;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.History;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class HistoryViewModel extends BaseViewModel<HistoryNavigator> {

    private final MutableLiveData<List<History>> historyItemsLiveData;
    private ObservableBoolean isRefreshing;
    private History deletingHistory, isDoneHistory;

    private DialogInterface.OnClickListener deleteHistoryOnClickListener = (dialog, which) -> {
        if (which == AlertDialog.BUTTON_POSITIVE) {
            deleteHistory();
        }
    };

    private DialogInterface.OnClickListener isDoneHistoryOnClickListener = (dialog, which) -> {
        if (which == AlertDialog.BUTTON_POSITIVE) {
            setDoneHistory();
        }
    };

    public HistoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        super(dataManager, schedulerProvider, settings);
        historyItemsLiveData = new MutableLiveData<>();
        isRefreshing = new ObservableBoolean();
        fetchData();
    }

    private void fetchData() {
        isRefreshing.set(true);
        getCompositeDisposable().add(getDataManager().getAllHistories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(histories -> {
                    getHistoryItemsLiveData().setValue(histories);
                    isRefreshing.set(false);
                }, throwable -> {
                    Timber.e(throwable);
                    isRefreshing.set(false);
                })
        );
    }

    public void onRefresh() {
        fetchData();
    }

    public MutableLiveData<List<History>> getHistoryItemsLiveData() {
        return historyItemsLiveData;
    }

    public ObservableBoolean getIsRefreshing() {
        return isRefreshing;
    }

    public void setIsRefreshing(ObservableBoolean isRefreshing) {
        this.isRefreshing = isRefreshing;
    }

    public DialogInterface.OnClickListener getDeleteHistoryOnClickListener() {
        return deleteHistoryOnClickListener;
    }

    public DialogInterface.OnClickListener getIsDoneHistoryOnClickListener() {
        return isDoneHistoryOnClickListener;
    }

    void setDeletingHistory(History deletingHistory) {
        this.deletingHistory = deletingHistory;
    }

    void setIsDoneHistory(History isDoneHistory) {
        this.isDoneHistory = isDoneHistory;
    }

    private void deleteHistory() {
        getCompositeDisposable().add(getDataManager().deleteHistory(deletingHistory)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    if (result && Objects.requireNonNull(historyItemsLiveData.getValue()).contains(deletingHistory)) {
                        historyItemsLiveData.getValue().remove(deletingHistory);
                        historyItemsLiveData.setValue(historyItemsLiveData.getValue());
                        deletingHistory = null;
                    }

                }, Timber::e)
        );
    }

    private void setDoneHistory() {
        isDoneHistory.setDone(true);
        getCompositeDisposable().add(getDataManager().updateHistory(isDoneHistory)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    if (result) {
                        historyItemsLiveData.setValue(historyItemsLiveData.getValue());
                        getNavigator().setDoneHistory(isDoneHistory);
                        isDoneHistory = null;
                    }
                }, Timber::e)
        );
    }
}
