# easy_event

## 使用说明

1. 在项目的application中初始化

```kotlin

    EventPoster.register(this)

```

2. 在想要接收的页面进行注册监听

```kotlin

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

```

3. 发送事件

```kotlin

    EventPoster.postEvent(1, Bundle().also { it.putString("hello", "你好") })
    
    // 需要注意的是，注册粘性事件的页面在注册Observer时要早于OnResume，因为处理粘性事件的时机就是在OnResume回调中
    EventPoster.postStickyEvent(1, Bundle().also { it.putString("hello", "页面1你好") })

```

所有的事件尽量定义在一起，事件从1开始逐步递增，这样避免出现重复定义的问题。

代码很简单，因为大多数情况下，回调都是在主线程进行处理的，所以接收的回调都是在主线程的。有子线程之间的通信，也可以把代码下下来自己改改的，相比`EventBus`轻量多了
