package com.example.triangle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triangle.ui.theme.TriangleTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriangleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DemoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DemoText(message: String, fontSize: TextUnit) {
    Text(
        text = message,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold
    )
}

fun calculate(letter: String, value: Double): String {
    var a = 0.0
    var h = 0.0
    var s = 0.0

    when (letter) {
        "a" -> {
            a = value
            h = a * sqrt(2.0)
            s = a * a / 2
        }
        "h" -> {
            h = value
            a = h / sqrt(2.0)
            s = h * h / 4
        }
        "s" -> {
            s = value
            a = sqrt(2 * s)
            h = 2 * sqrt(s)
        }
        else -> {
            return "Ошибка! Введите a, h или s"
        }
    }

    return "Результат:\n" +
            "Катет a = $a\n" +
            "Гипотенуза h = $h\n" +
            "Площадь s = $s"
}

@Composable
fun DemoScreen(modifier: Modifier = Modifier) {
    var letter by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("")}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        DemoText(
            message = "Вычисление элементов прямоугольного равнобедренного треугольника",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Элементы: a - катет, h - гипотенуза, s - площадь", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = letter,
            onValueChange = { letter = it },
            label = { Text("Введите первую букву известного элемента") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF27A6F5),
            unfocusedBorderColor = Color(0xFFF268DC)
        )
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("Введите значение") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF27A6F5),
                unfocusedBorderColor = Color(0xFFF268DC)
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                if (letter.isEmpty() || value.isEmpty()) {
                    result = "Заполните оба поля!"
                } else {
                    val num = value.replace(",", ".").toDoubleOrNull()
                    if (num == null || num <= 0) {
                        result = "Ошибка: введите положительное число!"
                    } else {
                        result = calculate(letter, num)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF268DC),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Вычислить", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (result.isNotEmpty()) {
            Text(
                text = result,
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color(0xFFF268DC),
                        shape = RoundedCornerShape(16.dp)  // ← закругление
                    )
                    .padding(16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DemoScreenPreview() {
    TriangleTheme {
        DemoScreen()
    }
}