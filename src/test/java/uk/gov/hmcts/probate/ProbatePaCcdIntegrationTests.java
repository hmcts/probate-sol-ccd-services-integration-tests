package uk.gov.hmcts.probate;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class ProbatePaCcdIntegrationTests extends IntegrationTestBase {

    @Test
    public void validatePostSuccessCCDCase() {
        String token = generateEventToken();
        String rep = testUtils.getJsonFromFile("success.pa.ccd.json").replace("\"event_token\": \"sampletoken\"", "\"event_token\":\"" + token + "\"");
        SerenityRest.given()
                .headers(testUtils.getHeadersWithUserId())
                .body(rep)
                .when().post("/citizens/" + testUtils.getUserId() + "/jurisdictions/PROBATE/case-types/GrantOfRepresentation/cases").
                then()
                .statusCode(201);
    }

    private String generateEventToken() {
        return SerenityRest.given()
                        .headers(testUtils.getHeadersWithUserId())
                        .when().get("/citizens/" + testUtils.getUserId() + "/jurisdictions/PROBATE/case-types/GrantOfRepresentation/event-triggers/applyForGrant/token")
                        .then().assertThat().statusCode(200).extract().path("token");
    }
}
