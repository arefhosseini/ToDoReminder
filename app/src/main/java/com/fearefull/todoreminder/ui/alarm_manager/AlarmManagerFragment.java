package com.fearefull.todoreminder.ui.alarm_manager;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fearefull.todoreminder.BR;
import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.ViewModelProviderFactory;
import com.fearefull.todoreminder.data.model.other.Alarm;
import com.fearefull.todoreminder.data.model.other.RepeatTypeItem;
import com.fearefull.todoreminder.databinding.FragmentAlarmManagerBinding;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.utils.ViewUtils;

import java.util.Objects;

import javax.inject.Inject;

import static com.fearefull.todoreminder.utils.AppConstants.ALARM_KEY;

public class AlarmManagerFragment extends BaseFragment<FragmentAlarmManagerBinding, AlarmManagerViewModel>
        implements AlarmManagerNavigator, RepeatAdapter.RepeatAdapterListener {

    @Inject
    RepeatAdapter repeatAdapter;
    @Inject
    LinearLayoutManager layoutManager;
    public static final String TAG = AlarmManagerFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private AlarmManagerViewModel viewModel;
    private FragmentAlarmManagerBinding binding;

    public static AlarmManagerFragment newInstance(Alarm alarm) {
        Bundle args = new Bundle();
        AlarmManagerFragment fragment = new AlarmManagerFragment();
        args.putSerializable(ALARM_KEY, alarm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_alarm_manager;
    }

    @Override
    public AlarmManagerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(AlarmManagerViewModel.class);
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        assert getArguments() != null;
        viewModel.setAlarm((Alarm) getArguments().getSerializable(ALARM_KEY));
        viewModel.updateAlarm();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        repeatAdapter.setListener(this);
        setUp();
    }

    private void setUp() {
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.repeatRecyclerView.setLayoutManager(layoutManager);
        binding.repeatRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.repeatRecyclerView.setAdapter(repeatAdapter);

        setUpNumberPicker(binding.hoursPicker, viewModel.getHours(), viewModel.getAlarm().getTime().getHourIndex());
        setUpNumberPicker(binding.minutesPicker, viewModel.getMinutes(), viewModel.getAlarm().getTime().getMinuteIndex());
        setUpNumberPicker(binding.typePicker, viewModel.getTimeTypes(), viewModel.getAlarm().getTime().getTimeTypeIndex());

    }

    private void setUpNumberPicker(NumberPicker picker, String[] data, int defaultIndex) {
        ViewUtils.setDividerColor(picker, getResources().getColor(R.color.secondaryColorLightTheme));
        picker.setMaxValue(data.length - 1);
        picker.setValue(defaultIndex);
        picker.setDisplayedValues(data);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void openDatePickerFragment() {

    }

    @Override
    public void openRepeatPickerFragment() {

    }

    @Override
    public void openCustomRepeatPickerFragment() {

    }

    @Override
    public void openEveryCustomRepeatPickerFragment() {

    }

    @Override
    public void openOnCustomRepeatPickerFragment() {

    }

    @Override
    public void onUpdateAlarm(Alarm alarm, String fragmentTag) {

    }

    @Override
    public void onRepeatItemClick(RepeatTypeItem repeatTypeItem) {
        viewModel.updateRepeatType(repeatTypeItem.getRepeatType());
    }

    @Override
    public void closeAllExpansions() {
        if (binding.timeExpansionLayout.isExpanded())
            binding.timeExpansionLayout.collapse(true);
        else if (binding.noteExpansionLayout.isExpanded())
            binding.noteExpansionLayout.collapse(true);
        else if (binding.repeatExpansionLayout.isExpanded())
            binding.repeatExpansionLayout.collapse(true);
    }
}
