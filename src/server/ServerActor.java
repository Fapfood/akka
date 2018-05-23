package server;

import akka.actor.AbstractActor;
import akka.actor.Props;
import message.OrderRequest;
import message.SearchRequest;
import message.StreamRequest;

public class ServerActor extends AbstractActor {

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(SearchRequest.class, request -> {
                    context().child("searcher").get().tell(request, sender());
                })
                .match(OrderRequest.class, request -> {
                    context().child("orderer").get().tell(request, sender());
                })
                .match(StreamRequest.class, request -> {
                    context().child("streamer").get().tell(request, sender());
                })
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }

    @Override
    public void preStart() {
        context().actorOf(Props.create(SearchersSupervisorsSupervisor.class), "searcher");
        context().actorOf(Props.create(Orderer.class), "orderer");
        context().actorOf(Props.create(StreamersSupervisor.class), "streamer");
    }
}