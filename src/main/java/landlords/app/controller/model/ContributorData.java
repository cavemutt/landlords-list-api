package landlords.app.controller.model;

import java.util.HashSet;


import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import landlords.app.entity.Contributor;
import landlords.app.entity.Landlord;
import landlords.app.entity.Region;
import landlords.app.entity.Unit;


@Data
@NoArgsConstructor
public class ContributorData {

	private Long contributorId;
	private String contributorName;
	private String contributorEmail;
	private Set<LandlordResponse> landlords = new HashSet<>();
	
	public ContributorData(Contributor contributor) {
		contributorId = contributor.getContributorId();
		contributorName = contributor.getContributorName();
		contributorEmail = contributor.getContributorEmail();
		
		for (Landlord landlord : contributor.getLandlords()) {
			landlords.add(new LandlordResponse(landlord));
		}
	}

	
//	@Value
	@Data
	@NoArgsConstructor
	static class LandlordResponse {
		private Long landlordId;
		private String landlordName;
		private String landlordPhone;
		private String landlordEmail;
		private String landlordNotes;
		private Set<String> regions = new HashSet<>();
		private Set<Integer> units = new HashSet<>();
		
		LandlordResponse(Landlord landlord) {
			landlordId = landlord.getLandlordId();
			landlordName = landlord.getLandlordName();
			landlordPhone = landlord.getLandlordPhone();
			landlordEmail = landlord.getLandlordEmail();
			landlordNotes = landlord.getLandlordNotes();
			for (Region region : landlord.getRegions()) {
				regions.add(region.getRegion());
			}
			for (Unit unit : landlord.getUnits()) {
				units.add(unit.getNumberBedrooms());
			}
			
		}
	}
}
