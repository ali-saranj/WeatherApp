package com.kasra.weather.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kasra.weather.R
import com.kasra.weather.data.model.CityInfo

@Composable
fun CardWeather(
    weatherInfo: CityInfo,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                IconButton(onClick = onClose) {
                    Icon(
                        modifier = Modifier.size(44.dp),
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        tint = Color(0xFF2E3440)
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.End,
                    text = weatherInfo.today,
                    fontSize = 18.sp,
                    color = Color(0xFF2E3440)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = weatherInfo.city,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E3440)
            )
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                modifier = Modifier.size(140.dp),
                model = weatherInfo.iconWeather,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.load),
                contentDescription = weatherInfo.description
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${weatherInfo.temperature}°C",
                fontSize = 50.sp,
                color = Color(0xFF2E3440)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = weatherInfo.description,
                fontSize = 20.sp,
                color = Color(0xFF2E3440)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row() {
                Text(
                    text = "Max: ${weatherInfo.maxTemperature}°C",
                    fontSize = 20.sp,
                    color = Color(0xFF2E3440)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Min: ${weatherInfo.minTemperature}°C",
                    fontSize = 20.sp,
                    color = Color(0xFF2E3440),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
            ) {
                Text(
                    text = "Wind: ${weatherInfo.windSpeed} m/s",
                    fontSize = 20.sp,
                    color = Color(0xFF2E3440)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.wind_icon),
                    contentDescription = "wind speed",
                    tint = Color(0xFF2E3440)
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
            ) {
                Text(
                    text = "Humidity: ${weatherInfo.humidity}%",
                    fontSize = 20.sp,
                    color = Color(0xFF2E3440)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.humidity),
                    contentDescription = "wind speed",
                    tint = Color(0xFF2E3440)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }


    }
}

@Preview(name = "CardWeather")
@Composable
private fun PreviewCardWeather() {
    CardWeather(
        weatherInfo = CityInfo(
            latitude = 1.0,
            longitude = 1.0,
            temperature = 1.0,
            maxTemperature = 1.0,
            minTemperature = 1.0,
            windSpeed = 1.0,
            description = "test",
            iconWeather = "test",
            today = "2024-01-01 ",
            city = "test",
            humidity = 15
        ),
        backgroundColor = Color.White,
        onClose = {}
    )

}