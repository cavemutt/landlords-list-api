package landlords.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import landlords.app.entity.Landlord;

public interface LandlordDao extends JpaRepository<Landlord, Long> {

}
