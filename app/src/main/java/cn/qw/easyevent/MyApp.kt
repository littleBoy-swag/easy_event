package cn.qw.easyevent

import android.app.Application
import android.util.Log
import cn.qw.easy_event.EventPoster
import cn.qw.easyevent.lifecycle.ActivityLifecycleHandler

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

        registerActivityLifecycleCallbacks(ActivityLifecycleHandler(object :
            ActivityLifecycleHandler.LifecycleListener {
            override fun onApplicationStopped() {
                Log.w("panfei", "onApplicationStopped")
            }

            override fun onApplicationStarted() {
                Log.w("panfei", "onApplicationStarted")
            }

            override fun onApplicationPaused() {
                Log.w("panfei", "onApplicationPaused")
            }

            override fun onApplicationResumed() {
                Log.w("panfei", "onApplicationResumed")
            }
        }))

    }

}