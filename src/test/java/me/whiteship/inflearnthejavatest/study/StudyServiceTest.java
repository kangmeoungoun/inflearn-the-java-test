package me.whiteship.inflearnthejavatest.study;

import lombok.extern.slf4j.Slf4j;
import me.whiteship.inflearnthejavatest.domain.Member;
import me.whiteship.inflearnthejavatest.domain.Study;
import me.whiteship.inflearnthejavatest.domain.StudyStatus;
import me.whiteship.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
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

    @Mock
    MemberService memberService;
    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService(){

        StudyService studyService = new StudyService(memberService , studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("kes98202@naver.com");

        Study study = new Study(10 , "테스트");

        when(memberService.findById(1l)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1l , study);
        assertEquals(member,study.getOwner());
        //memberService의 notify 메소드를 1번 호출했는지 확인
        verify(memberService,times(1)).notify(study);
        //notify(study) 호출후 아무것도 memberService 아무것도 하지말아라 테스트 깨진다. 어떤 액션 후에 그 목을 다시는 사용하지 말아라
        verify(memberService,times(1)).notify(member);
        //memberService의 validate 메소드 를 전혀 호출이 되지 않는지 확인
        verify(memberService,never()).validate(any());

        //순서 테스트 study 를 먼저 호출하고 member 을 호출한다.
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);


    }
}