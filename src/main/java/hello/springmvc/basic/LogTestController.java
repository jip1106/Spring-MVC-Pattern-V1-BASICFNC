package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//Controller은 앞서 알아봤던 ViewResolver을 통해 페이지로 반환되지만
//RestController은 문자를 반환하면 스트링 자체가 반환된다.
@Slf4j
@RestController
public class LogTestController {
    //lombok의 @Slf4j로 대체 가능
    //private final Logger log = LoggerFactory.getLogger(LogTestController.class);
    //private final Logger log = LoggerFactory.getLogger(getClass());


    //@Controller은 반환값이 String이면 뷰 이름으로 인식되고, 뷰가 랜더링 된다.
    //@RestController은 반호나 값으로 뷰를 찾는게 아니라 HTTP 메시지 바디에 바로 입력한다.
    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";
        System.out.println("name = " + name);

        //application.properties 파일에 로그 레벨별로 설정 가능하고, default 는 info 레벨
        log.trace("trace log={}",name);
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        return "ok";
    }

}
