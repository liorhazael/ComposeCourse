package com.example.composecourse

import android.animation.Keyframe
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/*
@Composable
private fun Part() {

}
*/

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Row(modifier = Modifier.fillMaxSize()) {
                Greeting(listOf("Mother Fucker", "Zanaaaa"))
            }
        }
    }

    @Composable
    fun Greeting(names: List<String>) {
        for (name in names) {
            Text(modifier = Modifier.padding(10.dp),text = "Hello $name")
        }
    }

    /**
     * Animated Circular Progress Bar
     */
    @Composable
    private fun Part12() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Part12CircularProgressBar(percentage = 1f, number = 100, animDuration = 2000)
        }
    }

    @Composable
    private fun Part12CircularProgressBar(
        percentage: Float,
        number: Int,
        fontSize: TextUnit = 28.sp,
        radius: Dp = 50.dp,
        color: Color = Color.Green,
        strokeWidth: Dp = 8.dp,
        animDuration: Int = 1000,
        animDelay: Int = 0
    ) {
        var animationPlayed by remember {
            mutableStateOf(false)
        }
        val curPercentage = animateFloatAsState(
            targetValue = if (animationPlayed) percentage else 0f,
            animationSpec = tween(
                durationMillis = animDuration,
                delayMillis = animDelay
            )
        )

        LaunchedEffect(key1 = true) {
            animationPlayed = true
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(radius * 2f)
        ) {
            Canvas(modifier = Modifier.size(radius * 2f)) { // Executing onDraw
                drawArc(
                    color = color,
                    -90f,
                    360 * curPercentage.value,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }

            Text(
                text = (curPercentage.value * number).toInt().toString(),
                color = Color.Black,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }


    /**
     * Simple Animations.
     */
    @Composable
    private fun Part11() {
        // Size animations.
        var sizeState by remember {
            mutableStateOf(200.dp)
        }
        val size by animateDpAsState(
            targetValue = sizeState,
            /*tween(
                durationMillis = 3000,
                delayMillis = 300, // The delay in millis to start after.
                easing = LinearEasing  // The way of animation's easing curve.
            )*/

            /*spring(
                Spring.DampingRatioHighBouncy
            )*/
            keyframes {
                durationMillis = 5000
                sizeState at 0 with LinearEasing
                sizeState * 1.5f at 1000 with FastOutLinearInEasing
                //sizeState * 2f at 5000
            }
        )

        // Infinite animation.
        val infiniteTransition = rememberInfiniteTransition()
        val color by infiniteTransition.animateColor(
            initialValue = Color.Red,
            targetValue = Color.Green,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 2000),
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(
            modifier = Modifier.size(size),
            contentAlignment = Alignment.Center
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(color),
                onClick = {
                    sizeState += 50.dp
                }) {
                Text("Increase Size")
            }
        }
    }


    /**
     * Side effects.
     */
    @Composable
    private fun Part10(backPressedDispatcher: OnBackPressedDispatcher) {
        val scaffoldState = rememberScaffoldState()
        Scaffold(scaffoldState = scaffoldState) {
            val counter = produceState(initialValue = 0) {
                kotlinx.coroutines.delay(3000L)
                value = 4
            }
            /*val counter  = by remember {
                mutableStateOf(0)
            }*/

            if (counter.value % 5 == 0 && counter.value > 0) {
                LaunchedEffect(key1 = scaffoldState.snackbarHostState) {
                    scaffoldState.snackbarHostState.showSnackbar("Hello")
                }
            }
            Button(onClick = { }) {
                Text(text = "Click me ${counter.value}")
            }
        }


        val callback = remember { // remember function - evaluate at composition only.
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Do something
                }
            }
        }

        // Side Effect - Will execute only when the composition is is successful.
        SideEffect {

        }
        // Disposable Effect
        DisposableEffect(key1 = backPressedDispatcher) {
            backPressedDispatcher.addCallback(callback)
            onDispose {
                callback.remove()
            }
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Click me")
        }
    }


    /**
     * Constraint Layout example.
     */
    @Composable
    private fun Part9() {
        // Constraint Layout
        val constraints = ConstraintSet {
            val greenBox = createRefFor("green_box")
            val redBox = createRefFor("red_box")
            val guideLine = createGuidelineFromTop(0.5f)

            constrain(greenBox) {
                top.linkTo(guideLine) // top to top of parent
                start.linkTo(parent.start) // start to start of parent
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            }

            constrain(redBox) {
                top.linkTo(parent.top) // top to top of parent
                start.linkTo(greenBox.end) // start to end of greenBox
                end.linkTo(parent.end)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            }

            createHorizontalChain(greenBox, redBox, chainStyle = ChainStyle.Packed)
        }
        ConstraintLayout(constraints, Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .layoutId("green_box")
            )
            Box(
                modifier = Modifier
                    .background(Color.Red)
                    .layoutId("red_box")
            )
        }
    }


    /**
     * RecyclerView-like example.
     */
    @Composable
    private fun Part8() {
        // Kind of recycler view
        LazyColumn() {
            itemsIndexed(
                listOf("This", "is", "Jetpack", "Compose")
            ) { _, item ->
                Text(
                    text = item,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                )
            }
        }
    }


    /**
     * Scaffold - material design.
     */
    @Composable
    private fun Part7() {
        val scaffoldState = rememberScaffoldState()
        var textFieldState by remember {
            mutableStateOf("")
        }
        val scope = rememberCoroutineScope()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                TextField(
                    value = textFieldState,
                    label = {
                        Text("Enter your name")
                    },
                    onValueChange = {
                        textFieldState = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Hello $textFieldState")
                    }
                }) {
                    Text(text = "Please greet me")
                }
            }
        }
    }


    /**
     * remember function and states.
     */
    @Composable
    private fun Part6A() {
        Column(Modifier.fillMaxSize()) {
            val color = remember {
                mutableStateOf(Color.Yellow)
            }

            Part6B(
                Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                color.value = it
            }

            Box(
                modifier = Modifier
                    .background(color.value)
                    .weight(1f)
                    .fillMaxSize()
            )
        }
    }

    @Composable
    private fun Part6B(modifier: Modifier = Modifier, updateColor: (Color) -> Unit) {
        Box(modifier = modifier
            .background(Color.Red)
            .clickable {
                updateColor(
                    Color(
                        Random.nextFloat(),
                        Random.nextFloat(),
                        Random.nextFloat(),
                        1f
                    )
                )
            }
        )
    }


    /**
     * Fonts and spannable string builder.
     */
    @Composable
    private fun Part5() {
        // Fonts
        val fontFamily = FontFamily(
            Font(R.font.lexend_thin, FontWeight.Thin),
            Font(R.font.lexend_light, FontWeight.Light),
            Font(R.font.lexend_regular, FontWeight.Normal),
            Font(R.font.lexend_medium, FontWeight.Medium),
            Font(R.font.lexend_bold, FontWeight.Bold),
            Font(R.font.lexend_extrabold, FontWeight.ExtraBold),
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101010))
        ) {
            Text(
                // The same as spannable string builder
                text = buildAnnotatedString {

                    withStyle(
                        style = SpanStyle(
                            color = Color.Green,
                            fontSize = 50.sp
                        )
                    ) {
                        append("J")
                    }

                    append("etpack ")

                    withStyle(
                        style = SpanStyle(
                            color = Color.Green,
                            fontSize = 50.sp
                        )
                    ) {
                        append("C")
                    }

                    append("ompose")
                },
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline
            )
        }
    }


    /**
     * Image card component.
     */
    @Composable
    private fun Part4() {
        // Image Card example.
        val painter = painterResource(id = R.drawable.kermit)
        val description = "Kermit in the snow"
        val title = "Kermit is a motherfucker"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                elevation = 5.dp,
                backgroundColor = Color.Blue
            ) {
                Box(modifier = Modifier.height(200.dp)) {

                    Image(
                        painter = painter,
                        contentDescription = description,
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    ),
                                    startY = 300f
                                )
                            )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
                    }

                }
            }

        }


    }


    /**
     * Modifier behaviour - executes sequentially.
     */
    @Composable
    private fun Part3() {
        Column(
            Modifier
                .background(Color.Green)
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .border(5.dp, Color.Magenta)
                .padding(5.dp)
                .border(5.dp, Color.Blue)
                .padding(5.dp) // Will be executed sequentially
        ) {
            Text(text = "Hello", modifier = Modifier.clickable {

            })
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "World")
        }
    }

    /**
     * Basic positioning - Rows/Columns and alignments/arrangement, behaving like LinearLayout.
     */
    @Composable
    private fun Part1AndPart2() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Green),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Hello")
            Text(text = "World")
            Text(text = "Hello")
        }
    }


}


