package client;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import message.*;

public class ClientActor extends AbstractActor {

    private ActorSelection server = context().actorSelection("akka.tcp://server_system@127.0.0.1:3552/user/server");

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    String[] commands = s.split(" ", 2);
                    switch (commands[0]) {
                        case "search":
                            server.tell(new SearchRequest(commands[1]), self());
                            break;
                        case "order":
                            server.tell(new OrderRequest(commands[1]), self());
                            break;
                        case "stream":
                            server.tell(new StreamRequest(commands[1]), self());
                            break;
                    }
                })
                .match(SearchResponse.class, response -> System.out.println(response.getTitle() + " cost " + response.getPrice()))
                .match(OrderResponse.class, response -> System.out.println("ordered " + response.getTitle()))
                .match(StreamResponse.class, response -> System.out.println(response.getTitle() + " " + response.getSentence()))
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }
}
