package br.com.movieapp.core.presentation.components.commom

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import br.com.movieapp.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AsyncImageUrl(
    modifier: Modifier = Modifier,
    imageUrl: String,
    crossFadeEnable: Boolean = true,
    @DrawableRes errorImage: Int = R.drawable.ic_error_image,
    @DrawableRes placeholderImage: Int = R.drawable.ic_placeholder,
    contentScale: ContentScale = ContentScale.FillHeight
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(crossFadeEnable)
            .error(errorImage)
            .placeholder(placeholderImage)
            .build(),
        contentDescription = "",
        contentScale = contentScale,
        modifier = modifier
    )

}