package br.com.tmvolpato.mygames.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.TypedQuery;

/**
 * Classe abstrata query.
 *
 * @author Thiago Michel Volpato
 * @sice 2017
 * @version 1.0.0
 */
public abstract class AbstractQuery {

    /**
     * Seta valores de dados da paginação.
     *
     * @param query
     * @param pageable
     */
    protected void addRestrictionPagined(final TypedQuery<?> query, final Pageable pageable) {
        final int pageCurrent = pageable.getPageNumber();
        final int totalRegisterByPage = pageable.getPageSize();
        final int firstRegisterOfPage = pageCurrent * totalRegisterByPage;

        query.setFirstResult(firstRegisterOfPage);
        query.setMaxResults(totalRegisterByPage);
    }

    /**
     * Constroi o sorte.
     *
     * @param sortBy
     * @param sortOrder
     * @return
     */
    protected final Sort constructSort(final String sortBy, final String sortOrder) {
       Sort sortInfo = null;
       if (sortBy != null) {
           sortInfo = new Sort(Sort.Direction.fromString(sortOrder), sortBy);
       }

       return sortInfo;
    }
}
