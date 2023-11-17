package com.practice.talkingavatar.model.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Region
import com.amazonaws.services.s3.AmazonS3Client
import com.practice.talkingavatar.BuildConfig
import com.practice.talkingavatar.R
import com.practice.talkingavatar.model.database.AppDatabase
import com.practice.talkingavatar.model.repository.*
import com.practice.talkingavatar.model.services.DidService
import com.practice.talkingavatar.model.services.ElevenLabService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DidServiceOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ElevenLabOkHttpClient

@Module
@InstallIn(SingletonComponent::class)
class TalkingAvatarServiceModule {

    @Provides
    fun provideDidService(@DidServiceOkHttpClient retrofit: Retrofit): DidService =
        retrofit.create(DidService::class.java)

    @Provides
    fun provideElevenLabService(@ElevenLabOkHttpClient retrofit: Retrofit): ElevenLabService =
        retrofit.create(ElevenLabService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
class TalkingAvatarNetworkModule {

    @Provides
    @Singleton
    fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    @DidServiceOkHttpClient
    fun provideDidServiceOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            DidServiceBasicAuthInterceptor(
                userName = BuildConfig.D_ID_USERNAME,
                password = BuildConfig.D_ID_PASSWORD
            )
        )
        .build()

    @Provides
    @Singleton
    @ElevenLabOkHttpClient
    fun provideElevenLabServiceOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            ElevenLabServiceCredentialsInterceptor(
                apiKey = BuildConfig.ELEVEN_LAB_API_KEY
            )
        )
        .build()

    @Provides
    @Singleton
    @DidServiceOkHttpClient
    fun provideDidServiceRetrofit(
        @DidServiceOkHttpClient client: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.d-id.com")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()

    @Provides
    @Singleton
    @ElevenLabOkHttpClient
    fun provideElevenLabServiceRetrofit(
        @ElevenLabOkHttpClient client: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.elevenlabs.io/v1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideS3Client(): AmazonS3Client = AmazonS3Client(
        BasicAWSCredentials(BuildConfig.S3_ACCESS_KEY, BuildConfig.S3_SECRET_KEY),
        Region.getRegion(BuildConfig.S3_REGION)
    )

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(
            context, AppDatabase::class.java,
            context.getString(R.string.talking_avatar_database)
        )
        .fallbackToDestructiveMigration()
        .build()
}

@Module
@InstallIn(ViewModelComponent::class)
interface TalkingAvatarRepositoryModule {

    @Binds
    fun bindS3Repository(defaultS3Repository: DefaultS3Repository): S3Repository

    @Binds
    fun bindTalkingAvatarRepository(defaultTalkingAvatarRepository: DefaultDidServiceRepository): DidServiceRepository

    @Binds
    fun bindElevenLabRepository(defaultElevenLabRepository: DefaultElevenLabRepository): ElevenLabRepository

}

@Module
@InstallIn(ViewModelComponent::class)
class VideoPlayerModule {

    @Provides
    @ViewModelScoped
    fun provideVideoPlayer(@ApplicationContext context: Context): ExoPlayer =
        ExoPlayer.Builder(context).build()
}