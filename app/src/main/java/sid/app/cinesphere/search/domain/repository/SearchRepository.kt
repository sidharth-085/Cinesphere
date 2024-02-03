package sid.app.cinesphere.search.domain.repository

import sid.app.cinesphere.util.Resource
import sid.app.cinesphere.main.domain.models.Media
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchList(
        fetchFromRemote: Boolean,
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

}










