package com.fearefull.todoreminder.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.other.RepeatItem;
import com.fearefull.todoreminder.data.model.other.RepeatManagerItem;
import com.fearefull.todoreminder.ui.alarm_manager.RepeatAdapter;
import com.fearefull.todoreminder.ui.alarm_manager.repeat_manager.RepeatManagerAdapter;
import com.fearefull.todoreminder.ui.home.AlarmAdapter;

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

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }
}