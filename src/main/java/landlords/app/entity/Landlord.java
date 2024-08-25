package landlords.app.entity;

import java.util.HashSet;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Landlord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long landlordId;
	
	private String landlordName;
	private String realtyAffiliation;
	private String landlordPhone;
	private String landlordEmail;
	private String landlordNotes;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "contributor_id", nullable = false)
	private Contributor contributor;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "landlord_region", joinColumns = @JoinColumn(name = "landlord_id"), inverseJoinColumns = @JoinColumn(name = "region_id"))
	private Set<Region> regions = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL)
	private Set<Unit> units = new HashSet<>();
	

}
