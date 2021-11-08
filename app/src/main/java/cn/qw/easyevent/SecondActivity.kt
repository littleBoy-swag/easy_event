package cn.qw.easyevent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.qw.easy_event.EventPoster

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    fun postEvent1(view: View) {
        EventPoster.postEvent(1, Bundle().also { it.putString("hello", "你好") })
    }

    fun postEvent2(view: View) {
        EventPoster.postEvent(2, Bundle().also { it.putString("great", "真棒") })
    }

    fun jumpAct3(view: View) {
        EventPoster.postStickyEvent(3, Bundle().also { it.putString("aha", "啊哈") })
        startActivity(Intent(this, ThirdActivity::class.java))
    }
}