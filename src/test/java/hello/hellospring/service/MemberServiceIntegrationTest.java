package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    // 테스트에서는 Field Injection 사용
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Test
    // @Commit -> DB에 넣는다. (@Transactional이 있으면 DB에 저장 X지만 )
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("배제우");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("배제우");

        Member member2 = new Member();
        member2.setName("배제우");
        // when
        memberService.join(member1);

        // 예외 처리 확인
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 예외 메세지 확인
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}