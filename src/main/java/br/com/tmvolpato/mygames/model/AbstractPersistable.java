package br.com.tmvolpato.mygames.model;

import br.com.tmvolpato.mygames.common.constant.ConstantColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Abstract class.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@MappedSuperclass
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public abstract class AbstractPersistable implements Persistable, Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ConstantColumn.ID, nullable = false, updatable = false)
    @ApiModelProperty(hidden = true)
    private Long id;

    @JsonIgnore
    @Getter
    @Column(name = ConstantColumn.DATE_TIME_INCLUSION, nullable = false, updatable = false)
    @ApiModelProperty(hidden = true)
    private LocalDateTime inclusion;

    @JsonIgnore
    @Getter
    @Column(name = ConstantColumn.DATE_TIME_LAST_EDITION)
    @ApiModelProperty(hidden = true)
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
