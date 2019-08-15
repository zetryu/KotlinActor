package actors

import common.*
import common.Actor.Companion.getChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.SendChannel


class KotlinActor : Actor<ActorMsg> {
    private val tag = "[KotlinActor]"
    override val channel: SendChannel<ActorMsg> = getChannel(this, scope = CoroutineScope(Dispatchers.Default))

    override fun receive(msg: ActorMsg) {
        when(msg) {
            is TestMsg -> println("$tag TestMsg:${msg.body}")
            is ResponseMsg -> {
                println("$tag CallbackMsg:${msg.body}")
                msg.response.complete("$tag good morning")
            }
            is CallbackMsg -> {
                println("$tag CallbackMsg:${msg.body}")
                msg.callback("$tag hi callback")
            }
            else -> println("$tag unknown:$msg")
        }
    }

    fun finalize() {
        channel.close()
    }
}