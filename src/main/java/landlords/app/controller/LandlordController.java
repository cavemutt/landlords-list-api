package landlords.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import landlords.app.controller.model.ContributorData;
import landlords.app.controller.model.LandlordData;
import landlords.app.controller.model.RegionData;
import landlords.app.controller.model.UnitData;
import landlords.app.service.LandlordService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/landlords")
@Slf4j
public class LandlordController {
	
	@Autowired
	private LandlordService landlordService;
	
//	Create contributor
	@PostMapping("/contributor")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ContributorData insertContributor(@RequestBody ContributorData contributorData) {
		log.info("Creating contributor {}", contributorData);
		return landlordService.saveContributor(contributorData);
	}
	
//	Update contributor
	@PutMapping("/contributor/{contributorId}")
	public ContributorData updateContributor(@PathVariable Long contributorId, @RequestBody ContributorData contributorData) {
		contributorData.setContributorId(contributorId);
		log.info("Updating contributor {}", contributorData);
		return landlordService.saveContributor(contributorData);
	}
	
//	Read all contributors
	@GetMapping("/contributor")
	public List<ContributorData> retrieveallContributors() {
		log.info("Retrieve all contributors called");
		return landlordService.retrieveAllContributors();
	}
//  Read one contributor
	@GetMapping("/contributor/{contributorId}")
	public ContributorData retrieveContributorById(@PathVariable Long contributorId) {
		log.info("Retrieving contributor with ID={}", contributorId);
		return landlordService.retrieveContributorById(contributorId);
	}
	
//	attempt to Delete all contributors 
	@DeleteMapping("/contributor")
	public void deleteAllContributors() {
		log.info("Attempting to delete all contributors");
		throw new UnsupportedOperationException("Deleting all contributors is not allowed.");
	}
	
//	Delete contributor by ID
	@DeleteMapping("/contributor/{contributorId}")
	public Map<String, String> deleteContributorById(@PathVariable Long contributorId) {
		log.info("Deleting contributor with ID={}", contributorId);
		landlordService.deleteContributorById(contributorId);
		return Map.of("message", "Deletion of contributor with ID=" + contributorId + " was successful.");
	}
	
//  Read all regions
	@GetMapping("/region")
	public List<RegionData> retrieveallRegions() {
		log.info("Retrieve all region data");
		return landlordService.retrieveAllRegions();
	}

//	Read region by ID
	@GetMapping("/region/{regionId}")
	public RegionData retrieveRegionById(@PathVariable Long regionId) {
		log.info("Retrieving info for region with ID={}", regionId);
		return landlordService.retrieveRegionById(regionId);
	}
	
//	Create landlord
	@PostMapping("/contributor/{contributorId}/landlord")
	public LandlordData insertLandlord(@PathVariable Long contributorId, @RequestBody LandlordData landlordData) {
		log.info("Creating landlord {} for contributor with ID={}, landlordData, contributorId");
		return landlordService.saveLandlord(contributorId, landlordData);
	}
	
//	Update landlord
	@PutMapping("/contributor/{contributorId}/landlord/{landlordId}")
	public LandlordData updateLandlord(@PathVariable Long contributorId, @PathVariable Long landlordId, @RequestBody LandlordData landlordData) {
		landlordData.setLandlordId(landlordId);
		log.info("Updating landlord {} for contributor with ID={}, landlordData, contributorId)");
		return landlordService.saveLandlord(contributorId, landlordData);
	}
	
//	Read all landlords
	@GetMapping("/contributor/{contributorId}/landlord/{landlordId}")
	public LandlordData retrieveLandlordById(@PathVariable Long contributorId, @PathVariable Long landlordId) {
		log.info("Retrieving landlord with ID={} inserted by contributor with ID={}", landlordId, contributorId);
		return landlordService.retrieveLandlordById(contributorId, landlordId);
	}
	
//	Read landlord by ID
	@GetMapping("/contributor/{contributorId}/landlord")
	public List<LandlordData> retrieveallLandlords(Long contributorId) {
		log.info("Retrieve all landlords for contributor with ID={}", contributorId);
		return landlordService.retrieveAllLandlords(contributorId);
	}
	
//	attempt to Delete all landlords
	@DeleteMapping("/contributor/{contributorId}/landlord")
	public void deleteAllLandlords() {
		log.info("Attempting to delete all landlords");
		throw new UnsupportedOperationException("Deleting all landlords is not allowed.");
	}
	
//	Delete landlord by ID
	@DeleteMapping("/contributor/{contributorId}/landlord/{landlordId}")
	public Map<String, String> deleteLandlordById(@PathVariable Long contributorId, @PathVariable Long landlordId) {
		log.info("Deleting landlord with ID={} from contributor with ID={}", landlordId, contributorId);
		landlordService.deleteLandlordById(contributorId, landlordId);
		return Map.of("message", "Deletion of landlord with ID=" + landlordId + "from contributor with ID=" + contributorId + " was successful.");
	}
	
//	Create Unit
	@PostMapping("/contributor/{contributorId}/landlord/{landlordId}/unit")
	public UnitData insertUnit(@PathVariable Long contributorId, @PathVariable Long landlordId, @RequestBody UnitData unitData) {
		log.info("Creating unit {} belonging to landlord with ID={} for contributor with ID={}", unitData, landlordId, contributorId);
		return landlordService.saveUnit(landlordId, unitData);
	}
	
}
