package server;

import akka.Done;
import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.stream.ActorMaterializer;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import message.StreamRequest;
import message.StreamResponse;
import repository.BooksRepository;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Streamer extends AbstractActor {

    private final ActorMaterializer materializer = ActorMaterializer.create(context().system());

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StreamRequest.class, request -> {
                    List<StreamResponse> streamResponses = BooksRepository.getInstance().getContent(request.getTitle());
                    final Source<StreamResponse, NotUsed> source = Source.from(streamResponses);
                    final Sink<StreamResponse, NotUsed> sinkPrint = Sink.actorRef(sender(), new StreamResponse(request.getTitle(), "DONE"));
                    source.throttle(1, Duration.create(1, TimeUnit.SECONDS), 1, ThrottleMode.shaping()).runWith(sinkPrint, materializer);
                    context().stop(self());
                })
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }
}
