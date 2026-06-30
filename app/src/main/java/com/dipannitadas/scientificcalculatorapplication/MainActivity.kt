package com.dipannitadas.scientificcalculatorapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dipannitadas.scientificcalculatorapplication.ui.theme.ScientificCalculatorApplicationTheme
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import java.text.DecimalFormat
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var darkMode by remember {
                mutableStateOf(false)
            }

            ScientificCalculatorApplicationTheme(
                darkTheme = darkMode
            ) {

                CalculatorScreen(
                    darkMode = darkMode,
                    onDarkModeChange = {
                        darkMode = !darkMode
                    }
                )

            }
        }
    }
}
fun factorial(n: Int): Long {

    var result = 1L

    for(i in 1..n)
        result *= i

    return result
}
fun calculateExpression(expression: String): String {

    return try {

        var exp = expression.replace(" ", "")

        if (exp.endsWith("!")) {

            val number = exp.dropLast(1).toInt()
            return factorial(number).toString()

        }

        if (exp.startsWith("sqrt(") && exp.endsWith(")")) {

            val number = exp.substringAfter("sqrt(")
                .substringBefore(")")
                .toDouble()

            return sqrt(number).toString()

        }

        if (exp.startsWith("sin(") && exp.endsWith(")")) {

            val angle = exp.substringAfter("sin(")
                .substringBefore(")")
                .toDouble()

            return sin(Math.toRadians(angle)).toString()

        }

        if (exp.startsWith("cos(") && exp.endsWith(")")) {

            val angle = exp.substringAfter("cos(")
                .substringBefore(")")
                .toDouble()

            return cos(Math.toRadians(angle)).toString()

        }

        if (exp.startsWith("tan(") && exp.endsWith(")")) {

            val angle = exp.substringAfter("tan(")
                .substringBefore(")")
                .toDouble()

            return tan(Math.toRadians(angle)).toString()

        }

        if (exp.startsWith("log(") && exp.endsWith(")")) {

            val number = exp.substringAfter("log(")
                .substringBefore(")")
                .toDouble()

            return log10(number).toString()

        }

        if (exp.startsWith("ln(") && exp.endsWith(")")) {

            val number = exp.substringAfter("ln(")
                .substringBefore(")")
                .toDouble()

            return ln(number).toString()

        }



        val result = ExpressionBuilder(exp)
            .build()
            .evaluate()

        val formatter = DecimalFormat("#.##########")
        return formatter.format(result)
    }

    catch (e: Exception) {

        "Invalid Expression"

    }

}

@Composable
fun CalculatorScreen(
    darkMode: Boolean,
    onDarkModeChange: () -> Unit
) {

    var display by remember {
        mutableStateOf("0")
    }

    var history by remember {
        mutableStateOf(listOf<String>())
    }

    var scientificMode by remember {
        mutableStateOf(true)
    }
    val keypad = listOf(
        listOf("7", "8", "9", "-"),
        listOf("4", "5", "6", "+"),
        listOf("1", "2", "3", "="),
        listOf("0", ".", "mod", "!")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text("Scientific Calculator")

            Button(
                onClick = {
                    onDarkModeChange()
                }
            ){
                Text(
                    if (darkMode)
                        "☀"
                    else
                        "🌙"
                )
            }

        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Row {

            Button(
                onClick = {
                    scientificMode = false
                }
            ) {

                Text("Basic")

            }

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Button(
                onClick = {
                    scientificMode = true
                }
            ) {

                Text("Scientific")

            }

        }
        Text(
            text = display,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
        ) {
            items(history) { item ->
                Text(
                    text = item,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Row {

            CalcButton(
                text = "AC",
                onClick = {
                    display = "0"
                    history = emptyList()
                }
            )

            CalcButton(
                text = "C",
                onClick = {
                    display = "0"
                }
            )

            CalcButton(
                text = "⌫",
                onClick = {

                    if (display.length > 1)
                        display = display.dropLast(1)
                    else
                        display = "0"

                }
            )

            CalcButton(
                text = "%",
                onClick = {
                    display += "%"
                }
            )

        }
        if(scientificMode) {
        Row {

            CalcButton(
                text = "√",
                onClick = {
                    if (display == "0")
                        display = "sqrt("
                    else
                        display += "sqrt("
                }
            )

            CalcButton(
                text = "sin",
                onClick = {
                    if (display == "0")
                        display = "sin("
                    else
                        display += "sin("
                }
            )

            CalcButton(
                text = "cos",
                onClick = {
                    if (display == "0")
                        display = "cos("
                    else
                        display += "cos("
                }
            )

            CalcButton(
                text = "tan",
                onClick = {
                    if (display == "0")
                        display = "tan("
                    else
                        display += "tan("
                }
            )

        }

        Row {

            CalcButton(
                text = "log",
                onClick = {
                    if (display == "0")
                        display = "log("
                    else
                        display += "log("
                }
            )

            CalcButton(
                text = "ln",
                onClick = {
                    if (display == "0")
                        display = "ln("
                    else
                        display += "ln("
                }
            )

            CalcButton(
                text = "π",
                onClick = {
                    if (display == "0")
                        display = "3.14159265359"
                    else
                        display += "3.14159265359"
                }
            )

            CalcButton(
                text = "^",
                onClick = {
                    display += "^"
                }
            )

        }
            }

        Row {

            CalcButton(
                text = "×",
                onClick = {
                    display += "*"
                }
            )

            CalcButton(
                text = "÷",
                onClick = {
                    display += "/"
                }
            )

            CalcButton(
                text = "(",
                onClick = {
                    if (display == "0")
                        display = "("
                    else
                        display += "("
                }
            )

            CalcButton(
                text = ")",
                onClick = {
                    display += ")"
                }
            )

        }

        keypad.forEach { row ->

            Row {

                row.forEach { value ->

                    CalcButton(
                        text = value,
                        onClick = {

                            when (value) {

                                "+" -> display += "+"

                                "-" -> display += "-"

                                "=" -> {

                                    val result =
                                        calculateExpression(display)

                                    history =
                                        history + "$display = $result"

                                    display = result

                                }

                                "mod" -> display += "%"

                                "!" -> display += "!"

                                else -> {

                                    if (display == "0")
                                        display = value
                                    else
                                        display += value

                                }
                            }

                        }
                    )

                }

            }

        }

    }

}

@Composable
fun CalcButton(
    text: String,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .size(
                width = 86.dp,
                height = 55.dp
            )
    ) {

        Text(
            text = text,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

    }

}