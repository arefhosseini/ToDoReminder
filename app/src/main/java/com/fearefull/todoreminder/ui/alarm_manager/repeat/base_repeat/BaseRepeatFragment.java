package com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat;

import android.widget.Toast;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.ui.base.BaseFragment;
import com.fearefull.todoreminder.ui.base.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public abstract class BaseRepeatFragment<T extends ViewDataBinding, V extends BaseRepeatViewModel>
        extends BaseFragment<T, V> implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProviderFactory factory;
    private RepeatCallBack callBack;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    public ViewModelProviderFactory getFactory() {
        return factory;
    }

    public void setUp() {
    }


    public void showError() {
        shake();
        Toast.makeText(getContext(), R.string.not_updated_time_error, Toast.LENGTH_SHORT).show();
    }

    public void showDuplicate() {
        shake();
        Toast.makeText(getContext(), R.string.duplicate_time_error, Toast.LENGTH_SHORT).show();
    }

    public void send() {
        callBack.onAlarmChanged(getViewModel().getAlarm());
    }

    public void shake() {
    }

    public void setCallBack(RepeatCallBack callBack) {
        this.callBack = callBack;
    }

    public interface RepeatCallBack {
        void onAlarmChanged(Alarm alarm);
    }
}
