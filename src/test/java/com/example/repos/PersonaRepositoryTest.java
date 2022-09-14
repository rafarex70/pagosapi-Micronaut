package com.example.repos;

import com.example.Application;
import com.example.domain.Persona;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


@MicronautTest(application = Application.class, startApplication = false)
class PostRepositoryTest {
    @Inject
    PersonaRepository personaRepository;
    @PersistenceContext
    EntityManager entityManager;
    @Test
    void testCreatePersona() {
        Persona persona = Persona.builder().name("Francisco Buyo").build();
        this.entityManager.persist(persona);
        assertThat(persona.getId()).isNotNull();
        assertTrue(personaRepository.findById(persona.getId()).isPresent());
    }
}