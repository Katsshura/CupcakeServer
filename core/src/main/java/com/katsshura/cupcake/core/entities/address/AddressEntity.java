package com.katsshura.cupcake.core.entities.address;

import com.katsshura.cupcake.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class AddressEntity extends BaseEntity {

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Column(name = "property_number", nullable = false)
    private String propertyNumber;

    @Column(name = "complement")
    private String complement;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (Objects.equals(getId(), ((AddressEntity) o).getId())) return true;
        AddressEntity that = (AddressEntity) o;
        return Objects.equals(cep, that.cep)
                && Objects.equals(logradouro, that.logradouro)
                && Objects.equals(propertyNumber, that.propertyNumber)
                && Objects.equals(complement, that.complement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cep, logradouro, propertyNumber, complement);
    }
}
