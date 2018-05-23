import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_LocalActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    if (s.startsWith("!")) {
                        System.out.println(s.substring(1));
                    } else {
                        ActorSelection remoteActor = getContext().actorSelection("akka.tcp://remote_system@127.0.0.1:3552/user/remote");
                        remoteActor.tell(s, getSelf());
                    }
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
