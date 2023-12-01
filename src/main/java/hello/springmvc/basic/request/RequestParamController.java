package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username ={}, age={}" , username ,age);

        response.getWriter().write("ok");
    }

    @RequestMapping("/request-param-v2")
    @ResponseBody
    public String requestParamV2(@RequestParam("username") String memberName,
                               @RequestParam("age") int memberAge) throws IOException {

        log.info("username ={}, age={}" , memberName ,memberAge);

        return "ok";
    }

    //앞에서 넘어오는 name이랑(요청파라미터) 받는 파라미터명이 같으면 @RequestParam만 사용 가능
    @RequestMapping("/request-param-v3")
    @ResponseBody
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) throws IOException {

        log.info("username ={}, age={}" , username ,age);

        return "ok";
    }
    
    //앞에서 넘어오는 name이랑(요청파라미터) 받는 파라미터명이 같고, String, int, Integer 등의 단순 타입이면
    // 변수명으로만 받을 수도 있음
    @RequestMapping("/request-param-v4")
    @ResponseBody
    public String requestParamV4(String username, int age) throws IOException {

        log.info("username ={}, age={}" , username ,age);

        return "ok";
    }


    //@RequestParam 의 기본 required는 true값
    //@RequestParam 이 false이면 요청 파라미터에 없어도 되지만 기본값이 null로 들어온다.
    @RequestMapping("/request-param-required")
    @ResponseBody
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age) throws IOException {

        log.info("username ={}, age={}" , username ,age);

        return "ok";
    }

    @RequestMapping("/request-param-default")
    @ResponseBody
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                       @RequestParam(required = false, defaultValue = "-1") int age) {

        log.info("username ={}, age={}" , username ,age);

        return "ok";
    }

    //파라미터를 list로 조회하기
    // @RequestParam("명칭") List<String> paramList -> "명칭"과 변수명 paramList 가 같으면 name 생략 가능
    // @RequestParam List<String> paramList
    @RequestMapping("/request-param-list")
    @ResponseBody
    public String requestParamMap(@RequestParam("paramList") List<String> paramList) {
        int i=0;
        for (String tmp : paramList){
            log.info("tmp : {} " ,paramList.get(i));
            i++;
        }

//        log.info("username ={}, age={}" , paramMap.get("username") ,paramMap.get("age"));

        return "ok";
    }

    //파라미터를 map 으로 조회하기
    @RequestMapping("/request-param-map")
    @ResponseBody
    public String requestParamMap(@RequestParam Map<String, Object> paramMap,
                                  @RequestParam MultiValueMap<String, Object> paramMap2) {
        for (String key : paramMap.keySet()) {
            log.info("key={}, value={}", key, paramMap.get(key));
        }

        for (String key : paramMap2.keySet()) {
            log.info("key={}, value={}", key, paramMap2.get(key));
        }

//        log.info("username ={}, age={}" , paramMap.get("username") ,paramMap.get("age"));

        return "ok";
    }

    /*

     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(/*@RequestParam String username , @RequestParam int age*/
                                @ModelAttribute HelloData helloData){

        log.info("HelloData = {}", helloData);
        log.info("helloData.getUsername() = {} " , helloData.getUsername().replace(",","").trim());
        System.out.println("helloData.getUsername().replace(\",\",\"\").trim().length() = " + helloData.getUsername().replace(",","").trim().length());
        return "ok";
    }

    /**
     * @ModelAttribute 는 생략 가능하다.
     * 스프링은 String, int Integer 등 단순 타입은  @RequestParam 으로 인식하고
     * 나머지는 ModelAttribute로 인식한다 (argument resolver 제외)
     * @param helloData
     * @return
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData){

        log.info("HelloData = {}", helloData);
        return "ok";
    }


}
