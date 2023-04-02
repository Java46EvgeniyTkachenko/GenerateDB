package telran.monitoring.service;

import telran.monitoring.entities.Doctor;
import telran.monitoring.entities.Patient;
import telran.monitoring.entities.Visit;
import telran.monitoring.model.NotificationData;

public interface DProviderService {
NotificationData getNData(long patientId);
void addPatient(Patient patient);
void addDoctor(Doctor doctor);
void addVisit(Visit visit);
}
