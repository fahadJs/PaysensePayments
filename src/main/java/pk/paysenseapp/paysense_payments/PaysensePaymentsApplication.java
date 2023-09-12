package pk.paysenseapp.paysense_payments;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Paysense Payments APIs",
				description = "Complete financial transaction and backend management using spring-boot 3",
				version = "v1",
				contact = @Contact(
						name = "Developer Fahad Javed",
						email = "fahad.js@yahoo.com",
						url = "https://github.com/fahadJs"
				),
				license = @License(
						name = "Paysense Payments",
						url = "https://paysense.pk"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Documentation of complete financial transaction and backend management using spring-boot 3",
				url = "https://github.com/fahadJs/PaysensePayments"
		)
)
public class PaysensePaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaysensePaymentsApplication.class, args);
	}

}