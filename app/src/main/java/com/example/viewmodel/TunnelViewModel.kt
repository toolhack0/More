package com.example.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.PresetConfigs
import com.example.model.ConnectionStatus
import com.example.model.LogLevel
import com.example.model.NetworkTweak
import com.example.model.ServerNode
import com.example.model.TrafficStats
import com.example.model.TunnelConfig
import com.example.model.TunnelProtocol
import com.example.vpn.ElhnafyVpnService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class HostCheckResult(
    val host: String,
    val statusCode: Int = 0,
    val statusMessage: String = "",
    val pingMs: Long = 0,
    val serverHeader: String = "",
    val isSuccess: Boolean = false,
    val error: String? = null
)

class TunnelViewModel(application: Application) : AndroidViewModel(application) {

    val connectionStatus: StateFlow<ConnectionStatus> = ElhnafyVpnService.status
    val trafficStats: StateFlow<TrafficStats> = ElhnafyVpnService.stats
    val logEntries = ElhnafyVpnService.logs

    val serverList: List<ServerNode> = PresetConfigs.servers
    val networkTweaks: List<NetworkTweak> = PresetConfigs.networkTweaks

    private val _selectedServer = MutableStateFlow(PresetConfigs.servers[0])
    val selectedServer: StateFlow<ServerNode> = _selectedServer.asStateFlow()

    private val _selectedNetworkTweak = MutableStateFlow(PresetConfigs.networkTweaks[0])
    val selectedNetworkTweak: StateFlow<NetworkTweak> = _selectedNetworkTweak.asStateFlow()

    private val _selectedProtocol = MutableStateFlow(TunnelProtocol.SSL_TLS)
    val selectedProtocol: StateFlow<TunnelProtocol> = _selectedProtocol.asStateFlow()

    private val _sniHost = MutableStateFlow(PresetConfigs.networkTweaks[0].sniHost)
    val sniHost: StateFlow<String> = _sniHost.asStateFlow()

    private val _customPayload = MutableStateFlow(
        "CONNECT [host_port] [protocol][crlf]Host: [host][crlf]X-Online-Host: [host][crlf]Connection: Keep-Alive[crlf][crlf]"
    )
    val customPayload: StateFlow<String> = _customPayload.asStateFlow()

    private val _proxyHost = MutableStateFlow("10.0.0.1")
    val proxyHost: StateFlow<String> = _proxyHost.asStateFlow()

    private val _proxyPort = MutableStateFlow(8080)
    val proxyPort: StateFlow<Int> = _proxyPort.asStateFlow()

    // Configs list
    private val _savedConfigs = MutableStateFlow<List<TunnelConfig>>(PresetConfigs.defaultConfigs)
    val savedConfigs: StateFlow<List<TunnelConfig>> = _savedConfigs.asStateFlow()

    // Host Checker state
    private val _hostCheckLoading = MutableStateFlow(false)
    val hostCheckLoading: StateFlow<Boolean> = _hostCheckLoading.asStateFlow()

    private val _hostCheckResult = MutableStateFlow<HostCheckResult?>(null)
    val hostCheckResult: StateFlow<HostCheckResult?> = _hostCheckResult.asStateFlow()

    // UI Drawer state
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _timerNotificationDisabled = MutableStateFlow(false)
    val timerNotificationDisabled: StateFlow<Boolean> = _timerNotificationDisabled.asStateFlow()

    // Tunnel Settings
    var mtu: Int = 1500
    var primaryDns: String = "1.1.1.1"
    var secondaryDns: String = "8.8.8.8"
    var udpGwPort: Int = 7300
    var enableCompression: Boolean = true
    var enableWakelock: Boolean = true

    // Hotspot Tethering
    private val _hotspotProxyActive = MutableStateFlow(false)
    val hotspotProxyActive: StateFlow<Boolean> = _hotspotProxyActive.asStateFlow()

    fun selectServer(server: ServerNode) {
        _selectedServer.value = server
        ElhnafyVpnService.addLog(LogLevel.INFO, "Selected Server: ${server.name} (${server.country})")
    }

    fun selectNetworkTweak(tweak: NetworkTweak) {
        _selectedNetworkTweak.value = tweak
        _sniHost.value = tweak.sniHost
        if (tweak.payload.isNotBlank()) {
            _customPayload.value = tweak.payload
        }
        _selectedProtocol.value = tweak.protocol
        ElhnafyVpnService.addLog(LogLevel.INFO, "Selected Network: ${tweak.title} [SNI: ${tweak.sniHost}]")
    }

    fun toggleDarkTheme(enable: Boolean) {
        _isDarkTheme.value = enable
    }

    fun toggleTimerNotification(disable: Boolean) {
        _timerNotificationDisabled.value = disable
    }

    fun selectProtocol(protocol: TunnelProtocol) {
        _selectedProtocol.value = protocol
    }

    fun updateSniHost(host: String) {
        _sniHost.value = host
    }

    fun updateCustomPayload(payload: String) {
        _customPayload.value = payload
    }

    fun startVpn() {
        val server = _selectedServer.value
        val tweak = _selectedNetworkTweak.value
        val protocol = _selectedProtocol.value
        val sni = if (protocol.requiresSni || tweak.sniHost.isNotBlank()) _sniHost.value else ""
        val payload = if (protocol.requiresPayload) _customPayload.value else ""

        ElhnafyVpnService.start(
            context = getApplication(),
            serverName = "${server.name} - ${tweak.title}",
            serverIp = server.ip,
            protocol = protocol.title,
            sni = sni,
            payload = payload
        )
    }

    fun stopVpn() {
        ElhnafyVpnService.stop(getApplication())
    }

    fun clearLogs() {
        ElhnafyVpnService.clearLogs()
    }

    fun copyLogsToClipboard() {
        val allLogs = logEntries.value.joinToString("\n") { "[${it.timestamp}] [${it.level.name}] ${it.message}" }
        val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Elhnafy V Logs", allLogs)
        clipboard.setPrimaryClip(clip)
    }

    fun applyConfig(config: TunnelConfig) {
        _selectedProtocol.value = config.protocol
        val matchingServer = serverList.find { it.id == config.serverId } ?: serverList[0]
        _selectedServer.value = matchingServer
        if (config.sniHost.isNotBlank()) _sniHost.value = config.sniHost
        if (config.payload.isNotBlank()) _customPayload.value = config.payload
        if (config.proxyHost.isNotBlank()) {
            _proxyHost.value = config.proxyHost
            _proxyPort.value = config.proxyPort
        }
        ElhnafyVpnService.addLog(
            LogLevel.SUCCESS,
            "Loaded profile: ${config.name}"
        )
    }

    fun importConfigFromJson(jsonText: String): Boolean {
        return try {
            val obj = JSONObject(jsonText)
            val name = obj.optString("name", "Imported Config")
            val protocolStr = obj.optString("protocol", TunnelProtocol.SSL_TLS.name)
            val protocol = try {
                TunnelProtocol.valueOf(protocolStr)
            } catch (_: Exception) {
                TunnelProtocol.SSL_TLS
            }
            val serverId = obj.optString("serverId", "vipv2_1")
            val sni = obj.optString("sniHost", "")
            val payload = obj.optString("payload", "")
            val proxyHost = obj.optString("proxyHost", "")
            val proxyPort = obj.optInt("proxyPort", 8080)
            val author = obj.optString("author", "User")
            val notes = obj.optString("notes", "")

            val imported = TunnelConfig(
                id = "imported_${System.currentTimeMillis()}",
                name = name,
                protocol = protocol,
                serverId = serverId,
                sniHost = sni,
                payload = payload,
                proxyHost = proxyHost,
                proxyPort = proxyPort,
                author = author,
                notes = notes
            )

            val current = _savedConfigs.value.toMutableList()
            current.add(0, imported)
            _savedConfigs.value = current
            applyConfig(imported)
            true
        } catch (e: Exception) {
            ElhnafyVpnService.addLog(LogLevel.ERROR, "Failed to import config: ${e.message}")
            false
        }
    }

    fun exportCurrentConfig(configName: String, notes: String): String {
        val obj = JSONObject().apply {
            put("name", configName)
            put("protocol", _selectedProtocol.value.name)
            put("serverId", _selectedServer.value.id)
            put("sniHost", _sniHost.value)
            put("payload", _customPayload.value)
            put("proxyHost", _proxyHost.value)
            put("proxyPort", _proxyPort.value)
            put("notes", notes)
            put("author", "Elhnafy V")
            put("exportedAt", System.currentTimeMillis())
        }
        val configStr = obj.toString(2)
        val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Elhnafy V Config", configStr)
        clipboard.setPrimaryClip(clip)
        return configStr
    }

    fun checkHost(hostInput: String) {
        val trimmed = hostInput.trim().removePrefix("http://").removePrefix("https://")
        if (trimmed.isBlank()) return

        _hostCheckLoading.value = true
        _hostCheckResult.value = null

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                val client = OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .followRedirects(false)
                    .build()

                val url = if (hostInput.startsWith("http")) hostInput else "https://$trimmed"
                val startTime = System.currentTimeMillis()

                try {
                    val request = Request.Builder()
                        .url(url)
                        .header("User-Agent", "Mozilla/5.0 (Android; Mobile)")
                        .build()

                    client.newCall(request).execute().use { response ->
                        val duration = System.currentTimeMillis() - startTime
                        val serverHeader = response.header("Server") ?: response.header("server") ?: "Unknown"
                        HostCheckResult(
                            host = trimmed,
                            statusCode = response.code,
                            statusMessage = response.message,
                            pingMs = duration,
                            serverHeader = serverHeader,
                            isSuccess = response.code in 200..399
                        )
                    }
                } catch (e: Exception) {
                    val duration = System.currentTimeMillis() - startTime
                    HostCheckResult(
                        host = trimmed,
                        statusCode = 0,
                        statusMessage = "Failed",
                        pingMs = duration,
                        isSuccess = false,
                        error = e.localizedMessage ?: "Connection error"
                    )
                }
            }
            _hostCheckLoading.value = false
            _hostCheckResult.value = result
        }
    }

    fun toggleHotspotProxy() {
        _hotspotProxyActive.value = !_hotspotProxyActive.value
        if (_hotspotProxyActive.value) {
            ElhnafyVpnService.addLog(LogLevel.SUCCESS, "Hotspot Tethering Proxy enabled on 192.168.43.1:8080")
        } else {
            ElhnafyVpnService.addLog(LogLevel.INFO, "Hotspot Tethering Proxy stopped.")
        }
    }

    fun generatePayload(
        method: String,
        targetHost: String,
        injectType: String,
        keepAlive: Boolean,
        onlineHost: Boolean,
        userAgent: Boolean,
        forwardHost: Boolean,
        reverseProxy: Boolean
    ): String {
        val host = if (targetHost.isNotBlank()) targetHost else "[host]"
        val sb = StringBuilder()

        when (injectType) {
            "Front Inject" -> {
                sb.append("$method http://$host/ HTTP/1.1[crlf]")
                sb.append("Host: $host[crlf]")
                if (onlineHost) sb.append("X-Online-Host: $host[crlf]")
                if (forwardHost) sb.append("X-Forward-For: 127.0.0.1[crlf]")
                if (reverseProxy) sb.append("X-Reverse-Proxy: $host[crlf]")
                if (userAgent) sb.append("User-Agent: [ua][crlf]")
                if (keepAlive) sb.append("Connection: Keep-Alive[crlf]")
                sb.append("[crlf]")
                sb.append("CONNECT [host_port] HTTP/1.1[crlf][crlf]")
            }
            "Back Inject" -> {
                sb.append("CONNECT [host_port] HTTP/1.1[crlf][crlf]")
                sb.append("$method http://$host/ HTTP/1.1[crlf]")
                sb.append("Host: $host[crlf]")
                if (onlineHost) sb.append("X-Online-Host: $host[crlf]")
                if (keepAlive) sb.append("Connection: Keep-Alive[crlf]")
                sb.append("[crlf]")
            }
            else -> { // Normal
                sb.append("$method [host_port] [protocol][crlf]")
                sb.append("Host: $host[crlf]")
                if (onlineHost) sb.append("X-Online-Host: $host[crlf]")
                if (forwardHost) sb.append("X-Forward-For: 127.0.0.1[crlf]")
                if (reverseProxy) sb.append("X-Reverse-Proxy: $host[crlf]")
                if (userAgent) sb.append("User-Agent: [ua][crlf]")
                if (keepAlive) sb.append("Connection: Keep-Alive[crlf]")
                sb.append("[crlf]")
            }
        }
        return sb.toString()
    }
}
