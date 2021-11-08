package cn.qw.easy_event

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * 添加类注释
 *
 * @author panfei.pf
 * @since 2021/11/8 9:53
 * @slogan I wave my pad and keyboard, and swear to write the world clearly.
 */
object AppUtils {

    /**
     * 注册activity生命周期监听
     */
    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                EventPoster.register(p0)
            }

            override fun onActivityStarted(p0: Activity) {

            }

            override fun onActivityResumed(p0: Activity) {
                // 检查粘性事件
                if (EventPoster.observerMap[p0] != null) {
                    EventPoster.stickEventMap.entries.forEach { stickyEvent ->
                        EventPoster.observerMap[p0]?.apply {
                            if (subscribeEventList().contains(stickyEvent.key)) {
                                // 主线程处理
                                EventPoster.publishHandler.post {
                                    onSubscribe(stickyEvent.key, stickyEvent.value)
                                }
                            }
                        }
                    }
                }
            }

            override fun onActivityPaused(p0: Activity) {

            }

            override fun onActivityStopped(p0: Activity) {

            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

            }

            override fun onActivityDestroyed(p0: Activity) {
                EventPoster.unregister(p0)
            }
        })
    }

}