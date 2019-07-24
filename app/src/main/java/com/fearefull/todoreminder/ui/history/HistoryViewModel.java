package com.fearefull.todoreminder.ui.history;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.History;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;

import timber.log.Timber;

public class HistoryViewModel extends BaseViewModel<HistoryNavigator> {

    private final MutableLiveData<List<History>> historyItemsLiveData;

    public HistoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        historyItemsLiveData = new MutableLiveData<>();
        fetchData();
    }

    private void fetchData() {
        getCompositeDisposable().add(getDataManager().getAllHistories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(historyItemsLiveData::setValue, Timber::e)
        );
    }

    public MutableLiveData<List<History>> getHistoryItemsLiveData() {
        return historyItemsLiveData;
    }
}
