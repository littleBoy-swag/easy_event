package cn.qw.easyevent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import cn.qw.easy_event.EventPoster
import cn.qw.easy_event.Observer

class MainActivity : AppCompatActivity() {

    private lateinit var tvContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvContent = findViewById(R.id.tv_content)

        EventPoster.setOnSubscribe(this, object : Observer() {
            override fun onSubscribe(event: Int, options: Bundle) {
                when (event) {
                    1 -> {
                        tvContent.text = options.getString("hello")
                    }
                    2 -> {
                        tvContent.text = options.getString("great")
                    }
                    3 -> {
                        tvContent.text = options.getString("aha")
                    }
                    else -> {
                    }
                }
            }

            override fun subscribeEventList(): IntArray {
                return intArrayOf(1, 2, 3)
            }
        })
    }

    fun jumpAct2(view: View) {
        startActivity(Intent(this, SecondActivity::class.java))
    }

    fun postEvent(view: View) {
        EventPoster.postStickyEvent(1, Bundle().also { it.putString("hello", "页面1你好") })
    }

}