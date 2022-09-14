package com.example.domain;


import java.util.*;
import javax.persistence.*;

import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "pagos")
public class Persona {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,  mappedBy = "persona")
    private List<Pago> pagos = new ArrayList<>();

    public void addPago(Pago pago) {
        pagos.add(pago);
        pago.setPersona(this);
    }

    public void removePago(Pago pago) {
        boolean removed = pagos.remove(pago);
        if(!removed) {
            throw new NoSuchElementException();
        }
        pago.setPersona(null);
    }

}