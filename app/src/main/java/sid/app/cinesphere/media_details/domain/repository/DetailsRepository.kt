package sid.app.cinesphere.media_details.domain.repository

import sid.app.cinesphere.main.domain.models.Media
import sid.app.cinesphere.util.Resource
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    suspend fun getDetails(
        type: String,
        isRefresh: Boolean,
        id: Int,
        apiKey: String
    ): Flow<Resource<Media>>

}










