package sid.app.cinesphere.search.presentation

import sid.app.cinesphere.main.domain.models.Media

sealed class SearchUiEvents {
    data class Refresh(val type: String) : SearchUiEvents()
    data class OnPaginate(val type: String) : SearchUiEvents()
    data class OnSearchQueryChanged(val query: String) : SearchUiEvents()
    data class OnSearchedItemClick(val media: Media) : SearchUiEvents()
}