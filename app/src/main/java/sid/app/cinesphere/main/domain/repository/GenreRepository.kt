package sid.app.cinesphere.main.domain.repository

import sid.app.cinesphere.util.Resource
import sid.app.cinesphere.main.domain.models.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    suspend fun getGenres(
        fetchFromRemote: Boolean,
        type: String,
        apiKey: String
    ): Flow<Resource<List<Genre>>>
}










