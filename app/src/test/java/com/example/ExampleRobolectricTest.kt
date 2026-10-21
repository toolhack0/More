package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.TunnelProtocol
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Elhnafy V", appName)
  }

  @Test
  fun `test config json serialization in android environment`() {
    val json = JSONObject().apply {
      put("name", "Elhnafy Custom Config")
      put("protocol", TunnelProtocol.SSL_TLS.name)
      put("serverId", "eg_cairo")
      put("sniHost", "web.whatsapp.net")
    }

    assertEquals("Elhnafy Custom Config", json.getString("name"))
    assertEquals("SSL_TLS", json.getString("protocol"))
    assertEquals("web.whatsapp.net", json.getString("sniHost"))
  }
}
