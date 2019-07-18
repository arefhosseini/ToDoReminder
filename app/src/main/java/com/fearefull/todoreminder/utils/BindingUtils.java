package com.fearefull.todoreminder.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.other.RepeatItem;
import com.fearefull.todoreminder.data.model.other.RepeatManagerItem;
import com.fearefull.todoreminder.ui.alarm_manager.RepeatAdapter;
import com.fearefull.todoreminder.ui.alarm_manager.repeat_manager.RepeatManagerAdapter;
import com.fearefull.todoreminder.ui.base.BaseViewPager;
import com.fearefull.todoreminder.ui.home.AlarmAdapter;

import org.w3c.dom.Text;

import java.util.List;

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"adapter"})
    public static void addRepeatTypeItems(RecyclerView recyclerView, List<RepeatItem> repeatItems) {
        RepeatAdapter adapter = (RepeatAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(repeatItems);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addAlarmItems(RecyclerView recyclerView, List<Alarm> alarms) {
        AlarmAdapter adapter = (AlarmAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(alarms);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addRepeatManagerITem(RecyclerView recyclerView, List<RepeatManagerItem> items) {
        RepeatManagerAdapter adapter = (RepeatManagerAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
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
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }
}