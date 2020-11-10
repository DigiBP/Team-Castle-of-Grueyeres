package ch.fhnw.digibp.analysis;

import java.time.LocalDate;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ch.fhnw.digibp.AbstractEntity;
import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
public class Analysis extends AbstractEntity {

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @Column
    private ResultCategory resultCategory;
    @Column
    private String resultDescription = "BLA BLA BLA";
    @Column
    private String methodDescription;
    @Column
    private String remarks;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ResultCategory getResultCategory() {
        return resultCategory;
    }

    public void setResultCategory(ResultCategory resultCategory) {
        this.resultCategory = resultCategory;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }

    public enum ResultCategory {
        TEST
    }


}
