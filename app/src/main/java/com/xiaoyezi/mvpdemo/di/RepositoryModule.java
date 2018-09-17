package com.xiaoyezi.mvpdemo.di;

import com.xiaoyezi.mvpdemo.api.WebService;
import com.xiaoyezi.mvpdemo.repository.WebServiceRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Singleton
    @Provides
    public static WebServiceRepository provideWebServiceRepository(WebService webService) {
        return new WebServiceRepository(webService);
    }
}
