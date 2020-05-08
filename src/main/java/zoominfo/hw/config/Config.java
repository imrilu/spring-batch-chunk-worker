package zoominfo.hw.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import zoominfo.hw.DocProcessor;
import zoominfo.hw.DocReader;
import zoominfo.hw.DocsWriter;
import zoominfo.hw.model.Document;

@Configuration
@EnableBatchProcessing
public class Config {

    final private int CHUNK_SIZE = 50;

    @Autowired private JobBuilderFactory jobs;

    @Autowired private StepBuilderFactory steps;

    @Bean
    public JobRepository jobRepository() throws Exception {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
        factory.setTransactionManager(transactionManager());
        return factory.getObject();
    }

    @Bean
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository());
        return jobLauncher;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public ItemReader<Document> itemReader() {
        return new DocReader();
    }

    @Bean
    public ItemProcessor<Document, String> itemProcessor() {
        return new DocProcessor();
    }

    @Bean
    public ItemWriter<String> itemWriter() {
        return new DocsWriter();
    }

    @Bean
    protected Step processDocs(ItemReader<Document> reader, ItemProcessor<Document, String> processor, ItemWriter<String> writer) {
        return steps.get("processDocs").<Document, String> chunk(CHUNK_SIZE)
          .reader(reader)
          .processor(processor)
          .writer(writer)
          .build();
    }

    @Bean
    public Job job() {
        return jobs
          .get("chunksJob")
          .start(processDocs(itemReader(), itemProcessor(), itemWriter()))
          .build();
    }
}