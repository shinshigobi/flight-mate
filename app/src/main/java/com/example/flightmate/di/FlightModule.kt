package com.example.flightmate.di

import com.example.flightmate.data.repository.flight.FlightRepository
import com.example.flightmate.domain.usecase.GetFlightListUseCase
import com.example.flightmate.domain.usecase.GetFlightListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object FlightModule {
    @Provides
    fun provideGetFlightListUseCase(repository: FlightRepository): GetFlightListUseCase {
        return GetFlightListUseCaseImpl(repository)
    }
}
