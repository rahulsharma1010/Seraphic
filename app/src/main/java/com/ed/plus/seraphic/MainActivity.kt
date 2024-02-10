package com.ed.plus.seraphic
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            PreviewCategoryScreen(navController)
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
                        .clickable(
//                                interactionSource = interactionSource,
//                                indication = null
                        ) {
                            navController.navigate("screen2")
                        },


                    )


            }
        })

}


data class Category(val categoryName: String, val subcategories: List<SubCategory>, var isCatChecked: Boolean)
data class SubCategory(val subCategoryName: String, var isSubChecked: Boolean)

@Composable
fun PreviewCategoryScreen(navController: NavHostController) {
    val categories = listOf(
        Category("All", emptyList(),false),
        Category("1 Room", listOf(
            SubCategory("Sub Category A",false),
        ),false),
        Category("2 Room", listOf(
            SubCategory("Sub Category B",false),
            SubCategory("Sub Category C",false),
            SubCategory("Sub Category D",false)
        ),false),
        Category("3 Room", listOf(
            SubCategory("Sub Category E",false),
            SubCategory("Sub Category F",false),
        ),false)
    )
//    CategoryScreen(categories,navController)
    CategoryScreen(categoryList = categories ,navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(categoryList: List<Category>,navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Filled.KeyboardArrowLeft, "backIcon",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                ),
            )
        },
        content = {
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
                        .padding(start = 14.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxHeight()
                        .padding(top = 10.dp),
                    content = {
                        categoryList.onEachIndexed { index, category ->
                            item {
                                var checkedCatState = remember { mutableStateOf(category.isCatChecked) }

                                // Update category's state based on subcategories
                                val allSubCategoriesChecked = category.subcategories.all { it.isSubChecked }
                                if (allSubCategoriesChecked != checkedCatState.value) {
                                    checkedCatState.value = allSubCategoriesChecked
                                }
                                Column {


                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                // Toggle category's state
                                                category.isCatChecked = !checkedCatState.value
                                                category.subcategories.forEach { it.isSubChecked = checkedCatState.value }

                                                Log.d("category", category.toString())

                                            }
                                    ) {
//                                            category.isCatChecked = checkedCatState.value
                                        Text(
                                            text = category.categoryName,
                                            color = Color.White,
                                            modifier = Modifier
                                                .padding(start = 14.dp)

                                        )
                                        CustomCheckbox(
                                            checked = checkedCatState.value,
                                        )

                                    }
                                    if (index == 0) {
                                        Divider(
                                            color = Color(0XFF252328), modifier = Modifier
                                                .padding(horizontal = 10.dp, vertical = 10.dp)
                                                .height(2.dp)
                                        )
                                    }
                                }

                            }

                            items(category.subcategories) { subCat ->

                                Column {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                subCat.isSubChecked = !subCat.isSubChecked

                                            }

                                    ) {

                                        Text(
                                            text = subCat.subCategoryName,
                                            color = Color.White,
                                            fontWeight = FontWeight(600),
                                            modifier = Modifier
                                                .padding(start = 24.dp)
                                        )

                                        CustomCheckbox(
                                            checked = subCat.isSubChecked,
                                        )


                                    }

                                    // Draw divider below specific categories
                                    if (categoryList[index].subcategories.last().subCategoryName == subCat.subCategoryName) {
                                        if (!(categoryList[categoryList.size - 1].subcategories.last().subCategoryName == subCat.subCategoryName)) {
                                            Divider(
                                                color = Color(0XFF252328), modifier = Modifier
                                                    .padding(
                                                        horizontal = 10.dp,
                                                        vertical = 10.dp
                                                    )
                                                    .height(2.dp)
                                            )
                                        }
                                    }
                                }


                            }
                        }
                    }
                )
            }
        })

}




@Composable
fun CustomCheckbox(checked: Boolean) {
    Icon(
        painter = if (checked) painterResource(R.drawable.baseline_done_24) else painterResource(R.drawable.baseline_done_24),
        contentDescription = "Custom Checkbox",
        tint = if (checked) Color(0XFF7D7DA5) else Color.Red
    )
}