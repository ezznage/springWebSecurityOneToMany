package com.green.nowon.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import lombok.extern.log4j.Log4j2;


//인증이 성공된이후 이동페이지 설정  onAuthenticationSuccess메서드가 처리
//인증이 필요한 링크를 클릭했을때 -->로그인페이지로 이동시키고 인증성공하면 -->인증이 필요한 링크
//로그인을 직접클릭한경우(타켓페이지가 존재하지 않는경우 ) --> 이전페이지로 이동설정
//이전페이지정보가 로그인에서 요청되었을때--> / 인덱스페이지로

@Log4j2
public class MySuccessHandler  extends SimpleUrlAuthenticationSuccessHandler{
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	//protected final Log logger = LogFactory.getLog(this.getClass());
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		clearAuthenticationAttributes(request);
		SavedRequest savedRequest = this.requestCache.getRequest(request, response);
		log.info(">>>> savedRequest:" + savedRequest);
		//log.info(">>>> savedRequest_RedirectUrl: "+savedRequest.getRedirectUrl());
		String targetUrl=null;
		if(savedRequest !=null) targetUrl=savedRequest.getRedirectUrl();		
		//signin 페이지에서 저장한 pervPage
		String prevPage= (String) request.getSession().getAttribute("prevPage");
		if(prevPage != null) request.getSession().removeAttribute("prevPage");
		// Use the DefaultSavedRequest URL
		//security 인증을 위해 로그인페이지로 보낼때 인증이후 이동할페이지를 저장한 url
		String url=getDefaultTargetUrl(); // "/"
		log.info(">>>> getDefaultTargetUrl: "+url);
		if(targetUrl!=null && !targetUrl.contains("/signin")) {
			url=targetUrl;
		}else {
			url=prevPage;
		}
		log.info(">>>> 인증완료후 이동하는페이지: "+url);
		//RedirectStrategy객체가 최종적으로 페이지 이동지원하는 객체 
		getRedirectStrategy().sendRedirect(request, response, url);
		
	}

	

}