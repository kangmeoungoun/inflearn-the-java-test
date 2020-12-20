package me.whiteship.inflearnthejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.annotation.Profile;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Created by jojoldu@gmail.com on 2020-11-22
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ExtendWith(FindSLowTestExtension.class)
class StudyTest {
    @RegisterExtension
    static FindSLowTestExtension findSLowTestExtension = new FindSLowTestExtension(1000L);

    int value =1;
    @BeforeAll
    static void beforeAll(){
        System.out.println("before all");
    }
    @AfterAll
    static void afterAll(){
        System.out.println("after all");
    }
    @BeforeEach
    void beforeEach(){
        System.out.println("beforeEach");
    }
    @AfterEach
    void afterEach(){
        System.out.println("afterEach");
    }


    @EnabledOnOs(OS.MAC)
    @EnabledOnJre({JRE.JAVA_8,JRE.JAVA_9,JRE.JAVA_10,JRE.JAVA_11})
    void create_new_sutdy(){

    }
    @FastTest("test")
    @Order(1)
    void create_new_study(){
        System.out.println(this);
        System.out.println(value++);
        Study acStudy = new Study(1);
        assertThat(acStudy.getLimit()).isGreaterThan(0);
    }

    @Order(1)
    @Test
    void create_new_study2() throws InterruptedException {
        Thread.sleep(1005L);
        System.out.println(this);
        value++;
        System.out.println(value);
    }

    @SlowFast
    @DisplayName("스터디 만들기 slow")
    @Order(2)
    void create_new_study_again(){
        System.out.println("22222");
    }

    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10 ,name ="{displayName},{currentRepetition}/{totalRepetitions}" )
    void repeatTest(RepetitionInfo info){

        System.out.println(info.getCurrentRepetition()+"/"+info.getTotalRepetitions());
    }
    @DisplayName("스터디 만들기")
    @ParameterizedTest(name ="{index} {displayName} message ={0}")
    //@ValueSource(ints = {10,20,40})
    @CsvSource({"10 ,'자바 스터디'","20 ,'스프링'"})
    //void prameterizedTest(@ConvertWith(StudyConverter.class) Study study){
    void prameterizedTest(@AggregateWith(StudyAggregator.class)Study study){
        //Study study = new Study(name,limit);
        //Study study = new Study(argumentsAccessor.getString(1), argumentsAccessor.getInteger(0));
        System.out.println(study.toString());
    }

    static class StudyAggregator implements ArgumentsAggregator{
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            Study study = new Study(argumentsAccessor.getString(1), argumentsAccessor.getInteger(0));
            return study;
        }
    }

    // 아규먼트 커스텀 하게 해서 받는 방법
    static class StudyConverter extends SimpleArgumentConverter{
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class,targetType,()->"Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }
    @Test
    void create2(){
        assertTimeout(Duration.ofMillis(100),()->{
            new Study(10);
            //Thread.sleep(300);
        });
        assertTimeoutPreemptively(Duration.ofMillis(100),()->{
            new Study(10);
            //Thread.sleep(1000);
        });
        Study study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(0);
    }

    @Test
    void create1(){
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = illegalArgumentException.getMessage();
        assertEquals("limit은 0보다 커야 한다.",message);
    }

    @Test
    @DisplayName("테스트트🥝🍇🍊")

    public void create(){
        Study study = new Study(11);
        assertAll(
                ()->assertNotNull(study,()->"null 입니다."),
                ()->assertEquals(StudyStauts.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 +"+StudyStauts.DRAFT+"여야 한다."),
                ()->assertTrue(study.getLimit()> 10,"스터디 최대 참석 가능 인원은 0보다 커야 한다.")
        );



    }
}