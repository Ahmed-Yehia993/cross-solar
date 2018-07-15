package com.crossover.techtrial.dto;

import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * DailyElectricity class will hold sum, average,minimum and maximum electricity for a given day.
 *
 * @author Ahmed Yehia
 */
public interface DailyElectricity {

     LocalDate getDate();
     Long getSum();
     Double getAverage();
     Long getMin();
     Long getMax();

}
