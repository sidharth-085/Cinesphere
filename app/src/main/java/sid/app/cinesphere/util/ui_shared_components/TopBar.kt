package sid.app.cinesphere.util.ui_shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import sid.app.cinesphere.R
import sid.app.cinesphere.search.presentation.SearchScreenState
import sid.app.cinesphere.theme.BigRadius
import sid.app.cinesphere.util.Route

@Composable
fun NonFocusedTopBar(
    toolbarOffsetHeightPx: Int,
    navController: NavController,
) {

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .height(BigRadius.dp)
            .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NonFocusedSearchBar(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clickable {
                        navController.navigate(Route.SEARCH_SCREEN)
                    }
                    .padding(start = 8.dp, end = 16.dp),
                placeholderText = stringResource(R.string.search_for_a_movie_or_tv_series),
            )

            Icon(
                painter = painterResource(id = R.drawable.contact_us_icon),
                contentDescription = stringResource(id = R.string.contact_us),
                modifier = Modifier
                    .size(38.dp)
                    .clickable {
                        navController.navigate(Route.CONTACT_US_SCREEN)
                    }
                    .clip(CircleShape),
                tint = Color(0xFFAA3CA0)
            )
        }
    }
}

@Composable
fun FocusedTopBar(
    toolbarOffsetHeightPx: Int,
    searchScreenState: SearchScreenState,
    onSearch: (String) -> Unit = {}
) {

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .height(BigRadius.dp)
            .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx) }
    ) {
        SearchBar(
            leadingIcon = {
                Icon(
                    Icons.Rounded.Search,
                    null,
                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(50.dp),
            placeholderText = stringResource(R.string.search_for_a_movie_or_tv_series),
            searchScreenState = searchScreenState
        ) {
            onSearch(it)
        }
    }
}
