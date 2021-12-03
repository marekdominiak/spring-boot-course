package pl.dominussoft.springbootcourse.app.application


import pl.dominussoft.springbootcourse.app.application.configuration.ServiceTest
import spock.lang.Specification

@ServiceTest
class I18nSpec extends Specification {

    def "1. Load messages from a messages_XXX.properties "() {

        expect:
        false
        //messages.getMessage("taxes", [] as Object[], new Locale("pl")) == "Podatki"
        //to samo dla Norwegi
    }

    def "2. Load messages default message from a messages.properties for not supported locale"() {

        expect:
        messages.getMessage("taxes", [] as Object[], Locale.CANADA_FRENCH) == "Taxes"
    }

    def "3. Load message with parameter: Message will be: 'Taxes in USA are too high!'"() {

        expect:
        false
//        messages.getMessage("taxes.rate.description", [""] as String[], Locale.US) == "Taxes in USA are too high!"
    }

}
