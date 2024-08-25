package landlords.app.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import landlords.app.entity.Region;

public interface RegionDao extends JpaRepository<Region, Long> {

	Set<Region> findAllByRegionIn(Set<String> regions);

}
