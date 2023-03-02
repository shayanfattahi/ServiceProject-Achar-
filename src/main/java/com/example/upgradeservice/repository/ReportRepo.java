package com.example.upgradeservice.repository;

import com.example.upgradeservice.model.order.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepo extends JpaRepository<Ordered , Long> {

    List<Ordered> readOrderedByClientId(Long id);

    Optional<Ordered>readOrderedByTechnicianIdAndClientId(Long id1,Long id2);

//    Optional<Ordered>readOrderedByAcceptedIsFalseAndClientId(Long id);
//
//    Optional<Ordered>readOrderedByAcceptedIsTrueAndClientId(Long id);
//
//    Optional<Ordered>readOrderedByAcceptedIsTrueAndDonedIsTrueAndClientId(Long id);

    Optional<Ordered>readOrderedById(Long id);

    @Query(value ="select * from ordered where under_service_id in (select under_services_id from technician_under_services where technician_id=?1) and ordered.ordered_status = 'WAITINGFOROFFERED'" , nativeQuery = true)
    List<Ordered> findSuitable(Long id);

    @Query(value = "SELECT COUNT(client_id)\n" +
            "FROM ordered where client_id =?1" , nativeQuery = true)
    int findCountOfClient(Long id);
}
