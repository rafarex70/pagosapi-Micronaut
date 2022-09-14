package com.example.rest;

import com.example.domain.Persona;
import com.example.model.PagoDTO;
import com.example.model.PersonaDTO;
import com.example.model.PersonaInDTO;
import com.example.repos.PersonaRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static io.micronaut.http.HttpResponse.ok;

@Controller("/personas")
@RequiredArgsConstructor(onConstructor_ = {@Inject})
@Validated
public class PersonaRest {
    private final PersonaRepository personaRepository;

    @Get(uri = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PersonaDTO>> getAll() {
        var body = personaRepository.findAll()
                .stream()
                .map(p -> new PersonaDTO(p.getId(), p.getName()))
                .toList();
        return ok(body);
    }

    @Post(uri = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> create(@Body PersonaInDTO persona) {
        return ok(personaRepository.save(Persona.builder().name(persona.getName()).build()));
    }

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> getById(@PathVariable Long id) {
        return personaRepository.findById(id)
                .map(p -> ok(new PersonaDTO(p.getId(), p.getName())))
                .orElseGet(HttpResponse::notFound);
    }
}