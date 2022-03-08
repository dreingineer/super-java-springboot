package com.company;

import com.company.user.User;
import com.company.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("piccipie@email.com");
        user.setPassword("picci12345");
        user.setFirstName("Picci");
        user.setLastName("Pie");
        user.setEnabled(true);

        User savedUser = repo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for (User user : users
             ) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate() {
        Integer userId = 1;
        Optional<User> user = repo.findById(userId);
        User updatedUser = user.get();
        updatedUser.setPassword("amba12345");
        repo.save(updatedUser);

        User newUserPassword = repo.findById(userId).get();
        Assertions.assertThat(newUserPassword.getPassword()).isEqualTo("amba12345");
    }

    @Test
    public void testGet() {
        Integer userId = 7;
        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        Integer userId = 7;
        repo.deleteById(userId);

        Optional<User> user = repo.findById(userId);
        Assertions.assertThat(user).isNotPresent();
    }
}
