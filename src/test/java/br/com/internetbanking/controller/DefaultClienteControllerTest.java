package br.com.internetbanking.controller;

import br.com.internetbanking.InternetbankingApplication;
import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.api.usecase.RetrieveCliente;
import br.com.internetbanking.utils.MockUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = InternetbankingApplication.class)
@AutoConfigureMockMvc
@Transactional
class DefaultClienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private RetrieveCliente retrieveCliente;

    @Test
    void successCreateClient() throws Exception {

        mockMvc.perform(post("/api/v1/clientes").contentType(MediaType.APPLICATION_JSON)
                        .content(MockUtils.getJson("create-cliente.json")))
                .andExpect(status().isCreated());
    }

    @Test
    void exceptionCreateClientBadRequestValidation() throws Exception {

        mockMvc.perform(post("/api/v1/clientes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void exceptionCreateClientResourceNotFound() throws Exception {

        mockMvc.perform(post("/api/v1/clientes").contentType(MediaType.APPLICATION_JSON)
                        .content(MockUtils.getJson("create-cliente.json")))
                .andExpect(status().isCreated());

    }
}
