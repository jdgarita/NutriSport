package dev.jdgarita.nutrisport.home.domain

import dev.jdgarita.nutrisport.shared.Resources
import dev.jdgarita.nutrisport.shared.navigation.Screen
import org.jetbrains.compose.resources.DrawableResource

enum class BottomBarDestination(
    val icon: DrawableResource,
    val title: String,
    val screen: Screen
) {
    ProductOverView(
        icon = Resources.Icon.Home,
        title = "Nutri Sport",
        screen = Screen.ProductOverview
    ),
    Cart(
        icon = Resources.Icon.ShoppingCart,
        title = "Cart",
        screen = Screen.Cart
    ),
    Categories(
        icon = Resources.Icon.Categories,
        title = "Categories",
        screen = Screen.Categories
    )
}