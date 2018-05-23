package server;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import exeption.NotFoundInFileException;
import message.SearchRequest;
import message.SearchResponse;
import scala.concurrent.duration.Duration;

import java.io.FileNotFoundException;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;

public class SearchersSupervisor extends AbstractActor {

    private boolean received = false;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchRequest.class, request -> {
                    ActorRef child1 = context().actorOf(Props.create(Searcher.class, 1));
                    ActorRef child2 = context().actorOf(Props.create(Searcher.class, 2));
                    child1.tell(request, sender());
                    child2.tell(request, sender());
                })
                .match(SearchResponse.class, response -> {
                    if (received) {
                        context().stop(self());
                    } else {
                        if(response.getTitle() != null)
                            sender().tell(response, null);
                            received = true;
                    }
                })
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }

    private static SupervisorStrategy strategy
            = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .match(NotFoundInFileException.class, o -> stop())
            .match(FileNotFoundException.class, o -> restart())
            .matchAny(o -> restart())
            .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
