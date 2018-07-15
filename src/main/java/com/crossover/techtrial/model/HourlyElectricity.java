package com.crossover.techtrial.model;

import com.crossover.techtrial.dto.DailyElectricity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;


/**
 * HourlyElectricity is responsible for electricity generated by a Panel in an hour.
 *
 * @author Ahmed Yehia
 */

@Entity
@Table(name = "hourly_electricity")

@NamedNativeQuery( name = "HourlyElectricity.getDailyStatistics",
        query = "SELECT CAST(hourly_electricity.reading_at AS DATE) as date,sum(generated_electricity) as sum,avg(generated_electricity)as average,min(generated_electricity) as min,max(generated_electricity) as max   \n" +
                "FROM hourly_electricity\n" +
                "Inner JOIN panel on hourly_electricity.panel_id = panel.id\n" +
                "where panel.serial = ? and hourly_electricity.reading_at is not null group by CAST(hourly_electricity.reading_at AS DATE) order by date ASC;", resultSetMapping = "DailyElectricity"
)
@SqlResultSetMapping(name = "DailyElectricity",
        columns = {
                @ColumnResult(name = "date", type = LocalDate.class),
                @ColumnResult(name = "sum", type = Long.class),
                @ColumnResult(name = "average", type = Double.class),
                @ColumnResult(name = "min", type = Long.class),
                @ColumnResult(name = "max", type = Long.class)
        })
public class HourlyElectricity implements Serializable {

    private static final long serialVersionUID = -575347909928592140L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "panel_id", referencedColumnName = "id")
    Panel panel;

    @Column(name = "generated_electricity")
    Long generatedElectricity;

    @Column(name = "reading_at")
    LocalDateTime readingAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public Long getGeneratedElectricity() {
        return generatedElectricity;
    }

    public void setGeneratedElectricity(Long generatedElectricity) {
        this.generatedElectricity = generatedElectricity;
    }

    public LocalDateTime getReadingAt() {
        return readingAt;
    }

    public void setReadingAt(LocalDateTime readingAt) {
        this.readingAt = readingAt;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((readingAt == null) ? 0 : readingAt.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HourlyElectricity other = (HourlyElectricity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (readingAt == null) {
            if (other.readingAt != null) {
                return false;
            }
        } else if (!readingAt.equals(other.readingAt)) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HourlyElectricity [id=" + id + ", panel=" + panel + ", generatedElectricity="
                + generatedElectricity + ", readingAt=" + readingAt + "]";
    }


}