package ch.fhnw.digibp.recommendation;

import java.util.List;

import ch.fhnw.digibp.analysis.Analysis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AnalysisEntryRepository extends CrudRepository<AnalysisEntry, Long> {
    @Query("select count(ae.id) from AnalysisEntry ae where ae.resultValue = :value and ae.resultCategory = :category")
    long countEntries(@Param("value") double value, @Param("category") Analysis.ResultCategory category);

    @Query("select new ch.fhnw.digibp.recommendation.RecommendationEntry(count(ae.id), ae.recommendation) from AnalysisEntry ae where ae.resultValue = :value and ae.resultCategory = :category group by ae.recommendation")
    List<RecommendationEntry> findRecommendation(@Param("value") double value, @Param("category") Analysis.ResultCategory category);


}
