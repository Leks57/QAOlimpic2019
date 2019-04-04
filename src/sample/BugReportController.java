package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BugReportController {


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
        PDPage page = new PDPage();
        PDImageXObject pdimage;
        PDPageContentStream content;
        try {
            pdimage = PDImageXObject.createFromFile("chart.png",doc);
            content = new PDPageContentStream(doc, page);
            content.drawImage(pdimage, 100, 100);
            content.close();
            doc.addPage(page);
            doc.save("pdf_file.pdf");
            doc.close();
            file.delete();
        } catch (IOException ex) {
            //Logger.getLogger(NodeToPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
