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
public class UnitData {
	private Long unitId;
	private int numberBedrooms;
	private String unitCity;
	private String unitNotes;
	private UnitLandlord landlord;
	
	public UnitData(Unit unit) {
		unitId = unit.getUnitId();
		numberBedrooms = unit.getNumberBedrooms();
		unitCity = unit.getUnitCity();
		unitNotes = unit.getUnitNotes();
		landlord = new UnitLandlord(unit.getLandlord());
	}
	
	@Data
	@NoArgsConstructor
	public static class UnitLandlord {
		private Long landlordId;		
		private String landlordName;
		private String realtyAffiliation;
		private String landlordPhone;
		private String landlordEmail;
		private String landlordNotes;
		private LandlordsContributor contributor;
		private Set<LandlordRegion> regions = new HashSet<>();
		
		public UnitLandlord(Landlord landlord) {
			landlordId = landlord.getLandlordId();
			landlordName = landlord.getLandlordName();
			realtyAffiliation = landlord.getRealtyAffiliation();
			landlordPhone = landlord.getLandlordPhone();
			landlordEmail = landlord.getLandlordEmail();
			landlordNotes = landlord.getLandlordNotes();
			contributor = new LandlordsContributor(landlord.getContributor());
			for(Region region : landlord.getRegions()) {
				regions.add(new LandlordRegion(region));
			}
		}
		
		@Data
		@NoArgsConstructor
		public static class LandlordsContributor {
			private Long contributorId;
			private String contributorName;
			private String contributorEmail;
			public LandlordsContributor(Contributor contributor) {
				contributorId = contributor.getContributorId();
				contributorName = contributor.getContributorName();
				contributorEmail = contributor.getContributorEmail();
			}
		}
		
		@Data
		@NoArgsConstructor
		public static class LandlordRegion {
			private Long regionId;
			private String regionName;
			
			public LandlordRegion(Region region) {
				regionId = region.getRegionId();
				regionName = region.getRegion();
			}
		}
	}
}
