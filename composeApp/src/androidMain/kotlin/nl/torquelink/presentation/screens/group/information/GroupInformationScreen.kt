package nl.torquelink.presentation.screens.group.information

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nl.torquelink.domain.enums.BaseScreenTabs
import nl.torquelink.domain.window.WindowSize
import nl.torquelink.domain.window.getCurrentWindowSize
import nl.torquelink.presentation.language.interfaces.Language
import nl.torquelink.presentation.language.useLanguage
import nl.torquelink.presentation.screens.generic.BaseCompactScreenLayout
import nl.torquelink.presentation.screens.generic.components.LoadingIndicator
import nl.torquelink.presentation.screens.group.components.EventListItem
import nl.torquelink.presentation.theme.TorqueLinkTheme
import nl.torquelink.shared.enums.group.GroupMemberRole
import nl.torquelink.shared.enums.group.MemberListVisibility
import nl.torquelink.shared.models.group.Groups
import org.jetbrains.compose.resources.painterResource
import torquelink.composeapp.generated.resources.Res
import torquelink.composeapp.generated.resources.favorite
import torquelink.composeapp.generated.resources.groups
import torquelink.composeapp.generated.resources.no_profile_picture

@Composable
fun GroupInformationScreen(
    state: GroupInformationScreenState,
    onEvent: (GroupInformationScreenEvents) -> Unit,
    windowSize: WindowSize = getCurrentWindowSize(),
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    language: Language = useLanguage()
) {
    val scrollState = rememberScrollState()

    BaseCompactScreenLayout(
        modifier = modifier,
        snackBarHostState = snackBarHostState,
        activeTab = BaseScreenTabs.GROUPS,
        onTabSwitch = {
            onEvent(GroupInformationScreenEvents.OnTabSwitch(it))
        },
        profileAvatar = state.profile?.avatar?.let {
            { AsyncImage(
                modifier = Modifier.size(24.0.dp).clip(IconButtonDefaults.filledShape),
                model = it,
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            ) }
        }
    ) {
        when(state) {
            is GroupInformationScreenState.ErrorScreenState -> {

            }
            is GroupInformationScreenState.LoadingScreenState -> {
                LoadingIndicator()
            }
            is GroupInformationScreenState.ScreenStateWithData -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Box(
                        Modifier.then(
                            if(scrollState.value > 10){
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
                                    if(scrollState.value > 10){
                                        Modifier.height(256.dp)
                                    } else {
                                        Modifier.height(128.dp)
                                    }
                                )
                                .fillMaxWidth(),
                            model = state.groupData.coverPhotoUrl,
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
                                    text = state.groupData.groupName,
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
                                    text = state.groupData.members.count {
                                        it.role in listOf(GroupMemberRole.MEMBER, GroupMemberRole.ADMIN)
                                    } .toString(),
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(Res.drawable.favorite),
                                    contentDescription = ""
                                )
                                Text(
                                    text = state.groupData.members.count {
                                        it.role == GroupMemberRole.FOLLOWER
                                    } .toString(),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            enabled = state.groupData.members.none {
                                it.user.id == state.profile?.id
                                        && it.role >= GroupMemberRole.MEMBER
                            } && state.groupData.joinRequestsEnabled,
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
                            enabled = state.groupData.members.none {
                                it.user.id == state.profile?.id
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

                    state.groupData.description?.let { description ->
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                            text = description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        text = language.groupDetails.membersTitle,
                        style = MaterialTheme.typography.titleLarge
                    )

                    if(state.groupData.memberListVisibility == MemberListVisibility.VISIBLE) {
                        LazyVerticalGrid(
                            modifier = Modifier.requiredHeightIn(min = 50.dp, max = 300.dp),
                            columns = GridCells.Fixed(4),
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start)
                        ) {
                            items(state.groupData.members) { member ->
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

                    Text(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        text = language.groupDetails.eventsTitle,
                        style = MaterialTheme.typography.titleLarge
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(
                            items = state.groupData.events.sortedByDescending {
                                it.eventDateTime
                            }
                        ){ event ->
                            EventListItem(
                                event = event,
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(device = Devices.PIXEL_XL)
@Composable
fun GroupOverviewScreen() {
    TorqueLinkTheme {
        GroupInformationScreen(
            windowSize = WindowSize.Small(0,0),
            state = STATE_WITH_GROUP_DATA,
            onEvent = {},
            snackBarHostState = remember { SnackbarHostState() }
        )
    }
}

@Preview(device = Devices.PIXEL_TABLET)
@Composable
fun GroupInformationScreenTabletPreview() {
    TorqueLinkTheme {
        GroupInformationScreen(
            windowSize = WindowSize.Small(0,0),
            state = STATE_WITH_GROUP_DATA,
            onEvent = {},
            snackBarHostState = remember { SnackbarHostState() }
        )
    }
}

val STATE_WITH_GROUP_DATA = GroupInformationScreenState.ScreenStateWithData(
    profile = null,
    groupData = Groups.GroupWithDetailsDto(
        id = 23434,
        members = emptyList(),
        events = emptyList(),
        groupName = "Torquelink",
        description = "Test description",
        logoUrl = "https://autonxt.net/wp-content/uploads/2018/04/autocontentexp.com370z-drift_1200-f7d42d39ce1d4fb0551fc0a947576f88240181ad.jpg",
        coverPhotoUrl = "https://www.completecovergroup.com/wp-content/uploads/2019/01/homepage-banner-license-plate-3-compressed.jpg",
        privateGroup = false,
        joinRequestsEnabled = true,
        memberListVisibility = MemberListVisibility.VISIBLE,
        facebookUrl = "",
        instagramUrl = "",
        twitterUrl = "",
        linkedInUrl = "",
        websiteUrl = ""
    )
)