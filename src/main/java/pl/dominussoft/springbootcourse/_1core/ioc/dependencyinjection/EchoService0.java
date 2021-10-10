package pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection;


import org.springframework.stereotype.Component;

@Component
public class EchoService0 {

    String echo(String msg) {
        return "Echo: " + msg;
    }
}
