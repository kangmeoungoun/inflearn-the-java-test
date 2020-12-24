package me.whiteship.inflearnthejavatest.study;

import lombok.extern.slf4j.Slf4j;
import me.whiteship.inflearnthejavatest.domain.Member;
import me.whiteship.inflearnthejavatest.domain.Study;
import me.whiteship.inflearnthejavatest.domain.StudyStatus;
import me.whiteship.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest{

    //@Mock
    //MemberService memberService;
    //@Mock
    //StudyRepository studyRepository;

    @Test
    void createNewStudy(@Mock MemberService memberService
            , @Mock StudyRepository studyRepository){

        StudyService studyService = new StudyService(memberService , studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("kes98202@naver.com");

        /*
        //2L 로 호출할시 객체 못받는다.
        //when(memberService.findById(1L)).thenReturn(Optional.of(member));
        //파라미터러 멀 호출하던간에 같은 객체롤 호출
        when(memberService.findById(any())).thenReturn(Optional.of(member));

        assertEquals("kes98202@naver.com",memberService.findById(1L).get().getEmail());
        assertEquals("kes98202@naver.com",memberService.findById(2L).get().getEmail());

        doThrow(new IllegalArgumentException()).when(memberService).validate(1l);
        assertThrows(IllegalArgumentException.class,()->{
            memberService.validate(1l);
        });
        memberService.validate(2l);

        when(memberService.findById(1L)).thenThrow(new RuntimeException());
        */
        when(memberService.findById(any())).thenReturn(Optional.of(member))
                                            .thenThrow(new RuntimeException())
                                            .thenReturn(Optional.empty());

        Optional<Member> byId = memberService.findById(1L);
        assertEquals("kes98202@naver.com",byId.get().getEmail());

        assertThrows(RuntimeException.class,()->{
            memberService.findById(2L);
        });

        assertEquals(Optional.empty(),memberService.findById(3L));

    }
}