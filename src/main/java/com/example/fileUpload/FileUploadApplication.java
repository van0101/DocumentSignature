package com.example.fileUpload;

import com.example.fileUpload.security.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ Config.class })
public class FileUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploadApplication.class, args);
	}

}
