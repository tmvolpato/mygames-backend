package br.com.tmvolpato.mygames.repository.company;

import br.com.tmvolpato.mygames.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
}
