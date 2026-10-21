package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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
import com.example.model.TunnelConfig
import com.example.ui.theme.*
import com.example.viewmodel.TunnelViewModel

@Composable
fun ConfigsScreen(
    viewModel: TunnelViewModel,
    onNavigateHome: () -> Unit,
    onConfigApplied: () -> Unit
) {
    val configs by viewModel.savedConfigs.collectAsState()
    var showImportDialog by remember { mutableStateOf(false) }
    var showExportDialog by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueLightBg)
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        // Top Action Row
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

                Column {
                    Text(
                        text = "TUNNEL CONFIGURATIONS",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueTextPrimary,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "${configs.size} Profiles Available",
                        fontSize = 11.sp,
                        color = BlueTextSecondary
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { showImportDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = WhitePure),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, BluePrimary),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("button_import_config_modal")
                ) {
                    Icon(
                        imageVector = Icons.Default.FileDownload,
                        contentDescription = "Import",
                        tint = BluePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Import", color = BluePrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { showExportDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("button_export_config_modal")
                ) {
                    Icon(
                        imageVector = Icons.Default.FileUpload,
                        contentDescription = "Export",
                        tint = WhitePure,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Export", color = WhitePure, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Configs List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            items(configs, key = { it.id }) { config ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = WhitePure),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, BlueBorderSubtle),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.applyConfig(config)
                            snackbarMessage = "Loaded: ${config.name}"
                            onConfigApplied()
                        }
                        .testTag("config_item_${config.id}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = config.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = BlueTextPrimary,
                                modifier = Modifier.weight(1f)
                            )

                            Surface(
                                color = BluePillBg,
                                shape = RoundedCornerShape(6.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, BlueBorderSubtle)
                            ) {
                                Text(
                                    text = config.protocol.title.take(12),
                                    color = BluePrimary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        if (config.notes.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = config.notes,
                                fontSize = 12.sp,
                                color = BlueTextSecondary
                            )
                        }

                        if (config.sniHost.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "SNI: ",
                                    fontSize = 11.sp,
                                    color = BlueTextMuted
                                )
                                Text(
                                    text = config.sniHost,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = BluePrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "By ${config.author}",
                                fontSize = 10.sp,
                                color = BlueTextMuted
                            )

                            Button(
                                onClick = {
                                    viewModel.applyConfig(config)
                                    snackbarMessage = "Loaded: ${config.name}"
                                    onConfigApplied()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text("APPLY & GO", color = WhitePure, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        snackbarMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Snackbar(
                containerColor = BlueSecondary,
                contentColor = WhitePure,
                action = { TextButton(onClick = { snackbarMessage = null }) { Text("OK", color = BlueAccent) } }
            ) {
                Text(msg)
            }
        }
    }

    if (showImportDialog) {
        ImportConfigDialog(
            onImport = { json ->
                val success = viewModel.importConfigFromJson(json)
                if (success) {
                    snackbarMessage = "Configuration imported successfully!"
                    showImportDialog = false
                    onConfigApplied()
                } else {
                    snackbarMessage = "Invalid configuration format."
                }
            },
            onDismiss = { showImportDialog = false }
        )
    }

    if (showExportDialog) {
        ExportConfigDialog(
            onExport = { name, notes ->
                viewModel.exportCurrentConfig(name, notes)
                snackbarMessage = "Exported & copied to clipboard (.ehnf format)!"
                showExportDialog = false
            },
            onDismiss = { showExportDialog = false }
        )
    }
}

@Composable
fun ImportConfigDialog(
    onImport: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var textInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = WhitePure,
        title = {
            Text("Import .ehnf / .npvt Config", color = BlueTextPrimary, fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text(
                    "Paste configuration JSON or exported code:",
                    color = BlueTextSecondary,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    placeholder = { Text("{\n  \"name\": \"Custom\", ...\n}", color = BlueTextMuted) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .testTag("input_import_json"),
                    textStyle = androidx.compose.ui.text.TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BluePrimary,
                        unfocusedBorderColor = BlueBorderSubtle,
                        focusedTextColor = BlueTextPrimary,
                        unfocusedTextColor = BlueTextPrimary,
                        focusedContainerColor = BlueLightBg,
                        unfocusedContainerColor = BlueLightBg
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onImport(textInput) },
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) {
                Text("Import", color = WhitePure, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = BlueTextSecondary)
            }
        }
    )
}

@Composable
fun ExportConfigDialog(
    onExport: (name: String, notes: String) -> Unit,
    onDismiss: () -> Unit
) {
    var configName by remember { mutableStateOf("Elhnafy Custom Config") }
    var notes by remember { mutableStateOf("Fast proxy tunnel profile") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = WhitePure,
        title = {
            Text("Export .ehnf Profile", color = BlueTextPrimary, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Config Profile Name:", color = BlueTextSecondary, fontSize = 12.sp)
                OutlinedTextField(
                    value = configName,
                    onValueChange = { configName = it },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BluePrimary,
                        unfocusedBorderColor = BlueBorderSubtle,
                        focusedTextColor = BlueTextPrimary,
                        unfocusedTextColor = BlueTextPrimary,
                        focusedContainerColor = BlueLightBg,
                        unfocusedContainerColor = BlueLightBg
                    )
                )

                Text("Description / Notes:", color = BlueTextSecondary, fontSize = 12.sp)
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BluePrimary,
                        unfocusedBorderColor = BlueBorderSubtle,
                        focusedTextColor = BlueTextPrimary,
                        unfocusedTextColor = BlueTextPrimary,
                        focusedContainerColor = BlueLightBg,
                        unfocusedContainerColor = BlueLightBg
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onExport(configName, notes) },
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) {
                Text("Export & Copy", color = WhitePure, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = BlueTextSecondary)
            }
        }
    )
}
