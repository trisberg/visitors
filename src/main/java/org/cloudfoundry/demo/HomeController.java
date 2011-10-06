package org.cloudfoundry.demo;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@Autowired
	private CloudRepository cloudRepository;
	
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
	
	@RequestMapping(value="/clouds", method=RequestMethod.GET)
	public String clouds(Model model) {
		List<Cloud> clouds = cloudRepository.getClouds();
		long total = 0;
		for (Cloud cloud : clouds) {
			if (cloud.getVisitCount() != null) {
				total += cloud.getVisitCount();
			}
		}
		model.addAttribute("cloudList", clouds);
		model.addAttribute("cloud", new Cloud());
		model.addAttribute("total", total);
		return "clouds";
	}

	@RequestMapping(value="/cloud/delete/{cloudId}", method=RequestMethod.GET)
	public String cloudDelete(@PathVariable("cloudId") String cloudId) {
		cloudRepository.deleteCloud(cloudId);
		return "redirect:/clouds";
	}

	@RequestMapping(value="/clouds", method=RequestMethod.POST)
	public String addCloud(@ModelAttribute("cloud") Cloud newCloud) {
		if (newCloud != null) {
			cloudRepository.saveCloud(newCloud);
		}
		return "redirect:/clouds";
	}

	@RequestMapping(value="/count", method=RequestMethod.GET)
	public String count(Model model) {
		model.addAttribute("count", visitRepository.getCount());
		return "count";
	}

	@RequestMapping(value="/dump", method=RequestMethod.GET)
	public String dump(Model model) {
		model.addAttribute("aggregates", visitRepository.getAggregates());
		model.addAttribute("mongodata", visitRepository.getDump());
		model.addAttribute("clouds", cloudRepository.getDump());
		return "dump";
	}

	@RequestMapping(value="/clear", method=RequestMethod.GET)
	public String clear(Model model) {
		visitRepository.clear();
		return "redirect:/";
	}
}
