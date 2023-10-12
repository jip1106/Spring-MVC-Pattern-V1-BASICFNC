package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);

        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws Exception{

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);

        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws Exception{

        String messageBody = httpEntity.getBody();

        log.info("messageBody={}",messageBody);

        return new HttpEntity<>("ok");
    }

    @PostMapping("/request-body-string-v3_request_response")
    public HttpEntity<String> requestBodyStringV3_Request_Response(RequestEntity<String> httpEntity) throws Exception{

        String messageBody = httpEntity.getBody();

        log.info("messageBody={}",messageBody);

        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }


    @PostMapping("/request-body-string-v4")
    @ResponseBody
    public String requestBodyStringV4(@RequestBody String messageBody) throws Exception{

        log.info("messageBody={}",messageBody);

        return "ok";
    }
}
