package landlords.app.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import landlords.app.entity.Unit;

public interface UnitDao extends JpaRepository<Unit, Long> {


}
