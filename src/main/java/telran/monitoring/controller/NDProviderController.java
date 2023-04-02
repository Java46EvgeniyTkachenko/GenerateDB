package telran.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import telran.monitoring.model.NotificationData;
import telran.monitoring.service.DProviderService;

@RestController
@RequestMapping("data")
public class NDProviderController {
	@Autowired
	DProviderService dataProvider;
	@GetMapping("/{patientId}")
	NotificationData getNotificationData(@PathVariable long patientId) {
		return dataProvider.getNData(patientId);
	}
}
