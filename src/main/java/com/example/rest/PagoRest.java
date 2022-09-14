package com.example.rest;

import com.example.domain.Pago;
import com.example.domain.Persona;
import com.example.model.*;
import com.example.repos.PagoRepository;
import com.example.repos.PersonaRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static io.micronaut.http.HttpResponse.ok;

@Controller("/pagos")
@RequiredArgsConstructor(onConstructor_ = {@Inject})
@Validated
public class PagoRest {

    private final PagoRepository pagoRepository;
    private final PersonaRepository personaRepository;

    @Get(uri = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PagoDTO>> getAll() {
        var body = pagoRepository.findAllOrderByFechaDesc()
                .stream()
                .map(p -> new PagoDTO(p.getId(), p.getImporte(),p.getFecha(),p.getConcepto(),
                        p.getPersona() == null ? null : p.getPersona().getName()))
                .toList();
        return ok(body);
    }

    @Post(uri = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> create(@Body PagoInDTO pago) {
        Optional<Persona> persona = personaRepository.findById(pago.getIdPersona());
        if (persona.isPresent()){
            Pago newPago = Pago.builder().fecha(LocalDateTime.now())
                    .concepto(pago.getConcepto()).importe(pago.getImporte()).build();
            newPago.setPersona(persona.get());
            newPago = pagoRepository.save(newPago);
            return (ok(new PagoDTO(newPago.getId(), newPago.getImporte(),newPago.getFecha(),newPago.getConcepto(),newPago.getPersona().getName())));
        } else return HttpResponse.notFound("Persona no encontrada");
    }

    @Get(uri = "/balance", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<BalanceDTO>> getBalance() {//List<BalanceDTO>
        List<Pago> pagos = pagoRepository.findAll();
        BigDecimal totalPagos = pagos.stream()
                .map(Pago::getImporte)
                .reduce(new BigDecimal(0), BigDecimal::add);
        List<Persona> personas = personaRepository.findAll();
        Long totalPersonas = personas.stream().count();
        BigDecimal importePersona = totalPagos.divide(new BigDecimal(totalPersonas));

        return ok(personas.stream()
                .map(persona -> new BalanceDTO(pagos.stream()
                        .filter(pago -> pago.getPersona().getId().equals(persona.getId()))
                        .map(Pago::getImporte)
                        .reduce(new BigDecimal(0),BigDecimal::add).subtract(importePersona),persona.getName()))
                .toList());
    }

    /*
    @Get(uri = "/balance", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<BalanceDTO>> getBalance() {//List<BalanceDTO>
        List<PagosPersonaDTO> balancePagos = personaRepository.balancePagos();
        Double totalPagos = balancePagos.stream()
                .map(p -> p.getImporte())
                .reduce(0.0, Double::sum);
        Long totalPersonas = balancePagos.stream().count();
        BigDecimal importePersona = new BigDecimal(totalPagos).divide(new BigDecimal(totalPersonas));

        return ok(balancePagos.stream()
                .map(p -> new BalanceDTO(new BigDecimal(p.getImporte()).subtract(importePersona),p.getName()))
                .toList());
    }
    */
}