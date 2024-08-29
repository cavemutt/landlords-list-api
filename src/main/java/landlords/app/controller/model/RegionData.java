package landlords.app.controller.model;

import java.util.HashSet;
import java.util.Set;

import landlords.app.entity.Contributor;
import landlords.app.entity.Landlord;
import landlords.app.entity.Region;
import landlords.app.entity.Unit;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegionData {
	private Long regionId;	
	private String regionName;
	Set<RegionLandlord> landlords = new HashSet<>();
	
	public RegionData (Region region) {
		regionId = region.getRegionId();
		regionName = region.getRegion();
		for (Landlord landlord : region.getLandlords()) {
			landlords.add(new RegionLandlord(landlord));
		}
		
	}
	
	@Data
	@NoArgsConstructor
	static class RegionLandlord {
		private Long landlordId;
		private String landlordName;
		private String landlordPhone;
		private String landlordEmail;
		private String landlordNotes;
		private RegionLandlordContributor contributor;
		private Set<LandlordUnit> units = new HashSet<>();
		
		public RegionLandlord(Landlord landlord) {
			landlordId = landlord.getLandlordId();
			landlordName = landlord.getLandlordName();
			landlordPhone = landlord.getLandlordPhone();
			landlordEmail = landlord.getLandlordEmail();
			landlordNotes = landlord.getLandlordNotes();
			contributor = new RegionLandlordContributor(landlord.getContributor());
			for (Unit unit : landlord.getUnits()) {
				units.add(new LandlordUnit(unit));
			}
			
		}
		
		@Data
		@NoArgsConstructor
		public static class RegionLandlordContributor {
			private Long contributorId;
			private String contributorName;
			private String contributorEmail;
			
			public RegionLandlordContributor(Contributor contributor) {
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
}
