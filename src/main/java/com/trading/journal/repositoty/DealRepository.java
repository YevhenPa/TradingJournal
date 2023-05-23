package com.trading.journal.repositoty;

import com.trading.journal.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealRepository extends JpaRepository<Deal, Integer> {
    List<Deal> findAllByUserId(long id);
    Optional<Deal> findByIdAndUserId(int dealId, long userId);
}
