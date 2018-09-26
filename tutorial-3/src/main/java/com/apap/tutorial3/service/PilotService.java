package com.apap.tutorial3.service;

import java.util.List;

import com.apap.tutorial3.model.PilotModel;

public interface PilotService {
	List<PilotModel> getPilotList();
	void addPilot (PilotModel pilot);
	void deletePilot(PilotModel pilot);
	PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
	PilotModel getPilotDetailById(String id);
}
