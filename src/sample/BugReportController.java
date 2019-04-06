package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BugReportController {

    @FXML
    Button createReport;
    @FXML
    TextArea bugDescription;

    float POINTS_PER_INCH = 72;
    float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;
    float WIDTH_A4 = 297 * POINTS_PER_MM;
    float HEIGHT_A4 = 210 * POINTS_PER_MM;

    @FXML
    private void createReport(ActionEvent event) {

        SnapshotParameters ssp = new SnapshotParameters();
        WritableImage nodeShot = Main.getPrimaryStage().getScene().snapshot(null);
        File file = new File("screenshot.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(nodeShot, null), "png", file);
        } catch (IOException e) {

        }

        PDDocument doc = null;
        Path path = Paths.get("pdf_file.pdf");
        File pdfFile = new File("pdf_file.pdf");
        if (Files.exists(path)) {
            try {
                doc = PDDocument.load(pdfFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            doc = new PDDocument();
        }

        PDPage page = new PDPage(new PDRectangle(WIDTH_A4, HEIGHT_A4));
        PDImageXObject pdimage;
        PDPageContentStream content;
        try {
            pdimage = PDImageXObject.createFromFile("screenshot.png",doc);
            content = new PDPageContentStream(doc, page);

            content.beginText();
            //Setting the font to the Content stream

            PDFont formFont = PDType0Font.load(doc, new FileInputStream("times.ttf"), false);
            content.setFont(formFont, 14);

            //Setting the position for the line
            content.newLineAtOffset(25 + 150, HEIGHT_A4 - 20);
            content.showText("Пользователь: " + "      " +  "Учреждение: ");
            content.newLineAtOffset(-150, -15);
            content.showText("Описание дефекта:");
            content.newLineAtOffset(0, -20);

            //for testing
            //String text = "Job Description:\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit lectus nec ipsum gravida placerat. Fusce eu erat orci. Nunc eget augue neque. Fusce arcu risus, pulvinar eu blandit ac, congue non tellus. Sed eu neque vitae dui placerat ultricies vel vitae mi. Vivamus vulputate nullam.";
            //String[] stringArray = text.split("\n");
            String[] stringArray = bugDescription.getText().split("\n");
            for(String paragraph : stringArray){
                printParagraph(content, paragraph);
            }
            content.endText();

            float scale = getRightScale(pdimage.getWidth(), pdimage.getHeight());
            float rightWidth = pdimage.getWidth() / scale;
            float rightHeight = pdimage.getHeight() / scale;
            content.drawImage(pdimage, (WIDTH_A4 - rightWidth)/2, 20, rightWidth, rightHeight);
            content.close();
            doc.addPage(page);
            doc.save("pdf_file.pdf");
            doc.close();
            file.delete();
        } catch (IOException ex) {
            //Logger.getLogger(NodeToPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = (Stage) createReport.getScene().getWindow();
        stage.close();
    }

    public float getRightScale(float initialWidth, float initialHeight) {
        if ((initialWidth > 0.7 * WIDTH_A4) | (initialHeight > 0.6 * HEIGHT_A4)) {
            float scaleWidth = initialWidth / ((float) 0.9 * WIDTH_A4);
            float scaleHeight = initialHeight / ((float) 0.7 * HEIGHT_A4);
            float scale = (scaleWidth > scaleHeight) ? scaleWidth : scaleHeight;
            return scale;
        }
        return 1;
    }

    public void printParagraph(PDPageContentStream content, String paragraph) throws IOException {
        int maxLength = 120;
        String[] arr = paragraph.split(" ");
        StringBuilder builder = new StringBuilder();
        for(String s : arr){
            if (builder.length() < maxLength) {
                builder.append(" " + s);
            } else {
                //print out a new line
                content.showText(builder.toString());
                builder.setLength(0);
                content.newLineAtOffset(0, -15);
                builder.append(" " + s);
            }
        }
        content.showText(builder.toString());
        content.newLineAtOffset(0, -15);
        builder.setLength(0);
    }
}
