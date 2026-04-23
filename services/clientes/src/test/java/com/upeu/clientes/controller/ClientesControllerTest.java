package com.upeu.clientes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upeu.clientes.dto.ClientesRequest;
import com.upeu.clientes.dto.ClientesResponse;
import com.upeu.clientes.exception.GlobalExceptionHandler;
import com.upeu.clientes.service.ClientesService;
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

@WebMvcTest(ClientesController.class)
@Import(GlobalExceptionHandler.class)
class ClientesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

        @MockBean
        private ClientesService clientesService;

    @Test
    void shouldReturnClientess() throws Exception {
        when(clientesService.findAll()).thenReturn(List.of(
                ClientesResponse.builder().id(1).nombre("Laptop").descripcion("Portatil").idCategoria(2).build()
        ));

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Laptop"))
                .andExpect(jsonPath("$[0].idCategoria").value(2));
    }

    @Test
    void shouldValidateCreateRequest() throws Exception {
        ClientesRequest request = ClientesRequest.builder()
                .nombre("")
                .descripcion("Clientess")
                .idCategoria(null)
                .build();

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error de validación"))
                .andExpect(jsonPath("$.validationErrors.nombre").exists())
                .andExpect(jsonPath("$.validationErrors.idCategoria").exists());
    }
}