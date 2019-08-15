package common

import kotlinx.coroutines.CompletableDeferred

sealed class ActorMsg
class TestMsg(val body:String) : ActorMsg()
class ResponseMsg(val body:String, val response: CompletableDeferred<String>) : ActorMsg()
class CallbackMsg(val body:String, val callback: (msg:String)->Unit) : ActorMsg()
class DataMsg(val msg:String) : ActorMsg()