package com.fearefull.todoreminder.ui.history;

import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;

@Module
public class HistoryFragmentModule {
    @Provides
    LinearLayoutManager provideLinearLayoutManager(HistoryFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    HistoryAdapter provideHistoryAdapter() {
        return new HistoryAdapter();
    }
}
