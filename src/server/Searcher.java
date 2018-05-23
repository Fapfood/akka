package server;

import akka.actor.AbstractActor;
import message.SearchRequest;
import message.SearchResponse;
import repository.PricesRepository;

import java.io.FileNotFoundException;

public class Searcher extends AbstractActor {

    private int dbNumber;

    public Searcher(int dbNumber) {
        this.dbNumber = dbNumber;
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(SearchRequest.class, request -> {
                    SearchResponse response = PricesRepository.getInstance().searchForTitleIn(request.getTitle(), dbNumber);
                    context().parent().tell(response, sender());
                    context().stop(self());
                })
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }
}
