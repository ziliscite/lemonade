package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(1.0f, 0.922f, 0.231f, 1.0f),
                                titleContentColor = Color(0.0f, 0.0f, 0.0f, 1.0f),
                            ),
                            title = {
                                LemonAppHeader()
                            },
                            modifier = Modifier.height(150.dp)
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                        LemonApp(
                            modifier = Modifier.padding(innerPadding)
                        )
                }
            }
        }
    }
}

enum class LemonadeStepEnum(
    val stepText: Int, val imageId: Int,
    val imageDescription: String
) {
    StepOne(R.string.step_one, R.drawable.lemon_tree, "Lemon Tree"),
    StepTwo(R.string.step_two, R.drawable.lemon_squeeze, "Lemon"),
    StepThree(R.string.step_three, R.drawable.lemon_drink, "Lemonade"),
    StepFour(R.string.step_four, R.drawable.lemon_restart, "Empty Glass")
}

enum class LemonadeStep(val step: LemonadeStepEnum) {
    StepOne(LemonadeStepEnum.StepOne),
    StepTwo(LemonadeStepEnum.StepTwo),
    StepThree(LemonadeStepEnum.StepThree),
    StepFour(LemonadeStepEnum.StepFour);

    private lateinit var _nextStep: LemonadeStep
    val nextStep: LemonadeStep
        get() = _nextStep

    companion object {
        init {
            StepOne._nextStep = StepTwo
            StepTwo._nextStep = StepThree
            StepThree._nextStep = StepFour
            StepFour._nextStep = StepOne
        }
    }
}

@Composable
fun LemonAppHeader() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Lemonade",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .offset(y = (36).dp)
        )
    }
}

@Composable
fun LemonAppContent() {
    var lemonadeStep: LemonadeStep by remember { mutableStateOf(LemonadeStep.StepOne) }
    var clickCount: Int by remember { mutableIntStateOf(0) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .offset(y = (-20).dp)
    ) {
        Image(
            painter = painterResource(id = lemonadeStep.step.imageId),
            contentDescription = lemonadeStep.step.imageDescription,
            modifier = Modifier.size(300.dp).clickable(
                onClick = {
                    if (lemonadeStep.step == LemonadeStepEnum.StepTwo && clickCount < (2..5).random()) {
                        clickCount++
                    } else {
                        lemonadeStep = lemonadeStep.nextStep
                        clickCount = 0
                    }
                }
            )
        )
        Text(
            text = stringResource(lemonadeStep.step.stepText),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Composable
fun LemonApp(modifier: Modifier = Modifier) {
    Surface (
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            // LemonAppHeader()
            LemonAppContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonApp()
    }
}