package com.example

import android.app.Activity
import android.net.VpnService
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.ui.components.ElhnafyDrawerContent
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.TunnelViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: TunnelViewModel by viewModels()

    private val vpnPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.startVpn()
        } else {
            Toast.makeText(this, "VPN permission required to tunnel traffic", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestVpnAndStart() {
        val vpnIntent = VpnService.prepare(this)
        if (vpnIntent != null) {
            vpnPermissionLauncher.launch(vpnIntent)
        } else {
            viewModel.startVpn()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()
            ElhnafyTheme(darkTheme = isDarkTheme) {
                MainAppWithDrawer(
                    viewModel = viewModel,
                    onRequestVpn = { requestVpnAndStart() }
                )
            }
        }
    }
}

enum class NavigationTab(val title: String, val testTag: String) {
    HOME("Home", "nav_tab_home"),
    LOGS("Logs", "nav_tab_logs"),
    CONFIGS("Configs", "nav_tab_configs"),
    TOOLS("Tools", "nav_tab_tools")
}

@Composable
fun MainAppWithDrawer(
    viewModel: TunnelViewModel,
    onRequestVpn: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentTab by remember { mutableStateOf(NavigationTab.HOME) }

    BackHandler(enabled = drawerState.isOpen || currentTab != NavigationTab.HOME) {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            currentTab = NavigationTab.HOME
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ElhnafyDrawerContent(
                viewModel = viewModel,
                onCloseDrawer = { scope.launch { drawerState.close() } },
                onOpenConfigs = {
                    currentTab = NavigationTab.CONFIGS
                    scope.launch { drawerState.close() }
                },
                onOpenTools = {
                    currentTab = NavigationTab.TOOLS
                    scope.launch { drawerState.close() }
                },
                onOpenLogs = {
                    currentTab = NavigationTab.LOGS
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = BlueLightBg
            // Bottom bar removed as requested to match the original app layout exactly
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentTab) {
                    NavigationTab.HOME -> {
                        HomeScreen(
                            viewModel = viewModel,
                            onRequestVpnPermission = onRequestVpn,
                            onOpenDrawer = { scope.launch { drawerState.open() } },
                            onOpenConfigs = { currentTab = NavigationTab.CONFIGS }
                        )
                    }
                    NavigationTab.LOGS -> {
                        LogsScreen(
                            viewModel = viewModel,
                            onNavigateHome = { currentTab = NavigationTab.HOME }
                        )
                    }
                    NavigationTab.CONFIGS -> {
                        ConfigsScreen(
                            viewModel = viewModel,
                            onNavigateHome = { currentTab = NavigationTab.HOME },
                            onConfigApplied = { currentTab = NavigationTab.HOME }
                        )
                    }
                    NavigationTab.TOOLS -> {
                        ToolsScreen(
                            viewModel = viewModel,
                            onNavigateHome = { currentTab = NavigationTab.HOME }
                        )
                    }
                }
            }
        }
    }
}
