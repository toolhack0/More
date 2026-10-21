package com.example.data

import com.example.model.CarrierType
import com.example.model.NetworkTweak
import com.example.model.ServerNode
import com.example.model.TunnelConfig
import com.example.model.TunnelProtocol

object PresetConfigs {

    val servers = listOf(
        ServerNode(
            id = "auto_server",
            name = "Auto Select Server",
            country = "Random Server",
            flag = "🌐",
            ip = "159.69.144.20",
            pingMs = 24,
            load = 20,
            protocolBadge = "Auto",
            isAuto = true
        ),
        ServerNode(
            id = "vipv2_1",
            name = "Server - VIPV2 (1)",
            country = "Germany",
            flag = "🇩🇪",
            ip = "159.69.144.20",
            pingMs = 38,
            load = 42,
            protocolBadge = "V2Ray"
        ),
        ServerNode(
            id = "vipv2_2",
            name = "Server - VIPV2 (2)",
            country = "Canada",
            flag = "🇨🇦",
            ip = "142.93.155.60",
            pingMs = 85,
            load = 32,
            protocolBadge = "V2Ray"
        ),
        ServerNode(
            id = "vipv2_3",
            name = "Server - VIPV2 (3)",
            country = "South Africa",
            flag = "🇿🇦",
            ip = "102.130.114.10",
            pingMs = 110,
            load = 28,
            protocolBadge = "V2Ray"
        ),
        ServerNode(
            id = "vipv2_4",
            name = "Server - VIPV2 (4)",
            country = "United States",
            flag = "🇺🇸",
            ip = "104.244.72.115",
            pingMs = 76,
            load = 45,
            protocolBadge = "V2Ray"
        ),
        ServerNode(
            id = "vipv2_5",
            name = "Server - VIPV2 (5)",
            country = "Thailand",
            flag = "🇹🇭",
            ip = "128.199.202.14",
            pingMs = 135,
            load = 25,
            protocolBadge = "V2Ray"
        ),
        ServerNode(
            id = "vipv2_6",
            name = "Server - VIPV2 (6)",
            country = "France",
            flag = "🇫🇷",
            ip = "51.159.22.45",
            pingMs = 42,
            load = 38,
            protocolBadge = "V2Ray"
        ),
        ServerNode(
            id = "vipv2_7",
            name = "Server - VIPV2 (7)",
            country = "United Kingdom",
            flag = "🇬🇧",
            ip = "178.62.11.89",
            pingMs = 49,
            load = 40,
            protocolBadge = "V2Ray"
        ),
        ServerNode(
            id = "vipv2_8",
            name = "Server - VIPV2 (8)",
            country = "Netherlands",
            flag = "🇳🇱",
            ip = "185.220.101.5",
            pingMs = 44,
            load = 50,
            protocolBadge = "V2Ray"
        ),
        ServerNode(
            id = "vipv2_9",
            name = "Server - VIPV2 (9)",
            country = "Egypt Cairo",
            flag = "🇪🇬",
            ip = "197.34.18.90",
            pingMs = 18,
            load = 65,
            protocolBadge = "V2Ray"
        )
    )

    // Complete list of network tweaks exactly as shown in original Hamo Tunnel Plus screenshots
    val networkTweaks = listOf(
        // Etisalat Egypt
        NetworkTweak(
            id = "eti_v2",
            title = "{e&} Etisalat [V2]",
            subtitle = "نت اتصالات ببلاش يومي (VIP)",
            carrierType = CarrierType.ETISALAT,
            sniHost = "web.whatsapp.net"
        ),
        NetworkTweak(
            id = "eti_v2_vip",
            title = "{e&&} Etisalat [V2-VIP⚡]",
            subtitle = "خطوط اتصالات مجانية كل يوم {VIP}",
            carrierType = CarrierType.ETISALAT,
            sniHost = "free.facebook.com"
        ),
        NetworkTweak(
            id = "eti_gift_daily",
            title = "خطوط أي أند - للهديه اليومية Etisalat",
            subtitle = "استخدام عرض الهديه اولا 🎁",
            carrierType = CarrierType.ETISALAT,
            sniHost = "myetisalat.etisalat.eg"
        ),
        NetworkTweak(
            id = "eti_2026_q3",
            title = "{3} Etisalat2026 = اعلى جوده 🇪🇬",
            subtitle = "المتوقع انترنت مجانا يومي تصل الي تخطي او تثبيت سوشيال",
            carrierType = CarrierType.ETISALAT,
            sniHost = "m.youtube.com"
        ),
        NetworkTweak(
            id = "eti_2026_q4",
            title = "{4} Etisalat2026 = اعلى جوده 🇪🇬",
            subtitle = "انترنت لجميع شبكات اتصالات - اذا لم يعمل فعل وضع طيران والغي",
            carrierType = CarrierType.ETISALAT,
            sniHost = "speed.cloudflare.com"
        ),
        NetworkTweak(
            id = "eti_2026_q5",
            title = "{5} Etisalat2026 = اعلى جوده 🇪🇬",
            subtitle = "انترنت اتصالات - تثبيت هدية يوميا - تخطي ميجات مع البعض",
            carrierType = CarrierType.ETISALAT,
            sniHost = "web.whatsapp.net"
        ),
        NetworkTweak(
            id = "eti_wifi_unlimited",
            title = "[🇪🇬][EGY] [Wifi ETISALAT WE UNLIMITED]",
            subtitle = "—Elhnafy V",
            carrierType = CarrierType.ETISALAT,
            sniHost = "ekb.eg"
        ),
        NetworkTweak(
            id = "eti_router_1",
            title = "1 رفع سرعه رواتر اتصالات{Etisalat}",
            subtitle = "جميع رواتر اتصالات الخلصانه",
            carrierType = CarrierType.ETISALAT,
            sniHost = "speed.cloudflare.com"
        ),
        NetworkTweak(
            id = "eti_router_2",
            title = "2 رفع سرعه رواتر اتصالات{Etisalat}",
            subtitle = "تاكد ان الرواتر خلصان نت ومتبقي ايام",
            carrierType = CarrierType.ETISALAT,
            sniHost = "fast.com"
        ),
        NetworkTweak(
            id = "eti_router_3",
            title = "3 رفع سرعه رواتر اتصالات{Etisalat}",
            subtitle = "رفع سرعة جميع رواتر اتصالات الخلصانه",
            carrierType = CarrierType.ETISALAT,
            sniHost = "cloudflare.com"
        ),
        NetworkTweak(
            id = "eti_free_5g",
            title = "نت مجانا اتصالات{Etisalat}",
            subtitle = "اتصالات فائق 5G",
            carrierType = CarrierType.ETISALAT,
            sniHost = "web.whatsapp.net"
        ),
        NetworkTweak(
            id = "eti_save_pack",
            title = "وفر الباقة{Etisalat}",
            subtitle = "Everyday Internet Assistant",
            carrierType = CarrierType.ETISALAT,
            sniHost = "free.facebook.com"
        ),
        NetworkTweak(
            id = "eti_ramadan_1",
            title = "رمضان 2026 | Etisalat 1",
            subtitle = "تثبيت ميجات الهدية اليومية - انترنت فري",
            carrierType = CarrierType.ETISALAT,
            sniHost = "myetisalat.etisalat.eg"
        ),
        NetworkTweak(
            id = "eti_business_3egp",
            title = "باقة 3جنيه اتصالات بيزنس Etisalat",
            subtitle = "استخدام المضيف بعد تفعيل الباقة من ماي",
            carrierType = CarrierType.ETISALAT,
            sniHost = "web.whatsapp.net"
        ),

        // Vodafone Egypt
        NetworkTweak(
            id = "voda_net4k",
            title = "{Vodafone}{Net4K}",
            subtitle = "فودافون ميجات نت كل يوم",
            carrierType = CarrierType.VODAFONE,
            sniHost = "web.whatsapp.net"
        ),
        NetworkTweak(
            id = "voda_free_gold",
            title = "{Vodafone}{Free}Gold",
            subtitle = "فودافون كل الانظمه",
            carrierType = CarrierType.VODAFONE,
            sniHost = "free.facebook.com"
        ),
        NetworkTweak(
            id = "voda_1gb_daily",
            title = "{1GBVodafone} فودا نت يومي",
            subtitle = "نت مجانا لخطوط فودافون كل يوم",
            carrierType = CarrierType.VODAFONE,
            sniHost = "live.vodafone.com.eg"
        ),
        NetworkTweak(
            id = "voda_onfire_2026",
            title = "(Vodafone)OnFire2026 🔥",
            subtitle = "باقات فليكس خلصانة ومنتهية",
            carrierType = CarrierType.VODAFONE,
            sniHost = "m.tiktok.com"
        ),
        NetworkTweak(
            id = "voda_free_net_all",
            title = "نت لخط فودافون ببلاش (Vodafone)",
            subtitle = "انترنت لكل خطوط فودافون بدون باقة",
            carrierType = CarrierType.VODAFONE,
            sniHost = "free.facebook.com"
        ),
        NetworkTweak(
            id = "voda_14pt",
            title = "خط فودافون اي نظام - [Vodafone]",
            subtitle = "انترنت يوميا فودافون نظام 14قرش",
            carrierType = CarrierType.VODAFONE,
            sniHost = "web.whatsapp.net"
        ),
        NetworkTweak(
            id = "voda_flex_system",
            title = "انظمة فليكس جميع خطوط فودافون",
            subtitle = "باقات فليكس المنتهية او الخلصانة خط فودافون",
            carrierType = CarrierType.VODAFONE,
            sniHost = "zoom.us"
        ),
        NetworkTweak(
            id = "voda_wifi_1",
            title = "Egypt Wifi Vodafone [1] 🇪🇬",
            subtitle = "Egypt Wi-Fi Vodafone",
            carrierType = CarrierType.VODAFONE,
            sniHost = "speed.cloudflare.com"
        ),
        NetworkTweak(
            id = "voda_wifi_2",
            title = "Egypt Wifi Vodafone [2] 🇪🇬",
            subtitle = "Egypt Wi-Fi Vodafone",
            carrierType = CarrierType.VODAFONE,
            sniHost = "googlevideo.com"
        ),
        NetworkTweak(
            id = "voda_wifi_3",
            title = "Egypt Wifi Vodafone [3] 🇪🇬",
            subtitle = "Egypt Wi-Fi Vodafone",
            carrierType = CarrierType.VODAFONE,
            sniHost = "fast.com"
        ),
        NetworkTweak(
            id = "voda_flex_egypt",
            title = "Egypt- Vodafone Flex 🇪🇬",
            subtitle = "كل باقات فليكس شبكات فودافون - والمزيد",
            carrierType = CarrierType.VODAFONE,
            sniHost = "free.facebook.com"
        ),

        // WE & Router Wi-Fi
        NetworkTweak(
            id = "we_router_boost_1",
            title = "مسَرّع الراوتر وي الخلصان (1) Wifi 🔋",
            subtitle = "تثبيت ورفع سرعة لرواتر وي المنتهية",
            carrierType = CarrierType.WE_WIFI,
            sniHost = "ekb.eg"
        ),
        NetworkTweak(
            id = "we_router_boost_2",
            title = "رفع سرعات رواتر وي (+WE) [2] Wifi 🔋",
            subtitle = "جميع رواتر we الخلصانه جيجات ومتبقي ايام",
            carrierType = CarrierType.WE_WIFI,
            sniHost = "speed.cloudflare.com"
        ),
        NetworkTweak(
            id = "we_router_boost_free",
            title = "رفع سرعة رواتر we = جيجات فري Wifi",
            subtitle = "تثبيت جيجات رواتر مشحون = رفع سرعة رواتر",
            carrierType = CarrierType.WE_WIFI,
            sniHost = "ekb.eg"
        ),
        NetworkTweak(
            id = "we_increased_2026",
            title = "Wifi we Increased speed 2026",
            subtitle = "تثبيت جيجات واي فاي we",
            carrierType = CarrierType.WE_WIFI,
            sniHost = "speed.cloudflare.com"
        ),
        NetworkTweak(
            id = "we_stabilize_wifi",
            title = "تثبيت جيجات واي فاي [Wifi we]",
            subtitle = "تثبيت ورفع رواتر we",
            carrierType = CarrierType.WE_WIFI,
            sniHost = "cloudflare.com"
        ),
        NetworkTweak(
            id = "we_landline_raise",
            title = "Wifi رفع سرعة الانترنت الارضي وي 📡",
            subtitle = "شرط انتهاء الجيجات ومتبقي ايام في الشهر",
            carrierType = CarrierType.WE_WIFI,
            sniHost = "ekb.eg"
        ),
        NetworkTweak(
            id = "we_wifi_10g",
            title = "تثبيت جيجات الانترنت Wifi10G الارضي وي",
            subtitle = "تثبيت جيجات من الرواتر",
            carrierType = CarrierType.WE_WIFI,
            sniHost = "fast.com"
        ),
        NetworkTweak(
            id = "we_weak_router",
            title = "زياده السرعة للرواتر الضعيفة Wifi 🔵",
            subtitle = "استرجاع السرعة للوي فاي المنتهي",
            carrierType = CarrierType.WE_WIFI,
            sniHost = "speed.cloudflare.com"
        ),

        // Orange Egypt
        NetworkTweak(
            id = "ora_free_egypt",
            title = "اورنج مجانا - Egypt 🇪🇬",
            subtitle = "من فضلك فعل وضع الطيران عده مرات لحين الاتصال ✅",
            carrierType = CarrierType.ORANGE,
            sniHost = "free.facebook.com"
        ),
        NetworkTweak(
            id = "ora_msg_2026",
            title = "انترنت مسدج اورنج = 2026 🍊",
            subtitle = "انترنت واتساب = فري اورنج",
            carrierType = CarrierType.ORANGE,
            sniHost = "web.whatsapp.net"
        ),
        NetworkTweak(
            id = "ora_voda_line",
            title = "line | Vodafone + Orange {🍊}",
            subtitle = "خطوط فودافون و اورنج",
            carrierType = CarrierType.ORANGE,
            sniHost = "free.facebook.com"
        ),
        NetworkTweak(
            id = "ora_cocktails",
            title = "اورنج وفودافون - Cocktails",
            subtitle = "بعض الخطوط في اورنج - ولكل خطوط فودافون",
            carrierType = CarrierType.ORANGE,
            sniHost = "zoom.us"
        ),
        NetworkTweak(
            id = "ora_mnos_udp",
            title = "Orange MNOs(UDP)",
            subtitle = "يجيب عليك تفعيل وضع الطيران ثم الغاء ثم تشغيل الخادم",
            carrierType = CarrierType.ORANGE,
            sniHost = "speed.cloudflare.com",
            protocol = TunnelProtocol.SLOW_DNS
        ),

        // Social Bundles (تحويل الميجات)
        NetworkTweak(
            id = "social_yt",
            title = "تحويل للكل ميجات YouTube 📺",
            subtitle = "ميجات اليوتيوب - فودافون - اتصالات - المزيد",
            carrierType = CarrierType.SOCIAL,
            sniHost = "googlevideo.com"
        ),
        NetworkTweak(
            id = "social_wa",
            title = "تحويل للكل ميجات WhatsApp 💬",
            subtitle = "باقات الواتساب - بيزنس اتصالات - فودافون -",
            carrierType = CarrierType.SOCIAL,
            sniHost = "web.whatsapp.net"
        ),
        NetworkTweak(
            id = "social_fb",
            title = "تحويل للكل ميجات الفيسبوك Facebook 🔵",
            subtitle = "تحويل جميع ميجات الفيسبوك",
            carrierType = CarrierType.SOCIAL,
            sniHost = "free.facebook.com"
        ),
        NetworkTweak(
            id = "social_pubg",
            title = "تحويل للكل ميجات باقات ببجي PUBG 🎮",
            subtitle = "باقات ببجي من خط فودافون - اتصالات - رواتر وي",
            carrierType = CarrierType.SOCIAL,
            sniHost = "speed.cloudflare.com"
        ),
        NetworkTweak(
            id = "social_tiktok",
            title = "تحويل ميجات التيك توك TikTok 🎵",
            subtitle = "تحويل ميجات التيك توك لكل المنصات",
            carrierType = CarrierType.SOCIAL,
            sniHost = "m.tiktok.com"
        ),
        NetworkTweak(
            id = "social_snap",
            title = "تحويل ميجات سناب شات Snapchat 👻",
            subtitle = "تحويل ميجابايت عروض سناب شات",
            carrierType = CarrierType.SOCIAL,
            sniHost = "app.snapchat.com"
        ),
        NetworkTweak(
            id = "social_twitter",
            title = "تحويل ميجات تويتر Twitter 🐦",
            subtitle = "تحويل عروض منصة تويتر",
            carrierType = CarrierType.SOCIAL,
            sniHost = "api.twitter.com"
        ),
        NetworkTweak(
            id = "social_shahid",
            title = "تحويل للكل ميجات تطبيق شاهد 🎬",
            subtitle = "ميجات موقع شاهد المسلسلات",
            carrierType = CarrierType.SOCIAL,
            sniHost = "shahid.mbc.net"
        ),

        // eSIM & Global
        NetworkTweak(
            id = "esim_firsty",
            title = "esim fristy{🌐}",
            subtitle = "High speed free internet",
            carrierType = CarrierType.ESIM,
            sniHost = "speed.cloudflare.com"
        ),
        NetworkTweak(
            id = "esim_go",
            title = "eSIM GO - Electronic chips(🌐)",
            subtitle = "E - SIM",
            carrierType = CarrierType.ESIM,
            sniHost = "free.facebook.com"
        ),
        NetworkTweak(
            id = "esim_redbull",
            title = "redbull mobile esim{🌐}",
            subtitle = "انترنت بعد انتهاء ميجات الشريحة esim",
            carrierType = CarrierType.ESIM,
            sniHost = "speed.cloudflare.com"
        ),
        NetworkTweak(
            id = "mobily_saudi",
            title = "[🇸🇦]Saudi|Mobily 04 20 60",
            subtitle = "Saudi|Mobily 04 20 60 🔥",
            carrierType = CarrierType.GLOBAL,
            sniHost = "web.whatsapp.net"
        ),
        NetworkTweak(
            id = "morocco_inwi",
            title = "Morocco Inwi6 ⭐ [30DH] OVPN 🇲🇦",
            subtitle = "Morocco Inwi",
            carrierType = CarrierType.GLOBAL,
            sniHost = "web.whatsapp.net"
        ),
        NetworkTweak(
            id = "iraq_asiasell",
            title = "اسياسيل وزين العراق 🌍",
            subtitle = "انترنت اسياسيل وزين العراق",
            carrierType = CarrierType.GLOBAL,
            sniHost = "free.facebook.com"
        )
    )

    val defaultConfigs = listOf(
        TunnelConfig(
            id = "cfg_voda_100mb",
            name = "100MB Vodafone daily",
            protocol = TunnelProtocol.SSL_TLS,
            serverId = "vipv2_1",
            sniHost = "web.whatsapp.net",
            notes = "فودافون 100 ميجا يومي مجانا - تخطي باقات وتجديد تلقائي",
            author = "Elhnafy V Official"
        ),
        TunnelConfig(
            id = "cfg_eti_300mb",
            name = "300 ميجا خطوط اتصالات هدية",
            protocol = TunnelProtocol.SSL_TLS,
            serverId = "vipv2_9",
            sniHost = "myetisalat.etisalat.eg",
            notes = "تثبيت وتجديد هدية اتصالات اليومية 300 ميجا مجانا",
            author = "Elhnafy V Official"
        ),
        TunnelConfig(
            id = "cfg_orange_unlimited",
            name = "Orange Unlimited Free Net 💥",
            protocol = TunnelProtocol.SSL_PAYLOAD,
            serverId = "vipv2_1",
            sniHost = "free.facebook.com",
            payload = "CONNECT [host_port] HTTP/1.1[crlf]Host: free.facebook.com[crlf]X-Online-Host: free.facebook.com[crlf]Connection: Keep-Alive[crlf][crlf]",
            notes = "اورنج انترنت مفتوح وتخطي بعد تفعيل وضع الطيران والالغاء",
            author = "Elhnafy V Official"
        ),
        TunnelConfig(
            id = "cfg_we_wifi_vip5",
            name = "WE Wi-Fi Boost VIP 5",
            protocol = TunnelProtocol.SSL_TLS,
            serverId = "vipv2_9",
            sniHost = "ekb.eg",
            notes = "رفع سرعة وتثبيت جيجات راوتر وي بعد الانتهاء",
            author = "Elhnafy V Official"
        ),
        TunnelConfig(
            id = "cfg_we_boost_hamo",
            name = "رفع سرعة وي (WE Landline Boost)",
            protocol = TunnelProtocol.SSL_TLS,
            serverId = "vipv2_9",
            sniHost = "speed.cloudflare.com",
            notes = "استرجاع سرعة راوتر وي الخلصان ومتبقي أيام في الشهر",
            author = "Elhnafy V Official"
        ),
        TunnelConfig(
            id = "cfg_better_esim",
            name = "Better eSIM High Speed",
            protocol = TunnelProtocol.SSL_TLS,
            serverId = "vipv2_4",
            sniHost = "speed.cloudflare.com",
            notes = "شريحة الكترونية eSIM نت مجاني عالي السرعة",
            author = "Elhnafy V Official"
        ),
        TunnelConfig(
            id = "cfg_esim_hamovip",
            name = "eSIM Elhnafy VIP 🦅🔥",
            protocol = TunnelProtocol.SSL_TLS,
            serverId = "vipv2_6",
            sniHost = "cloudflare.com",
            notes = "سيرفر شريحة eSIM VIP فائق السرعة",
            author = "Elhnafy V Official"
        ),
        TunnelConfig(
            id = "cfg_firsty_esim",
            name = "Firsty eSIM Free Internet",
            protocol = TunnelProtocol.SSL_TLS,
            serverId = "vipv2_4",
            sniHost = "speed.cloudflare.com",
            notes = "Firsty eSIM شريحة الكترونية بدون رصيد",
            author = "Elhnafy V Official"
        ),
        TunnelConfig(
            id = "cfg_redbull_esim",
            name = "Redbull Mobile eSIM",
            protocol = TunnelProtocol.SSL_TLS,
            serverId = "vipv2_8",
            sniHost = "speed.cloudflare.com",
            notes = "Redbull Mobile شريحة الكترونية انترنت مجاني",
            author = "Elhnafy V Official"
        )
    )
}
