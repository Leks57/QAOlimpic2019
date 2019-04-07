package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BugReportController {

    @FXML
    Button createReport, cancel;
    @FXML
    TextArea bugDescription;
    @FXML
    CheckBox needScreenshot;

    float POINTS_PER_INCH = 72;
    float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;
    float WIDTH_A4 = 297 * POINTS_PER_MM;
    float HEIGHT_A4 = 210 * POINTS_PER_MM;

    @FXML
    private void createReport(ActionEvent event) {

        PDDocument doc = null;
        String reportName = Group.getInstance().getParticipantName() + " отчет.pdf";
        Path path = Paths.get(reportName);
        File pdfFile = new File(reportName);
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
        PDPageContentStream content;
        try {
            content = new PDPageContentStream(doc, page);

            content.beginText();
            //Setting the font to the Content stream
            PDFont formFont = PDType0Font.load(doc, new FileInputStream("times.ttf"), false);
            content.setFont(formFont, 14);

            //Setting the position for the line
            content.newLineAtOffset(25 + 75, HEIGHT_A4 - 20);
            content.showText("Пользователь: " + Group.getInstance().getParticipantName() + "      " +  "Учреждение: " + Group.getInstance().getParticipantUniversity());
            content.newLineAtOffset(-75, -15);
            content.showText("Описание дефекта:");
            content.newLineAtOffset(0, -20);

            String[] stringArray = bugDescription.getText().split("\n");
            for(String paragraph : stringArray){
                printParagraph(content, paragraph);
            }
            content.endText();

            if (needScreenshot.isSelected()) {
                SnapshotParameters ssp = new SnapshotParameters();
                WritableImage nodeShot = Main.getPrimaryStage().getScene().snapshot(null);
                File file = new File("screenshot.png");
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(nodeShot, null), "png", file);
                } catch (IOException e) {
                }

                PDImageXObject pdimage;
                pdimage = PDImageXObject.createFromFile("screenshot.png", doc);
                float scale = getRightScale(pdimage.getWidth(), pdimage.getHeight());
                float rightWidth = pdimage.getWidth() / scale;
                float rightHeight = pdimage.getHeight() / scale;
                content.drawImage(pdimage, (WIDTH_A4 - rightWidth) / 2, 20, rightWidth, rightHeight);
                file.delete();
            }
            content.close();
            doc.addPage(page);
            doc.save(reportName);
            doc.close();

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

    @FXML
    public void cancelReport(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
