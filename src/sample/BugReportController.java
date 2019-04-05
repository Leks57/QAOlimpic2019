package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BugReportController {

    @FXML
    Button createReport;

    float POINTS_PER_INCH = 72;
    float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;

    @FXML
    private void createReport(ActionEvent event) {

        SnapshotParameters ssp = new SnapshotParameters();
        WritableImage nodeShot = Main.getPrimaryStage().getScene().snapshot(null);
        File file = new File("chart.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(nodeShot, null), "png", file);
        } catch (IOException e) {

        }

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(297 * POINTS_PER_MM, 210 * POINTS_PER_MM));
        PDImageXObject pdimage;
        PDPageContentStream content;
        try {
            pdimage = PDImageXObject.createFromFile("chart.png",doc);
            content = new PDPageContentStream(doc, page);
            float scale = getRightScale(pdimage.getWidth(), pdimage.getHeight());
            content.drawImage(pdimage, 30, 30, pdimage.getWidth() / scale, pdimage.getHeight() / scale);
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
        if ((initialWidth > 200 * POINTS_PER_MM) | (initialHeight > 150 * POINTS_PER_MM)) {
            float scaleWidth = initialWidth / (200 * POINTS_PER_MM);
            float scaleHeight = initialHeight / (150 * POINTS_PER_MM);
            float scale = (scaleWidth > scaleHeight) ? scaleWidth : scaleHeight;
            return scale;
        }
        return 1;
    }
}
