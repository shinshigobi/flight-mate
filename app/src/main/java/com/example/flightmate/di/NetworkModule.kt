package com.example.flightmate.di

import com.example.flightmate.data.remote.CurrencyApi
import com.example.flightmate.data.remote.FlightApi
import com.example.flightmate.data.repository.currency.CurrencyRepository
import com.example.flightmate.data.repository.currency.CurrencyRepositoryImpl
import com.example.flightmate.data.repository.flight.FlightRepository
import com.example.flightmate.data.repository.flight.FlightRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("kia")
    fun provideKiaRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://www.kia.gov.tw/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    @Named("currency")
    fun provideCurrencyRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.freecurrencyapi.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideFlightApi(@Named("kia") retrofit: Retrofit): FlightApi {
        return retrofit.create(FlightApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyApi(@Named("currency") retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFlightRepository(api: FlightApi): FlightRepository {
        return FlightRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(api: CurrencyApi): CurrencyRepository {
        return CurrencyRepositoryImpl(api)
    }
}
