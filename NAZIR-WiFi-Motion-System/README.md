# NAZIR Wi-Fi Motion Lab

Starter project: Android TV samples its connected Wi-Fi RSSI and sends it to a Windows PC dashboard on the same LAN.

**Limitations:** RSSI changes are not proof of human movement and cannot identify, locate, or count people. This is not CSI sensing. Use only in a space where everyone knows about and agrees to the experiment.

## Build
1. Upload this folder's contents to a GitHub repository.
2. Open Actions and run **Build NAZIR Wi-Fi Motion Lab**.
3. Download artifact `nazir-wifi-motion-builds`.
4. Install `NAZIR-WiFi-TV.apk` on Android TV.
5. Extract and run `NAZIR-WiFi-Receiver.exe` on Windows.
6. Allow it through Windows Firewall on Private networks only.
7. Open `http://localhost:8765`, enter the PC's LAN IPv4 address in the TV app, then Start.

Find PC IPv4 using `ipconfig`. TV and PC must be on the same home network. Keep the TV app open while testing.
