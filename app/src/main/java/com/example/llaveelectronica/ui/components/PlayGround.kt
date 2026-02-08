package com.example.llaveelectronica.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@Composable
fun HelloWorld(
    modifier: Modifier = Modifier
) {
    Text (
        //modifier = modifier,
        text = "Hello World"
    )
}

@Preview (
    showBackground = true
)
@Composable
fun HelloWorldPreview(){
    LlaveElectronicaTheme{
        HelloWorld()
    }
}