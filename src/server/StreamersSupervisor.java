package server;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import message.StreamRequest;
import scala.concurrent.duration.Duration;

import java.io.FileNotFoundException;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.stop;

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

    private static SupervisorStrategy strategy
            = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .match(FileNotFoundException.class, o -> stop())
            .matchAny(o -> restart())
            .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
