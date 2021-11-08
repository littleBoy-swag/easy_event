package cn.qw.easyevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import cn.qw.easy_event.EventPoster
import cn.qw.easy_event.Observer

class ThirdActivity : AppCompatActivity() {

    private lateinit var tvContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        tvContent = findViewById(R.id.tv_content)

    }

    override fun onResume() {
        reg() // 最晚要在此处完成setOnSubscribe()
        super.onResume()
    }


    private fun reg() {
        EventPoster.setOnSubscribe(this, object : Observer() {
            override fun onSubscribe(event: Int, options: Bundle) {
                if (event == 3) {
                    tvContent.text = options.getString("aha")
                    EventPoster.removeStickyEvent(3)
                }
            }

            override fun subscribeEventList(): IntArray {
                return intArrayOf(3)
            }

        })
    }

}