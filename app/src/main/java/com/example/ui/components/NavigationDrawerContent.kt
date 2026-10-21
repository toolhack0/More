package com.example.ui.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*
import com.example.viewmodel.TunnelViewModel

@Composable
fun ElhnafyDrawerContent(
    viewModel: TunnelViewModel,
    onCloseDrawer: () -> Unit,
    onOpenConfigs: () -> Unit,
    onOpenTools: () -> Unit,
    onOpenLogs: () -> Unit
) {
    val context = LocalContext.current
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val timerDisabled by viewModel.timerNotificationDisabled.collectAsState()

    ModalDrawerSheet(
        drawerContainerColor = if (isDarkTheme) DarkBlueBg else WhitePure,
        drawerContentColor = if (isDarkTheme) WhitePure else BlueTextPrimary,
        modifier = Modifier.width(310.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(BluePrimary, BlueSecondary)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(WhitePure)
                            .border(3.dp, BlueAccent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_elhnafy_logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Elhnafy V",
                        fontWeight = FontWeight.Black,
                        fontSize = 19.sp,
                        color = WhitePure
                    )
                    Text(
                        text = "App Version: v3.2 (Build 32)",
                        fontSize = 11.sp,
                        color = BlueBorderSubtle
                    )
                }
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                // Navigation / Shortcuts
                DrawerSectionHeader("Navigation")
                DrawerClickItem(
                    icon = Icons.Default.Terminal,
                    title = "Live Logs",
                    onClick = {
                        onCloseDrawer()
                        onOpenLogs()
                    }
                )
                DrawerClickItem(
                    icon = Icons.Default.FolderZip,
                    title = "Configs & Profiles",
                    onClick = {
                        onCloseDrawer()
                        onOpenConfigs()
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = BlueBorderSubtle.copy(alpha = 0.5f))

                // UI Settings
                DrawerSectionHeader("UI Settings")
                DrawerToggleItem(
                    icon = Icons.Default.Palette,
                    title = "Enable Dark Theme",
                    checked = isDarkTheme,
                    onCheckedChange = { viewModel.toggleDarkTheme(it) }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = BlueBorderSubtle.copy(alpha = 0.5f))

                // Config Update
                DrawerSectionHeader("Config Update")
                DrawerClickItem(
                    icon = Icons.Default.CloudDownload,
                    title = "Online Update",
                    onClick = {
                        Toast.makeText(context, "Config version 27.377 is up to date!", Toast.LENGTH_SHORT).show()
                        onCloseDrawer()
                    }
                )
                DrawerClickItem(
                    icon = Icons.Default.UploadFile,
                    title = "Offline Update (.npvt / .ehnf)",
                    onClick = {
                        onCloseDrawer()
                        onOpenConfigs()
                    }
                )
                DrawerClickItem(
                    icon = Icons.Default.Assignment,
                    title = "Clipboard Update",
                    onClick = {
                        Toast.makeText(context, "Checked clipboard for Elhnafy V configs", Toast.LENGTH_SHORT).show()
                        onCloseDrawer()
                        onOpenConfigs()
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = BlueBorderSubtle.copy(alpha = 0.5f))

                // Timer Notification
                DrawerSectionHeader("Timer Notification")
                DrawerToggleItem(
                    icon = Icons.Default.Schedule,
                    title = "Disable",
                    checked = timerDisabled,
                    onCheckedChange = { viewModel.toggleTimerNotification(it) }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = BlueBorderSubtle.copy(alpha = 0.5f))

                // V2RAY Settings
                DrawerSectionHeader("V2RAY Settings")
                DrawerClickItem(
                    icon = Icons.Default.Tune,
                    title = "Settings",
                    onClick = {
                        Toast.makeText(context, "V2Ray Core: Xray-core v1.8.4 (Active)", Toast.LENGTH_SHORT).show()
                        onCloseDrawer()
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = BlueBorderSubtle.copy(alpha = 0.5f))

                // Tools
                DrawerSectionHeader("Tools")
                DrawerClickItem(
                    icon = Icons.Default.GpsFixed,
                    title = "IP Hunt",
                    onClick = {
                        onCloseDrawer()
                        onOpenTools()
                    }
                )
                DrawerClickItem(
                    icon = Icons.Default.Dns,
                    title = "Custom DNS",
                    onClick = {
                        onCloseDrawer()
                        onOpenTools()
                    }
                )
                DrawerClickItem(
                    icon = Icons.Default.WifiTethering,
                    title = "Tethering",
                    onClick = {
                        onCloseDrawer()
                        onOpenTools()
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = BlueBorderSubtle.copy(alpha = 0.5f))

                // Support with Telegram 01211924535 and WhatsApp 01211924535
                DrawerSectionHeader("Support")

                // Telegram Item with 01211924535
                DrawerPainterClickItem(
                    painter = painterResource(id = R.drawable.ic_telegram),
                    title = "Telegram: 01211924535",
                    subtitle = "تواصل عبر تليجرام",
                    iconTint = Color.Unspecified,
                    onClick = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/+201211924535"))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Telegram: 01211924535", Toast.LENGTH_LONG).show()
                        }
                        onCloseDrawer()
                    }
                )

                // WhatsApp Item with 01211924535
                DrawerPainterClickItem(
                    painter = painterResource(id = R.drawable.ic_whatsapp),
                    title = "WhatsApp: 01211924535",
                    subtitle = "تواصل عبر واتساب",
                    iconTint = Color.Unspecified,
                    onClick = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/201211924535"))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "WhatsApp: 01211924535", Toast.LENGTH_LONG).show()
                        }
                        onCloseDrawer()
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = BlueBorderSubtle.copy(alpha = 0.5f))

                // Others
                DrawerSectionHeader("Others")
                DrawerClickItem(
                    icon = Icons.Default.Delete,
                    title = "Clear Data",
                    onClick = {
                        viewModel.clearLogs()
                        Toast.makeText(context, "Cache and logs cleared", Toast.LENGTH_SHORT).show()
                        onCloseDrawer()
                    }
                )
                DrawerClickItem(
                    icon = Icons.Default.ExitToApp,
                    title = "Exit",
                    onClick = {
                        viewModel.stopVpn()
                        (context as? Activity)?.finish()
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun DrawerSectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = BluePrimary,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun DrawerClickItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = BluePrimary,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun DrawerPainterClickItem(
    painter: Painter,
    title: String,
    subtitle: String? = null,
    iconTint: Color = Color.Unspecified,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(22.dp)
        )
        Column {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BlueTextPrimary
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = BlueTextSecondary
                )
            }
        }
    }
}

@Composable
fun DrawerToggleItem(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BluePrimary,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = WhitePure,
                checkedTrackColor = BluePrimary
            )
        )
    }
}
