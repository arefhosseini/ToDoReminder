package com.fearefull.todoreminder.ui.alarm_manager.repeat.weekly_repeat;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.data.model.other.item.DayWeekItem;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class WeeklyRepeatViewModel extends BaseRepeatViewModel<WeeklyRepeatNavigator> {
    private final MutableLiveData<List<DayWeekItem>> dayWeekItemsLiveData;

    public WeeklyRepeatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        super(dataManager, schedulerProvider, settings);
        dayWeekItemsLiveData = new MutableLiveData<>();
        getPageLimitPager().setValue(1);
    }

    void init() {
        fetchData();
    }

    private void fetchData() {
        dayWeekItemsLiveData.setValue(AlarmUtils.getDayWeekItems());
    }

    public MutableLiveData<List<DayWeekItem>> getDayWeekItemsLiveData() {
        return dayWeekItemsLiveData;
    }

    void changeDayWeekItem(DayWeekItem dayWeekItem) {
        //dayWeekItemsLiveData.setValue(dayWeekItemsLiveData.getValue());
    }

    void sendDaysWeek(int minute, int hour) {
        getRepeatModel().setMinute(minute);
        getRepeatModel().setHour(hour);
        for (DayWeekItem item: Objects.requireNonNull(dayWeekItemsLiveData.getValue())) {
            if (item.isSelected()) {
                getRepeatModel().addToDaysWeek(item.getType().getValue());
            }
        }
        checkForSend(Repeat.WEEKLY);
    }
}
