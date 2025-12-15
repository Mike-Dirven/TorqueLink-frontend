package nl.torquelink.presentation.language.interfaces

import nl.torquelink.presentation.language.enums.Languages
import nl.torquelink.presentation.language.interfaces.sub.Generic
import nl.torquelink.presentation.language.interfaces.sub.GroupDetails
import nl.torquelink.presentation.language.interfaces.sub.Login
import nl.torquelink.presentation.language.interfaces.sub.Profile
import nl.torquelink.presentation.language.interfaces.sub.Register
import nl.torquelink.presentation.language.interfaces.sub.ResetPassword

interface Language {
    val locale: Languages
    val generic: Generic
    val login: Login
    val register: Register
    val resetPassword: ResetPassword
    val profile: Profile
    val groupDetails: GroupDetails
}