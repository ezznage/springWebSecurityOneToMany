package com.green.nowon.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogController {

    @GetMapping("/signin")
    public String signin(HttpServletRequest request){
        //로그인페이지로 이동하기 전 페이지 정보
        String prevPageUrl = request.getHeader("Referer");
        System.out.println("prevPage: "+prevPageUrl);
        //로그인페이지에서 다시 로그인요청된경우는 제외
        if(prevPageUrl!=null && !prevPageUrl.contains("/signin")) {     //로그인페이지에서 다시 로그인요청된경우는 제외
            request.getSession().setAttribute("prevPage", prevPageUrl);
        }
        return "/sign/signin";
    }


}