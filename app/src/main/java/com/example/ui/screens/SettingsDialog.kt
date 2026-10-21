package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.TunnelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    viewModel: TunnelViewModel,
    onDismiss: () -> Unit
) {
    var selectedMtu by remember { mutableIntStateOf(viewModel.mtu) }
    var primaryDns by remember { mutableStateOf(viewModel.primaryDns) }
    var secondaryDns by remember { mutableStateOf(viewModel.secondaryDns) }
    var udpPort by remember { mutableIntStateOf(viewModel.udpGwPort) }
    var compression by remember { mutableStateOf(viewModel.enableCompression) }
    var wakelock by remember { mutableStateOf(viewModel.enableWakelock) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = WhitePure,
        dragHandle = { BottomSheetDefaults.DragHandle(color = BlueTextSecondary) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tunnel & Advanced Settings",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = BlueTextPrimary
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = BlueTextSecondary)
                }
            }

            // MTU Selection
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Maximum Transmission Unit (MTU):", fontSize = 12.sp, color = BlueTextSecondary)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(1500, 1400, 1280).forEach { mtuVal ->
                        FilterChip(
                            selected = selectedMtu == mtuVal,
                            onClick = {
                                selectedMtu = mtuVal
                                viewModel.mtu = mtuVal
                            },
                            label = { Text("$mtuVal") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BluePrimary,
                                selectedLabelColor = WhitePure
                            )
                        )
                    }
                }
            }

            Divider(color = BlueBorderSubtle)

            // DNS Settings
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Custom DNS Resolvers:", fontSize = 12.sp, color = BlueTextSecondary)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = primaryDns,
                        onValueChange = {
                            primaryDns = it
                            viewModel.primaryDns = it
                        },
                        label = { Text("Primary DNS", fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BluePrimary,
                            unfocusedBorderColor = BlueBorderSubtle,
                            focusedTextColor = BlueTextPrimary,
                            unfocusedTextColor = BlueTextPrimary
                        )
                    )
                    OutlinedTextField(
                        value = secondaryDns,
                        onValueChange = {
                            secondaryDns = it
                            viewModel.secondaryDns = it
                        },
                        label = { Text("Secondary DNS", fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BluePrimary,
                            unfocusedBorderColor = BlueBorderSubtle,
                            focusedTextColor = BlueTextPrimary,
                            unfocusedTextColor = BlueTextPrimary
                        )
                    )
                }
            }

            Divider(color = BlueBorderSubtle)

            // UDPGW Port
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("UDPGW (UDP Gateway Port for VoIP/Gaming):", fontSize = 12.sp, color = BlueTextSecondary)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(7300, 7200, 7100).forEach { port ->
                        FilterChip(
                            selected = udpPort == port,
                            onClick = {
                                udpPort = port
                                viewModel.udpGwPort = port
                            },
                            label = { Text(":$port") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BluePrimary,
                                selectedLabelColor = WhitePure
                            )
                        )
                    }
                }
            }

            Divider(color = BlueBorderSubtle)

            // Toggles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Data Stream Compression", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = BlueTextPrimary)
                    Text("Reduces mobile data usage over slow 3G/LTE", fontSize = 11.sp, color = BlueTextSecondary)
                }
                Switch(
                    checked = compression,
                    onCheckedChange = {
                        compression = it
                        viewModel.enableCompression = it
                    },
                    colors = SwitchDefaults.colors(checkedThumbColor = WhitePure, checkedTrackColor = BluePrimary)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("CPU WakeLock Keep-Alive", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = BlueTextPrimary)
                    Text("Prevents OS from killing tunnel during screen off", fontSize = 11.sp, color = BlueTextSecondary)
                }
                Switch(
                    checked = wakelock,
                    onCheckedChange = {
                        wakelock = it
                        viewModel.enableWakelock = it
                    },
                    colors = SwitchDefaults.colors(checkedThumbColor = WhitePure, checkedTrackColor = BluePrimary)
                )
            }

            Divider(color = BlueBorderSubtle)

            // About Elhnafy V
            Card(
                colors = CardDefaults.cardColors(containerColor = BlueLightBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, BlueBorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Elhnafy V (Tunnel Plus)", fontWeight = FontWeight.Bold, color = BluePrimary, fontSize = 13.sp)
                    Text("The all-in-one SSH, TLS/SSL, Bug SNI, and Custom Payload injector client.", fontSize = 11.sp, color = BlueTextSecondary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Version 5.2.0 • Build 32", fontSize = 10.sp, color = BlueTextMuted)
                }
            }
        }
    }
}
