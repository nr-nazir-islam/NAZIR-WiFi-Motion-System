package com.nazir.wifimotion
import android.app.Activity
import android.os.Bundle
import android.net.wifi.WifiManager
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity: Activity() {
 private lateinit var status:TextView
 private lateinit var host:EditText
 @Volatile private var running=false
 override fun onCreate(savedInstanceState:Bundle?) { super.onCreate(savedInstanceState)
  val box=LinearLayout(this).apply { orientation=1; gravity=Gravity.CENTER; setPadding(28,24,28,24); setBackgroundColor(Color.rgb(13,18,31)) }
  fun txt(s:String,n:Float)=TextView(this).apply { text=s; textSize=n; setTextColor(Color.WHITE); gravity=Gravity.CENTER }
  box.addView(txt("NAZIR / WI-FI MOTION LAB",24f))
  box.addView(txt("Connected Wi-Fi RSSI experiment",16f))
  host=EditText(this).apply { hint="PC IPv4, e.g. 192.168.0.108"; setSingleLine(true); setTextColor(-1); setHintTextColor(0xffaaaaaa.toInt()) }; box.addView(host)
  status=txt("Connect TV and PC to the same router.",18f); box.addView(status)
  box.addView(Button(this).apply { text="START"; setOnClickListener { startSending() } })
  box.addView(Button(this).apply { text="STOP"; setOnClickListener { running=false; status.text="Stopped" } })
  box.addView(txt("RSSI is not reliable people detection. No identity or device scanning.",13f))
  setContentView(ScrollView(this).apply { addView(box) })
 }
 private fun startSending() {
  val ip=host.text.toString().trim()
  if(ip.isBlank() || ip.contains(" ") || ip.contains("/")) { status.text="Enter PC IPv4 address"; return }
  if(running)return
  running=true
  thread {
   val wm=getSystemService(Context.WIFI_SERVICE) as WifiManager
   while(running) try {
    @Suppress("DEPRECATION") val r=wm.connectionInfo.rssi
    val c=URL("http://$ip:8765/sample").openConnection() as HttpURLConnection
    c.requestMethod="POST"; c.connectTimeout=2500; c.readTimeout=2500; c.doOutput=true
    c.setRequestProperty("Content-Type","application/json")
    c.outputStream.use { it.write("{\\"rssi\\":$r}".toByteArray()) }
    c.inputStream.close(); c.disconnect()
    runOnUiThread { status.text="Sending RSSI: $r dBm to $ip" }
    Thread.sleep(3000)
   } catch(e:Exception) { runOnUiThread { status.text="Cannot reach PC. Check IP and firewall." }; Thread.sleep(3000) }
  }
 }
 override fun onDestroy(){ running=false; super.onDestroy() }
}
