package zoominfo.hw;

import zoominfo.hw.model.Document;
import zoominfo.hw.model.FileUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

/**
 * Class for reading the document objects from a file
 */
public class DocReader implements ItemReader<Document>, StepExecutionListener {

    private FileUtils df;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        df = new FileUtils("text.txt");
        System.out.println("Document Reader initialized.");
    }

    @Override
    public Document read() throws Exception {
        Document doc = df.readDocument();
        if (doc != null) System.out.println("Read doc: " + doc.toString());
        return doc;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        df.closeReader();
        System.out.println("Document Reader ended.");
        return ExitStatus.COMPLETED;
    }
}
