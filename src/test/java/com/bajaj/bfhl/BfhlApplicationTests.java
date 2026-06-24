package com.bajaj.bfhl;

import com.bajaj.bfhl.config.AppConfig;
import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;
import com.bajaj.bfhl.service.impl.BfhlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BfhlApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BfhlService bfhlService;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/bfhl";
    }


    @Test
    @DisplayName("Example A: mixed input with alpha, numbers, and special chars")
    void testExampleA() {
        BfhlRequest request = buildRequest("a", "1", "334", "4", "R", "$");

        BfhlResponse response = bfhlService.process(request);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).containsExactly("1");
        assertThat(response.getEvenNumbers()).containsExactlyInAnyOrder("334", "4");
        assertThat(response.getAlphabets()).containsExactlyInAnyOrder("A", "R");
        assertThat(response.getSpecialCharacters()).containsExactly("$");
        assertThat(response.getSum()).isEqualTo("339");
        assertThat(response.getConcatString()).isEqualTo("Ra");
    }

    @Test
    @DisplayName("Example B: more complex mixed input")
    void testExampleB() {
        BfhlRequest request = buildRequest("2", "a", "y", "4", "&", "-", "*", "5", "92", "b");

        BfhlResponse response = bfhlService.process(request);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).containsExactly("5");
        assertThat(response.getEvenNumbers()).containsExactly("2", "4", "92");
        assertThat(response.getAlphabets()).containsExactly("A", "Y", "B");
        assertThat(response.getSpecialCharacters()).containsExactly("&", "-", "*");
        assertThat(response.getSum()).isEqualTo("103");
        assertThat(response.getConcatString()).isEqualTo("ByA");
    }

    @Test
    @DisplayName("Example C: multi-character alphabetic strings only")
    void testExampleC() {
        BfhlRequest request = buildRequest("A", "ABCD", "DOE");

        BfhlResponse response = bfhlService.process(request);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).isEmpty();
        assertThat(response.getEvenNumbers()).isEmpty();
        assertThat(response.getAlphabets()).containsExactly("A", "ABCD", "DOE");
        assertThat(response.getSpecialCharacters()).isEmpty();
        assertThat(response.getSum()).isEqualTo("0");
        assertThat(response.getConcatString()).isEqualTo("EoDdCbAa");
    }

    @Test
    @DisplayName("Edge case: empty data array returns zeros and empty lists")
    void testEmptyDataArray() {
        BfhlRequest request = buildRequest(); // no elements

        BfhlResponse response = bfhlService.process(request);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).isEmpty();
        assertThat(response.getEvenNumbers()).isEmpty();
        assertThat(response.getAlphabets()).isEmpty();
        assertThat(response.getSpecialCharacters()).isEmpty();
        assertThat(response.getSum()).isEqualTo("0");
        assertThat(response.getConcatString()).isEmpty();
    }

    @Test
    @DisplayName("Edge case: all even numbers")
    void testAllEvenNumbers() {
        BfhlRequest request = buildRequest("2", "4", "100");

        BfhlResponse response = bfhlService.process(request);

        assertThat(response.getEvenNumbers()).containsExactly("2", "4", "100");
        assertThat(response.getOddNumbers()).isEmpty();
        assertThat(response.getSum()).isEqualTo("106");
        assertThat(response.getConcatString()).isEmpty();
    }

    @Test
    @DisplayName("Edge case: all odd numbers")
    void testAllOddNumbers() {
        BfhlRequest request = buildRequest("1", "3", "7");

        BfhlResponse response = bfhlService.process(request);

        assertThat(response.getOddNumbers()).containsExactly("1", "3", "7");
        assertThat(response.getEvenNumbers()).isEmpty();
        assertThat(response.getSum()).isEqualTo("11");
    }

    @Test
    @DisplayName("Edge case: only special characters")
    void testOnlySpecialChars() {
        BfhlRequest request = buildRequest("@", "#", "$", "&");

        BfhlResponse response = bfhlService.process(request);

        assertThat(response.getSpecialCharacters()).containsExactly("@", "#", "$", "&");
        assertThat(response.getAlphabets()).isEmpty();
        assertThat(response.getOddNumbers()).isEmpty();
        assertThat(response.getEvenNumbers()).isEmpty();
        assertThat(response.getSum()).isEqualTo("0");
        assertThat(response.getConcatString()).isEmpty();
    }

    @Test
    @DisplayName("User identity fields are correctly populated")
    void testUserIdentityFields() {
        BfhlRequest request = buildRequest("1");

        BfhlResponse response = bfhlService.process(request);

        assertThat(response.getUserId()).isEqualTo("ankit_bansal_15072005");
        assertThat(response.getEmail()).isEqualTo("bansalankit1575@gmail.com");
        assertThat(response.getRollNumber()).isEqualTo("2310992106");
    }

    @Test
    @DisplayName("Single-char alphabets produce correct uppercase and concat")
    void testSingleCharAlphabets() {
        BfhlRequest request = buildRequest("z");

        BfhlResponse response = bfhlService.process(request);

        assertThat(response.getAlphabets()).containsExactly("Z");
        // Only one char: reversed = [z], alternating → uppercase → "Z"
        assertThat(response.getConcatString()).isEqualTo("Z");
    }

    // =========================================================================
    // Integration tests (full HTTP stack via TestRestTemplate)
    // =========================================================================

    @Test
    @DisplayName("Integration: POST /bfhl returns 200 for valid request")
    void integrationTestValidRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = "{\"data\":[\"a\",\"1\",\"334\",\"4\",\"R\",\"$\"]}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<BfhlResponse> response =
                restTemplate.postForEntity(baseUrl, entity, BfhlResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isSuccess()).isTrue();
        assertThat(response.getBody().getConcatString()).isEqualTo("Ra");
    }

    @Test
    @DisplayName("Integration: POST /bfhl returns 400 when 'data' field is missing (null)")
    void integrationTestMissingDataField() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // data field is explicitly null
        String body = "{\"data\":null}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(baseUrl, entity, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsKey("is_success");
        assertThat(response.getBody().get("is_success")).isEqualTo(false);
    }

    @Test
    @DisplayName("Integration: POST /bfhl returns 400 for malformed JSON body")
    void integrationTestMalformedJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = "not-valid-json";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(baseUrl, entity, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    // =========================================================================
    // Helper
    // =========================================================================

    private BfhlRequest buildRequest(String... items) {
        BfhlRequest req = new BfhlRequest();
        req.setData(Arrays.asList(items));
        return req;
    }
}
