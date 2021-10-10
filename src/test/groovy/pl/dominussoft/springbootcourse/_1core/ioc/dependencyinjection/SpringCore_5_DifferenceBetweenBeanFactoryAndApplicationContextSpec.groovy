package pl.dominussoft.springbootcourse._1core.ioc.dependencyinjection


import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.HierarchicalBeanFactory
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.MessageSource
import org.springframework.core.env.EnvironmentCapable
import org.springframework.core.io.support.ResourcePatternResolver
import spock.lang.Specification

@SpringBootTest
class SpringCore_5_DifferenceBetweenBeanFactoryAndApplicationContextSpec extends Specification {

    @Autowired
    private BeanFactory beanFactory

    @Autowired
    private ApplicationContext applicationContext;

    def "show difference between beanFactory and ApplicationContext"() {
        expect:
        applicationContext != null
        applicationContext.getBean(EchoService0.class).echo("Msg") == "Echo: Msg"

        applicationContext != beanFactory
        BeanFactory.class.isAssignableFrom(ApplicationContext.class)

        ApplicationContext.class.getInterfaces().contains(EnvironmentCapable.class)
        ApplicationContext.class.getInterfaces().contains(ListableBeanFactory.class)
        ApplicationContext.class.getInterfaces().contains(HierarchicalBeanFactory.class)
        ApplicationContext.class.getInterfaces().contains(MessageSource.class)
        ApplicationContext.class.getInterfaces().contains(ApplicationEventPublisher.class)
        ApplicationContext.class.getInterfaces().contains(ResourcePatternResolver.class)

        !BeanFactory.class.getInterfaces().contains(EnvironmentCapable.class)
        !BeanFactory.class.getInterfaces().contains(ListableBeanFactory.class)
        !BeanFactory.class.getInterfaces().contains(HierarchicalBeanFactory.class)
        !BeanFactory.class.getInterfaces().contains(MessageSource.class)
        !BeanFactory.class.getInterfaces().contains(ApplicationEventPublisher.class)
        !BeanFactory.class.getInterfaces().contains(ResourcePatternResolver.class)



        !ApplicationContext.class.getMethods().findAll { it.getName() == 'getResources'}.isEmpty()
        !ApplicationContext.class.getMethods().findAll { it.getName() == 'getMessage'}.isEmpty()

        BeanFactory.class.getDeclaredMethods().findAll { it.getName() == 'getResources'}.isEmpty()
        BeanFactory.class.getDeclaredMethods().findAll { it.getName() == 'getMessage'}.isEmpty()


    }

}
