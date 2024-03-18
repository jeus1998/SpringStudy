package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "배제우");
        return "hello";
    }
    @GetMapping("hello-mvc")
    public String helloMVC(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    // Http 쿼리 파라미터 1개 이상 받기
    @GetMapping("mvc-test")
    public String mcvTest(@RequestParam("name") String name,  @RequestParam("age") Integer age , Model model){
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "mvc-test-template";
    }
    // 파라미터에 required 옵션주기
    @GetMapping("required-option")
    public String requiredTest(@RequestParam(value = "name") String name, @RequestParam(value = "age", required = false) Integer age, Model model){
        model.addAttribute("name", name);
        if(age != null) model.addAttribute("age", age);
        return "mvc-test-template";
    }
    // API 방식
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;
    }
    // JSON 을 이용한 방식
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    static class Hello{
        private String name;
        public String getName() {return name;}
        public void setName(String name) {this.name= name;}
    }

}
