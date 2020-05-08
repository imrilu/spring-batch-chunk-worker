package zoominfo.hw;

import zoominfo.hw.model.FileUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Class for writing a result batch to a file
 */
public class DocsWriter implements ItemWriter<String>, StepExecutionListener {

    private FileUtils df;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        df = new FileUtils("output.txt");
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
