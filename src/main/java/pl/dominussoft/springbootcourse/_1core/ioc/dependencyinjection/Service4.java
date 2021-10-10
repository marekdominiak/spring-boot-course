package pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection;

import org.springframework.stereotype.Service;

@Service
public class Service4 {

    // Inject EchoService0 without using Autowired
    private final EchoService0 echo;

    public Service4(EchoService0 echo) {
        this.echo = echo;
    }

    String wrapper(String msg) {
        return String.format("wrapper: (%s)", echo.echo(msg));
    }
}
