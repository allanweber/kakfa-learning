package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class ReadingReportService {

    private final Path source = new File("src/main/reosurces/report.txt").toPath();

    public static void main(String[] args) {
        var readingReportService = new ReadingReportService();
        try (var service = new KafkaService<>(ReadingReportService.class.getSimpleName(),
                "USER_GENERATE_READING_REPORT",
                readingReportService::parse,
                User.class,
                Map.of())) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, User> record) throws IOException {
        System.out.println("------------------------------------------");
        System.out.println("Processing report for " + record.value());

        var user = record.value();
        File target = new File(user.getReportPath());
        IO.copyTo(source, target);
        IO.append(target, "Created for  " + user.getId());

        System.out.println("File created -> " + target.getAbsolutePath());
    }
}
