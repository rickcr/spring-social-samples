package org.springframework.social.hybridcanvas;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConnectedToHandlerInterceptor extends HandlerInterceptorAdapter {

	private ConnectionRepository connectionRepository;

	public ConnectedToHandlerInterceptor(ConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("user") != null ) {
			request.setAttribute("connectedToFacebook", connectionRepository.findConnections("facebook").size() > 0);
		}
		return true;
	}

}

