package cn.qw.easy_event

import android.os.Bundle

/**
 * 添加类注释
 *
 * @author panfei.pf
 * @since 2021/11/8 9:44
 * @slogan I wave my pad and keyboard, and swear to write the world clearly.
 */
open class Observer {
    open fun onSubscribe(event: Int, options: Bundle) {}
    open fun subscribeEventList(): IntArray {
        return intArrayOf()
    }
}