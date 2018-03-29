package br.com.tmvolpato.mygames.common.web.mapper;

import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
import br.com.tmvolpato.mygames.common.interfaces.IWithLongId;
import org.springframework.stereotype.Component;

/**
 * Classe que mapeia a URI do recurso.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@Component
public class UriMapper implements IUriMapper {

    public UriMapper(){
        super();
    }

    @Override
    public <T extends IWithLongId> String getUriBase(final Class<T> clazz) {
        String simpleName = clazz.getSimpleName().toLowerCase();

        if (simpleName.endsWith("dto")) {
            simpleName = simpleName.substring(ConstantNumeric.ZERO, simpleName.length() - ConstantNumeric.THREE);
        }
        return simpleName + "s";
    }
}
