package zoominfo.hw;

import zoominfo.hw.model.DocumentFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class resultWriter implements ItemWriter<String>, StepExecutionListener {

    private DocumentFactory df;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        df = new DocumentFactory("output.txt");
        System.out.println("Result Writer initialized.");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        df.closeWriter();
        System.out.println("Result Writer ended.");
        return ExitStatus.COMPLETED;
    }

    @Override
    public void write(List<? extends String> ids) throws Exception {
        for (String id : ids) {
            df.writeId(id);
            System.out.println("Wrote id " + id);
        }
    }
}
