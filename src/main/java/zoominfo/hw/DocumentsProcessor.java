package zoominfo.hw;

import zoominfo.hw.model.Document;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;


public class DocumentsProcessor implements ItemProcessor<Document, String>, StepExecutionListener {


    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Documents Processor initialized.");
    }

    @Override
    public String process(Document doc) {
        if (doc.getFirstName() != null && doc.getFirstName().contains("'")) {
            System.out.println("Found apostrophe in ID " + doc.getId() + "'s first name");
            return doc.getId();
        }
        return null;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("Documents Processor ended.");
        return ExitStatus.COMPLETED;
    }
}
