package com.example.applicationtest.container;

import com.example.applicationtest.domain.Member;
import com.example.applicationtest.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;

import java.io.File;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@Transactional
@ContextConfiguration(initializers = ContainerTest.ContainerPropertyInitializer.class)
public class ContainerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Autowired
    Environment env;

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql")
            .withDatabaseName("membertest");

    @Container
    static GenericContainer genericContainer = new GenericContainer("mysql")
            .withExposedPorts(20000)
            .withEnv("MYSQL_DB", "membertest");

    @Container
    static DockerComposeContainer composeContainer =
            new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
                    .withExposedService("member-db", 10000);

    @BeforeEach
    public void beforeEach() {
        memberRepository.deleteAll();
    }

    @Test
    void test() {
        Member member = Member.builder()
                .name("name")
                .build();
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
    }

    @Test
    void propertyTest() {
        String property = env.getProperty("container.port");
        System.out.println("property = " + property);
    }

    static class ContainerPropertyInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of("container.port=" + mySQLContainer.getMappedPort(10000))
                    .applyTo(applicationContext.getEnvironment());
        }
    }

}
