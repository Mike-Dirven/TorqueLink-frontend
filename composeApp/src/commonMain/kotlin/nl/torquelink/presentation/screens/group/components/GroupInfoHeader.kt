package nl.torquelink.presentation.screens.group.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nl.torquelink.presentation.language.interfaces.Language
import nl.torquelink.presentation.language.interfaces.sub.Profile
import nl.torquelink.presentation.language.useLanguage
import nl.torquelink.shared.enums.group.GroupMemberRole
import nl.torquelink.shared.models.group.Groups
import nl.torquelink.shared.models.profile.UserProfiles
import org.jetbrains.compose.resources.painterResource
import torquelink.composeapp.generated.resources.Res
import torquelink.composeapp.generated.resources.favorite
import torquelink.composeapp.generated.resources.groups

@Composable
fun GroupInfoHeader(
    group: Groups.GroupWithDetailsDto,
    hasScrolled: Boolean,
    currentProfile: UserProfiles.UserProfileWithSettingsDto? = null,
    language: Language = useLanguage(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(
            targetState = hasScrolled
        ){
            Box(
                Modifier.then(
                    if (!hasScrolled) {
                        Modifier.height(256.dp)
                    } else {
                        Modifier.height(128.dp)
                    }
                ).fillMaxWidth(),
                contentAlignment = Alignment.BottomStart
            ) {
                AsyncImage(
                    modifier = Modifier
                        .then(
                            if (!hasScrolled) {
                                Modifier.height(256.dp)
                            } else {
                                Modifier.height(128.dp)
                            }
                        )
                        .fillMaxWidth(),
                    model = group.coverPhotoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.background.copy(alpha = 0.6f)
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = group.groupName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(Res.drawable.groups),
                            contentDescription = ""
                        )
                        Text(
                            text = group.members.count {
                                it.role in listOf(GroupMemberRole.MEMBER, GroupMemberRole.ADMIN)
                            }.toString(),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(Res.drawable.favorite),
                            contentDescription = ""
                        )
                        Text(
                            text = group.members.count {
                                it.role == GroupMemberRole.FOLLOWER
                            }.toString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                enabled = group.members.none {
                    it.user.id == currentProfile?.id
                            && it.role >= GroupMemberRole.MEMBER
                } && group.joinRequestsEnabled,
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Text(
                    text = language.groupDetails.joinGroupButton,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Button(
                enabled = group.members.none {
                    it.user.id == currentProfile?.id
                },
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Text(
                    text = language.groupDetails.followGroupButton,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}