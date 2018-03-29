package br.com.tmvolpato.mygames.service;

import br.com.tmvolpato.mygames.common.interfaces.IEntity;

/**
 * Interface serviço crud.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public interface IRawService<T extends IEntity> {

    T create(T entity);

    T update(T entity);

    void delete(Long primaryKey);
}
