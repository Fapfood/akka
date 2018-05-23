import akka.actor.AbstractActor;
import akka.actor.AllForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;

public class Z2_RemoteActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    String[] commands = s.split(" ");
                    switch (commands[0]) {
                        case "search":
                            System.out.println("hello");
                            break;
                        case "order":
                            context().child("multiplyWorker").get().tell(s, getSelf()); // send task to child

                            break;
                        case "stream":
                            context().child("divideWorker").get().tell(s, getSelf()); // send task to child

                            break;
                    }
                    getSender().tell("!" + s.toUpperCase(), getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(Z1_MultiplyWorker.class), "searcher_1");
        context().actorOf(Props.create(Z1_DivideWorker.class), "searcher_2");
    }

    private static SupervisorStrategy strategy
            = new AllForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .match(ArithmeticException.class, o -> resume())
            .matchAny(o -> restart())
            .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
