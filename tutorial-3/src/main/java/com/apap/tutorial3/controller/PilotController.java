package com.apap.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "licenseNumber", required = true) String licenseNumber,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "flyHour", required = true) int flyHour) {
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam(value = "licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", archive);
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		model.addAttribute("listPilot", archive);
		return "viewall-pilot";
	}
	
	@RequestMapping("/pilot/view/license-number/{licenseNumber}")
	public String viewPath(@PathVariable Optional<String> licenseNumber, Model model) {
		String errorMessage = "";
		if(licenseNumber.isPresent()) {
			PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			if(pilot != null) {
				model.addAttribute("pilot", pilot);
				return "view-pilot";
			}
			else {
				errorMessage = "Error! Nomor lisensi kosong";
			}
		}
		else {
			errorMessage = "Error! Pilot dengan nomor lisensi " + licenseNumber.get() + " tidak ditemukan";
		}
		model.addAttribute("errorMessage", errorMessage);
		return "view-error";
	}
	
	@RequestMapping("/pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}")
	public String updateFlyHour(@PathVariable Optional<String> licenseNumber, @PathVariable Integer flyHour, Model model) {
		String errorMessage = "";
		if(licenseNumber.isPresent()) {
			PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			if(pilot != null) {
				pilot.setFlyHour(flyHour);
				String successMessage = "Pilot berhasil diupdate!";
				model.addAttribute("successMessage", successMessage);
				return "update-success";
			}
			else {
				errorMessage = "Error! Nomor lisensi kosong";
			}
		}
		else {
			errorMessage = "Error! Pilot dengan nomor lisensi " + licenseNumber.get() + " tidak ditemukan";
		}
		model.addAttribute("errorMessage", errorMessage);
		return "error";	
	}
	
	@RequestMapping("/pilot/delete/id/{id}")
	public String deletePilot(@PathVariable Optional<String> id, Model model) {
		String errorMessage = "";
		if(id.isPresent()) {
			PilotModel pilot = pilotService.getPilotDetailById(id.get());
			if(pilot != null) {
				pilotService.deletePilot(pilot);
				String successMessage = "ID berhasil dihapus!";
				model.addAttribute("successMessage", successMessage);
				return "delete-success";
			}
			else{
				errorMessage = "Error! Pilot dengan id " + id.get() + " tidak ditemukan";
				model.addAttribute("errorMessage", errorMessage);
				return "error";	
			}
		}
		else {
			errorMessage = "Error! Nomor id anda kosong";
			model.addAttribute("errorMessage", errorMessage);
			return "error";
		}	
	}
}
