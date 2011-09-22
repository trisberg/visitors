package org.cloudfoundry.demo;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private VisitRepository visitRepository;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		Visit v = new Visit();
		v.setOriginAddress(request.getHeader("X-FORWARDED-FOR"));
		v.setRemoteAddress(request.getRemoteAddr());
		v.setTimeOfVisit(new Date());
		visitRepository.newVisit(v);
		
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(v.getTimeOfVisit());
		
		model.addAttribute("address", v.originAddress);

		model.addAttribute("serverTime", formattedDate);

		model.addAttribute("totalVisits", visitRepository.getTotalVisits());

		model.addAttribute("visitList", visitRepository.getLastTen());

		return "home";
	}
	
	@RequestMapping(value={"/dump"}, method=RequestMethod.GET)
	public String dump(Model model) {
		model.addAttribute("aggregates", visitRepository.getAggregates());
		model.addAttribute("mongodata", visitRepository.getDump());
		return "dump";
	}

	@RequestMapping(value={"/clear"}, method=RequestMethod.GET)
	public String clear(Model model) {
		visitRepository.clear();
		return "redirect:/";
	}
}
