package br.com.tmvolpato.mygames.data;

import br.com.tmvolpato.mygames.model.Company;
import br.com.tmvolpato.mygames.repository.company.CompanyRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

/**
 * Unit test of CompanyRepository.
 *
 * @author Thiago Michel Volpato
 * @since 2018
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository repository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
        final Company company = new Company("Konami");
        this.repository.save(company);
        Assertions.assertThat(company.getId()).isNotNull();
        Assertions.assertThat(company.getName()).isEqualTo("Konami");
    }

    @Test
    public void deleteShouldRemoveData() {
       final Company company = new Company("Ubisoft");
       this.repository.save(company);

       this.repository.delete(company);
       Assertions.assertThat(this.repository.findById(company.getId()).isPresent()).isEqualTo(false);
    }

    @Test
    public void updateChangePersistData() {
       final Company company = new Company("Capcom");
       this.repository.save(company);

       company.setName("Bethesda");
       this.repository.save(company);
       Assertions.assertThat(company.getName()).isEqualTo("Bethesda");
    }

    @Test
    public void createWhenNameIsEmptyShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);
        final Company company = new Company("");
        this.repository.save(company);
    }

    @Test
    public void createWhenNameIsNullShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);
        final Company company = new Company(null);
        this.repository.save(company);
    }

    @Test
    public void createWhenNameUniqueShouldThrownDataIntegrityViolationException() {
        this.thrown.expect(DataIntegrityViolationException.class);

        final Company companyOne = new Company("Capcom");
        this.repository.save(companyOne);

        final Company companyTwo = new Company("Capcom");
        this.repository.save(companyTwo);
    }
}
