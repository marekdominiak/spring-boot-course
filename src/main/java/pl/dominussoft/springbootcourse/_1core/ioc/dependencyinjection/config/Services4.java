package pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection.config;

public class Services4 {

    static class Controller{
        private Service service = new ServiceFirstImpl();

        void method() {
            service.doStuff();
        }
    }
    interface Service {

        void doStuff();
    }

    interface Repository {

    }

    static class RepositoryJdbcImpl implements Repository {

    }
    static class ServiceFirstImpl implements Service {

        @Override
        public void doStuff() {
            System.out.println("First service impl");
        }
    }
    static class ServiceSecondImpl implements Service {
        Repository repository = new RepositoryJdbcImpl();

        @Override
        public void doStuff() {
            System.out.println("Second service impl");
        }
    }





}
