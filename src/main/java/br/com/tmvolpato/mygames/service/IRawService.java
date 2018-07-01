package br.com.tmvolpato.mygames.service;

import br.com.tmvolpato.mygames.common.interfaces.IEntity;

/**
 * Interface service generic.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public interface IRawService<E, T extends IEntity>{

    T create(E userApplication, T entity);

    T update(E userApplication, T entity);

    void delete(E userApplication, Long primaryKey);
}
