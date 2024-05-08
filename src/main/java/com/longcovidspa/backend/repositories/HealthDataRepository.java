package com.longcovidspa.backend.repositories;

import com.longcovidspa.backend.model.HealthData;
import com.longcovidspa.backend.model.HeartRateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface HealthDataRepository extends JpaRepository<HealthData,Long> {
    @Query("SELECT to_char(hd.receivedDate, 'YYYY-MM-DD HH24') as period, AVG(hd.heartRateVariability) as averageRate " +
            "FROM HealthData hd " +
            "GROUP BY to_char(hd.receivedDate, 'YYYY-MM-DD HH24')")
    List<Object[]> findHourlyAverage(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT to_char(hd.receivedDate, 'YYYY-MM-DD') as period, AVG(hd.heartRateVariability) as averageRate " +
            "FROM HealthData hd WHERE hd.receivedDate BETWEEN :startDate AND :endDate " +
            "GROUP BY to_char(hd.receivedDate, 'YYYY-MM-DD')")
    List<HeartRateDTO> findDailyAverage(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query("SELECT to_char(hd.receivedDate, 'YYYY-MM') as period, AVG(hd.heartRateVariability) as averageRate " +
            "FROM HealthData hd WHERE hd.receivedDate BETWEEN :startDate AND :endDate " +
            "GROUP BY to_char(hd.receivedDate, 'YYYY-MM')")
    List<HeartRateDTO> findMonthlyAverage(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}
