package org.springframework.social.hybridcanvas.controller;

import org.springframework.social.hybridcanvas.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

@Controller
public class HomeController {

	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

	private final Facebook facebook;

	private ConnectionRepository connectionRepository;

	@Inject
	public HomeController(Facebook facebook, ConnectionRepository connectionRepository) {
		logger.debug("HomeController constructor");
		this.facebook = facebook;
		this.connectionRepository = connectionRepository;
	}

	//FB sends as post
	@RequestMapping(value = "/"	,method = RequestMethod.POST)
	public String home(Model model) {
		logger.debug("In home() from POST");
		handleHomeRequest(model);
		return "home";
	}

	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String homeGet(Model model) {
		logger.debug("In home() from GET");
		handleHomeRequest(model);
		return "home";
	}

	private void handleHomeRequest(Model model) {
		if (connectionRepository.findConnections("facebook").size() > 0 ) {
			logger.debug("facebook found in connectionRepository");
			model.addAttribute(SecurityContext.getCurrentUser());
			model.addAttribute("friends", facebook.friendOperations().getFriendProfiles());
		} else {
			logger.debug("facebook NOT found in connectionRepository");
		}
	}

}
