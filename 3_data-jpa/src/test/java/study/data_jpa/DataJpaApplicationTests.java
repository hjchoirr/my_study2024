package study.data_jpa;

import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootTest
@EnableJpaAuditing
class DataJpaApplicationTests {

	@Test
	void contextLoads() {
	}

}
