package nl.torquelink.presentation.language.mappings.dutch

import nl.torquelink.presentation.language.enums.Languages
import nl.torquelink.presentation.language.interfaces.Language
import nl.torquelink.presentation.language.interfaces.sub.Generic
import nl.torquelink.presentation.language.interfaces.sub.GroupDetails
import nl.torquelink.presentation.language.interfaces.sub.Login
import nl.torquelink.presentation.language.interfaces.sub.Profile
import nl.torquelink.presentation.language.interfaces.sub.Register
import nl.torquelink.presentation.language.interfaces.sub.ResetPassword
import nl.torquelink.presentation.language.mappings.dutch.sub.GenericMapping
import nl.torquelink.presentation.language.mappings.dutch.sub.GroupDetailsMapping
import nl.torquelink.presentation.language.mappings.dutch.sub.LoginMapping
import nl.torquelink.presentation.language.mappings.dutch.sub.ProfileMapping
import nl.torquelink.presentation.language.mappings.dutch.sub.RegisterMapping
import nl.torquelink.presentation.language.mappings.dutch.sub.ResetPasswordMapping

object DutchMapping : Language {
    override val locale: Languages = Languages.NL

    override val generic: Generic by lazy { GenericMapping }
    override val login: Login by lazy { LoginMapping }
    override val register: Register by lazy { RegisterMapping }
    override val resetPassword: ResetPassword by lazy { ResetPasswordMapping }
    override val profile: Profile by lazy { ProfileMapping }
    override val groupDetails: GroupDetails by lazy { GroupDetailsMapping }
}