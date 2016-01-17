package com.vega.app.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.path.android.jobqueue.JobManager;
import com.vega.BuildConfig;
import com.vega.app.core.Navigator;
import com.vega.app.core.application.VegaApplication;
import com.vega.app.core.error.CommonErrorHandler;
import com.vega.app.core.eventbus.AndroidBus;
import com.vega.app.domain.InvokerExecutor;
import com.vega.app.geo.manager.GeoLocationManager;
import com.vega.app.net.OkHttpClientFactory;
import com.vega.data.datasources.realm.services.RealmServicesDataSource;
import com.vega.data.datasources.rest.services.RestServicesDataSource;
import com.vega.data.datasources.shared.services.SharedPreferencesServicesDataSource;
import com.vega.data.service.RestClientService;
import com.vega.domain.usecase.invoker.Invoker;
import com.vega.repository.datasources.IDataBaseDataSource;
import com.vega.repository.datasources.IRestDataSource;
import com.vega.repository.datasources.ISharedPreferencesDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmObject;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Component created on 17/11/2015.
 *
 * @author dmartin
 */
@Module
public class ApplicationModule {

    private final VegaApplication application;

    public ApplicationModule(VegaApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public VegaApplication provideWtnApplication() {
        return this.application;
    }

    @Provides
    @Singleton
    public Navigator provideNavigator() {
        return new Navigator(application.getApplicationContext());
    }

    @Provides
    @Singleton
    public CommonErrorHandler provideCommonErrorHandler() {
        return new CommonErrorHandler(application.getApplicationContext());
    }

    @Provides
    @Singleton
    public AndroidBus provideEventBus() {
        return new AndroidBus();
    }

    @Provides
    @Singleton
    public Invoker<String> provideInvoker() {
        return new InvokerExecutor<>(new JobManager(application.getApplicationContext()));
    }

    @Provides
    @Singleton
    public RestClientService provideRestClientService() {

        RestClientService restClientService = null;

        try {

            restClientService = new Retrofit.Builder()
                    .baseUrl(BuildConfig.HOST)
                    .addConverterFactory(GsonConverterFactory.create(provideGson()))
                    .client(OkHttpClientFactory.createSecuredClient(application.getApplicationContext()))
                    .build()
                    .create(RestClientService.class);

        } catch (Exception e) {
            Log.e(getClass().getName(), e.getMessage());
        }

        return restClientService;

    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences() {
        return application.getApplicationContext().getSharedPreferences("trackingStore", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public IRestDataSource provideRestDataSource() {
        return new RestServicesDataSource(provideRestClientService());
    }

    @Provides
    @Singleton
    public ISharedPreferencesDataSource provideCacheDataSource() {
        return new SharedPreferencesServicesDataSource(provideSharedPreferences(), provideGson());
    }

    @Provides
    @Singleton
    public IDataBaseDataSource provideDataBaseSource() {
        return new RealmServicesDataSource(application.getApplicationContext());
    }

    @Provides
    @Singleton
    public Gson provideGson() {

        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

    }

    @Provides
    @Singleton
    public GeoLocationManager provideGeoLocationManager() {
        return new GeoLocationManager(application.getApplicationContext());
    }

}
