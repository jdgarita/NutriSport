package dev.jdgarita.nutrisport.home.domain

import dev.jdgarita.nutrisport.shared.Resources
import org.jetbrains.compose.resources.DrawableResource

enum class DrawerItem(
    val title: String,
    val icon: DrawableResource
) {

    Profile(
        title = "Profile",
        icon = Resources.Icon.Person
    ),
    Blog(
        title = "Blog",
        icon = Resources.Icon.Book
    ),
    Locations(
        title = "Locations",
        icon = Resources.Icon.MapPin
    ),
    Contact(
        title = "Contact us",
        icon = Resources.Icon.Edit
    ),
    SignOut(
        title = "Sign out",
        icon = Resources.Icon.SignOut
    ),
    AdminPanel(
        title = "Admin Panel",
        icon = Resources.Icon.Unlock
    )
}