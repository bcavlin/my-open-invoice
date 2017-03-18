package com.bgh.myopeninvoice.db.dao;

import com.bgh.myopeninvoice.db.model.TaxEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by bcavlin on 14/03/17.
 */
public interface TaxRepository extends PagingAndSortingRepository<TaxEntity, Integer> {

}
