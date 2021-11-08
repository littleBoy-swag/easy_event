package cn.qw.easyevent

import android.app.Application
import cn.qw.easy_event.EventPoster

/**
 * 添加类注释
 *
 * @author panfei.pf
 * @since 2021/11/8 10:07
 * @slogan I wave my pad and keyboard, and swear to write the world clearly.
 */
class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        EventPoster.register(this)
    }

}