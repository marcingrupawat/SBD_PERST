import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.garret.perst.*;

class Osoba extends Persistent{
	
	int idOsoba;
	Link idStudent;
	Link idWykladowca;
	String imie;
	String nazwisko;
	String ulica;
	String kodPocztowy;
	String miasto;
	String dataDodania;
	
	 public LocalDateTime dataDodania() { 
		 	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			System.out.println(dtf.format(now)); 
			return now;
     }
	
	
}