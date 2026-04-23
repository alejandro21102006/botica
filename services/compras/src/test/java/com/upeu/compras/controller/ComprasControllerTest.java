package com.upeu.compras.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upeu.compras.dto.ComprasRequest;
import com.upeu.compras.dto.ComprasResponse;
import com.upeu.compras.exception.GlobalExceptionHandler;
import com.upeu.compras.service.ComprasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ComprasController.class)
@Import(GlobalExceptionHandler.class)
class ComprasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

        @MockBean
        private ComprasService comprasService;

    @Test
    void shouldReturnComprass() throws Exception {
        when(comprasService.findAll()).thenReturn(List.of(
                ComprasResponse.builder().id(1).nombre("Laptop").descripcion("Portatil").idCategoria(2).build()
        ));

        mockMvc.perform(get("/api/v1/compras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Laptop"))
                .andExpect(jsonPath("$[0].idCategoria").value(2));
    }

    @Test
    void shouldValidateCreateRequest() throws Exception {
        ComprasRequest request = ComprasRequest.builder()
                .nombre("")
                .descripcion("Comprass")
                .idCategoria(null)
                .build();

        mockMvc.perform(post("/api/v1/compras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error de validación"))
                .andExpect(jsonPath("$.validationErrors.nombre").exists())
                .andExpect(jsonPath("$.validationErrors.idCategoria").exists());
    }
}