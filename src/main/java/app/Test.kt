package app

import actors.JavaActor
import actors.KotlinActor
import common.ActorMsg
import common.CallbackMsg
import common.ResponseMsg
import common.TestMsg
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking

class Test {

    fun start(args:Array<String>) = runBlocking {
        val actor1 = KotlinActor()
        val actor2 = JavaActor()
        actor1.send(TestMsg("hello"))
        actor2.send(TestMsg("hello"))

        val response1 = CompletableDeferred<String>()
        val response2 = CompletableDeferred<String>()
        actor1.send(ResponseMsg("hello", response1) )
        actor2.send(ResponseMsg("hello", response2))

        println("response1:${response1.await()}")
        println("response2:${response2.await()}")

        actor1.send(CallbackMsg("hello" ){ msg ->
            println("callback from actor1 : $msg")
        } )
        actor2.send(CallbackMsg("hello") { msg ->
            println("callback from actor1 : $msg")
        })
    }

    companion object {
        @JvmStatic
        fun main(args:Array<String>) {
            Test().start(args)
        }
    }
}