package org.apiExample;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrelloApiTest {

    private TrelloApi trelloApi;

    @BeforeEach
    public void setUp() {
        trelloApi = new TrelloApi();
        trelloApi.createBoard();
    }

    @Test
    public void testTrelloBoardAndCards() {
        trelloApi.createCards();
        trelloApi.updateCard();
    }

    @AfterEach
    public void tearDown() {
        trelloApi.deleteCards();
        trelloApi.deleteBoard();
    }
}