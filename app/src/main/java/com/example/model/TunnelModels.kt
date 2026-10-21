package com.example.model

enum class TunnelProtocol(
    val title: String,
    val subtitle: String,
    val defaultPort: Int,
    val requiresPayload: Boolean = false,
    val requiresSni: Boolean = false
) {
    SSL_TLS(
        title = "SSL / TLS (SNI Spoof)",
        subtitle = "Zero-rated host spoofing over TLS 1.3",
        defaultPort = 443,
        requiresSni = true
    ),
    CUSTOM_PAYLOAD(
        title = "Custom Payload (HTTP Proxy)",
        subtitle = "Custom HTTP headers & bug host injection",
        defaultPort = 8080,
        requiresPayload = true
    ),
    SSL_PAYLOAD(
        title = "SSL + Custom Payload",
        subtitle = "Encrypted stunnel with HTTP injection",
        defaultPort = 443,
        requiresPayload = true,
        requiresSni = true
    ),
    SSH_DIRECT(
        title = "Direct SSH",
        subtitle = "Pure SSH secure tunnel without proxy",
        defaultPort = 22
    ),
    SLOW_DNS(
        title = "SlowDNS (UDP 53)",
        subtitle = "DNS tunneling for zero-balance SIMs",
        defaultPort = 53
    ),
    V2RAY_WS(
        title = "V2Ray VMess (WS+TLS)",
        subtitle = "WebSocket CDN obfuscated proxy",
        defaultPort = 443,
        requiresSni = true
    )
}

data class ServerNode(
    val id: String,
    val name: String,
    val country: String,
    val flag: String,
    val ip: String,
    val pingMs: Int,
    val load: Int,
    val protocolBadge: String = "V2Ray",
    val isAuto: Boolean = false
)

enum class CarrierType {
    ETISALAT,
    VODAFONE,
    ORANGE,
    WE_WIFI,
    ESIM,
    SOCIAL,
    GLOBAL
}

data class NetworkTweak(
    val id: String,
    val title: String,
    val subtitle: String,
    val carrierType: CarrierType,
    val sniHost: String = "web.whatsapp.net",
    val payload: String = "",
    val protocol: TunnelProtocol = TunnelProtocol.SSL_TLS
)

data class TunnelConfig(
    val id: String,
    val name: String,
    val protocol: TunnelProtocol,
    val serverId: String,
    val sniHost: String = "",
    val payload: String = "",
    val proxyHost: String = "",
    val proxyPort: Int = 8080,
    val notes: String = "",
    val author: String = "Elhnafy V",
    val isLocked: Boolean = false
)

enum class ConnectionStatus {
    DISCONNECTED,
    CONNECTING,
    AUTHENTICATING,
    ESTABLISHING_TUNNEL,
    CONNECTED,
    DISCONNECTING
}

enum class LogLevel {
    INFO,
    SUCCESS,
    WARNING,
    ERROR,
    PROTOCOL
}

data class LogEntry(
    val id: Long = System.nanoTime(),
    val timestamp: String,
    val level: LogLevel,
    val message: String
)

data class TrafficStats(
    val downloadSpeedBps: Long = 0L,
    val uploadSpeedBps: Long = 0L,
    val totalDownloadBytes: Long = 0L,
    val totalUploadBytes: Long = 0L,
    val pingMs: Int = 0,
    val durationSeconds: Long = 0L,
    val virtualIp: String = "10.8.0.2",
    val externalIp: String = "194.36.89.12"
)
