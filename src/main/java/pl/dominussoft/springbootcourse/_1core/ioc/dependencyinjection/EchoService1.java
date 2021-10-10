package pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection;


import org.springframework.stereotype.Component;

@Component
public class EchoService1 {

    String echo(String msg) {
        return "Echo1: " + msg;
    }
}
