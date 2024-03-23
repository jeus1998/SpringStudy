package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    // 1. @Autowired DataSource dataSource;
    // 2. 생성자 주입
    private EntityManager em;
    private DataSource dataSource;
    private final MemberRepository memberRepository; // Spring Jpa 가 Spring jpa 구현체를 자동으로 주입한다.
    @Autowired
    public SpringConfig(DataSource dataSource, EntityManager em , MemberRepository memberRepository) {
        // this.dataSource = dataSource;
        // this.em = em;
        this.memberRepository = memberRepository;

    }
    @Bean
    public MemberService memberService(){
        // return new MemberService(memberRepository());
        return new MemberService(memberRepository);
    }
    @Bean
    public MemberRepository memberRepository(){
        // return new MemoryMemberRepository();
        // return new JdbcMemberRepository(dataSource);
        // return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}
