package com.upeu.proveedores.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upeu.proveedores.dto.ProveedoresRequest;
import com.upeu.proveedores.dto.ProveedoresResponse;
import com.upeu.proveedores.exception.GlobalExceptionHandler;
import com.upeu.proveedores.service.ProveedoresService;
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

@WebMvcTest(ProveedoresController.class)
@Import(GlobalExceptionHandler.class)
class ProveedoresControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

        @MockBean
        private ProveedoresService proveedoresService;

    @Test
    void shouldReturnProveedoress() throws Exception {
        when(proveedoresService.findAll()).thenReturn(List.of(
                ProveedoresResponse.builder().id(1).nombre("Laptop").descripcion("Portatil").idCategoria(2).build()
        ));

        mockMvc.perform(get("/api/v1/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Laptop"))
                .andExpect(jsonPath("$[0].idCategoria").value(2));
    }

    @Test
    void shouldValidateCreateRequest() throws Exception {
        ProveedoresRequest request = ProveedoresRequest.builder()
                .nombre("")
                .descripcion("Proveedoress")
                .idCategoria(null)
                .build();

        mockMvc.perform(post("/api/v1/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error de validación"))
                .andExpect(jsonPath("$.validationErrors.nombre").exists())
                .andExpect(jsonPath("$.validationErrors.idCategoria").exists());
    }
}