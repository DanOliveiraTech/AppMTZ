package com.metricaz.metaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.metricaz.metaapp.ui.theme.METAAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            METAAppTheme {
                firebaseAnalytics = FirebaseAnalytics.getInstance(this)
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(navController, firebaseAnalytics)
                        logScreenView("home")
                    }
                    composable("boas_vindas") {
                        BoasVindasScreen(navController, firebaseAnalytics)
                        logScreenView("boas_vindas")
                    }
                    composable("avaliacao_colab") {
                        AvaliacaoColabScreen(navController, firebaseAnalytics)
                        logScreenView("avaliacao-colab")
                    }
                    composable("this_is_the_end") {
                        EndScreen { finish() }
                        logScreenView("this-is-the-end")
                    }
                }
            }
        }
    }

    private fun logScreenView(screenName: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
}

@Composable
fun HomeScreen(navController: NavHostController, firebaseAnalytics: FirebaseAnalytics) {
    Scaffold(
        content = { innerPadding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(innerPadding)
            ) {
                Button(
                    onClick = {
                        val bundle = Bundle().apply {
                            putString("custom_message", "sucesso-abriu-gracas-a-deus")
                        }
                        firebaseAnalytics.logEvent("login_app_dan", bundle)
                        navController.navigate("boas_vindas")
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.size(100.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Text(
                        text = "MTZ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    )
}

@Composable
fun BoasVindasScreen(navController: NavHostController, firebaseAnalytics: FirebaseAnalytics) {
    Scaffold(
        content = { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFF9800))
                    .padding(innerPadding)
            ) {
                Text(
                    text = "BEM-VINDO À METRICAZ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            val bundle = Bundle().apply {
                                putString("custom_message", "nao-tem-nada-na-home")
                            }
                            firebaseAnalytics.logEvent("back_home_app", bundle)
                            navController.popBackStack()
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier.size(100.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Text(
                            text = "◀",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    Button(
                        onClick = {
                            val bundle = Bundle().apply {
                                putString("custom_message", "dar-menos-que-cinco-estrelas-e-um-esculacho")
                            }
                            firebaseAnalytics.logEvent("next_feedback360", bundle)
                            navController.navigate("avaliacao_colab")
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier.size(100.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Text(
                            text = "▶",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun AvaliacaoColabScreen(navController: NavHostController, firebaseAnalytics: FirebaseAnalytics) {
    val rating = remember { mutableIntStateOf(0) }

    Scaffold(
        content = { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(innerPadding)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.colaborador),
                    contentDescription = "Foto do Colaborador",
                    modifier = Modifier.size(150.dp)
                )
                Text(
                    text = "Classifique o desempenho deste colaborador?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..5) {
                        IconButton(
                            onClick = {
                                rating.intValue = i
                                val bundle = Bundle().apply {
                                    putInt("custom_nota_atribuida", i)
                                }
                                firebaseAnalytics.logEvent("feedback360", bundle)
                            },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = if (i <= rating.intValue) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = null,
                                tint = if (i <= rating.intValue) Color.Yellow else Color.Gray
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val bundle = Bundle().apply {
                            putInt("custom_final_score", rating.intValue)
                            putString("custom_message", "desculpe-me-a-demora-na-entrega-da-task")
                        }
                        firebaseAnalytics.logEvent("complete_performance_review", bundle)
                        navController.navigate("this_is_the_end")
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    modifier = Modifier.size(100.dp)
                ) {
                    Text(
                        text = "Enviar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    )
}

@Composable
fun EndScreen(onCloseApp: () -> Unit) {
    val firebaseAnalytics = FirebaseAnalytics.getInstance(LocalContext.current)

    Scaffold(
        content = { innerPadding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(innerPadding)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Obrigado amigo, você é um amigo!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            val bundle = Bundle().apply {
                                putString("custom_message", "obrigado-especial-ao-chatGPT")
                            }
                            firebaseAnalytics.logEvent("final_event", bundle)
                            onCloseApp()
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .size(100.dp)
                    ) {
                        Text(
                            text = "Close",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    )
}
