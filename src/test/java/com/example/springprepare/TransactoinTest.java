package com.example.springprepare;

import com.example.springprepare.memo.entity.Memo;
import com.example.springprepare.memo.repository.MemoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class TransactoinTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    MemoRepository memoRepository;

    @Test
    @Transactional
    @Rollback(value = false) // test완료 후 rollback 방지
    @DisplayName("memo success")
    void test1() {
        Memo memo = new Memo();
        memo.setUsername("User");
        memo.setContents("@Transactional test");

        em.persist(memo); // memo entity를 영속성 context에 저장
    }

    @Test
    @DisplayName("메모 생성 실패")
    /*
    No EntityManager with actual transaction available for current thread
    - cannot reliably process 'persist' call

    transactional 환경이 아니여서
     */
    void test2() {
        Memo memo = new Memo();
        memo.setUsername("Robbie");
        memo.setContents("@Transactional 테스트 중!");

        em.persist(memo);  // 영속성 컨텍스트에 메모 Entity 객체를 저장합니다.
    }

    @Test
//    @Transactional
    @Rollback(value = false)
    @DisplayName("트랜잭션 전파 테스트")
    void test3() {
        memoRepository.createMemo(em);
        System.out.println("테스트 test3 메서드 종료");
    }
}
