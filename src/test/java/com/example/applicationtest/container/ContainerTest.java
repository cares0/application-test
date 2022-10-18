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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@Transactional
public class ContainerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql");

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
}
