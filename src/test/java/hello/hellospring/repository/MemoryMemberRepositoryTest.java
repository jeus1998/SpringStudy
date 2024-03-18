package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class MemoryMemberRepositoryTest {
   MemoryMemberRepository repository = new MemoryMemberRepository();
   @AfterEach
   public void afterEach(){
        repository.clear();
   }
    @Test
    public void save(){
        Member member = new Member();
        member.setName("baejeu");
        repository.save(member);
        Member result = repository.findById(member.getId()).get();
        assertEquals(member, result);
        assertThat(member).isEqualTo(result);
    }
    @Test
    public void findByname(){
        Member member1 = new Member();
        member1.setName("baejeu1");
        Member member2 = new Member();
        member2.setName("baejeu2");
        repository.save(member1);
        repository.save(member2);

        Member result = repository.findByName("baejeu1").get();
        assertThat(member1).isEqualTo(result);
    }
    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("baejeu1");
        Member member2 = new Member();
        member2.setName("baejeu2");
        Member member3 = new Member();
        member3.setName("baejeu3");
        repository.save(member1);
        repository.save(member2);
        repository.save(member3);
        List<Member> result = repository.findAll();
        assertThat(3).isEqualTo(result.size());
    }
}
