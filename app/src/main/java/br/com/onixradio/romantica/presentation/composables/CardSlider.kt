package br.com.onixradio.romantica.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.onixradio.romantica.R
import br.com.onixradio.romantica.presentation.model.Card
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CardSlider() {

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
    ) {
        HorizontalPager(
            count = 3,
            state = pagerState,
            // Add 32.dp horizontal padding to 'center' the pages
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = Modifier
                .fillMaxWidth(),
        ) { page ->
            PagerSampleItem(
                banner = when (page) {
                    1 -> Card(R.drawable.banner1, "Aquela música que não tem idade")
                    2 -> Card(R.drawable.banner2, "Você já ouviu sua música favorita hoje?")
                    else -> Card(R.drawable.banner3, "Qual música não saí dos seus ouvidos")

                }
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = Color.White,
            inactiveColor = Color.LightGray,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
internal fun PagerSampleItem(
    banner: Card,
) {
    Image(painterResource(banner.image), banner.contentDescription, modifier = Modifier.padding(20.dp))
}