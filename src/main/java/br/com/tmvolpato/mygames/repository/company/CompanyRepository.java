package br.com.tmvolpato.mygames.repository.company;

import br.com.tmvolpato.mygames.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface repository of company.
 *
 * @author Thiago Michel Volpato
 * @since 2018
 * @version 1.0.0
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
}
