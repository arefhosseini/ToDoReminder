package com.fearefull.todoreminder.data.model.other.item;

import com.fearefull.todoreminder.data.model.other.type.AlarmTitleType;
import com.fearefull.todoreminder.databinding.ItemAlarmTitleBinding;

public class AlarmTitleItem {
    private final AlarmTitleType type;
    private ItemAlarmTitleBinding binding;
    private boolean isSelected;

    public AlarmTitleItem(AlarmTitleType type, boolean isSelected) {
        this.type = type;
        this.binding = binding;
        this.isSelected = isSelected;
    }

    public AlarmTitleType getType() {
        return type;
    }

    public ItemAlarmTitleBinding getBinding() {
        return binding;
    }

    public void setBinding(ItemAlarmTitleBinding binding) {
        this.binding = binding;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
