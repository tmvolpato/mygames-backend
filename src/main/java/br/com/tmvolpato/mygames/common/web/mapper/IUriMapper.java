package br.com.tmvolpato.mygames.common.web.mapper;

import br.com.tmvolpato.mygames.common.interfaces.IWithLongId;

/**
 * Interface of uri mapper.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public interface IUriMapper {

    <T extends IWithLongId> String getUriBase(Class<T> clazz);
}
