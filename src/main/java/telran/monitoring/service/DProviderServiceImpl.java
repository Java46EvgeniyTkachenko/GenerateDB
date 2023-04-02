package telran.monitoring.service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.monitoring.entities.Doctor;
import telran.monitoring.entities.Patient;
import telran.monitoring.entities.Visit;
import telran.monitoring.model.NotificationData;
import telran.monitoring.repo.*;
@Service
public class DProviderServiceImpl implements DProviderService {
	DoctorRepository doctorRepository;
	PatientRepository patientRepository;
	VisitRepository visitRepository;
	@Override
	public NotificationData getNData(long patientId) {
		String doctorEmail = visitRepository.getDoctorEmail(patientId);
		String doctorName = doctorRepository.findById(doctorEmail).get().getName();
		String patientName = patientRepository.findById(patientId).get().getName();
		return  new NotificationData(doctorEmail, doctorName, patientName);
	}
	public DProviderServiceImpl(DoctorRepository doctorRepository, PatientRepository patientRepository,
			VisitRepository visitRepository) {
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.visitRepository = visitRepository;
	}
	@Override
	
	public void addPatient(Patient patient) {
		if(patientRepository.existsById(patient.getId())) {
			throw new IllegalStateException(String.format("Patient with id %d already exist",
					patient.getId()));
		}
		patientRepository.save(new Patient(patient.getId(), patient.getName()));
		
	}
	@Override
	
	public void addDoctor(Doctor doctor) {
		if(doctorRepository.existsById(doctor.getEmail())) {
			throw new IllegalStateException(String.format("Doctor with email %s already exist",
					doctor.getEmail()));
		}
		doctorRepository.save(new Doctor(doctor.getEmail(), doctor.getName()));
		
	}
	@Override
	
	public void addVisit(Visit visits) {
		Doctor doctor = doctorRepository.findById(visits.getDoctor().getEmail()).orElse(null);
		if (doctor == null) {
			throw new NoSuchElementException(String.format("doctor with id %d doesn't exist",
					visits.getId()));
		}
		Patient patient = patientRepository.findById(visits.getPatient().getId()).orElse(null);
		if(patient == null) {
			throw new NoSuchElementException(String.format("subject with id %d doesn't exist", 
					visits.getId()));
		}
		Visit visit = new Visit(LocalDate.now(),doctor,patient);
		visitRepository.save(visit);
		
	}

}
