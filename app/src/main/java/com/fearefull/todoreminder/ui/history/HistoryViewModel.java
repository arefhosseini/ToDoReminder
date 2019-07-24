package com.fearefull.todoreminder.ui.history;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.History;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;

import timber.log.Timber;

public class HistoryViewModel extends BaseViewModel<HistoryNavigator> {

    private final MutableLiveData<List<History>> historyItemsLiveData;
    private ObservableBoolean isRefreshing;

    public HistoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
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
}
