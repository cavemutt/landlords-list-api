package landlords.app.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import landlords.app.controller.model.ContributorData;
import landlords.app.controller.model.LandlordData;
import landlords.app.controller.model.RegionData;
import landlords.app.controller.model.UnitData;
import landlords.app.dao.ContributorDao;
import landlords.app.dao.LandlordDao;
import landlords.app.dao.RegionDao;
import landlords.app.dao.UnitDao;
import landlords.app.entity.Contributor;
import landlords.app.entity.Landlord;
import landlords.app.entity.Region;
import landlords.app.entity.Unit;

@Service
public class LandlordService {
	
	@Autowired
	private ContributorDao contributorDao;
	
	@Autowired
	private LandlordDao landlordDao;
	
	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private UnitDao unitDao;

//	Contributor methods
	@Transactional(readOnly = false)
	public ContributorData saveContributor(ContributorData contributorData) {
		Long contributorId = contributorData.getContributorId();
		Contributor contributor = findOrCreateContributor(contributorId, contributorData.getContributorEmail());
		
		setFieldsInContributor(contributor, contributorData);
		return new ContributorData(contributorDao.save(contributor));
	}

	private void setFieldsInContributor(Contributor contributor, ContributorData contributorData) {
		contributor.setContributorEmail(contributorData.getContributorEmail());
		contributor.setContributorName(contributorData.getContributorName());
		
	}

	private Contributor findOrCreateContributor(Long contributorId, String contributorEmail) {
		Contributor contributor;
		
		if(Objects.isNull(contributorId)) {
			Optional<Contributor> opContrib = contributorDao.findByContributorEmail(contributorEmail);
			if (opContrib.isPresent()) {
				throw new DuplicateKeyException("Contributor with email " + contributorEmail + " already exists.");
			}
			contributor = new Contributor();
		} else {
			contributor = findContributorById(contributorId);
		}
		
		return contributor;
	}

	private Contributor findContributorById(Long contributorId) {
		return contributorDao.findById(contributorId).orElseThrow();
	}

	@Transactional(readOnly = true)
	public List<ContributorData> retrieveAllContributors() {
//		@formatter:off
		return contributorDao
		.findAll()
		.stream()
		.map(ContributorData::new)
		.toList();
//		@formatter:on
	}

	@Transactional(readOnly = true)
	public ContributorData retrieveContributorById(Long contributorId) {
		Contributor contributor = findContributorById(contributorId);
		return new ContributorData(contributor);
	}

	@Transactional(readOnly = false)
	public void deleteContributorById(Long contributorId) {
		Contributor contributor = findContributorById(contributorId);
		contributorDao.delete(contributor);
		
	}

//	Landlord methods
	@Transactional(readOnly = false)
	public LandlordData saveLandlord(Long contributorId, LandlordData landlordData) {
		Contributor contributor = findContributorById(contributorId);
		Set<Region> regions = regionDao.findAllByRegionIn(landlordData.getRegions());
		Landlord landlord = findOrCreateLandlord(landlordData.getLandlordId());
		setLandlordFields(landlord, landlordData);
		landlord.setContributor(contributor);
		contributor.getLandlords().add(landlord);
		for(Region region : regions) {
			region.getLandlords().add(landlord);
			landlord.getRegions().add(region);
		}
		Landlord dbLandlord = landlordDao.save(landlord);
		return new LandlordData(dbLandlord);
	}

	private void setLandlordFields(Landlord landlord, LandlordData landlordData) {
		landlord.setLandlordName(landlordData.getLandlordName());
		landlord.setRealtyAffiliation(landlordData.getRealtyAffiliation());
		landlord.setLandlordPhone(landlordData.getLandlordPhone());
		landlord.setLandlordEmail(landlordData.getLandlordEmail());
		landlord.setLandlordNotes(landlordData.getLandlordNotes());
		landlord.setLandlordId(landlordData.getLandlordId());
	}

	private Landlord findOrCreateLandlord(Long landlordId) {
		Landlord landlord;
		if (Objects.isNull(landlordId)) {
			landlord = new Landlord();
		} else {
			landlord = findLandlordById(landlordId);
		}
		return landlord;
	}

	private Landlord findLandlordById(Long landlordId) {
		return landlordDao.findById(landlordId).orElseThrow();
	}

	@Transactional(readOnly = true)
	public LandlordData retrieveLandlordById(Long contributorId, Long landlordId) {
		findContributorById(contributorId);
		Landlord landlord = findLandlordById(landlordId);
		if (landlord.getContributor().getContributorId() != contributorId) {
			throw new IllegalStateException("Landlord with ID=" + landlordId + " is not owned by contributor with ID=" + contributorId);
		}
		return new LandlordData(landlord);
	}

	@Transactional(readOnly = true)
	public List<LandlordData> retrieveAllLandlords(Long contributorId) {
//		@formatter:off
		return landlordDao
		.findAll()
		.stream()
		.map(LandlordData::new)
		.toList();
//		@formatter:on
	}

	@Transactional(readOnly = false)
	public void deleteLandlordById(Long contributorId, Long landlordId) {
		findContributorById(contributorId);
		Landlord landlord = findLandlordById(landlordId);
		if (landlord.getContributor().getContributorId() != contributorId) {
			throw new IllegalStateException("Landlord with ID=" + landlordId + " is not owned by contributor with ID=" + contributorId);
		}
		landlordDao.delete(landlord);
	}

//	Unit methods
	@Transactional(readOnly = false)
	public UnitData saveUnit(Long landlordId, UnitData unitData) {
		Landlord landlord = findLandlordById(landlordId);
		Unit unit = findOrCreateUnit(unitData.getUnitId());
		setUnitFields(unit, unitData);
		unit.setLandlord(landlord);
		landlord.getUnits().add(unit);
		
		Unit dbUnit = unitDao.save(unit);
		return new UnitData(dbUnit);
	}

	private void setUnitFields(Unit unit, UnitData unitData) {
		unit.setUnitCity(unitData.getUnitCity());
		unit.setNumberBedrooms(unitData.getNumberBedrooms());
		unit.setUnitNotes(unitData.getUnitNotes());
		unit.setUnitId(unitData.getUnitId());
		
	}

	private Unit findOrCreateUnit(Long unitId) {
		Unit unit;
		if (Objects.isNull(unitId)) {
			unit = new Unit();
		} else {
			unit = findUnitById(unitId);
		}
		return unit;
	}

	private Unit findUnitById(Long unitId) {
		return unitDao.findById(unitId).orElseThrow();
	}

//	Region methods
	@Transactional(readOnly = true)
	public List<RegionData> retrieveAllRegions() {
//		@formatter:off
		return regionDao
		.findAll()
		.stream()
		.map(RegionData::new)
		.toList();
//		@formatter:on
	}
	
	@Transactional(readOnly = true)
	public RegionData retrieveRegionById(Long regionId) {
		Region region = findRegionById(regionId);
		return new RegionData(region);
	}

	private Region findRegionById(Long regionId) {
		return regionDao.findById(regionId).orElseThrow();
	}
}
