package nl.torquelink.presentation.language.mappings.dutch.sub

import nl.torquelink.presentation.language.interfaces.sub.Generic
import nl.torquelink.presentation.language.interfaces.sub.GroupDetails
import nl.torquelink.presentation.language.interfaces.sub.Login

object GroupDetailsMapping : GroupDetails {
    override val membersTitle: String = "Leden"
    override val eventsTitle: String = "Evenementen"
    //    Buttons
    override val joinGroupButton: String = "Lid worden"
    override val followGroupButton: String = "Volgen"
}