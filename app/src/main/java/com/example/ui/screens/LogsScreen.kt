package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LogLevel
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.example.ui.theme.*
import com.example.viewmodel.TunnelViewModel
import kotlinx.coroutines.launch

@Composable
fun LogsScreen(
    viewModel: TunnelViewModel,
    onNavigateHome: () -> Unit
) {
    val logs by viewModel.logEntries.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(logs.size) {
        if (logs.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(logs.size - 1)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueLightBg)
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        // Top action bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onNavigateHome,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = BluePrimary
                    )
                }

                Icon(
                    imageVector = Icons.Default.Terminal,
                    contentDescription = null,
                    tint = BluePrimary,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = "LIVE TUNNEL LOGS",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BlueTextPrimary,
                    letterSpacing = 0.5.sp
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        viewModel.copyLogsToClipboard()
                        snackbarMessage = "Logs copied to clipboard!"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = WhitePure),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, BluePrimary),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("button_copy_logs")
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        tint = BluePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Copy", color = BluePrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { viewModel.clearLogs() },
                    colors = ButtonDefaults.buttonColors(containerColor = WhitePure),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, StatusRed),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("button_clear_logs")
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = "Clear",
                        tint = StatusRed,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Clear", color = StatusRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Deep Navy Terminal Box
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Color(0xFF071228), RoundedCornerShape(14.dp))
                .border(1.5.dp, BlueBorder, RoundedCornerShape(14.dp))
                .padding(12.dp)
        ) {
            if (logs.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = ">_ Elhnafy V Console",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueAccent
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tap START to initiate tunnel handshake.",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            color = BlueBorderSubtle
                        )
                    }
                }
            } else {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(logs, key = { it.id }) { log ->
                        val tagColor = when (log.level) {
                            LogLevel.SUCCESS -> Color(0xFF69F0AE)
                            LogLevel.WARNING -> Color(0xFFFFD54F)
                            LogLevel.ERROR -> Color(0xFFFF5252)
                            LogLevel.PROTOCOL -> Color(0xFF40C4FF)
                            LogLevel.INFO -> Color(0xFF90CAF9)
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "${log.timestamp} ",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = Color(0xFF8197B8)
                            )
                            Text(
                                text = "[${log.level.name}] ",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = tagColor
                            )
                            Text(
                                text = log.message,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = when (log.level) {
                                    LogLevel.ERROR -> Color(0xFFFF8A80)
                                    LogLevel.SUCCESS -> Color(0xFFB9F6CA)
                                    LogLevel.PROTOCOL -> Color(0xFF80D8FF)
                                    else -> Color(0xFFF0F4F8)
                                }
                            )
                        }
                    }
                }
            }
        }

        snackbarMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Snackbar(
                containerColor = BluePrimary,
                contentColor = WhitePure,
                action = {
                    TextButton(onClick = { snackbarMessage = null }) {
                        Text("OK", color = WhitePure)
                    }
                }
            ) {
                Text(msg)
            }
        }
    }
}
