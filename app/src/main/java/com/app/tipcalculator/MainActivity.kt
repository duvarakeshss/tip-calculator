package com.app.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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

@Composable
fun rememberAppColors(isDark: Boolean = isSystemInDarkTheme()): AppColors {
    return if (isDark) {
        AppColors(
            primary = Color(0xFFFF7B7B),
            primaryVariant = Color(0xFFFF9E9E),
            secondary = Color(0xFF5ED5CC),
            secondaryVariant = Color(0xFF7EDDD6),
            background = Color(0xFF0F0F23),
            backgroundVariant = Color(0xFF1A1A2E),
            surface = Color(0xFF16213E).copy(alpha = 0.8f),
            surfaceVariant = Color(0xFF1F2937).copy(alpha = 0.9f),
            onSurface = Color(0xFFE2E8F0),
            onSurfaceVariant = Color(0xFFA0AEC0),
            textPrimary = Color(0xFFF7FAFC),
            textSecondary = Color(0xFFCBD5E0),
            textTertiary = Color(0xFF9CA3AF),
            border = Color(0xFF374151),
            borderFocused = Color(0xFFFF7B7B)
        )
    } else {
        AppColors(
            primary = Color(0xFFFF6B6B),
            primaryVariant = Color(0xFFFF8E8E),
            secondary = Color(0xFF4ECDC4),
            secondaryVariant = Color(0xFF44A08D),
            background = Color(0xFFFEFEFE),
            backgroundVariant = Color(0xFFF8F9FE),
            surface = Color.White.copy(alpha = 0.8f),
            surfaceVariant = Color.White.copy(alpha = 0.95f),
            onSurface = Color(0xFF2D3748),
            onSurfaceVariant = Color(0xFF4A5568),
            textPrimary = Color(0xFF2D3748),
            textSecondary = Color(0xFF4A5568),
            textTertiary = Color(0xFF718096),
            border = Color(0xFFE2E8F0),
            borderFocused = Color(0xFFFF6B6B)
        )
    }
}

data class AppColors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val background: Color,
    val backgroundVariant: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val border: Color,
    val borderFocused: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorLayout() {
    val isDark = isSystemInDarkTheme()
    val colors = rememberAppColors(isDark)

    var billAmountInput by remember { mutableStateOf("") }
    var tipPercentInput by remember { mutableStateOf("10") }
    var selectedTipIndex by remember { mutableStateOf(1) }

    // Calculations
    val billAmount = billAmountInput.toFloatOrNull() ?: 0f
    val tipPercent = tipPercentInput.toFloatOrNull() ?: 10f
    val tipAmount = billAmount * tipPercent / 100
    val totalAmount = billAmount + tipAmount

    // Smooth animations
    val animatedTipAmount by animateFloatAsState(
        targetValue = tipAmount,
        animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f),
        label = "tip_animation"
    )

    val animatedTotalAmount by animateFloatAsState(
        targetValue = totalAmount,
        animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f),
        label = "total_animation"
    )

    // Animated background colors
    val animatedBackground by animateColorAsState(
        targetValue = colors.background,
        animationSpec = tween(durationMillis = 300),
        label = "background_color"
    )

    val animatedBackgroundVariant by animateColorAsState(
        targetValue = colors.backgroundVariant,
        animationSpec = tween(durationMillis = 300),
        label = "background_variant"
    )

    // Organic shapes background with theme-aware colors
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(animatedBackground, animatedBackgroundVariant)
                )
            )
    ) {
        // Floating organic shapes with theme-aware opacity
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-50).dp, y = 100.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colors.primary.copy(alpha = if (isDark) 0.15f else 0.12f),
                            colors.primary.copy(alpha = if (isDark) 0.03f else 0.02f)
                        )
                    )
                )
                .blur(20.dp)
        )

        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(x = 280.dp, y = 200.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colors.secondary.copy(alpha = if (isDark) 0.15f else 0.12f),
                            colors.secondary.copy(alpha = if (isDark) 0.03f else 0.02f)
                        )
                    )
                )
                .blur(15.dp)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Theme-aware header
            Text(
                text = "Tip Calculator",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraLight,
                color = colors.textPrimary,
                letterSpacing = (-0.5).sp
            )

            Text(
                text = "Split the bill beautifully",
                fontSize = 16.sp,
                color = colors.textTertiary,
                modifier = Modifier.padding(top = 4.dp, bottom = 40.dp)
            )

            // Glass-morphism input card with theme support
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (isDark) 8.dp else 4.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(28.dp)
                ) {
                    Text(
                        text = "Bill Amount",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colors.textSecondary,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = billAmountInput,
                        onValueChange = { billAmountInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        placeholder = {
                            Text(
                                "0.00",
                                color = colors.textTertiary.copy(alpha = 0.6f)
                            )
                        },
                        leadingIcon = {
                            Text(
                                text = "₹",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.primary
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.borderFocused,
                            unfocusedBorderColor = colors.border,
                            focusedLabelColor = colors.borderFocused,
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )
                }
            }

            // Tip percentage selector with Indian tipping culture
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (isDark) 8.dp else 4.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(28.dp)
                ) {
                    Text(
                        text = "Tip Percentage",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colors.textSecondary,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    // Indian tip percentages
                    val tipOptions = listOf("5%", "10%", "15%", "20%", "25%")
                    val tipValues = listOf("5", "10", "15", "20", "25")

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isDark) Color(0xFF1F2937) else Color(0xFFF7FAFC)
                            )
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        tipOptions.forEachIndexed { index, tip ->
                            val isSelected = selectedTipIndex == index
                            val scale by animateFloatAsState(
                                targetValue = if (isSelected) 1.0f else 0.95f,
                                animationSpec = spring(dampingRatio = 0.7f),
                                label = "pill_scale"
                            )

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .scale(scale)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (isSelected) {
                                            Brush.horizontalGradient(
                                                listOf(colors.primary, colors.primaryVariant)
                                            )
                                        } else {
                                            Brush.horizontalGradient(
                                                listOf(Color.Transparent, Color.Transparent)
                                            )
                                        }
                                    )
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        selectedTipIndex = index
                                        tipPercentInput = tipValues[index]
                                    }
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = tip,
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else colors.textTertiary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Custom tip input with theme colors
                    OutlinedTextField(
                        value = tipPercentInput,
                        onValueChange = {
                            tipPercentInput = it
                            selectedTipIndex = -1
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        placeholder = {
                            Text(
                                "Custom %",
                                color = colors.textTertiary.copy(alpha = 0.6f)
                            )
                        },
                        trailingIcon = {
                            Text(
                                text = "%",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.secondary
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.secondary,
                            unfocusedBorderColor = colors.border,
                            focusedLabelColor = colors.secondary,
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )
                }
            }

            // Results with dark mode styling
            if (billAmount > 0) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(
                            animateFloatAsState(
                                targetValue = 1f,
                                animationSpec = spring(dampingRatio = 0.6f, stiffness = 200f),
                                label = "result_scale"
                            ).value
                        ),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colors.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (isDark) 12.dp else 8.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp)
                    ) {
                        // Header with theme-aware styling
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 24.dp)
                        ) {
                            Text(
                                text = "🧾",
                                fontSize = 24.sp,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text(
                                text = "Bill Summary",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.textPrimary
                            )
                        }

                        // Breakdown with dark mode colors
                        ResultRow(
                            label = "Subtotal",
                            value = billAmount,
                            color = colors.textTertiary,
                            textColor = colors.textSecondary
                        )

                        ResultRow(
                            label = "Tip (${"%.0f".format(tipPercent)}%)",
                            value = animatedTipAmount,
                            color = colors.primary,
                            textColor = colors.textSecondary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        // Elegant theme-aware divider
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            Color.Transparent,
                                            colors.border,
                                            Color.Transparent
                                        )
                                    )
                                )
                                .padding(vertical = 16.dp)
                        )

                        // Total with emphasis
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = colors.textPrimary
                            )
                            Text(
                                text = "₹${String.format("%.2f", animatedTotalAmount)}",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = colors.secondary
                            )
                        }

                        // Tip quality with Indian context
                        Spacer(modifier = Modifier.height(20.dp))

                        val (emoji, message, color) = when {
                            tipPercent >= 20 -> Triple("🌟", "Very generous!", if (isDark) Color(0xFF68D391) else Color(0xFF48BB78))
                            tipPercent >= 15 -> Triple("😊", "Great service tip", colors.secondary)
                            tipPercent >= 10 -> Triple("👌", "Standard tip", if (isDark) Color(0xFFFBB041) else Color(0xFFED8936))
                            tipPercent >= 5 -> Triple("💭", "Modest tip", if (isDark) Color(0xFFFBB041) else Color(0xFFED8936))
                            else -> Triple("💡", "Consider 10-15%", if (isDark) Color(0xFFFC8181) else Color(0xFFE53E3E))
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(color.copy(alpha = if (isDark) 0.2f else 0.1f))
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = emoji,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text(
                                text = message,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = color
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ResultRow(
    label: String,
    value: Float,
    color: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
        Text(
            text = "₹${String.format("%.2f", value)}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorPreview() {
    TipCalculatorTheme {
        TipCalculatorLayout()
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TipCalculatorDarkPreview() {
    TipCalculatorTheme {
        TipCalculatorLayout()
    }
}