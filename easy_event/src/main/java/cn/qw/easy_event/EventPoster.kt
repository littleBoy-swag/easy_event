package cn.qw.easy_event

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * 事件推送
 *
 * @author panfei.pf
 * @since 2021/11/8 9:42
 * @slogan I wave my pad and keyboard, and swear to write the world clearly.
 */
object EventPoster {

    internal val publishHandler = Handler(Looper.getMainLooper())
    internal val observerMap = WeakHashMap<Activity, Observer>()
    internal val stickEventMap = HashMap<Int, Bundle>()

    fun register(application: Application) {
        AppUtils.init(application)
    }

    /**
     * 注册
     */
    internal fun register(activity: Activity) {
        if (observerMap[activity] != null) {
            return
        }
        observerMap[activity] = Observer()
    }

    /**
     * 解除注册
     */
    internal fun unregister(activity: Activity) {
        observerMap.remove(activity)
    }

    /**
     * 设置监听回调
     */
    fun setOnSubscribe(activity: Activity, observer: Observer) {
        observerMap[activity] = observer
    }

    /**
     * 发送事件
     */
    fun postEvent(event: Int, options: Bundle = Bundle()) {
        observerMap.entries.forEach {
            if (it.value.subscribeEventList().contains(event)) {
                // 在主线程处理回调
                publishHandler.post { it.value.onSubscribe(event, options) }
            }
        }
    }

    /**
     * 发送粘性事件
     *
     * 需要注意的是，注册粘性事件的页面在注册Observer时要早于OnResume，
     * 因为处理粘性事件的时机就是在OnResume回调中
     *
     * 见demo
     *
     */
    fun postStickyEvent(event: Int, options: Bundle = Bundle()) {
        // 让想接收该事件的页面也收到
        postEvent(event, options)
        // 保存粘性事件到map，到下个页面的时候接收完需要及时移除，不然会多次回调
        stickEventMap[event] = options
    }

    /**
     * 移除粘性事件
     */
    fun removeStickyEvent(event: Int) {
        stickEventMap.remove(event)
    }

}