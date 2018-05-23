package server;

import akka.actor.AbstractActor;
import message.OrderRequest;
import message.OrderResponse;
import repository.OrdersRepository;

public class Orderer extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(OrderRequest.class, request -> {
                    OrdersRepository.getInstance().saveOrder(request.getTitle());
                    sender().tell(new OrderResponse(request.getTitle()), null);
                })
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }
}
