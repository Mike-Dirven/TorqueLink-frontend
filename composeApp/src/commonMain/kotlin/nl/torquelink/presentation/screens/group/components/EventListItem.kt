package nl.torquelink.presentation.screens.group.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nl.torquelink.shared.models.event.Events
import org.jetbrains.compose.resources.painterResource
import torquelink.composeapp.generated.resources.Res
import torquelink.composeapp.generated.resources.favorite
import torquelink.composeapp.generated.resources.groups

@Composable
fun EventListItem(
    event: Events.EventDto,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                modifier = Modifier.size(120.dp).background(MaterialTheme.colorScheme.background),
                model = event.eventImage,
                contentScale = ContentScale.FillBounds,
                contentDescription = ""
            )

            Column(
                modifier = Modifier.height(112.dp).padding(vertical = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.basicMarquee(),
                            text = event.eventTitle,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Row(
                            modifier = Modifier.padding(end = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(Res.drawable.groups),
                                contentDescription = ""
                            )
                            Text(
                                text = event.eventCapacity.toString(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Text(
                        text = event.eventLocation,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "${event.eventDateTime} - ${event.eventStatus}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}