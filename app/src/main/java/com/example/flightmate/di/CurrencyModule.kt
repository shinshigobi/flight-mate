package com.example.flightmate.di

import com.example.flightmate.data.repository.currency.CurrencyRepository
import com.example.flightmate.domain.usecase.currency.GetExchangeRateUseCase
import com.example.flightmate.domain.usecase.currency.GetExchangeRateDataUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CurrencyModule {
    @Provides
    fun provideGetExchangeRateUseCase(repository: CurrencyRepository): GetExchangeRateUseCase {
        return GetExchangeRateDataUseCaseImpl(repository)
    }
}
