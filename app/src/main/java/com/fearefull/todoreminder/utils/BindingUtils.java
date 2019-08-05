package com.fearefull.todoreminder.utils;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.History;
import com.fearefull.todoreminder.data.model.db.Ringtone;
import com.fearefull.todoreminder.data.model.other.item.AlarmTitleItem;
import com.fearefull.todoreminder.data.model.other.item.DayWeekItem;
import com.fearefull.todoreminder.data.model.other.item.RepeatItem;
import com.fearefull.todoreminder.data.model.other.item.RepeatManagerItem;
import com.fearefull.todoreminder.data.model.other.item.RingtonePickerItem;
import com.fearefull.todoreminder.ui.alarm_manager.AlarmTitleAdapter;
import com.fearefull.todoreminder.ui.alarm_manager.RepeatAdapter;
import com.fearefull.todoreminder.ui.alarm_manager.picker.ringtone_picker.RingtonePickerAdapter;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.weekly_repeat.DayWeekAdapter;
import com.fearefull.todoreminder.ui.alarm_manager.repeat_manager.RepeatManagerAdapter;
import com.fearefull.todoreminder.ui.base.BaseViewPager;
import com.fearefull.todoreminder.ui.history.HistoryAdapter;
import com.fearefull.todoreminder.ui.home.AlarmAdapter;

import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"adapter"})
    public static void addRepeatTypeItems(RecyclerView recyclerView, List<RepeatItem> repeatItemList) {
        RepeatAdapter adapter = (RepeatAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(repeatItemList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addAlarmTitleItems(RecyclerView recyclerView, List<AlarmTitleItem> alarmTitleItemList) {
        AlarmTitleAdapter adapter = (AlarmTitleAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(alarmTitleItemList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addAlarmItems(RecyclerView recyclerView, List<Alarm> alarmList) {
        AlarmAdapter adapter = (AlarmAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(alarmList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addHistoryItems(RecyclerView recyclerView, List<History> itemList) {
        HistoryAdapter adapter = (HistoryAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(itemList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addRepeatManagerITem(RecyclerView recyclerView, List<RepeatManagerItem> itemList) {
        RepeatManagerAdapter adapter = (RepeatManagerAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(itemList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addDayWeekItem(RecyclerView recyclerView, List<DayWeekItem> itemList) {
        DayWeekAdapter adapter = (DayWeekAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(itemList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addRingtoneItem(RecyclerView recyclerView, List<RingtonePickerItem> ringtoneList) {
        RingtonePickerAdapter adapter = (RingtonePickerAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(ringtoneList);
        }
    }

    @BindingAdapter({"currentTab"})
    public static void setNewTab(BaseViewPager viewPager, MutableLiveData<Integer> newTab) {
        if (newTab.getValue() != null) {
            if (viewPager.getCurrentItem() != newTab.getValue()) {
                viewPager.setCurrentItem(newTab.getValue());
            }
        }
    }

    @BindingAdapter({"pageLimit"})
    public static void setPageLimit(BaseViewPager viewPager, MutableLiveData<Integer> limit) {
        if (limit.getValue() != null) {
            if (viewPager.getOffscreenPageLimit() != limit.getValue())
                viewPager.setOffscreenPageLimit(limit.getValue());
        }
    }

    @BindingAdapter({"refreshColor"})
    public static void setRefreshColors(SwipeRefreshLayout refreshLayout, Boolean isSet) {
        if (isSet) {
            refreshLayout.setColorSchemeColors(
                    ContextCompat.getColor(refreshLayout.getContext(), R.color.secondaryColorLightTheme));
        }
    }

    @BindingAdapter({"values"})
    public static void setValues(NumberPicker picker, MutableLiveData<List<String>> values) {
        if (values.getValue() != null) {
            String[] convertedValues = values.getValue().toArray(new String[0]);
            if (picker.getDisplayedValues() != convertedValues) {
                picker.setDisplayedValues(convertedValues);
            }
        }
    }

    @BindingAdapter({"maxIndex"})
    public static void setMaxIndex(NumberPicker picker, MutableLiveData<Integer> index) {
        if (index.getValue() != null) {
            if (picker.getMaxValue() != index.getValue()) {
                picker.setMaxValue(index.getValue());
            }
        }
    }

    @BindingAdapter({"defaultIndex"})
    public static void setDefaultIndex(NumberPicker picker, MutableLiveData<Integer> index) {
        if (index.getValue() != null) {
            if (picker.getValue() != index.getValue()) {
                picker.setValue(index.getValue());
            }
        }
    }

    @BindingAdapter({"dividedColor"})
    public static void setDividedColor(NumberPicker picker, Integer dividedColor) {
        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(dividedColor);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    Timber.e(e);
                } catch (Resources.NotFoundException e) {
                    Timber.e(e);
                }
                catch (IllegalAccessException e) {
                    Timber.e(e);
                }
                break;
            }
        }
    }

    @BindingAdapter({"pickerSpeed"})
    public static void setPickerSpeed(NumberPicker pickerSpeed, Integer speed) {
        if (speed != null) {
            pickerSpeed.setOnLongPressUpdateInterval(speed.longValue());
        }
    }

    @BindingAdapter({"setDivider"})
    public static void setRefreshColors(RecyclerView recyclerView, Boolean isSet) {
        if (isSet) {
            DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.divider)));
            recyclerView.addItemDecoration(itemDecorator);
        }
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, Integer resource) {
        if (resource != null)
            imageView.setImageResource(resource);
    }

    @BindingAdapter({"android:selection"})
    public static void setTextEditSelectionCursor(AppCompatEditText editText, Integer selection) {
        if (selection != null)
            editText.setSelection(selection);
    }
}