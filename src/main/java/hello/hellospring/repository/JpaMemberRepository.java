package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{
    private EntityManager em; // JPA를 사용하려면 EntityManger 주입 받아야 한다. EntityManger는 DB연결 정보등
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    // SPQL
    @Override
    public Optional<Member> findByName(String name) {
         List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                 .setParameter("name", name).getResultList();
         return result.stream().findAny();
    }
    // SPQL
    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
