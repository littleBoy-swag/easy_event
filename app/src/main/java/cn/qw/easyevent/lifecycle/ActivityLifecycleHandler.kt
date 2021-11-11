package cn.qw.easyevent.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle

/**
 * 用于监听App的生命周期
 *
 * @author panfei.pf
 * @since 2021/11/11 9:19
 * @slogan I wave my pad and keyboard, and swear to write the world clearly.
 */
class ActivityLifecycleHandler(private val listener: LifecycleListener) :
    Application.ActivityLifecycleCallbacks {

    private var startedCount = 0
    private var resumedCount = 0
    private var transitionPossible = false
    private var isChangingConfigurations = false

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (startedCount == 0 && !isChangingConfigurations) {
            listener.onApplicationCreated(savedInstanceState)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        if (startedCount == 0 && !isChangingConfigurations) {
            listener.onApplicationStarted()
        }
        startedCount++
    }

    override fun onActivityResumed(activity: Activity) {
        if (resumedCount == 0 && !transitionPossible && !isChangingConfigurations) {
            listener.onApplicationResumed()
        }
        if (isChangingConfigurations) {
            isChangingConfigurations = false
        }
        transitionPossible = false
        resumedCount++
    }

    override fun onActivityPaused(activity: Activity) {
        transitionPossible = true
        resumedCount--
    }

    override fun onActivityStopped(activity: Activity) {
        if (activity.isChangingConfigurations) {
            isChangingConfigurations = true
        }
        if (startedCount == 1 && !activity.isChangingConfigurations) {
            // We only know the application was paused when it's stopped (because transitions always pause activities)
            // http://developer.android.com/guide/components/activities.html#CoordinatingActivities
            if (transitionPossible && resumedCount == 0) {
                listener.onApplicationPaused()
            }
            listener.onApplicationStopped()
        }
        transitionPossible = false
        startedCount--
    }

    override fun onActivitySaveInstanceState(activity: Activity, savedInstanceState: Bundle) {
        // https://developer.android.com/reference/android/app/Activity#onSaveInstanceState(android.os.Bundle)
        // If called, this method will occur after onStop() for applications targeting
        // platforms starting with Build.VERSION_CODES.P.
        // For applications targeting earlier platform versions this method will occur
        // before onStop() and there are no guarantees about whether it will occur
        // before or after onPause().
        val checkCounter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) 0 else 1
        if (startedCount == checkCounter && !activity.isChangingConfigurations) {
            listener.onApplicationSaveInstanceState(savedInstanceState)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (startedCount == 0 && !activity.isChangingConfigurations) {
            listener.onApplicationDestroy()
        }
    }

    interface LifecycleListener {

        /**
         * Called right after the application is created
         */
        fun onApplicationCreated(savedInstanceState: Bundle?) {}

        /**
         * Called right before the application is stopped.
         */
        fun onApplicationStopped()

        /**
         * Called right after the application has been started.
         */
        fun onApplicationStarted()

        /**
         * Called when the application is paused (but still awake).
         */
        fun onApplicationPaused()

        /**
         * Called right after the application has been resumed (come to the foreground).
         */
        fun onApplicationResumed()

        /**
         * Called right after the application is stopped (for Android P and later)
         * Called right before onStop or onPause (for before Android P)
         * https://developer.android.com/reference/android/app/Activity#onSaveInstanceState(android.os.Bundle)
         */
        fun onApplicationSaveInstanceState(savedInstanceState: Bundle) {}

        /**
         * Called when the last activity is destroy
         */
        fun onApplicationDestroy() {}
    }

}