package com.example.llaveelectronica.presentation.screens.setupIntoScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.llaveelectronica.R
import com.example.llaveelectronica.ui.components.AddVehicle
import com.example.llaveelectronica.ui.components.AddVehicle
import com.example.llaveelectronica.ui.components.AppBackground
import com.example.llaveelectronica.ui.components.Authentication
import com.example.llaveelectronica.ui.components.Permissions
import com.example.llaveelectronica.ui.components.PersonalData
import com.example.llaveelectronica.ui.components.SelectTheme
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupIntroScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SetupIntoViewModel = viewModel()
) {

    var avance by remember { mutableIntStateOf(0) }
    var currentIndex by remember { mutableIntStateOf(0) }

    val screens = listOf<@Composable () -> Unit>(
        {
                Text(
                    text = "Vamos a configurar la aplicación",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 160.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
        },
        { SelectTheme() },
        { Permissions() },
        {
            Authentication(
                pinLength = 4,
                onPinComplete = { /* avanzar */ }
            )
        },
        { PersonalData() },
        {
            var marca by remember { mutableStateOf("Ktm") }
            AddVehicle(
            valorSeleccionado = marca,
            onValorSeleccionadoChange = {marca = it}
        ) }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 24.dp)
        ) {

            // Logo
            Image(
                painter = painterResource(R.mipmap.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Título principal
            Text(
                text = "Mobile Access Key",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height((16.dp)))

            val progress by viewModel.progress

            val animatedProgress by animateFloatAsState(
                targetValue = progress,
                animationSpec = tween(400),
                label = "ProgressAnim"
            )

            LinearProgressIndicator(
                progress = {animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(50)),
                color = MaterialTheme.colorScheme.scrim, //primaryContainer,
                trackColor = MaterialTheme.colorScheme.secondaryContainer,
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedContent(
                targetState = currentIndex,
                transitionSpec = {
                    slideInHorizontally { it } togetherWith
                            slideOutHorizontally { -it }
                },
                label = "slide"
            ) { index ->
                screens[index]()
            }
        }

        // Botón
        Button(
            onClick = {
                avance += 1
                viewModel.setProgress(avance)

                if (currentIndex < screens.lastIndex) {
                    currentIndex++
                }

                      }, //onStartClick,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 140.dp)
                .height(42.dp)
        ) {
            Text(text = "Comenzar", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Preview (
    showBackground = true
)
@Composable
fun ViewSetupIntroScreen(){
    LlaveElectronicaTheme{
        AppBackground {
            SetupIntroScreen(
                onClick = {}
            )
        }
    }
}