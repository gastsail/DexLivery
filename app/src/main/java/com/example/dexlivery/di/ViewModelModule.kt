package com.example.dexlivery.di

import com.example.dexlivery.domain.MainRepo
import com.example.dexlivery.domain.MainRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class ViewModelModule {

    @Provides
    fun getMainRepo(): MainRepo = MainRepoImpl()
}