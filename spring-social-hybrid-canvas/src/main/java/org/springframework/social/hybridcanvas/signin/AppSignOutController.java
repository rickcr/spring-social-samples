package org.springframework.social.hybridcanvas.signin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppSignOutController {
	private static Logger logger = LoggerFactory.getLogger(AppSignOutController.class);

	@RequestMapping(value="/signout", method= RequestMethod.GET)
	public void signout()  {
		 //signout is handled in UserInterceptor, this method never actually reached, mapping needed to be declared though
	}
}
