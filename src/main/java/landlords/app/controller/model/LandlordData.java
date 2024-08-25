package landlords.app.controller.model;

import java.util.HashSet;
import java.util.Set;

import landlords.app.entity.Contributor;
import landlords.app.entity.Landlord;
import landlords.app.entity.Region;
import landlords.app.entity.Unit;
//import landlords.app.entity.Unit;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LandlordData {
	private Long landlordId;
	private String landlordName;
	private String realtyAffiliation;
	private String landlordPhone;
	private String landlordEmail;
	private String landlordNotes;
	private LandlordContributor contributor;
	private Set<String> regions = new HashSet<>();
	private Set<LandlordUnit> units = new HashSet<>();
	
	public LandlordData(Landlord landlord) {
		landlordId = landlord.getLandlordId();
		landlordName = landlord.getLandlordName();
		realtyAffiliation = landlord.getRealtyAffiliation();
		landlordPhone = landlord.getLandlordPhone();
		landlordEmail = landlord.getLandlordEmail();
		landlordNotes = landlord.getLandlordNotes();
		contributor = new LandlordContributor(landlord.getContributor());
		
		for (Region region : landlord.getRegions()) {
			regions.add(region.getRegion());
		}
		
		for (Unit unit : landlord.getUnits()) {
			units.add(new LandlordUnit(unit));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class LandlordContributor {
		private Long contributorId;
		private String contributorName;
		private String contributorEmail;
		
		public LandlordContributor(Contributor contributor) {
			contributorId = contributor.getContributorId();
			contributorName = contributor.getContributorName();
			contributorEmail = contributor.getContributorEmail();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class LandlordUnit {
		private Long unitId;
		private int numberBedrooms;
		private String unitNotes;
		
		public LandlordUnit(Unit unit) {
			unitId = unit.getUnitId();
			numberBedrooms = unit.getNumberBedrooms();
			unitNotes = unit.getUnitNotes();
		}
	}
	
	
}
