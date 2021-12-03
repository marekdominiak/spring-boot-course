package pl.dominussoft.springbootcourse.app.infrastructure.persistence;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.dominussoft.springbootcourse.app.domain.Cart;
import pl.dominussoft.springbootcourse.app.domain.CartRepository;
import pl.dominussoft.springbootcourse.app.domain.Course;
import pl.dominussoft.springbootcourse.app.domain.CourseRepository;
import pl.dominussoft.springbootcourse.app.domain.Currency;
import pl.dominussoft.springbootcourse.app.domain.Instructor;
import pl.dominussoft.springbootcourse.app.domain.InstructorRepository;
import pl.dominussoft.springbootcourse.app.domain.Price;
import pl.dominussoft.springbootcourse.app.domain.Role;
import pl.dominussoft.springbootcourse.app.domain.UserAccount;
import pl.dominussoft.springbootcourse.app.domain.UserAccountRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

@Profile("dev")
@Component
@RequiredArgsConstructor
@Slf4j
public class DevDatabaseInitializer implements CommandLineRunner {

    public static final Fairy FAIRY = Fairy.create();

    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder encoder;
    private final CartRepository cartRepository;

    @Override
    @Transactional
    public void run(String... args) {

        if (!Iterables.isEmpty(instructorRepository.findAll())) {
            return;
        }

        IntStream.range(0, 10).forEach(
                i -> {
                    Person person = FAIRY.person();
                    TextProducer textProducer = FAIRY.textProducer();
                    Set<String> keywords = Set.of(textProducer.latinWord(), textProducer.latinWord());
                    Instructor entity = new Instructor(person.getFirstName(), person.getLastName(), textProducer.text(), person.getAge(), keywords);
                    log.info("Saved instructor {}: {}", i, entity);
                    instructorRepository.save(entity);
                }
        );

        List<UUID> courses = new ArrayList<>();
        IntStream.range(0, 200).forEach(
                i -> {
                    TextProducer textProducer = FAIRY.textProducer();
                    Set<String> keywords = Set.of(textProducer.latinWord(), textProducer.latinWord());
                    Course entity = new Course(textProducer.sentence(), textProducer.sentence(), textProducer.sentence(), keywords,
                            randomPrice());
                    Course saved = courseRepository.save(entity);
                    courses.add(saved.getId());
                    log.info("Saved course {}: Saved{}", i, entity);
                }
        );

        // an admin
        UserAccount admin = new UserAccount("John", "Doe", "admin@ecourses.pl", encoder.encode("admin"), Role.ADMIN); // I know it's not secure ;)
        userAccountRepository.save(admin);
        log.info("Saved admin {}", admin);

        // some students
        List<UUID> students = new ArrayList<>();
        IntStream.range(0, 20).forEach(
                i -> {
                    Person person = FAIRY.person();
                    UserAccount student = new UserAccount(person.getFirstName(), person.getLastName(),
                            "student" + i + "@ecourses.pl",
                            encoder.encode("student" + i), Role.STUDENT); // I know it's not secure ;)
                    UserAccount saved = userAccountRepository.save(student);
                    students.add(saved.getId());
                    log.info("Saved student {}: Saved{}", i, student);
                }
        );

        // some carts
        Cart cart1 = cartRepository.save(new Cart(students.get(0), Sets.newHashSet(courses.subList(0, 4))));
        log.info("Saved cart {}: Saved {}", 1, cart1);
        Cart cart2 = cartRepository.save(new Cart(students.get(1), Sets.newHashSet(courses.subList(2, 8))));
        log.info("Saved cart {}: Saved {}", 2, cart2);
    }

    private Price randomPrice() {
        int amount = FAIRY.baseProducer().randomBetween(0, 10000);
        return new Price(BigDecimal.valueOf(amount), Currency.PLN);
    }
}
