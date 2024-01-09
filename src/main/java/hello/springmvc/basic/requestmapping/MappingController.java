package hello.springmvc.basic.requestmapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.awt.image.Kernel;
import java.lang.reflect.Array;
import java.util.*;

@RestController
public class MappingController {
    private Logger log = LoggerFactory.getLogger(getClass());

    private HashMap<String,String> createParamMap(HttpServletRequest request){

        Map<String, String[]> parameterMap = request.getParameterMap();
        HashMap<String, String> tmpMap = new HashMap<>();

        log.info("=== requestParameterMap() ===");
        for (String key : parameterMap.keySet()) {
            String concatValeus = "";

            String []values = parameterMap.get(key);
            log.info("key {} " , key);

            for (String value : values) {
                log.info("value {}" , value);
                concatValeus = concatValeus.concat(value);
            }
            tmpMap.put(key,concatValeus);
        }

        return tmpMap;

    }
    // http://localhost:9090/test1?name=jun&name=il&name=park&age=10
    @RequestMapping("/test1")
    public  String helloControllerRequest(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.info("requestURI = {}  " , requestURI);

        String requestURL = String.valueOf(request.getRequestURL());
        log.info("requestURL = {}  " , requestURL);

        HashMap<String, String> tmpMap = createParamMap(request);

        log.info("tmpMap : {} ", tmpMap);


        tmpMap.clear();
        request.getParameterNames().asIterator().forEachRemaining(tmp -> tmpMap.put(tmp, request.getParameter(tmp)));
        System.out.println("tmpMap = " + tmpMap);


        return "ok";
    }

    @RequestMapping("/test2")
    public String helloControllerRequest2(HttpServletRequest request){
        MultiValueMap<String, String> tmpMap = new LinkedMultiValueMap<>();

        request.getParameterNames().asIterator().forEachRemaining(tmp -> tmpMap.add(tmp, request.getParameter(tmp)));
        log.info("tmpMap : {} ", tmpMap);

        return "ok";
    }

    @RequestMapping({"/hello-basic","/hello-go"})
    public String helloBasic(){
        log.info("helloBasic");
        return "ok";
    }

    @RequestMapping(value = "/mapping-get-v1",method = RequestMethod.GET)
    public String mappingGetV1(){

        log.info("mapping-get-v1");
        return "ok";
    }

    /***
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMApping
     *
     */
    //@RequestMapping(value = "/mapping-get-v2",method = RequestMethod.GET)
    @GetMapping("/mapping-get-v2")
    public String mappingGetV2(){
        log.info("mapping-get-v2");
        return "ok";
    }

    /**
     * PathVariable 사용(경로변수)
     * @PathVariable의 이름과 파라미터 이름이 같으면 생략할 수 있다.
     * 변수명이 같으면 파라미터에 @PathVariable로 바로 변수를 받을 수 있다.
     *
     * @PathVariable("userId") String userId -> @PathVariable String userId
     * /mapping/userA
     * @return
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId){
        log.info("userId = {}" , userId);
        return "ok";
    }

    //http://localhost:9090/mapping2/2?name=park&name=jun&name=il&age=10
    @GetMapping("/mapping2/{userid}")
    public String mappingPath2(@PathVariable("userid") String userid, HttpServletRequest request){
        log.info("userid = {} " , userid);

        String requestURI = request.getRequestURI();
        log.info("requestURI = {}  " , requestURI);

        String requestURL = String.valueOf(request.getRequestURL());
        log.info("requestURL = {}  " , requestURL);

        HashMap<String, String> tmpMap = createParamMap(request);

        log.info("tmpMap : {} ", tmpMap);


        tmpMap.clear();
        request.getParameterNames().asIterator().forEachRemaining(tmp -> tmpMap.put(tmp, request.getParameter(tmp)));
        System.out.println("tmpMap = " + tmpMap);

        return "ok";
    }

    /**
     * PathVariable 사용 다중
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId, HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     *
     * 특정파라미터 정보가 있으면 호출가능
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    //@PostMapping(value = "/mapping-consume", consumes = "application/json")
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    //@PostMapping(value = "/mapping-produce", produces = "text/html")
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }




}
