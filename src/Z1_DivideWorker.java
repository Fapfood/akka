import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z1_DivideWorker extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private int counter = 0;

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    String result = Divide(s);
                    counter += 1;
                    getSender().tell("result: " + result + " (operation count: " + counter + ")", getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    private String Divide(String s) {
        String[] split = s.split(" ");
        int a = Integer.parseInt(split[1]);
        int b = Integer.parseInt(split[2]);
//        double a = Double.parseDouble(split[1]);
//        double b = Double.parseDouble(split[2]);
        return (a / b) + "";
    }
}
