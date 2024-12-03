package com.myhealthzip.backend;

import com.myhealthzip.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {UserRepository.class})
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
