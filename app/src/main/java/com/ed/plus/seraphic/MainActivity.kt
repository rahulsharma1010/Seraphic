package com.ed.plus.seraphic

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationComponent()
        }
    }
}

    @Composable
    fun NavigationComponent() {
        val navController = rememberNavController()

        NavHost(navController, startDestination = "screen1") {
            composable("screen1") {
                FilterScreen(navController)
            }
            composable("screen2") {
                PreviewCategoryScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FilterScreen(navController: NavHostController) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Filter")
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.ArrowBack, "backIcon")
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(0XFF808080),
                        titleContentColor = Color.White,
                    ),
                )
            }, content = {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .background(Color.White)
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Filters",
                        fontSize = 24.sp,
                        color = Color(0XFF555555),
                        fontWeight = FontWeight(400),
                        modifier = Modifier
                            .padding(26.dp, 40.dp, 0.dp, 0.dp)
                    )

                    OutlinedTextField(
                        value = " ",
                        onValueChange = {},
                        label = {
                            Text(
                                text = "Choose Locations",
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight(700)
                            )

                        },
                        enabled = false,
                        trailingIcon = {
                            Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Search")
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 14.dp)
                            .clickable {
                                navController.navigate("screen2")
                            },


                        )


                }
            })

}

data class Category(val name: String, val subcategories: List<Category> = listOf())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(categories: List<Category>) {
    var selectedCategories by remember { mutableStateOf(emptyList<Category>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.KeyboardArrowLeft, "backIcon",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                ),
            )
        }, content = {
//            customListView(LocalContext.current)
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Black)
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose Category",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight(500),
                modifier = Modifier
                    .padding(start = 10.dp)
            )

            LazyColumn {
                items(categories) { category ->
                    CategoryItem(category, selectedCategories) { clickedCategory ->
                        selectedCategories = updateSelection(clickedCategory, selectedCategories)
                    }
                }
            }
        }
    })

}

@Composable
fun CategoryItem(category: Category, selectedCategories: List<Category>, onItemClick: (Category) -> Unit) {
    val isSelected = selectedCategories.contains(category)

    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .clickable { onItemClick(category) }
    ) {

        if (category.name in listOf("All", "1 Room", "2 Room" , "And So on....")) {
            Text(text = category.name,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 16.dp ))
        }else {
            Text(text = category.name,
                color = Color.White,
                fontWeight = FontWeight(600),
                modifier = Modifier
                    .padding(start = 24.dp))
        }

        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(if (isSelected) R.drawable.baseline_done_24 else R.drawable.ic_launcher_foreground),
            contentDescription = if (isSelected) "Selected" else "Unselected",
            modifier = Modifier.size(24.dp)
        )
    }

//    if (isSelected) {
        category.subcategories.forEach { subcategory ->
            CategoryItem(subcategory, selectedCategories, onItemClick)
//        }
    }
    // Draw divider below specific categories
    if (category.name in listOf("All", "Sub Category C", "Sub Category F")) {
        Divider(color = Color(0XFF252328), modifier = Modifier
            .padding(top = 10.dp, start = 10.dp))
    }
}

fun updateSelection(clickedCategory: Category, selectedCategories: List<Category>): List<Category> {
    val updatedSelection = mutableListOf<Category>()
    if (selectedCategories.contains(clickedCategory)) {
        updatedSelection.addAll(selectedCategories.filterNot { it == clickedCategory })
    } else {
        updatedSelection.addAll(selectedCategories)
        updatedSelection.add(clickedCategory)
    }
    clickedCategory.subcategories.forEach { subcategory ->
        if (!updatedSelection.contains(subcategory)) {
            updatedSelection.add(subcategory)
        }
    }
    return updatedSelection
}

@Preview
@Composable
fun PreviewCategoryScreen() {
    val categories = listOf(
        Category("All"),
        Category("1 Room", listOf(
            Category("Sub Category A"),
            Category("Sub Category B"),
            Category("Sub Category C"),
        )),
        Category("2 Room", listOf(
            Category("Sub Category D"),
            Category("Sub Category E"),
            Category("Sub Category F"),
        )),
        Category("And So on....")
    )
    CategoryScreen(categories)
}
