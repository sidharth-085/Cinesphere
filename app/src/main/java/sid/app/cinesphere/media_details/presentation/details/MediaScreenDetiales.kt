package sid.app.cinesphere.media_details.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import sid.app.cinesphere.R
import sid.app.cinesphere.main.data.remote.api.MediaApi
import sid.app.cinesphere.main.domain.models.Media
import sid.app.cinesphere.media_details.presentation.details.detailScreenUiComponents.MovieImage
import sid.app.cinesphere.theme.SmallRadius
import sid.app.cinesphere.ui.theme.font
import sid.app.cinesphere.util.Constants
import sid.app.cinesphere.util.Route
import sid.app.cinesphere.util.ui_shared_components.Item
import sid.app.cinesphere.util.ui_shared_components.RatingBar
import sid.app.cinesphere.util.ui_shared_components.genresProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MediaDetailScreen(
    navController: NavController,
    media: Media,
    mediaDetailsScreenState: MediaDetailsScreenState,
    onEvent: (MediaDetailsScreenEvents) -> Unit
) {

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        onEvent(MediaDetailsScreenEvents.Refresh)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

    val imageUrl = "${MediaApi.IMAGE_BASE_URL}${media.backdropPath}"

    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )

    val surface = MaterialTheme.colorScheme.surface
    var averageColor by remember {
        mutableStateOf(surface)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .pullRefresh(refreshState)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

                VideoSection(
                    media = media,
                    imageState = imagePainter.state,
                ) { color ->
                    averageColor = color
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {

                    PosterSection(media = media) {}

                    Spacer(modifier = Modifier.width(12.dp))

                    InfoSection(
                        media = media,
                        mediaDetailsScreenState = mediaDetailsScreenState
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            OverviewSection(media = media)

            SimilarMediaSection(
                navController = navController,
                media = media,
                mediaDetailsScreenState = mediaDetailsScreenState
            )

            Spacer(modifier = Modifier.height(16.dp))

        }

        PullRefreshIndicator(
            refreshing, refreshState, Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun VideoSection(
    media: Media,
    imageState: AsyncImagePainter.State,
    onImageLoaded: (color: Color) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(0),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {

            MovieImage(
                imageState = imageState,
                description = media.title,
                noImageId = null,
            ) { color ->
                onImageLoaded(color)
            }
        }
    }
}

@Composable
fun PosterSection(
    media: Media,
    onImageLoaded: (color: Color) -> Unit
) {

    val posterUrl = "${MediaApi.IMAGE_BASE_URL}${media.posterPath}"
    val posterPainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(posterUrl).size(Size.ORIGINAL)
            .build()
    )
    val posterState = posterPainter.state


    Column {
        Spacer(modifier = Modifier.height(200.dp))

        Card(
            modifier = Modifier
                .width(180.dp)
                .height(250.dp)
                .padding(start = 16.dp),
            shape = RoundedCornerShape(SmallRadius),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                MovieImage(
                    imageState = posterState,
                    description = media.title,
                    noImageId = Icons.Rounded.ImageNotSupported
                ) { color ->
                    onImageLoaded(color)
                }
            }
        }
    }
}

@Composable
fun InfoSection(
    media: Media,
    mediaDetailsScreenState: MediaDetailsScreenState,
) {

    val genres = genresProvider(
        genre_ids = media.genreIds,
        allGenres = if (media.mediaType == Constants.MOVIE) mediaDetailsScreenState.moviesGenresList
        else mediaDetailsScreenState.tvGenresList
    )

    Column (modifier = Modifier.padding(start = 4.dp, end = 16.dp)){
        Spacer(modifier = Modifier.height(260.dp))

        Text(
            text = media.title,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            fontFamily = font,
            fontSize = 19.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingBar(
                modifier = Modifier,
                starsModifier = Modifier.size(18.dp),
                rating = media.voteAverage.div(2)
            )

            Text(
                modifier = Modifier.padding(
                    horizontal = 4.dp
                ),
                text = media.voteAverage.toString().take(3),
                fontFamily = font,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {


            Text(
                text =
                if (media.releaseDate != Constants.unavailable)
                    media.releaseDate.take(4)
                else "",

                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = font,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 0.5.dp),
                text = if (media.adult) "+18" else "-12",
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = font,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = genres,
            fontFamily = font,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(6.dp))
    }
}


@Composable
fun OverviewSection(
    media: Media
) {
    Column {

        Text(
            modifier = Modifier.padding(horizontal = 22.dp),
            text = stringResource(R.string.overview),
            fontFamily = font,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            modifier = Modifier.padding(horizontal = 22.dp),
            text = media.overview,
            fontFamily = font,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 16.sp
        )

    }
}

@Composable
fun SimilarMediaSection(
    navController: NavController,
    media: Media,
    mediaDetailsScreenState: MediaDetailsScreenState,
) {

    val title = stringResource(id = R.string.similar)
    val mediaList = mediaDetailsScreenState.smallSimilarMediaList

    if (mediaList.isNotEmpty()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp, end = 22.dp, top = 22.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = font,
                    fontSize = 18.sp
                )

                Text(
                    modifier = Modifier
                        .alpha(0.85f)
                        .clickable {
                            navController.navigate(
                                "${Route.SIMILAR_MEDIA_LIST_SCREEN}?title=${media.title}"
                            )
                        },
                    text = stringResource(id = R.string.see_all),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = font,
                    fontSize = 14.sp,
                )
            }

            LazyRow {
                items(mediaList.size) {

                    var paddingEnd = 0.dp
                    if (it == mediaList.size - 1) {
                        paddingEnd = 16.dp
                    }

                    Item(
                        media = mediaList[it],
                        navController = navController,
                        modifier = Modifier
                            .height(200.dp)
                            .width(150.dp)
                            .padding(start = 16.dp, end = paddingEnd)
                    )
                }
            }
        }
    }
}

@Composable
fun SomethingWentWrong() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.something_went_wrong),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            fontFamily = font,
            fontSize = 19.sp
        )
    }
}

