package com.example.upgradeservice.repository;

import com.example.upgradeservice.model.Offered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferedRepo extends JpaRepository<Offered , Long> {

    Offered readOfferedById(Long id);

    List<Offered> readOfferedByClientIdAndOrderedId(Long id1 , Long id2);

    List<Offered> findOfferedByClientIdAndOrderedIdOrderByPriceAsc(Long id , Long id2);

    @Query(value = "Select offered.id, offered.text, offered.price, offered.date, offered.accepted, offered.time, offered.client_id , offered.technician_id , offered.ordered_id , offered.under_service_id from offered\n " +
            "JOIN technician t on offered.technician_id=t.id\n " +
            "where offered.client_id=?1 ORDER BY t.point desc" , nativeQuery = true)
    List<Offered> readGoodPoint(Long id);

    @Query(value = "select offered.id, offered.text, offered.price, offered.date, offered.accepted, offered.time, offered.client_id , offered.technician_id , offered.ordered_id , offered.under_service_id from offered inner join under_service s on offered.under_service_id = s.id where s.services_id =?1" , nativeQuery = true)
    List<Offered> readOfferedByService(Long service);


    @Query(value = "SELECT * FROM Offered \n" +
            "WHERE date BETWEEN 14011101 AND 14020101" , nativeQuery = true)
    List<Offered> readOfferedBySpecialDate();

    @Query(value = "SELECT COUNT(technician_id)\n" +
            "FROM offered where technician_id =?1" , nativeQuery = true)
    int findCountOfTechnician(Long id);
}
