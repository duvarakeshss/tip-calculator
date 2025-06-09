package com.app.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipCalculatorLayout()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorLayout() {
    var billAmountInput by remember { mutableStateOf("") }
    var tipPercentInput by remember { mutableStateOf("15") }

    // Animation states
    val billAmount = billAmountInput.toFloatOrNull() ?: 0f
    val tipPercent = tipPercentInput.toFloatOrNull() ?: 15f
    val tipAmount = billAmount * tipPercent / 100
    val totalAmount = billAmount + tipAmount

    val animatedTipAmount by animateFloatAsState(
        targetValue = tipAmount,
        animationSpec = tween(durationMillis = 300),
        label = "tip_animation"
    )

    val animatedTotalAmount by animateFloatAsState(
        targetValue = totalAmount,
        animationSpec = tween(durationMillis = 300),
        label = "total_animation"
    )

    val resultCardScale by animateFloatAsState(
        targetValue = if (billAmount > 0) 1f else 0.95f,
        animationSpec = tween(durationMillis = 200),
        label = "card_scale"
    )

    // Dynamic colors based on tip amount
    val tipColor by animateColorAsState(
        targetValue = when {
            tipPercent >= 20 -> Color(0xFF4CAF50) // Green for generous tips
            tipPercent >= 15 -> Color(0xFFFF9800) // Orange for standard tips
            else -> Color(0xFFF44336) // Red for low tips
        },
        animationSpec = tween(durationMillis = 300),
        label = "tip_color"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF8F9FA),
                        Color(0xFFE9ECEF)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header with gradient background
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF667eea),
                                    Color(0xFF764ba2)
                                )
                            )
                        )
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "💰",
                            fontSize = 48.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Tip Calculator",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "Calculate your perfect tip",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.8f)
                            ),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // Input Cards
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Bill Amount",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF2C3E50)
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = billAmountInput,
                        onValueChange = { billAmountInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        label = { Text("Enter amount") },
                        leadingIcon = {
                            Text(
                                text = "$",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color(0xFF667eea),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF667eea),
                            focusedLabelColor = Color(0xFF667eea)
                        )
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Tip Percentage",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF2C3E50)
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = tipPercentInput,
                        onValueChange = { tipPercentInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text("Enter percentage") },
                        leadingIcon = {
                            Text(
                                text = "%",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = tipColor,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = tipColor,
                            focusedLabelColor = tipColor
                        )
                    )

                    // Quick tip percentage buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("10", "15", "18", "20", "25").forEach { percentage ->
                            FilterChip(
                                onClick = { tipPercentInput = percentage },
                                label = { Text("$percentage%") },
                                selected = tipPercentInput == percentage,
                                modifier = Modifier.weight(1f),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = tipColor.copy(alpha = 0.2f),
                                    selectedLabelColor = tipColor
                                )
                            )
                        }
                    }
                }
            }

            // Results Card with Animation
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(resultCardScale),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White,
                                    Color(0xFFF8F9FA)
                                )
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column {
                        Text(
                            text = "💳 Payment Summary",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2C3E50)
                            ),
                            modifier = Modifier.padding(bottom = 20.dp)
                        )

                        // Bill amount row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Bill Amount:",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Color(0xFF6C757D)
                                )
                            )
                            Text(
                                text = "$${String.format("%.2f", billAmount)}",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF2C3E50)
                                )
                            )
                        }

                        // Tip amount row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Tip Amount ($tipPercent%):",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Color(0xFF6C757D)
                                )
                            )
                            Text(
                                text = "$${String.format("%.2f", animatedTipAmount)}",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = tipColor
                                )
                            )
                        }

                        Divider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            thickness = 2.dp,
                            color = Color(0xFFE9ECEF)
                        )

                        // Total amount row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total Amount:",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2C3E50)
                                )
                            )
                            Text(
                                text = "$${String.format("%.2f", animatedTotalAmount)}",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF28A745)
                                )
                            )
                        }

                        // Tip quality indicator
                        if (billAmount > 0) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = tipColor.copy(alpha = 0.1f)
                                )
                            ) {
                                Text(
                                    text = when {
                                        tipPercent >= 20 -> "🌟 Generous tip!"
                                        tipPercent >= 15 -> "👍 Good tip!"
                                        tipPercent >= 10 -> "✅ Standard tip"
                                        else -> "💡 Consider 15-20%"
                                    },
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = tipColor
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorPreview() {
    TipCalculatorTheme {
        TipCalculatorLayout()
    }
}