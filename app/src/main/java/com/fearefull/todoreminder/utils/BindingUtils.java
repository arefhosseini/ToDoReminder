package com.fearefull.todoreminder.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fearefull.todoreminder.data.model.other.Alarm;
import com.fearefull.todoreminder.data.model.other.RepeatTypeItem;
import com.fearefull.todoreminder.ui.alarm_manager.RepeatAdapter;
import com.fearefull.todoreminder.ui.home.AlarmAdapter;

import java.util.List;

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"adapter"})
    public static void addRepeatTypeItems(RecyclerView recyclerView, List<RepeatTypeItem> repeatTypeItems) {
        RepeatAdapter adapter = (RepeatAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(repeatTypeItems);
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

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }
}