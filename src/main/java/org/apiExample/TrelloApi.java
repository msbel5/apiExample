package org.apiExample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrelloApi {

    private String key = "6ddf0405cad4841811305fb3f635c3ff";
    private String token = "50b08f710af881531ef098b1d1ba37375c32bb835510d3997fe14167049401ad";
    private String baseUrl = "https://api.trello.com/1";
    private String boardId;
    private List<String> cardIds = new ArrayList<>();


    public void createBoard() {
        Response response = RestAssured
                .given()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("name", "TestBoard")
                .when()
                .post(baseUrl + "/boards/")
                .then()
                .statusCode(200)
                .extract()
                .response();

        boardId = response.path("id");
        assertTrue(boardId != null);
    }


    public void createCard() {
        for (int i = 0; i < 2; i++) {
            RestAssured
                    .given()
                    .queryParam("key", key)
                    .queryParam("token", token)
                    .queryParam("idList", boardId)
                    .queryParam("name", "TestCard" + i)
                    .when()
                    .post(baseUrl + "/cards")
                    .then()
                    .statusCode(200);
        }
    }


    public void createCards() {
        for (int i = 0; i < 2; i++) {
            Response response = RestAssured
                    .given()
                    .queryParam("key", key)
                    .queryParam("token", token)
                    .queryParam("idList", boardId)
                    .queryParam("name", "TestCard" + i)
                    .when()
                    .post(baseUrl + "/cards")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            // Add the card ID to the list
            cardIds.add((String) response.path("id"));
        }
    }


    public void updateCard() {
        // Select a random card ID
        String cardId = cardIds.get(new Random().nextInt(cardIds.size()));

        RestAssured
                .given()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("name", "UpdatedCardName")
                .when()
                .put(baseUrl + "/cards/" + cardId)
                .then()
                .statusCode(200);
    }



    public void deleteCards() {
        for (String cardId : cardIds) {
            RestAssured
                    .given()
                    .queryParam("key", key)
                    .queryParam("token", token)
                    .when()
                    .delete(baseUrl + "/cards/" + cardId)
                    .then()
                    .statusCode(200);
        }
    }


    public void deleteBoard() {
        RestAssured
                .given()
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .delete(baseUrl + "/boards/" + boardId)
                .then()
                .statusCode(200);
    }
}
