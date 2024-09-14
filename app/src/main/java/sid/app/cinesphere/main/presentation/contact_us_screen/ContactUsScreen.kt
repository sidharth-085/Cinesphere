package sid.app.cinesphere.main.presentation.contact_us_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sid.app.cinesphere.R

@Composable
fun ContactUs() {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.contact_us),
            contentDescription = "Contact us Image",
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )

        Text(
            text = "Contact Us",
            fontSize = 35.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.semantics {
                contentDescription = "Info text"
            }
        )

        Text(
            text = "We value your feedback! Reach out with questions or suggestions through our Contact Us page, and let us enhance your app experience together.",
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .padding(37.dp, 20.dp, 37.dp, 20.dp)
                .semantics {
                    contentDescription = "Info Data"
                },
            textAlign = TextAlign.Justify,
        )

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.email_logo),
                contentDescription = "email icon",
                modifier = Modifier
                    .size(22.dp),
                alignment = Alignment.BottomCenter,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "sidharthkapoor085@gmail.com",
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ContactUsPreview() {
    ContactUs()
}