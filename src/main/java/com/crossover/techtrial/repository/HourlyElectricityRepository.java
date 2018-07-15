package com.crossover.techtrial.repository;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import java.sql.ResultSet;
import java.util.List;

/**
 * HourlyElectricity Repository is for all operations for HourlyElectricity.
 * @author Ahmed Yehia
 */
@RestResource(exported = false)
public interface HourlyElectricityRepository
    extends PagingAndSortingRepository<HourlyElectricity,Long> {
  Page<HourlyElectricity> findAllByPanelIdOrderByReadingAtDesc(Long panelId,Pageable pageable);
  List<HourlyElectricity> findAllByPanelId(Long panelId);


  List<DailyElectricity> getDailyStatistics(String panelSerial);


}
