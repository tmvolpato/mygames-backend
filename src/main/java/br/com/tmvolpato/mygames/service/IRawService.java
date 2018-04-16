package br.com.tmvolpato.mygames.service;

import br.com.tmvolpato.mygames.common.interfaces.IEntity;

/**
 * Interface serviço crud.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public interface IRawService<E, T extends IEntity>{

    T create(E owner, T entity);

    T update(E owner, T entity);

    void delete(E owner, Long primaryKey);
}
