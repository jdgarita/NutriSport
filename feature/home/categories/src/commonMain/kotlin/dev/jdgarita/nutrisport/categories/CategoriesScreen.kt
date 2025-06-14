package dev.jdgarita.nutrisport.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jdgarita.nutrisport.categories.component.CategoryCard
import dev.jdgarita.nutrisport.shared.domain.ProductCategory

@Composable
fun CategoriesScreen(
    navigateToCategorySearch: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProductCategory.entries.forEach { category ->
            CategoryCard(
                category = category,
                onClick = { navigateToCategorySearch(category.name) }
            )
        }
    }
}