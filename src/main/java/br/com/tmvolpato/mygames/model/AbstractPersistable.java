package br.com.tmvolpato.mygames.model;

import br.com.tmvolpato.mygames.common.constant.ConstantColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Abstract class.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
@MappedSuperclass
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public abstract class AbstractPersistable implements Persistable, Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ConstantColumn.ID, nullable = false, updatable = false)
    private Long id;

    @JsonIgnore
    @Getter
    @Column(name = ConstantColumn.DATE_TIME_INCLUSION, nullable = false, updatable = false)
    private LocalDateTime inclusion;

    @JsonIgnore
    @Getter
    @Column(name = ConstantColumn.DATE_TIME_LAST_EDITION)
    private LocalDateTime lastEdition;

    @PrePersist
    protected void prePersist() {
        this.inclusion = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.lastEdition = LocalDateTime.now();
    }
}
