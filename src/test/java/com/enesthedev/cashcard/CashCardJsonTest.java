package com.enesthedev.cashcard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CashCardJsonTest {
    @Autowired
    private JacksonTester<CashCard> json;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSerialize() throws IOException {
        CashCard card = new CashCard(1L, 100.0);

        assertThat(json.write(card)).isStrictlyEqualToJson("cashcard.expected.json");
        assertThat(json.write(card)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(card)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
        assertThat(json.write(card)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(card)).extractingJsonPathNumberValue("@.amount").isEqualTo(100.0);
    }

    @Test
    void testDeserialize() throws IOException {
        CashCard expectedCard = new CashCard(1L, 100.0);
        CashCard card = objectMapper.readValue(
                getClass().getResourceAsStream("cashcard.expected.json"), // Ensure file is in resources
                CashCard.class
        );

        assertThat(card).isNotNull();
        assertThat(card.id()).isEqualTo(expectedCard.id());
        assertThat(card.amount()).isEqualTo(expectedCard.amount());
    }
}
