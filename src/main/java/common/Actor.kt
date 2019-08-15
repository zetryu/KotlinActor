package common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor

interface Actor<T> {
    val channel: SendChannel<T>

    @JvmDefault
    suspend fun send(msg: T) {
        channel.send(msg)
    }

    fun receive(msg: T)

    companion object {
        @JvmStatic
        fun <T> getChannel(actor: Actor<T>, scope: CoroutineScope): SendChannel<T> {
            return scope.actor {
                for (msg in channel) {
                    actor.receive(msg)
                }
            }
        }

        @JvmStatic
        fun <T> getScope(dispatcher: CoroutineDispatcher): CoroutineScope = CoroutineScope(dispatcher)
    }
}

