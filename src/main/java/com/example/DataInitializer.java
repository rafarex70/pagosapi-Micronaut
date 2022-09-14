package com.example;

import com.example.domain.Pago;
import com.example.domain.Persona;
import com.example.repos.PagoRepository;
import com.example.repos.PersonaRepository;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.event.ApplicationStartupEvent;
import io.micronaut.transaction.TransactionOperations;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;


@Singleton
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationEventListener<ApplicationStartupEvent> {
    private final PersonaRepository personaRepository;

    private final PagoRepository pagoRepository;
    private final TransactionOperations<?> tx;
    @Override
    public void onApplicationEvent(ApplicationStartupEvent event) {
        log.info("initializing sample data...");
        Persona persona1 = Persona.builder().name("Francisco Buyo").build();
        Persona persona2 = Persona.builder().name("Alfonso Pérez").build();
        Persona persona3 = Persona.builder().name("Raúl González").build();
        Persona persona4 = Persona.builder().name("José María Gutiérrez").build();
        List<Persona> personas = List.of(persona1, persona2, persona3, persona4);
        Pago pago1 = Pago.builder().fecha(LocalDateTime.of(2022, Month.SEPTEMBER,14, 9,0))
                .concepto("Cena").importe(new BigDecimal(100)).persona(persona1).build();
        Pago pago2 = Pago.builder().fecha(LocalDateTime.of(2022, Month.SEPTEMBER,14, 8,0))
                .concepto("Taxi").importe(new BigDecimal(10)).persona(persona2).build();
        Pago pago3 = Pago.builder().fecha(LocalDateTime.of(2022, Month.SEPTEMBER,14, 7,0))
                .concepto("Compra").importe(new BigDecimal(53.4)).persona(persona2).build();
        List<Pago> pagos = List.of(pago1, pago2, pago3);
        tx.executeWrite(status -> {
            this.pagoRepository.deleteAll();
            this.personaRepository.deleteAll();
            this.personaRepository.saveAll(personas);
            this.pagoRepository.saveAll(pagos);
            return null;
        });
        tx.executeRead(status -> {
            this.personaRepository.findAll().forEach(p -> log.info("saved person: {}", p));
            this.pagoRepository.findAll().forEach(p -> log.info("saved pago: {}", p));
            return null;
        });
        log.info("data initialization is done...");
    }
}