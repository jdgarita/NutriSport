package dev.jdgarita.nutrisport.home


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.jdgarita.nutrisport.home.component.BottomBar
import dev.jdgarita.nutrisport.home.component.CustomDrawer
import dev.jdgarita.nutrisport.home.domain.BottomBarDestination
import dev.jdgarita.nutrisport.home.domain.CustomDrawerState
import dev.jdgarita.nutrisport.home.domain.opposite
import dev.jdgarita.nutrisport.shared.BebasNeueFont
import dev.jdgarita.nutrisport.shared.FontSize
import dev.jdgarita.nutrisport.shared.IconPrimary
import dev.jdgarita.nutrisport.shared.Resources
import dev.jdgarita.nutrisport.shared.Surface
import dev.jdgarita.nutrisport.shared.SurfaceLighter
import dev.jdgarita.nutrisport.shared.TextPrimary
import dev.jdgarita.nutrisport.shared.navigation.Screen
import dev.jdgarita.nutrisport.shared.util.getScreenWidth
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeGraphScreen() {
    val navigationController = rememberNavController()
    val currentRoute = navigationController.currentBackStackEntryAsState()

    val screenWidth = remember { getScreenWidth() }
    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
    val offsetValue by remember { derivedStateOf { (screenWidth / 1.5).dp } }

    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState == CustomDrawerState.Opened) offsetValue else 0.dp
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (drawerState == CustomDrawerState.Opened) 0.9f else 1f
    )

    val animatedRadius by animateDpAsState(
        targetValue = if (drawerState == CustomDrawerState.Opened) 20.dp else 0.dp
    )

    val selectedDestination by remember {
        derivedStateOf {
            val route = currentRoute.value?.destination?.route.toString()
            when {
                route.contains(BottomBarDestination.ProductOverView.screen.toString()) -> BottomBarDestination.ProductOverView
                route.contains(BottomBarDestination.Cart.screen.toString()) -> BottomBarDestination.Cart
                route.contains(BottomBarDestination.Categories.screen.toString()) -> BottomBarDestination.Categories
                else -> BottomBarDestination.ProductOverView
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceLighter)
            .systemBarsPadding(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        CustomDrawer(
            onProfileClick = {},
            onContactUsClick = {},
            onSignOutClick = {},
            onAdminPanelClick = {}
        )
        Box(
            modifier = Modifier.fillMaxSize()
                .clip(RoundedCornerShape(size = animatedRadius))
                .offset(x = animatedOffset)
                .scale(animatedScale)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(size = animatedRadius)
                )
        ) {
            Scaffold(
                containerColor = Surface,
                topBar = {
                    CenterAlignedTopAppBar(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        title = {
                            AnimatedContent(
                                targetState = selectedDestination,
                                label = "Top Bar Title"
                            ) { destination ->
                                Text(
                                    text = destination.title,
                                    fontFamily = BebasNeueFont(),
                                    fontSize = FontSize.LARGE,
                                    color = TextPrimary
                                )
                            }
                        },
                        navigationIcon = {
                            AnimatedContent(
                                targetState = drawerState,
                                label = "Drawer Icon"
                            ) { state ->
                                when (state) {
                                    CustomDrawerState.Opened -> IconButton(onClick = {
                                        drawerState = drawerState.opposite()
                                    }) {
                                        Icon(
                                            painter = painterResource(Resources.Icon.Close),
                                            contentDescription = "Close Drawer Icon",
                                            tint = IconPrimary
                                        )
                                    }

                                    CustomDrawerState.Closed -> IconButton(onClick = {
                                        drawerState = drawerState.opposite()
                                    }) {
                                        Icon(
                                            painter = painterResource(Resources.Icon.Menu),
                                            contentDescription = "Open Drawer Icon",
                                            tint = IconPrimary
                                        )
                                    }
                                }
                            }

                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Surface,
                            scrolledContainerColor = Surface,
                            titleContentColor = TextPrimary,
                            navigationIconContentColor = IconPrimary,
                            actionIconContentColor = IconPrimary
                        )
                    )
                },
            ) { padding ->
                Column(
                    modifier = Modifier.fillMaxSize().padding(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                    )
                ) {
                    NavHost(
                        modifier = Modifier.weight(1f),
                        navController = navigationController,
                        startDestination = Screen.ProductOverview
                    ) {
                        composable<Screen.ProductOverview> {}
                        composable<Screen.Cart> {}
                        composable<Screen.Categories> {}
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier.padding(all = 12.dp)
                    ) {
                        BottomBar(
                            selected = selectedDestination,
                            onSelect = { destination ->
                                navigationController.navigate(destination.screen) {
                                    launchSingleTop = true
                                    popUpTo(Screen.ProductOverview) {
                                        saveState = true
                                        inclusive = false
                                    }
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}