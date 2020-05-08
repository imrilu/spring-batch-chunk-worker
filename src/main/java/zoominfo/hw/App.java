package zoominfo.hw;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner
{
    @Autowired
    Job job;

    @Autowired
    JobLauncher jobLauncher;

    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        jobLauncher.run(job, new JobParameters());
    }
}