package pl.dominussoft.springbootcourse.app.application


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import pl.dominussoft.springbootcourse.app.application.configuration.ServiceTest
import spock.lang.Specification

@ServiceTest
class I18nSpec extends Specification {

    @Autowired
    MessageSource messages

    def "1. Load messages from a messages_XXX.properties "() {

        expect:
        messages.getMessage("taxes", [] as Object[], new Locale("pl")) == "Podatki"
        messages.getMessage("taxes", [] as Object[], new Locale("no")) == "Skatter"
    }

    def "2. Load messages default message from a messages.properties for not supported locale"() {

        expect:
        messages.getMessage("taxes", [] as Object[], Locale.CANADA_FRENCH) == "Taxes"
    }

    def "3. Load message with parameter: Message will be: 'Taxes in USA are too high!'"() {

        expect:
        messages.getMessage("taxes.rate.description", ["too high"] as String[], Locale.CANADA_FRENCH) == "Taxes in USA are too high!"
    }

}
