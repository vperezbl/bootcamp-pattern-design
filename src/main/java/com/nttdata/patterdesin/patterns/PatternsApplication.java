package com.nttdata.patterdesin.patterns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.nttdata.patterdesin.patterns.chain.Unidad;
import com.nttdata.patterdesin.patterns.chain.Unidad2;
import com.nttdata.patterdesin.patterns.chain.UnidadDeMando;
import com.nttdata.patterdesin.patterns.decorator.PersonDecorator;
import com.nttdata.patterdesin.patterns.decorator.PersonInterface;
import com.nttdata.patterdesin.patterns.domain.Bussines;
import com.nttdata.patterdesin.patterns.domain.Person;
import com.nttdata.patterdesin.patterns.proxy.AbstractPersonProxy;
import com.nttdata.patterdesin.patterns.proxy.PersonProxyBBDD;
import com.nttdata.patterdesin.patterns.proxy.PersonProxyCOLAS;
import com.nttdata.patterdesin.patterns.singleton.PersonSingleton;
import com.nttdata.patterdesin.patterns.singleton.PersonSingletonEnum;

@SpringBootApplication
@ComponentScan(value = "com.nttdata")
public class PatternsApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext context;

	private PersonInterface personPrototype;

	public PatternsApplication(PersonInterface personPrototype) {
		this.personPrototype = personPrototype;
	}

	public static void main(String[] args) {
		SpringApplication.run(PatternsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Inicio...");

		System.out.println("=============================================================================");
		System.out.println("Prácticas con Singleton");
		System.out.println("=============================================================================");
		PersonSingletonEnum.INSTANCE.person().setName("Joselito");

		System.out.println(PersonSingletonEnum.INSTANCE.person().getName());

		PersonSingleton.INSTANCE.setName("Ismael");
		System.out.println(
				"ID del objeto singleton: " + PersonSingleton.INSTANCE.getName() + " - " + PersonSingleton.INSTANCE);

		Bussines bussines = new Bussines();
		bussines.ejecuta();

		System.out.println("=============================================================================");
		System.out.println("Prácticas con Prototype");
		System.out.println("=============================================================================");

		Person ismael = new Person("Ismael", 42);
		Person ismaelClon = ismael.clone();

		System.out.println("ID del objeto ismael: " + ismael.getName() + " Edad: " + ismael.getAge() + "- " + ismael);
		System.out.println("ID del objeto ismaelClon: " + ismaelClon.getName() + " Edad: " + ismaelClon.getAge() + " - "
				+ ismaelClon);

		System.out.println("=============================================================================");
		System.out.println("Prácticas con Builder");
		System.out.println("=============================================================================");

		Person pepito = Person.Builder().name("ismael").age(15200).build();

		System.out.println("ID del objeto ismael: " + pepito.getName() + " Edad: " + pepito.getAge() + "- " + pepito);

		System.out.println("=============================================================================");
		System.out.println("Prácticas con Singleton/prototype/builder Spring/Lombok");
		System.out.println("=============================================================================");

		System.out.println("Singleton Object ID ->" + personPrototype.getName());
		System.out.println("Prototype Object ID ->" + ((Person) context.getBean("personPrototype")).getName());

		System.out.println("Singleton Object ID " + ((Person) context.getBean("personSingleton")).getName());
		System.out.println("Singleton Object ID " + context.getBean("personSingleton"));

		System.out.println("Prototype Object ID" + context.getBean("personPrototype"));
		System.out.println("Prototype Object ID" + context.getBean("personPrototype"));

		System.out.println("=============================================================================");
		System.out.println("Prácticas con Decorador");
		System.out.println("=============================================================================");
		
		PersonInterface person2 = Person.Builder().age(42).name("Ismael Palacios Estudillo").build();
		PersonInterface person3 =  new PersonDecorator(person2);
		
		System.out.println("Prototype Name " + person2.getName());
		System.out.println("Prototype Decorated Name " + person3.getName());
		
		System.out.println("Prototype Edad Meses" + person2.getAge());
		System.out.println("Prototype Decorated Edad Días " + person3.getAge());

		System.out.println("=============================================================================");
		System.out.println("Prácticas con Proxy");
		System.out.println("=============================================================================");

		Person entidad = Person.Builder().age(42).name("Fulanito Fernandez").build();
		
		
		AbstractPersonProxy proxyBBDD = new PersonProxyBBDD(entidad);
		AbstractPersonProxy proxyCOLAS = new PersonProxyCOLAS(entidad);
		
		proxyBBDD.operacion();
		proxyCOLAS.operacion();

		System.out.println("=============================================================================");
		System.out.println("Prácticas con Proxy");
		System.out.println("=============================================================================");

		UnidadDeMando unidadDeMando = new UnidadDeMando();
		
		unidadDeMando.anadirEjercito(new Unidad("Capitan"));
		unidadDeMando.anadirEjercito(new Unidad2("Sargento"));
		unidadDeMando.anadirEjercito(new Unidad("Soldado"));
		
		unidadDeMando.ejecutaOrden("Matar a Robocop");
		


		System.out.println("Fin...");
	}
}
