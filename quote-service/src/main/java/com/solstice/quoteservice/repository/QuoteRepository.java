package com.solstice.quoteservice.repository;

import com.solstice.quoteservice.model.AggregatedData;
import com.solstice.quoteservice.model.Quote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Long> {

    @Query(nativeQuery = true)
    AggregatedData getDataBySymbolAndDay(@Param("symbol") int symbol,@Param("date") Timestamp timestamp);

    @Query(nativeQuery = true)
    AggregatedData getDataBySymbolAndMonth(@Param("symbol") int symbol,@Param("month") Timestamp timestamp);
}
