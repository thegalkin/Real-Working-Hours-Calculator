package com.example.realworktimecalculator

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.realworktimecalculator.ui.theme.RealWorkTimeCalculatorTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealWorkTimeCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    mainView()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun mainView() {
    var text by remember { mutableStateOf(TextFieldValue()) }
    var result by remember { mutableStateOf(TextFieldValue()) }
    var multiplier by remember { mutableStateOf(TextFieldValue(text =  "2")) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        Handler(Looper.getMainLooper()).postDelayed({
            keyboard?.show()
        }, 200)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = text,
            onValueChange = {
                if (it.text.isDigitsOnly()) {
                    text = it
                }
            },
            label = { Text("Введите число") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier.padding(bottom = 20.dp).focusRequester(focusRequester),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboard?.hide()
                    result = TextFieldValue((text.text.toInt() * multiplier.text.toInt()).toString())
                }
            ),
        )
        TextField(
            value = multiplier,
            onValueChange = {
                if (it.text.isDigitsOnly()) {
                    multiplier = it
                }
                result = TextFieldValue((text.text.toInt() * multiplier.text.toInt()).toString())
            },
            label = { Text("Введите мультипликатор") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Button(
            onClick = {
                result = TextFieldValue((text.text.toInt() * multiplier.text.toInt()).toString())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(48.dp)

        ) {
            Text("Рассчитать")
        }
        Text(text = result.text, modifier = Modifier.padding(top = 20.dp))
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RealWorkTimeCalculatorTheme {
        mainView()
    }
}