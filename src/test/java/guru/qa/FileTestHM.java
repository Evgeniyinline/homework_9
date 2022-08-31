package guru.qa;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import guru.qa.domain.MyAnimalsFriends;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import static org.assertj.core.api.Assertions.assertThat;


public class FileTestHM {

    ClassLoader classLoader = FileTestHM.class.getClassLoader();
    String zipArhive = "hm9.zip";
    String zipPath = "src/test/resources/";
    String xlsFileName = "file_example_XLS_10.xls";
    String pdfFileName = "reg_report_series_02.pdf";
    String csvFileName = "bfotool-download.csv";

    @Test
    @DisplayName("Чтение pdf.pdf из zip-архива")
    void readPdfFromZip() throws Exception {
        InputStream is = classLoader.getResourceAsStream(zipArhive);
        ZipInputStream zip = new ZipInputStream(is);
        ZipFile zipFile = new ZipFile(new File(zipPath + zipArhive));
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().equals(pdfFileName)) {
                try (InputStream stream = zipFile.getInputStream(entry)) {
                    PDF pdf = new PDF(stream);
                    assertThat(pdf.text).contains("Сведения о государственной регистрации отчета об итогах выпуска ценных бумаг");
                }
            }
        }
        if (is != null) {
            is.close();
            zip.close();
        }
    }

    @Test
    @DisplayName("Чтение xls.xls из zip-архива")
    void readXlsFromZip() throws Exception {
        InputStream is = classLoader.getResourceAsStream(zipArhive);
        ZipInputStream zip = new ZipInputStream(is);
        ZipFile zipFile = new ZipFile(new File(zipPath + zipArhive));
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().equals(xlsFileName)) {
                try (InputStream stream = zipFile.getInputStream(entry)) {
                    XLS xls = new XLS(stream);
                    assertThat(xls.excel.getSheetAt(0)
                            .getRow(4)
                            .getCell(4)
                            .getStringCellValue())
                            .contains("France");
                }
            }
        }
        if (is != null) {
            is.close();
            zip.close();
        }
    }

    @Test
    @DisplayName("Чтение csv.csv из zip-архива")
    void testZipCsv() throws Exception {
        InputStream is = classLoader.getResourceAsStream(zipArhive);
        ZipInputStream zip = new ZipInputStream(is);
        ZipFile zfile = new ZipFile(new File(zipPath + zipArhive));
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().equals(csvFileName)) {
                try (InputStream stream = zfile.getInputStream(entry);
                     CSVReader reader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                    List<String[]> csv = reader.readAll();
                    assertThat(csv).contains(
                            new String[]{"laugh", "slightly", "fifty", "next", "breeze"},
                            new String[]{"green", "smooth", "sign", "him", "attention"}
                    );
                }
            }
        }
        if (is != null) {
            is.close();
            zip.close();
        }
    }

    @Test
    @DisplayName("Разбор json  файла библиотекой Jackson")
    void jsonParse() throws Exception {
            InputStream is = classLoader.getResourceAsStream("animals.json");
            ObjectMapper objectMapper = new ObjectMapper();
            String[] name = new String[] {"Bonya","Fart"};
            String[] ani = new String[] {"Cat","Dog"};
            Integer[] age = new Integer[] {5,2};
            String[] mas = new String[] {"Evgeniy","Irina"};
            MyAnimalsFriends animals = objectMapper.readValue(is, MyAnimalsFriends.class);
            assertThat(animals.getNames()).isEqualTo(name);
            assertThat(animals.getAnimals()).isEqualTo(ani);
            assertThat(animals.getAges()).isEqualTo(age);
            assertThat(animals.getMasters()).isEqualTo(mas);
            assertThat(animals.getIsGoodAnimals()).isEqualTo(true);

    }
}




