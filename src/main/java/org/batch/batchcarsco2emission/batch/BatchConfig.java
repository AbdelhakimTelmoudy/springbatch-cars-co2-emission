package org.batch.batchcarsco2emission.batch;
import lombok.RequiredArgsConstructor;
import org.batch.batchcarsco2emission.dao.CarRepository;
import org.batch.batchcarsco2emission.entities.Car;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final JobRepository jobRepository; private final CarRepository repository;
    private final PlatformTransactionManager platformTransactionManager;
    @Bean
    public FlatFileItemReader<Car> reader() {
        FlatFileItemReader<Car> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/cars.csv"));
        itemReader.setName("csvReader"); itemReader.setLinesToSkip(1);itemReader.setLineMapper(lineMapper());
        return itemReader;}
    @Bean
    public RepositoryItemWriter<Car> writer() {
        RepositoryItemWriter<Car> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);writer.setMethodName("save");
        return writer;}
    @Bean
    public Step step1() {
        return new StepBuilder("csvImport", jobRepository)
                .<Car, Car>chunk(1000, platformTransactionManager).reader(reader())
                .processor(processor()).writer(writer()).taskExecutor(taskExecutor()).build();}
    @Bean
    public Job runJob() {return new JobBuilder("importCars", jobRepository).start(step1()).build();}
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor(); asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;}
    @Bean
    public CartProcessor processor() {return new CartProcessor();}
    private LineMapper<Car> lineMapper() {
        DefaultLineMapper<Car> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","model","modelYear","make","vehicleClass","engineSize","cylinders","transmission","fuelType",
        "fuelConsumptionCity","fuelConsumptionHwy","fuelConsumptionComb","fuelConsumptionCombMpg","co2Emissions");
        BeanWrapperFieldSetMapper<Car> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Car.class);lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;}
}