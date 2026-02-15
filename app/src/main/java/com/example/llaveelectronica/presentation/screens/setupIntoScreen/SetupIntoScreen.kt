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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.llaveelectronica.R
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
    //modifier: Modifier = Modifier,
    viewModel: SetupIntoViewModel
) {
    val setupIntoScreenViewModel by viewModel.setupIntoState

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
                modifier = Modifier.size(128.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceDim)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Título principal
            Text(
                text = "Mobile Access Key",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.surfaceDim,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height((16.dp)))

            val animatedProgress by animateFloatAsState(
                targetValue = setupIntoScreenViewModel.progress,
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
                targetState = setupIntoScreenViewModel.currentStep,
                transitionSpec = {
                    slideInHorizontally { it } togetherWith
                            slideOutHorizontally { -it }
                },
                label = "slide"
            ) { step ->

                when (step) {
                    SetupStep.Welcome -> {
                        Text(
                            text = "Vamos a configurar la aplicación",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 160.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    SetupStep.Theme -> SelectTheme(viewModel)

                    SetupStep.Permissions -> Permissions()

                    SetupStep.Pin -> Authentication(
                        pinLength = 4,
                        onPinComplete = { /* viewModel.onPinChange(it) */ }
                    )

                    SetupStep.PersonalData -> PersonalData()

                    SetupStep.Vehicle -> AddVehicle(
                        valorSeleccionado = setupIntoScreenViewModel.marca,
                        onValorSeleccionadoChange = { /* viewModel.onMarcaChange(it) */ }
                    )

                    SetupStep.Completed -> Text(
                        text = "Configuración completada",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 160.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge)
                }
            }
        }

        // Botón
        Button(
            onClick = { viewModel.onNextClicked() },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 140.dp)
                .height(42.dp)
        ) {
            Text(text = "Continuar", color = Color.White, fontSize = 16.sp)
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
                onClick = {},
                viewModel = viewModel(),
            )
        }
    }
}