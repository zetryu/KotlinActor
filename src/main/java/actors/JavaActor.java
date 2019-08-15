package actors;

import common.*;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.channels.SendChannel;
import org.jetbrains.annotations.NotNull;

public class JavaActor implements Actor<ActorMsg> {

    private CoroutineScope scope = Actor.Companion.getScope(Dispatchers.getDefault());
    private SendChannel<ActorMsg> channel = Actor.Companion.getChannel(this, scope);
    private String tag = "[JavaActor]";

    @NotNull
    @Override
    public SendChannel<ActorMsg> getChannel() {
        return channel;
    }

    @Override
    public void receive(ActorMsg msg) {
        if (msg instanceof TestMsg) {
            TestMsg testMsg = (TestMsg) msg;
            System.out.println(tag + " TestMsg:" + testMsg.getBody());
        } else if (msg instanceof ResponseMsg) {
            ResponseMsg callbackMsg = (ResponseMsg) msg;
            System.out.println(tag + " CallbackMsg:" + callbackMsg.getBody());
            callbackMsg.getResponse().complete(tag + " good afternoon");
        } else if (msg instanceof CallbackMsg) {
            CallbackMsg callbackMsg = (CallbackMsg) msg;
            System.out.println(tag + " CallbackMsg:" + callbackMsg.getBody());
            callbackMsg.getCallback().invoke(tag + " hi callback");
        } else {
            System.out.println(tag + " unknown:" + msg.toString());
        }

    }

    @Override
    public void finalize() {
        channel.close(null);
    }
//    @Override
//    public Object send(ActorMsg msg, @NotNull Continuation<? super Unit> continuation) {
//        return null;
//    }
}
