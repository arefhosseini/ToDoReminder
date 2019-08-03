package com.fearefull.todoreminder.ui.base;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends ViewModel {

    private final DataManager dataManager;

    private final ObservableBoolean isLoading = new ObservableBoolean();

    private final SchedulerProvider schedulerProvider;

    private final Settings settings;

    private CompositeDisposable compositeDisposable;

    private WeakReference<N> navigator;

    public BaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.settings = settings;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public Settings getSettings() {
        return settings;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public N getNavigator() {
        return navigator.get();
    }

    public void setNavigator(N navigator) {
        this.navigator = new WeakReference<>(navigator);
    }

    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }
}