package nl.torquelink.presentation.screens.group.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nl.torquelink.shared.enums.group.MemberListVisibility
import nl.torquelink.shared.models.group.Groups
import nl.torquelink.shared.models.profile.UserProfiles
import org.jetbrains.compose.resources.painterResource
import torquelink.composeapp.generated.resources.Res
import torquelink.composeapp.generated.resources.no_profile_picture

@Composable
fun GroupInfoMembers(
    currentProfile: UserProfiles.UserProfileWithSettingsDto? = null,
    group: Groups.GroupWithDetailsDto
) {
    val stopIndexOfRow = if(group.members.size < 10) group.members.size else 10

    if(group.memberListVisibility == MemberListVisibility.VISIBLE) {
        LazyRow (
            modifier = Modifier,
            contentPadding = PaddingValues(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start)
        ) {
            items(group.members.subList(0, stopIndexOfRow)) { member ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
                ) {
                    if(member.user.avatar.isBlank()) {
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(Res.drawable.no_profile_picture),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth
                        )
                    } else {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth(),
                            model = member.user.avatar,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                    }

                    Text(
                        text = "${member.user.firstName} ${member.user.lastName}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}