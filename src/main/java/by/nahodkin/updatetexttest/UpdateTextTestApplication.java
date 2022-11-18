package by.nahodkin.updatetexttest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UpdateTextTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpdateTextTestApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(@Autowired UpdateText updateText) {
        return args -> {
            updateText.update();
        };
    }
}
