package com.fearefull.todoreminder.di.component;

import android.app.Application;

import com.fearefull.todoreminder.App;
import com.fearefull.todoreminder.di.builder.ActivityBuilder;
import com.fearefull.todoreminder.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {
    void inject(App app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
