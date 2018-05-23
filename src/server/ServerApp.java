package server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class ServerApp {

    public static void main(String[] args) throws Exception {

        // config
        File configFile = new File("server_app.conf");
        Config config = ConfigFactory.parseFile(configFile);

        // create actor system & actors
        final ActorSystem system = ActorSystem.create("server_system", config);
        final ActorRef server = system.actorOf(Props.create(ServerActor.class), "server");

//        // interaction
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        while (true) {
//            String line = br.readLine();
//            if (line.equals("q")) {
//                break;
//            }
//            server.tell(line, null);
//        }
//
//        system.terminate();
    }
}