package server;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import message.StreamRequest;

public class StreamersSupervisor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StreamRequest.class, request -> {
                    ActorRef child = context().actorOf(Props.create(Streamer.class));
                    child.tell(request, sender());
                })
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }
}
