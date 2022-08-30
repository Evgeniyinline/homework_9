package guru.qa;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import static org.assertj.core.api.Assertions.assertThat;


public class FileTestHM {

    ClassLoader classLoader = FileTestHM.class.getClassLoader();

    @Test
    void readPdfFromZip() throws Exception {
        InputStream is = classLoader.getResourceAsStream("Homework9.zip");
        ZipInputStream zip = new ZipInputStream(is);
        ZipEntry entry;
        ZipFile zipFile = new ZipFile(new File("src/test/resources/" + "Homework9.zip"));
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().contains("Ku_Ja.pdf")) {
                try (InputStream stream = zipFile.getInputStream(entry)) {
                    PDF pdf = new PDF(stream);
                    assertThat(pdf.text).contains("МОСКОВСКИЙ");
                }
            }
        }
    }


    @Test
    void readXlsFromZip() throws Exception {
        InputStream is = classLoader.getResourceAsStream("Homework9.zip");
        ZipInputStream zip = new ZipInputStream(is);
        ZipEntry entry;
        ZipFile zipFile = new ZipFile(new File("src/test/resources/" + "Homework9.zip"));
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().contains("hm9.xlsx")) {
                try (InputStream stream = zipFile.getInputStream(entry)) {
                    XLS xls = new XLS (stream);
                    assertThat(xls.excel.getSheetAt(0)
                            .getRow(2)
                            .getCell(0)
                            .getStringCellValue()).contains("Электробалластер ЭЛБ-4");
                }
            }
        }
    }

}



