package sid.app.cinesphere.di

import sid.app.cinesphere.media_details.data.repository.DetailsRepositoryImpl
import sid.app.cinesphere.media_details.data.repository.ExtraDetailsRepositoryImpl
import sid.app.cinesphere.media_details.domain.repository.DetailsRepository
import sid.app.cinesphere.media_details.domain.repository.ExtraDetailsRepository
import sid.app.cinesphere.main.data.repository.GenreRepositoryImpl
import sid.app.cinesphere.main.data.repository.MediaRepositoryImpl
import sid.app.cinesphere.search.data.repository.SearchRepositoryImpl
import sid.app.cinesphere.main.domain.repository.GenreRepository
import sid.app.cinesphere.main.domain.repository.MediaRepository
import sid.app.cinesphere.search.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMediaRepository(
        mediaRepositoryImpl: MediaRepositoryImpl
    ): MediaRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindGenreRepository(
        genreRepositoryImpl: GenreRepositoryImpl
    ): GenreRepository

    @Binds
    @Singleton
    abstract fun bindDetailsRepository(
        detailsRepositoryImpl: DetailsRepositoryImpl
    ): DetailsRepository

    @Binds
    @Singleton
    abstract fun bindExtraDetailsRepository(
       extraDetailsRepositoryImpl: ExtraDetailsRepositoryImpl
    ): ExtraDetailsRepository

}
