package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ConnectionStatus
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.TunnelViewModel

@Composable
fun HomeScreen(
    viewModel: TunnelViewModel,
    onRequestVpnPermission: () -> Unit,
    onOpenDrawer: () -> Unit,
    onOpenConfigs: () -> Unit
) {
    val status by viewModel.connectionStatus.collectAsState()
    val stats by viewModel.trafficStats.collectAsState()
    val selectedServer by viewModel.selectedServer.collectAsState()
    val selectedTweak by viewModel.selectedNetworkTweak.collectAsState()

    var showServerPicker by remember { mutableStateOf(false) }
    var showNetworkPicker by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    if (showServerPicker) {
        ServerPickerDialog(
            servers = viewModel.serverList,
            selectedServer = selectedServer,
            onSelect = { viewModel.selectServer(it) },
            onDismiss = { showServerPicker = false }
        )
    }

    if (showNetworkPicker) {
        NetworkPickerDialog(
            networks = viewModel.networkTweaks,
            selectedTweak = selectedTweak,
            onSelect = { viewModel.selectNetworkTweak(it) },
            onDismiss = { showNetworkPicker = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueLightBg)
    ) {
        // Top App Bar matching original (Grid icon, IP, Folder icon)
        ElhnafyTopBar(
            ipText = if (status == ConnectionStatus.CONNECTED) stats.virtualIp else "N/A",
            onOpenDrawer = onOpenDrawer,
            onOpenConfigs = onOpenConfigs
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Eagle Banner Graphic with Shield Logo
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = WhitePure),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, BlueBorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.elhnafy_eagle_banner),
                        contentDescription = "Elhnafy V Eagle Banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Overlay label
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 6.dp)
                            .background(
                                color = WhitePure.copy(alpha = 0.85f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(1.dp, BlueBorderSubtle, RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (status == ConnectionStatus.CONNECTED) "Live Tunnel Active" else "No Graph Data",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueSecondary
                        )
                    }
                }
            }

            // Real-Time Speed & Status Row (Cloud DL, Disconnected/Connected, Cloud UL)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Download
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudDownload,
                        contentDescription = null,
                        tint = BluePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = formatSpeed(stats.downloadSpeedBps),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = BlueTextPrimary
                    )
                }

                // Status text in center
                Text(
                    text = when (status) {
                        ConnectionStatus.CONNECTED -> "Connected"
                        ConnectionStatus.CONNECTING,
                        ConnectionStatus.AUTHENTICATING,
                        ConnectionStatus.ESTABLISHING_TUNNEL -> "Connecting..."
                        ConnectionStatus.DISCONNECTING -> "Disconnecting..."
                        ConnectionStatus.DISCONNECTED -> "Disconnected"
                    },
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = when (status) {
                        ConnectionStatus.CONNECTED -> StatusGreen
                        ConnectionStatus.DISCONNECTED -> StatusRed
                        else -> StatusOrange
                    }
                )

                // Upload
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = formatSpeed(stats.uploadSpeedBps),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = BlueTextPrimary
                    )
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = null,
                        tint = BluePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Card 1: Server Selector
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = WhitePure),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, BlueBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showServerPicker = true }
                    .testTag("card_select_server")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(BluePillBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = selectedServer.flag,
                                fontSize = 20.sp
                            )
                        }

                        Column {
                            Text(
                                text = selectedServer.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = BlueTextPrimary
                            )
                            Text(
                                text = if (selectedServer.isAuto) "Random Server" else "${selectedServer.country} • ${selectedServer.pingMs}ms",
                                fontSize = 11.sp,
                                color = BlueTextSecondary
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Select Server",
                        tint = BluePrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Card 2: Network / Tweak Selector
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = WhitePure),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, BlueBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showNetworkPicker = true }
                    .testTag("card_select_network")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        CarrierBadge(carrierType = selectedTweak.carrierType)

                        Column {
                            Text(
                                text = selectedTweak.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = BlueTextPrimary
                            )
                            Text(
                                text = selectedTweak.subtitle,
                                fontSize = 11.sp,
                                color = BlueTextSecondary,
                                maxLines = 1
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = BlueLightBg,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BlueBorderSubtle),
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = BluePrimary,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = "CREATE",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BluePrimary
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Select Network",
                            tint = BluePrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Connection Time
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = "Connection time",
                    fontSize = 12.sp,
                    color = BlueTextSecondary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = formatDuration(stats.durationSeconds),
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Black,
                    fontSize = 28.sp,
                    color = BlueTextPrimary,
                    letterSpacing = 2.sp
                )
            }

            // Big Circular Power Button in White and Royal Blue
            OriginalPowerButton(
                status = status,
                onToggle = {
                    when (status) {
                        ConnectionStatus.DISCONNECTED -> onRequestVpnPermission()
                        ConnectionStatus.CONNECTED,
                        ConnectionStatus.CONNECTING,
                        ConnectionStatus.AUTHENTICATING,
                        ConnectionStatus.ESTABLISHING_TUNNEL -> viewModel.stopVpn()
                        ConnectionStatus.DISCONNECTING -> {}
                    }
                }
            )

            // Config Version footer label
            Text(
                text = "Config Version: 27.377",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = BlueTextMuted,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}

@Composable
fun OriginalPowerButton(
    status: ConnectionStatus,
    onToggle: () -> Unit
) {
    val isConnected = status == ConnectionStatus.CONNECTED
    val isBusy = status == ConnectionStatus.CONNECTING ||
            status == ConnectionStatus.AUTHENTICATING ||
            status == ConnectionStatus.ESTABLISHING_TUNNEL

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isConnected || isBusy) 1.08f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(190.dp)
            .padding(8.dp)
    ) {
        // Outer glow circle
        Box(
            modifier = Modifier
                .size(174.dp)
                .scale(if (isConnected || isBusy) pulseScale else 1f)
                .clip(CircleShape)
                .background(
                    if (isConnected) BlueAccent.copy(alpha = 0.25f)
                    else BluePrimary.copy(alpha = 0.12f)
                )
        )

        // Mid border ring
        Box(
            modifier = Modifier
                .size(148.dp)
                .clip(CircleShape)
                .border(2.5.dp, BlueBorder, CircleShape)
                .background(WhitePure)
        )

        // Main Tap Circle
        Box(
            modifier = Modifier
                .size(126.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        if (isConnected) listOf(BlueAccent, BluePrimary)
                        else listOf(BluePrimary, BlueSecondary)
                    )
                )
                .clickable(onClick = onToggle)
                .testTag("button_connect_toggle"),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PowerSettingsNew,
                    contentDescription = "Power",
                    tint = WhitePure,
                    modifier = Modifier.size(38.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when {
                        isConnected -> "Connected"
                        isBusy -> "Connecting"
                        else -> "Disconnected"
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = WhitePure
                )
            }
        }
    }
}
