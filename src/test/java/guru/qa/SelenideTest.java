package guru.qa;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SelenideTest {

    @Test
    void downloadTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File file = $("#raw-url").download();
        try (InputStream is = new FileInputStream(file)) {
            byte[] fileContent = is.readAllBytes();
            assertThat(new String(fileContent, StandardCharsets.UTF_8)).contains("Contributions to JUnit 5");

        }
    }

    @Test
    void uploadTest() {
        Selenide.open("http://the-internet.herokuapp.com/upload");
        $("input[type='file']").uploadFromClasspath("1.txt");
        $("#file-submite").click();
    }

}
