package server;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import message.SearchRequest;

public class SearchersSupervisorsSupervisor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchRequest.class, request -> {
                    ActorRef child = context().actorOf(Props.create(SearchersSupervisor.class));
                    child.tell(request, sender());
                })
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }
}
