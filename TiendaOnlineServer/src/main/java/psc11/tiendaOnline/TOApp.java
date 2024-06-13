package psc11.tiendaOnline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TOApp {

	public static void main(String[] args) {
		SpringApplication.run(TOApp.class, args);
		System.out.println("Servidor iniciado correctamente");
	}
	
}