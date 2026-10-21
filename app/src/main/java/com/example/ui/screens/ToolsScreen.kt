package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.TunnelViewModel

@Composable
fun ToolsScreen(
    viewModel: TunnelViewModel,
    onNavigateHome: () -> Unit
) {
    var selectedToolTab by remember { mutableIntStateOf(0) }
    val toolTabs = listOf("Payload Gen", "Host Checker", "Hotspot Share", "Network IP")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueLightBg)
            .statusBarsPadding()
            .padding(16.dp)
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
            Text(
                text = "TUNNEL TOOLS & UTILITIES",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BlueTextPrimary,
                letterSpacing = 0.5.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        // Tool Tabs in Blue & White
        ScrollableTabRow(
            selectedTabIndex = selectedToolTab,
            containerColor = WhitePure,
            contentColor = BluePrimary,
            edgePadding = 0.dp,
            divider = {}
        ) {
            toolTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedToolTab == index,
                    onClick = { selectedToolTab = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedToolTab == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedToolTab == index) BluePrimary else BlueTextSecondary,
                            fontSize = 12.sp
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when (selectedToolTab) {
                0 -> PayloadGeneratorView(viewModel, onNavigateHome)
                1 -> HostCheckerView(viewModel)
                2 -> HotspotShareView(viewModel)
                3 -> NetworkInfoView(viewModel)
            }
        }
    }
}

@Composable
fun PayloadGeneratorView(
    viewModel: TunnelViewModel,
    onNavigateHome: () -> Unit
) {
    val context = LocalContext.current
    var method by remember { mutableStateOf("CONNECT") }
    var hostUrl by remember { mutableStateOf("web.whatsapp.net") }
    var injectType by remember { mutableStateOf("Normal") }

    var keepAlive by remember { mutableStateOf(true) }
    var onlineHost by remember { mutableStateOf(true) }
    var userAgent by remember { mutableStateOf(false) }
    var forwardHost by remember { mutableStateOf(true) }
    var reverseProxy by remember { mutableStateOf(false) }

    var generatedPayload by remember {
        mutableStateOf(
            viewModel.generatePayload(
                method, hostUrl, injectType, keepAlive, onlineHost, userAgent, forwardHost, reverseProxy
            )
        )
    }

    var message by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = WhitePure),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, BlueBorderSubtle),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Target Host / Bug URL:", fontSize = 11.sp, color = BlueTextSecondary, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = hostUrl,
                    onValueChange = { hostUrl = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_payload_host"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BluePrimary,
                        unfocusedBorderColor = BlueBorderSubtle,
                        focusedTextColor = BlueTextPrimary,
                        unfocusedTextColor = BlueTextPrimary,
                        focusedContainerColor = BlueLightBg,
                        unfocusedContainerColor = BlueLightBg
                    )
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("HTTP Method:", fontSize = 11.sp, color = BlueTextSecondary)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("CONNECT", "GET", "POST").forEach { m ->
                                SuggestionChip(
                                    onClick = { method = m },
                                    label = { Text(m, fontSize = 10.sp, color = if (method == m) BluePrimary else BlueTextSecondary) },
                                    colors = SuggestionChipDefaults.suggestionChipColors(
                                        containerColor = if (method == m) BluePillBg else WhitePure
                                    ),
                                    border = SuggestionChipDefaults.suggestionChipBorder(
                                        enabled = true,
                                        borderColor = if (method == m) BluePrimary else BlueBorderSubtle
                                    )
                                )
                            }
                        }
                    }
                }

                Text("Injection Mode:", fontSize = 11.sp, color = BlueTextSecondary)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("Normal", "Front Inject", "Back Inject").forEach { inj ->
                        SuggestionChip(
                            onClick = { injectType = inj },
                            label = { Text(inj, fontSize = 10.sp, color = if (injectType == inj) BluePrimary else BlueTextSecondary) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = if (injectType == inj) BluePillBg else WhitePure
                            ),
                            border = SuggestionChipDefaults.suggestionChipBorder(
                                enabled = true,
                                borderColor = if (injectType == inj) BluePrimary else BlueBorderSubtle
                            )
                        )
                    }
                }

                Text("Extra Injection Headers:", fontSize = 11.sp, color = BlueTextSecondary)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = onlineHost,
                        onCheckedChange = { onlineHost = it },
                        colors = CheckboxDefaults.colors(checkedColor = BluePrimary)
                    )
                    Text("Online Host (X-Online-Host)", fontSize = 11.sp, color = BlueTextPrimary)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = keepAlive,
                        onCheckedChange = { keepAlive = it },
                        colors = CheckboxDefaults.colors(checkedColor = BluePrimary)
                    )
                    Text("Keep-Alive Connection", fontSize = 11.sp, color = BlueTextPrimary)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = forwardHost,
                        onCheckedChange = { forwardHost = it },
                        colors = CheckboxDefaults.colors(checkedColor = BluePrimary)
                    )
                    Text("Forward Host (X-Forward-For)", fontSize = 11.sp, color = BlueTextPrimary)
                }

                Button(
                    onClick = {
                        generatedPayload = viewModel.generatePayload(
                            method, hostUrl, injectType, keepAlive, onlineHost, userAgent, forwardHost, reverseProxy
                        )
                        message = "Payload generated!"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                    modifier = Modifier.fillMaxWidth().testTag("button_generate_payload")
                ) {
                    Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = WhitePure)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Generate Payload", color = WhitePure, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Generated Output Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = WhitePure),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, BlueBorderSubtle),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Raw Payload Output:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF071228), RoundedCornerShape(8.dp))
                        .border(1.dp, BlueBorder, RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = generatedPayload,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = Color(0xFF69F0AE)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("Payload", generatedPayload))
                            message = "Payload copied to clipboard!"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = WhitePure),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, BluePrimary),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, tint = BluePrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Copy", color = BluePrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            viewModel.updateCustomPayload(generatedPayload)
                            message = "Applied to Home tunnel!"
                            onNavigateHome()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = WhitePure, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Apply & Go", color = WhitePure, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        message?.let {
            Text(text = it, color = BluePrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable
fun HostCheckerView(viewModel: TunnelViewModel) {
    var testHost by remember { mutableStateOf("web.whatsapp.net") }
    val isLoading by viewModel.hostCheckLoading.collectAsState()
    val result by viewModel.hostCheckResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = WhitePure),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, BlueBorderSubtle),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Bug Host / SNI Status Scanner",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = BlueTextPrimary
                )
                Text(
                    text = "Test host response codes (200 OK, 302 Found) to find working zero-rated bypass hosts.",
                    fontSize = 11.sp,
                    color = BlueTextSecondary
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = testHost,
                    onValueChange = { testHost = it },
                    singleLine = true,
                    placeholder = { Text("e.g. web.whatsapp.net or ekb.eg", color = BlueTextMuted) },
                    modifier = Modifier.fillMaxWidth().testTag("input_check_host"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BluePrimary,
                        unfocusedBorderColor = BlueBorderSubtle,
                        focusedTextColor = BlueTextPrimary,
                        unfocusedTextColor = BlueTextPrimary,
                        focusedContainerColor = BlueLightBg,
                        unfocusedContainerColor = BlueLightBg
                    )
                )

                Button(
                    onClick = { viewModel.checkHost(testHost) },
                    enabled = !isLoading && testHost.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                    modifier = Modifier.fillMaxWidth().testTag("button_start_check_host")
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), color = WhitePure, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Scanning Host…", color = WhitePure, fontWeight = FontWeight.Bold)
                    } else {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = WhitePure)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Check Host Status", color = WhitePure, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        result?.let { res ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = WhitePure),
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    if (res.isSuccess) StatusGreen else StatusRed
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = res.host,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = BlueTextPrimary
                        )

                        Surface(
                            color = if (res.isSuccess) StatusGreen.copy(alpha = 0.15f) else StatusRed.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = if (res.statusCode > 0) "${res.statusCode} ${res.statusMessage}" else "FAILED",
                                color = if (res.isSuccess) StatusGreen else StatusRed,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Divider(color = BlueBorderSubtle)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Latency", fontSize = 10.sp, color = BlueTextSecondary)
                            Text("${res.pingMs} ms", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = BluePrimary)
                        }
                        Column {
                            Text("Server Header", fontSize = 10.sp, color = BlueTextSecondary)
                            Text(res.serverHeader, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = BlueSecondary)
                        }
                        Column {
                            Text("Bypass Rating", fontSize = 10.sp, color = BlueTextSecondary)
                            Text(
                                text = if (res.statusCode in 200..399) "EXCELLENT" else "BLOCKED",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (res.isSuccess) StatusGreen else StatusRed
                            )
                        }
                    }

                    if (res.error != null) {
                        Text(
                            text = "Error: ${res.error}",
                            color = StatusRed,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HotspotShareView(viewModel: TunnelViewModel) {
    val active by viewModel.hotspotProxyActive.collectAsState()

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = WhitePure),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, BlueBorderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Default.WifiTethering, contentDescription = null, tint = BluePrimary)
                    Column {
                        Text("Wi-Fi Hotspot Sharing", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = BlueTextPrimary)
                        Text("Share VPN connection with PC / TV", fontSize = 11.sp, color = BlueTextSecondary)
                    }
                }

                Switch(
                    checked = active,
                    onCheckedChange = { viewModel.toggleHotspotProxy() },
                    colors = SwitchDefaults.colors(checkedThumbColor = WhitePure, checkedTrackColor = BluePrimary)
                )
            }

            Divider(color = BlueBorderSubtle)

            Text("Setup Instructions for Client Device:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = BluePrimary)

            Text(
                text = "1. Turn on Android Mobile Hotspot\n" +
                        "2. Connect your PC or second phone to this hotspot Wi-Fi\n" +
                        "3. On PC, open Proxy Settings and enter:\n" +
                        "    • Proxy IP: 192.168.43.1\n" +
                        "    • Proxy Port: 8080\n" +
                        "4. All traffic on the second device will now flow through Elhnafy V!",
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                color = BlueTextSecondary,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun NetworkInfoView(viewModel: TunnelViewModel) {
    val status by viewModel.connectionStatus.collectAsState()
    val stats by viewModel.trafficStats.collectAsState()
    val selectedServer by viewModel.selectedServer.collectAsState()

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = WhitePure),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, BlueBorderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("Network Interface Diagnostics", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = BlueTextPrimary)

            Divider(color = BlueBorderSubtle)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("VPN Tunnel Interface:", fontSize = 12.sp, color = BlueTextSecondary)
                Text(if (status == com.example.model.ConnectionStatus.CONNECTED) "tun0 (UP)" else "tun0 (DOWN)", fontWeight = FontWeight.Bold, color = if (status == com.example.model.ConnectionStatus.CONNECTED) StatusGreen else StatusRed)
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Assigned Virtual IP:", fontSize = 12.sp, color = BlueTextSecondary)
                Text(stats.virtualIp, fontFamily = FontFamily.Monospace, color = BlueTextPrimary)
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Target Server IP:", fontSize = 12.sp, color = BlueTextSecondary)
                Text(selectedServer.ip, fontFamily = FontFamily.Monospace, color = BluePrimary)
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("DNS Resolvers:", fontSize = 12.sp, color = BlueTextSecondary)
                Text("1.1.1.1, 8.8.8.8", fontFamily = FontFamily.Monospace, color = BlueTextPrimary)
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("MTU Packet Size:", fontSize = 12.sp, color = BlueTextSecondary)
                Text("${viewModel.mtu} bytes", fontFamily = FontFamily.Monospace, color = BlueTextPrimary)
            }
        }
    }
}
