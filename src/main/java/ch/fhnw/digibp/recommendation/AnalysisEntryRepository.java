package ch.fhnw.digibp.recommendation;

import java.util.List;

import ch.fhnw.digibp.domain.AnalysisType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AnalysisEntryRepository extends CrudRepository<AnalysisEntry, Long> {

    @Query("select ae from AnalysisEntry ae where ae.analysisType = :analysisType")
    List<AnalysisEntry> findPreviousRecommendation(@Param("analysisType") AnalysisType analysisType);

}
