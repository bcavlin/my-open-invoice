package com.bgh.myopeninvoice.jsfbeans.model;

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.ContactsEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bcavlin on 17/03/17.
 */
public class ContactsEntityLazyModel extends LazyDataModel<ContactsEntity> {

    private static Logger logger = LoggerFactory.getLogger(ContactsEntityLazyModel.class);

    private List<ContactsEntity> contactsEntityList;

    private InvoiceDAO invoiceDAO;

    public ContactsEntityLazyModel(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public List<ContactsEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        contactsEntityList = new ArrayList<>();

        PageRequest pageRequest = new PageRequest(first * pageSize, pageSize);

        Sort.Direction direction = null;
        if (sortOrder == SortOrder.ASCENDING) {
            direction = Sort.Direction.ASC;
        } else if (sortOrder == SortOrder.DESCENDING) {
            direction = Sort.Direction.DESC;
        }
        try {
            if (direction != null && sortField != null) {
                pageRequest = new PageRequest(
                        first * pageSize,
                        pageSize,
                        new Sort(new Sort.Order(direction, sortField)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        BooleanExpression predicate = null;

//        if (filters != null && !filters.isEmpty()) {
//            for (Map.Entry<String, Object> stringObjectEntry : filters.entrySet()) {
//                BooleanExpression temp = null;
//
//                if (temp != null) {
//                    if (predicate == null) {
//                        predicate = temp;
//                    } else {
//                        predicate.and(temp);
//                    }
//                }
//            }
//        }

        Page<ContactsEntity> contactsEntityPage = null;

        if (predicate == null) {
            contactsEntityPage = invoiceDAO.getContactsRepository().findAll(pageRequest);
        } else {
            contactsEntityPage = invoiceDAO.getContactsRepository().findAll(predicate, pageRequest);
        }

        setRowCount((int) contactsEntityPage.getTotalElements());
        setPageSize(getPageSize());

        for (ContactsEntity contactsEntity : contactsEntityPage) {
            contactsEntityList.add(contactsEntity);
        }
        return contactsEntityList;
    }

    @Override
    public Object getRowKey(ContactsEntity object) {
        return object.getContactId();
    }

    @Override
    public ContactsEntity getRowData(String rowKey) {
        Integer id = Integer.valueOf(rowKey);
        if (contactsEntityList != null) {
            for (ContactsEntity contactsEntity : contactsEntityList) {
                if (id.equals(contactsEntity.getContactId())) {
                    return contactsEntity;
                }
            }
        }
        return null;
    }
}
