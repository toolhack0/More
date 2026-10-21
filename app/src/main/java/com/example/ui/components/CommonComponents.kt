package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.CarrierType
import com.example.model.NetworkTweak
import com.example.model.ServerNode
import com.example.ui.theme.*
import java.util.Locale

@Composable
fun ElhnafyTopBar(
    ipText: String,
    onOpenDrawer: () -> Unit,
    onOpenConfigs: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // 4-Dots Grid Menu Icon
            IconButton(
                onClick = onOpenDrawer,
                modifier = Modifier
                    .size(40.dp)
                    .testTag("button_open_drawer")
            ) {
                Icon(
                    imageVector = Icons.Default.GridView,
                    contentDescription = "Menu Drawer",
                    tint = BluePrimary,
                    modifier = Modifier.size(26.dp)
                )
            }

            // IP display
            Text(
                text = "IP: $ipText",
                fontFamily = FontFamily.Monospace,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BlueTextPrimary
            )
        }

        // Folder / File Document Icon
        IconButton(
            onClick = onOpenConfigs,
            modifier = Modifier
                .size(40.dp)
                .testTag("button_open_configs_top")
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = BluePrimary,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.InsertDriveFile,
                        contentDescription = "Configs",
                        tint = WhitePure,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ServerPickerDialog(
    servers: List<ServerNode>,
    selectedServer: ServerNode,
    onSelect: (ServerNode) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = WhitePure,
            border = androidx.compose.foundation.BorderStroke(2.dp, BlueBorder),
            shadowElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Select Server Location",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = BlueTextPrimary
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = BlueTextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 480.dp)
                ) {
                    items(servers) { server ->
                        val isSelected = server.id == selectedServer.id
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) BluePillBg else WhitePure
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                if (isSelected) 2.dp else 1.dp,
                                if (isSelected) BluePrimary else BlueBorderSubtle
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelect(server)
                                    onDismiss()
                                }
                                .testTag("server_item_${server.id}")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = server.flag,
                                        fontSize = 24.sp
                                    )
                                    Column {
                                        Text(
                                            text = server.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = BlueTextPrimary
                                        )
                                        Text(
                                            text = if (server.isAuto) "Random Server" else "elhnafy tunnel v 🥇",
                                            fontSize = 11.sp,
                                            color = BlueTextSecondary
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (server.isAuto) StatusGreen.copy(alpha = 0.15f) else BluePrimary.copy(alpha = 0.12f),
                                        modifier = Modifier.padding(end = 8.dp)
                                    ) {
                                        Text(
                                            text = server.protocolBadge,
                                            color = if (server.isAuto) StatusGreen else BluePrimary,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    RadioButton(
                                        selected = isSelected,
                                        onClick = {
                                            onSelect(server)
                                            onDismiss()
                                        },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = BluePrimary,
                                            unselectedColor = BlueBorderSubtle
                                        ),
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NetworkPickerDialog(
    networks: List<NetworkTweak>,
    selectedTweak: NetworkTweak,
    onSelect: (NetworkTweak) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filtered = remember(searchQuery, networks) {
        if (searchQuery.isBlank()) networks
        else networks.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.subtitle.contains(searchQuery, ignoreCase = true)
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = WhitePure,
            border = androidx.compose.foundation.BorderStroke(2.dp, BlueBorder),
            shadowElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                // Search network name input
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search network name", color = BlueTextMuted, fontSize = 13.sp) },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = BluePrimary)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BluePrimary,
                        unfocusedBorderColor = BlueBorderSubtle,
                        focusedTextColor = BlueTextPrimary,
                        unfocusedTextColor = BlueTextPrimary,
                        focusedContainerColor = BlueLightBg,
                        unfocusedContainerColor = BlueLightBg
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_search_network")
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 480.dp)
                ) {
                    items(filtered) { tweak ->
                        val isSelected = tweak.id == selectedTweak.id
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) BluePillBg else WhitePure
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                if (isSelected) 2.dp else 1.dp,
                                if (isSelected) BluePrimary else BlueBorderSubtle
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelect(tweak)
                                    onDismiss()
                                }
                                .testTag("network_item_${tweak.id}")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    CarrierBadge(carrierType = tweak.carrierType)

                                    Column {
                                        Text(
                                            text = tweak.title,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = BlueTextPrimary
                                        )
                                        Text(
                                            text = tweak.subtitle,
                                            fontSize = 11.sp,
                                            color = BlueTextSecondary
                                        )
                                    }
                                }

                                RadioButton(
                                    selected = isSelected,
                                    onClick = {
                                        onSelect(tweak)
                                        onDismiss()
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = BluePrimary,
                                        unselectedColor = BlueBorderSubtle
                                    ),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CarrierBadge(carrierType: CarrierType) {
    val (badgeColor, text, icon) = when (carrierType) {
        CarrierType.ETISALAT -> Triple(Color(0xFF7CB342), "e&", Icons.Default.Bolt)
        CarrierType.VODAFONE -> Triple(Color(0xFFE53935), "V", Icons.Default.SignalCellularAlt)
        CarrierType.ORANGE -> Triple(Color(0xFFFF9800), "Or", Icons.Default.Wifi)
        CarrierType.WE_WIFI -> Triple(Color(0xFF1976D2), "WiFi", Icons.Default.Wifi)
        CarrierType.ESIM -> Triple(Color(0xFF00ACC1), "eSIM", Icons.Default.Public)
        CarrierType.SOCIAL -> Triple(Color(0xFF3949AB), "Apps", Icons.Default.PlayCircle)
        CarrierType.GLOBAL -> Triple(Color(0xFF5E35B1), "VPN", Icons.Default.Language)
    }

    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(badgeColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = WhitePure,
            fontWeight = FontWeight.Black,
            fontSize = 11.sp
        )
    }
}

fun formatSpeed(bps: Long): String {
    return when {
        bps >= 1_000_000 -> String.format(Locale.US, "%.1f MB", bps / 1_000_000.0)
        bps >= 1_000 -> String.format(Locale.US, "%.0f KB", bps / 1_000.0)
        else -> "$bps B"
    }
}

fun formatBytes(bytes: Long): String {
    return when {
        bytes >= 1_000_000_000 -> String.format(Locale.US, "%.2f GB", bytes / 1_000_000_000.0)
        bytes >= 1_000_000 -> String.format(Locale.US, "%.1f MB", bytes / 1_000_000.0)
        bytes >= 1_000 -> String.format(Locale.US, "%.0f KB", bytes / 1_000.0)
        else -> "$bytes B"
    }
}

fun formatDuration(seconds: Long): String {
    val hrs = seconds / 3600
    val mins = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format(Locale.US, "%02d:%02d:%02d", hrs, mins, secs)
}
