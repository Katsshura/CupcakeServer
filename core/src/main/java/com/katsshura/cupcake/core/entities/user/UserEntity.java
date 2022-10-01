package com.katsshura.cupcake.core.entities.user;


import com.katsshura.cupcake.core.entities.BaseEntity;
import com.katsshura.cupcake.core.entities.address.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`user`")
public class UserEntity extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "birthday_date", nullable = false)
    private LocalDate birthdayDate;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_address",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<AddressEntity> registeredAddresses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (Objects.equals(getId(), ((UserEntity) o).getId())) return true;
        UserEntity that = (UserEntity) o;
        return Objects.equals(email, that.email)
                && Objects.equals(name, that.name)
                && Objects.equals(cpf, that.cpf)
                && Objects.equals(birthdayDate, that.birthdayDate)
                && Objects.equals(password, that.password)
                && Objects.equals(registeredAddresses, that.registeredAddresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, name, cpf, birthdayDate, password, registeredAddresses);
    }
}
