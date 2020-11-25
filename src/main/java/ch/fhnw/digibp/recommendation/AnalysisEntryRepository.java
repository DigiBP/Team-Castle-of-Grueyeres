package ch.fhnw.digibp.recommendation;

import java.util.List;

import ch.fhnw.digibp.order.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AnalysisEntryRepository extends CrudRepository<AnalysisEntry, Long> {
    @Query("select count(ae.id) from AnalysisEntry ae where ae.resultValue = :value and ae.analysisType = :analysisType")
    long countEntries(@Param("value") double value, @Param("analysisType") Order.AnalysisType analysisType);

    @Query("select new ch.fhnw.digibp.recommendation.RecommendationEntry(count(ae.id), ae.recommendation) from AnalysisEntry ae where ae.resultValue = :value and ae.analysisType = :analysisType group by ae.recommendation")
    List<RecommendationEntry> findRecommendation(@Param("value") double value, @Param("analysisType") Order.AnalysisType analysisType);

}
