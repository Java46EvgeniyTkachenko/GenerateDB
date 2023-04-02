package telran.monitoring;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import telran.monitoring.entities.Doctor;
import telran.monitoring.entities.Patient;
import telran.monitoring.entities.Visit;
import telran.monitoring.service.DProviderService;

@Component
public class RandomDbCreation {
	public Patient patient = new Patient();
	static Logger LOG = LoggerFactory.getLogger(RandomDbCreation.class);
	@Value("${app.visit.amount: 50}")
	int nVisit;
	@Value("${spring.jpa.hibernate.ddl-auto:create }")
	String ddlAutoProp;
	
	@Autowired	
	DProviderService dataProviderService;
	
	String patientsName[] = { "Abraham", "Sarah", "Itshak", "Rahel", "Asaf", "Yacob", "Rivka", "Yosef",
			"Benyanim", "Dan"};
	String mailDoctor[] = {"doctor1@gmail.com","doctor2@gmail.com","doctor3@gmail.com"};
	String nameDoctor[] = {"Moshe","Abraham","Aviv"};
	
	@PostConstruct
	void createDB() {
		if (ddlAutoProp.equals("create")) {
			addDoctor();
			addPatient();
			addVisit();
			LOG.info("created {} random marks in DB", nVisit);
		} else {
			LOG.warn("DB no created - assumed that it exists");
		}
	}

	private void addPatient() {
		IntStream.range(0, patientsName.length-1)
		.forEach(i -> {
			dataProviderService.addPatient(new Patient(i+1, patientsName[i]));
		});		
	}
	private void addDoctor() {
		IntStream.range(0, nameDoctor.length-1)
		.forEach(i -> {
			dataProviderService.addDoctor(new Doctor(mailDoctor[i], nameDoctor[i]));
		});
		
	}
	
	private void addVisit() {
		IntStream.range(0, nVisit).forEach(i -> addOneVisit(i));
		
	}
	
	private void addOneVisit(long var) {
		int i = getRandomNumber(1, patientsName.length-1);
		Patient patient = new Patient (i, patientsName[i]);
		i = getRandomNumber(1, nameDoctor.length-1);
		Doctor doctor = new Doctor(mailDoctor[i],nameDoctor[i]);
		LocalDate dataVisit = LocalDate.now();
		dataProviderService.addVisit(new Visit(dataVisit.minusDays(var), doctor, patient));
		
	}
	private int getRandomNumber(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	

}
