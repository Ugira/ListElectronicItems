package com.ugira.ampersandChallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ugira.ampersandChallenge.network.ApiClient
import com.ugira.ampersandChallenge.structure.ElectronicItem
import com.ugira.ampersandChallenge.ui.theme.LightBlue
import com.ugira.ampersandChallenge.ui.theme.RecyclerViewJCYTTTheme
import kotlinx.coroutines.*
import org.json.JSONArray
import com.ugira.ampersandChallenge.CustomDialog as CustomDialog1

import com.ugira.ampersandChallenge.getObjectFromService as getObjectFromService1

class MainActivity : ComponentActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RecyclerViewJCYTTTheme {

                println("Getting api items . . . ")
                val myItems = getObjectFromService1()

                RecyclerViewItems(myItems)

            }
        }
    }
}

fun getDataManuallyForToSee(): List<ElectronicItem>? {
    val data =
        "[{\"id\":\"1\",\"name\":\"Google Pixel 6 Pro\",\"data\":{\"color\":\"Cloudy White\",\"capacity\":\"128 GB\"}},{\"id\":\"2\",\"name\":\"Apple iPhone 12 Mini, 256GB, Blue\",\"data\":null},{\"id\":\"3\",\"name\":\"Apple iPhone 12 Pro Max\",\"data\":{\"color\":\"Cloudy White\",\"capacity GB\":512}},{\"id\":\"4\",\"name\":\"Apple iPhone 11, 64GB\",\"data\":{\"price\":389.99,\"color\":\"Purple\"}},{\"id\":\"5\",\"name\":\"Samsung Galaxy Z Fold2\",\"data\":{\"price\":689.99,\"color\":\"Brown\"}},{\"id\":\"6\",\"name\":\"Apple AirPods\",\"data\":{\"generation\":\"3rd\",\"price\":120}},{\"id\":\"7\",\"name\":\"Apple MacBook Pro 16\",\"data\":{\"year\":2019,\"price\":1849.99,\"CPU model\":\"Intel Core i9\",\"Hard disk size\":\"1 TB\"}},{\"id\":\"8\",\"name\":\"Apple Watch Series 8\",\"data\":{\"Strap Colour\":\"Elderberry\",\"Case Size\":\"41mm\"}},{\"id\":\"9\",\"name\":\"Beats Studio3 Wireless\",\"data\":{\"Color\":\"Red\",\"Description\":\"High-performance wireless noise cancelling headphones\"}},{\"id\":\"10\",\"name\":\"Apple iPad Mini 5th Gen\",\"data\":{\"Capacity\":\"64 GB\",\"Screen size\":7.9}},{\"id\":\"11\",\"name\":\"Apple iPad Mini 5th Gen\",\"data\":{\"Capacity\":\"254 GB\",\"Screen size\":7.9}},{\"id\":\"12\",\"name\":\"Apple iPad Air\",\"data\":{\"Generation\":\"4th\",\"Price\":\"419.99\",\"Capacity\":\"64 GB\"}},{\"id\":\"13\",\"name\":\"Apple iPad Air\",\"data\":{\"Generation\":\"4th\",\"Price\":\"519.99\",\"Capacity\":\"256 GB\"}}]"
    return ApiClient.manualObjects(data)
}

fun getObjectFromService(): List<ElectronicItem> {
    var myItems = emptyList<ElectronicItem>();
    runBlocking { // this: CoroutineScope
        launch {
            val asyncJob = async {

                println("Getting api items . . . ")
                myItems = ApiClient.getObjects();
                println("...............33434333333333333.........My items: " + myItems.size)

            }
            asyncJob.await()
            println("...............33434333333333333.........My items: " + myItems.size)

        }
    }
    println("........................My items: " + myItems.size)
    return myItems;
}

@Composable
fun ListItem(item: ElectronicItem,onItemClick: (ElectronicItem) -> Unit) {
//    var item:ElectronicItem?=(item1 as? ElectronicItem)
//    if(item == null) item=  ElectronicItem(0,"NA", JSONArray() )
    val expanded = remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded.value) 24.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = LightBlue,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp))
            .clickable { onItemClick(item) }
    ) {

        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()

        ) {

            Row (
//                modifier= Modifier.clip(RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp))

                    ) {

//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
//                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .align(alignment = Alignment.CenterVertically),
                        textAlign = TextAlign.Center,
                        text = "" + item.id)
                    Text(
                        modifier = Modifier.weight(6f),
                        text = item.title, style = MaterialTheme.typography.h5 .copy(
                        fontWeight = FontWeight.SemiBold
                        )
                    )
//                }

                OutlinedButton(
                    modifier = Modifier
                        .align(alignment = Alignment.Bottom)
                        .alignByBaseline()
                        .weight(2f)
                        .background(
                            color = LightBlue,
                            shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)
                        ),

                    onClick = { expanded.value = !expanded.value }) {
                    Text(
                    text=if (expanded.value) "Show less" else "Show more")
                }
            }
            if (expanded.value) {

                Column(
                    modifier = Modifier.padding(
                        bottom = extraPadding.coerceAtLeast(0.dp)
                    )
                ) {
                    Text(text = getListedSpec(item.spec),
                    modifier = Modifier.padding(start = 40.dp)

                    )
//                    Text(text = item.spec)

                }

            }
        }

    }


}

@Composable
fun ListItemDetails(key: String, value:String) {
//    var item:ElectronicItem?=(item1 as? ElectronicItem)
//    if(item == null) item=  ElectronicItem(0,"NA", JSONArray() )

    Surface(
        color = LightBlue,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp))

    ) {

        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()

        ) {

            Row (
//                modifier= Modifier.clip(RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp))

            ) {

//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
//                ) {

                Text(
                    modifier = Modifier.weight(6f),
                    text = key, style = MaterialTheme.typography.h5 .copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .align(alignment = Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    text = value )
//                }
            }
        }

    }


}

fun getListedSpec(spec: Map<String, Any>?): String {
    return if (spec != null && spec.isNotEmpty()) displayMap(spec)
    else "no specification found"

}

fun displayMap(map: Map<String, Any>): String {
    val stringBuilder = StringBuilder()
    for ((key, value) in map) {
        stringBuilder.append("$key: $value\n")
    }
    return stringBuilder.toString()
}


@Composable
fun RecyclerViewItems(names: List<ElectronicItem>) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
//        MyAppBar(title = context.title.toString())
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Electronic Items",
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.SemiBold
            ),


            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp) // Rounded corners
                )
                .padding(start = 40.dp, end = 40.dp, top = 5.dp, bottom = 5.dp)

        )
        // Add a Spacer with 30 dp height
        Spacer(modifier = Modifier.height(20.dp))
        // State to manage dialog visibility
        val showDialog = remember { mutableStateOf(false) }
        val selectedElectronicItem = remember { mutableStateOf<ElectronicItem?>(null) }
//        var selectedItem= ElectronicItem(0,"NA", emptyMap());

        LazyColumn(modifier = Modifier.padding(vertical = 5.dp)) {

            items(items = names) { electronicItem ->

                ListItem(item = electronicItem
                ){ clickedItem ->
                    // Update the selected item
                    selectedElectronicItem.value = clickedItem
                    // Set showDialog to true to display the dialog
                    showDialog.value = true
                }


            }

        }
            // Display dialog if showDialog is true
        val selectedItem= selectedElectronicItem.value;
        println("Clicked item .....${selectedItem.toString()} ")

        if(selectedItem == null || (selectedItem.spec == null || selectedItem.spec.isEmpty())) {
            if (showDialog.value ) {
                CustomDialog1(
                    value = selectedItem?.title ?: "ITEM",
                    message = "The item Displayed below has no specifications",
                    setShowDialog = { showDialog.value = it },
                    setValue = { selectedElectronicItem.value!!.title + " Missing specification" }
                )
            }
        }else{
            println("Ndahagera . . . . .")
              val mySpec=selectedItem.spec;
            println("Displa......y: "+ displayMap(selectedItem.spec))
//          todo to revive next codes if display view works

//            selectedElectronicItem.value?.let { details ->
//                details.spec?.let { RecyclerViewItemsDetails(detailsMap = it) }
//            }
            if (showDialog.value ) {
                CustomDialog1(
                    value = selectedItem.title ?: "ITEM",
                    message = "Did you know that view more button can allow you to view details?",
                    setShowDialog = { showDialog.value = it },
                    setValue = { displayMap(selectedItem.spec)  }

                )
            }

        }


    }

}


@Composable
fun RecyclerViewItemsDetails(detailsMap: Map<String, Any>){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
//        MyAppBar(title = context.title.toString())
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Specification",
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.SemiBold
            ),


            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp) // Rounded corners
                )
                .padding(start = 40.dp, end = 40.dp, top = 5.dp, bottom = 5.dp)

        )
        // Add a Spacer with 30 dp height
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(modifier = Modifier.padding(vertical = 5.dp)) {

            items(items = detailsMap.entries.toList()) { (key, value) ->

                ListItemDetails(key=key, value="$value")



            }

        }
    }

}
@Composable
fun handleItemClick(item: ElectronicItem) {
    //  change the recycler view  to detail view, update state, etc.
    if(item.spec != null) RecyclerViewItemsDetails(item.spec)
    else displayDialog(title = "Information", message = "Specification not found")

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RecyclerViewJCYTTTheme {
        val data =
            "[{\"id\":\"1\",\"name\":\"Google Pixel 6 Pro\",\"data\":{\"color\":\"Cloudy White\",\"capacity\":\"128 GB\"}},{\"id\":\"2\",\"name\":\"Apple iPhone 12 Mini, 256GB, Blue\",\"data\":null},{\"id\":\"3\",\"name\":\"Apple iPhone 12 Pro Max\",\"data\":{\"color\":\"Cloudy White\",\"capacity GB\":512}},{\"id\":\"4\",\"name\":\"Apple iPhone 11, 64GB\",\"data\":{\"price\":389.99,\"color\":\"Purple\"}},{\"id\":\"5\",\"name\":\"Samsung Galaxy Z Fold2\",\"data\":{\"price\":689.99,\"color\":\"Brown\"}},{\"id\":\"6\",\"name\":\"Apple AirPods\",\"data\":{\"generation\":\"3rd\",\"price\":120}},{\"id\":\"7\",\"name\":\"Apple MacBook Pro 16\",\"data\":{\"year\":2019,\"price\":1849.99,\"CPU model\":\"Intel Core i9\",\"Hard disk size\":\"1 TB\"}},{\"id\":\"8\",\"name\":\"Apple Watch Series 8\",\"data\":{\"Strap Colour\":\"Elderberry\",\"Case Size\":\"41mm\"}},{\"id\":\"9\",\"name\":\"Beats Studio3 Wireless\",\"data\":{\"Color\":\"Red\",\"Description\":\"High-performance wireless noise cancelling headphones\"}},{\"id\":\"10\",\"name\":\"Apple iPad Mini 5th Gen\",\"data\":{\"Capacity\":\"64 GB\",\"Screen size\":7.9}},{\"id\":\"11\",\"name\":\"Apple iPad Mini 5th Gen\",\"data\":{\"Capacity\":\"254 GB\",\"Screen size\":7.9}},{\"id\":\"12\",\"name\":\"Apple iPad Air\",\"data\":{\"Generation\":\"4th\",\"Price\":\"419.99\",\"Capacity\":\"64 GB\"}},{\"id\":\"13\",\"name\":\"Apple iPad Air\",\"data\":{\"Generation\":\"4th\",\"Price\":\"519.99\",\"Capacity\":\"256 GB\"}}]"
        var myItems: List<ElectronicItem>? = ApiClient.manualObjects(data)
        if (myItems == null) myItems = emptyList();

        RecyclerViewItems(myItems)
    }
}
@Composable
fun displayDialog(title : String,message: String) {
    // State to control dialog visibility
    val showDialog = remember { mutableStateOf(false) }
    // State to hold the value set by dialog
    val dialogValue = remember { mutableStateOf("") }

    Column {
        Button(onClick = { showDialog.value = true }) {
            Text(text = message)
        }

        // Display the dialog when showDialog is true
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = {
                    Text(text = title)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Handle dialog confirmation
                            showDialog.value = false
                        }
                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            // Handle dialog dismissal
                            showDialog.value = false
                        }
                    ) {
                        Text(text = "Cancel")
                    }
                },
                // Content of the dialog
                text = {
                    CustomDialog1(
                        value = dialogValue.value,
                        message = "Message dutanga",
                        setShowDialog = { showDialog.value = it },
                        setValue = { dialogValue.value = it }
                    )
                }
            )
        }
    }
}
