package com.example.chiemtinh
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var astrologyService: AstrologyService
    private val apiKey = "e6149d4c9e7a497fb6f70042242411" // Thay YOUR_API_KEY bằng API key của bạn
    private lateinit var location :EditText
    private val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) // Ngày hiện tại
    private lateinit var tvHoroscope : TextView
    private lateinit var tvlocate:TextView
    private lateinit var sunrise:TextView
    private lateinit var sunset:TextView
    private lateinit var moonrise:TextView
    private lateinit var moonset:TextView
    private lateinit var moonphase:TextView
    private lateinit var tvsunrise:TextView
    private lateinit var tvsunset:TextView
    private lateinit var tvmoonrise:TextView
    private lateinit var tvmoonset:TextView
    private lateinit var tvmoonphase:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        location=findViewById(R.id.edtLocation)
        tvlocate=findViewById(R.id.tvLocation)
        tvsunrise=findViewById(R.id.tvSunrise)
        tvsunset=findViewById(R.id.tvSunset)
        tvmoonrise=findViewById(R.id.tvMoonrise)
        tvmoonset=findViewById(R.id.tvMoonset)
        tvmoonphase=findViewById(R.id.tvMoonPhase)
        tvHoroscope = findViewById(R.id.tvChiemtinh)
        sunrise= TextView(this)
        sunset=TextView(this)
        moonrise=TextView(this)
        moonset=TextView(this)
        moonphase=TextView(this)
        astrologyService = AstrologyService(apiKey)
        var btnGetLocation = findViewById<Button>(R.id.btnGetLocation)
        btnGetLocation.setOnClickListener {
            GlobalScope.launch {
                val astrologyData =
                    astrologyService.getAstrologyData(location.text.toString(), date)
                runOnUiThread {
                    astrologyData?.let {
                        sunrise.text = "${it.astronomy.astro.sunrise}"
                        sunset.text = "${it.astronomy.astro.sunset}"
                        moonrise.text = "${it.astronomy.astro.moonrise}"
                        moonset.text = "${it.astronomy.astro.moonset}"
                        moonphase.text = "${it.astronomy.astro.moon_phase}"
                        tvsunset.text = "Sunset: ${sunset.text}"
                        tvsunrise.text = "Sunrise: ${sunrise.text}"
                        tvmoonrise.text = "Moonrise: ${moonrise.text}"
                        tvmoonset.text = "Moonset: ${moonset.text}"
                        tvmoonphase.text = "Moon Phase: ${moonphase.text}"
                    } ?: run {
                        Toast.makeText(
                            this@MainActivity,
                            "Failed to retrieve data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        val zodiacSigns = arrayOf(
            "Bạch Dương", "Kim Ngưu", "Song Tử", "Cự Giải", "Sư Tử",
            "Xử Nữ", "Thiên Bình", "Bọ Cạp", "Nhân Mã", "Ma Kết",
            "Bảo Bình", "Song Ngư"
        )

        val spinnerZodiac = findViewById<Spinner>(R.id.spinnerZodiac)
        val tvSelectedZodiac = findViewById<TextView>(R.id.tvSelectedZodiac)

// Tạo ArrayAdapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, zodiacSigns)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerZodiac.adapter = adapter

// Xử lý sự kiện chọn
        spinnerZodiac.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedZodiac = zodiacSigns[position]
                tvSelectedZodiac.text = "Đã chọn cung hoàng đạo: $selectedZodiac"
                generateHoroscope(selectedZodiac)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                tvSelectedZodiac.text = "Hãy chọn cung hoàng đạo: "
            }
        }
    }
    private fun generateHoroscope(zodiacSign: String) {

        // Tạo thông điệp chiêm tinh dựa trên dữ liệu
        val horoscopeMessage = when (zodiacSign) {
            "Bạch Dương" -> "Hãy chuẩn bị cho những cơ hội mới khi mặt trời mọc lúc ${sunrise.text}."
            "Kim Ngưu" -> "Thời điểm để đánh giá những gì bạn đã đạt được trước khi mặt trời lặn lúc ${sunset.text}."
            "Song Tử" -> "Giai đoạn mặt trăng ${moonphase.text} sẽ mang lại cảm hứng sáng tạo cho bạn."
            "Cự Giải" -> "Hãy tập trung vào gia đình trong thời gian mặt trăng mọc lúc ${moonrise.text}."
            "Sư Tử" -> "Bạn sẽ tỏa sáng trong giai đoạn mặt trời lặn lúc ${sunset.text}."
            "Xử Nữ" -> "Dành thời gian để lên kế hoạch cho tương lai khi mặt trăng lặn lúc ${moonset.text}."
            "Thiên Bình" -> "Tìm kiếm sự cân bằng giữa công việc và cuộc sống trong giai đoạn này."
            "Bọ Cạp" -> "Hãy lắng nghe trực giác của bạn trong thời gian mặt trăng ${moonphase.text}."
            "Nhân Mã" -> "Khám phá những điều mới mẻ trong ánh sáng mặt trời lúc ${sunrise.text}."
            "Ma Kết" -> "Hãy kiên định với những quyết định của bạn trong thời gian mặt trời lặn lúc ${sunset.text}."
            "Bảo Bình" -> "Sự sáng tạo sẽ thăng hoa trong giai đoạn mặt trăng ${moonphase.text}."
            "Song Ngư" -> "Hãy để cảm xúc dẫn đường bạn trong thời gian mặt trăng mọc lúc ${moonrise.text}."
            else -> "Không có thông điệp chiêm tinh cho bạn."
        }

        // Hiển thị thông điệp chiêm tinh
        tvHoroscope.text = horoscopeMessage
    }
}