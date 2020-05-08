package zoominfo.hw;

import zoominfo.hw.model.Document;
import zoominfo.hw.model.DocumentFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

public class DocumentReader implements ItemReader<Document>, StepExecutionListener {

    private DocumentFactory df;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        df = new DocumentFactory("text.txt");
        System.out.println("Document Reader initialized.");
    }

    @Override
    public Document read() throws Exception {
        Document doc = df.readDocument();
        if (doc != null) System.out.println("Read doc: [firstname: " + doc.getFirstName() +
                " , lastname: " + doc.getLastName() + ", id: " + doc.getId());
        return doc;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        df.closeReader();
        System.out.println("Document Reader ended.");
        return ExitStatus.COMPLETED;
    }
}
