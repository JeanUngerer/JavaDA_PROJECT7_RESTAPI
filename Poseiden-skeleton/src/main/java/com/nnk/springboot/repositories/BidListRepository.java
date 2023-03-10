package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BidListRepository extends JpaRepository<BidList, Integer> {

    Optional<BidList> findByBidListId(Integer bidListId);
}
