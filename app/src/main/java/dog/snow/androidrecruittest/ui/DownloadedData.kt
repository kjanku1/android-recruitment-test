package dog.snow.androidrecruittest.ui

data class DownloadedData(val photos: String, val albums: String, val users: String)

fun getData(): DownloadedData {
    val a = "";
    val b = "";
    val c = "";
    return DownloadedData(a, b, c)
}
