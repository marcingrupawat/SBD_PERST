import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.garret.perst.*;


public class DB extends Persistent{
	
	static final int pagePoolSize = 32*1024*1024;
	
	static byte[] inputBuffer = new byte[256];
	
	 
	static String input(String prompt) {
        while (true) { 
            try { 
                System.out.print(prompt);
                int len = System.in.read(inputBuffer);
                String answer = new String(inputBuffer, 0, len).trim();
                if (answer.length() != 0) {
                    return answer;
                }
            } catch (IOException x) {}
        }
    }
	
	static long inputLong(String prompt) { 
        while (true) { 
            try { 
                return Long.parseLong(input(prompt), 10);
            } catch (NumberFormatException x) { 
                System.err.println("Invalid integer constant");
            }
        }
    }
	
	
	
	public static void main(String[] args) {
		
		Osoba osoba;
        Student student;
        Wykladowca wykladowca;
        Kierunek kierunek;
        RelKierunekStudentPrzedmiot relKSP;
        RelKierunekStudentPrzedmiot[] relKSPs;
        Przedmiot przedmiot;
        Set result; 
        Iterator iterator;
        
        
		
		Storage storage = StorageFactory.getInstance().createStorage();
		storage.open("SBD_PERST.dbs", pagePoolSize); 
		//storage.open("test1.dbs", pagePoolSize); 
		Database db = new Database(storage);
		
		//Query detailQuery = db.prepare(Przedmiot.class, "idPrzedmiot = ?");
		//Query detailQuery = db.prepare(Przedmiot.class, "nazwa Like ?");
		Query detailQuery = db.prepare(Przedmiot.class, "nazwa like ?");
		
		
        while (true) { 
            try { 
                switch ((int)inputLong("-------------------------------------\n" + 
                                       "Menu:\n" + 
                                       "1. Usuñ tabele\n" + 
                                       "2. Utwórz tabele\n" + 
                                       "3. Dodaj po jednym wierszu danych(testowe) do ka¿dej tabeli\n" + 
                                       "4. Import danych z pliku do ka¿dej tabeli(pliki csv w projekcie)\n" + 
                                       "5. Wylistuj zawartoœæ wszystkich tabel\n" + 
                                       "6. Wykonaj metodê z klasy Osoba dataDodania() -doda wiersz do tabeli osoba z uzupe³nionym polem dataDodania\n" + 
                                       "7. -------------\n" + 
                                       "8. -------------\n" + 
                                       "9. Exit\n\n>>"))
                {
                  case 1:
                	 
                	
	                	  db.dropTable(Osoba.class);  
	                	  db.dropTable(Student.class);
	                	  db.dropTable(Wykladowca.class);
	      		          db.dropTable(Kierunek.class);
	      		          db.dropTable(RelKierunekStudentPrzedmiot.class);
	      		          db.dropTable(Przedmiot.class);
	      		          storage.commit();
                	  
                	
 
      		       
      		        break;
                  case 2:
                	 
	                	db.createTable(Osoba.class);
	                  	db.createIndex(Osoba.class, "idOsoba", true);
	                  	
	                  	db.createTable(Student.class);
	                  	db.createIndex(Student.class, "idIndeks", true);  
	                  	
	                  	db.createTable(Kierunek.class);
	                  	db.createIndex(Kierunek.class, "idKierunek", true);
	                  	
	                  	db.createTable(Wykladowca.class);
	                  	db.createIndex(Wykladowca.class, "idPracownik", true);
	                  	
	                  	db.createTable(Przedmiot.class);
	                  	db.createIndex(Przedmiot.class, "idPrzedmiot", true);
	                  	//db.createIndex(Przedmiot.class, "nazwa", true);
	                  	                	
	                  	db.createTable(RelKierunekStudentPrzedmiot.class);
	                  	db.createIndex(RelKierunekStudentPrzedmiot.class, "idRelacja", true);
                  	
        		        storage.commit();
      		          
      		        break;
                  case 3:
                	  //Osoba student
          	  			osoba = new Osoba();
        		        osoba.idOsoba=100; 
        		        osoba.idStudent=storage.createLink();
        		        osoba.imie="Adam";
        		        osoba.nazwisko = "Kowalski";
        		        osoba.ulica = "Kwiatowa 4";
        		        osoba.kodPocztowy = "03-230";
        		        osoba.miasto = "Warszawa";
        		        osoba.dataDodania=osoba.dataDodania().toString();
        		        db.addRecord(osoba);
        		        storage.commit();
        		        
        		      //Osoba wykladowca
        		        osoba = new Osoba();
        		        osoba.idOsoba=101;
        		        osoba.idWykladowca=storage.createLink();
        		        osoba.imie="Jan";
        		        osoba.nazwisko = "Nowak";
        		        osoba.ulica = "Kwiatowa 4";
        		        osoba.kodPocztowy = "04-230";
        		        osoba.miasto = "Kraków";
        		        osoba.dataDodania=osoba.dataDodania().toString();
        		        db.addRecord(osoba);
        		        storage.commit();
      		       
        		        iterator = db.select(Osoba.class, "idOsoba=100");
        		        osoba = (Osoba)iterator.next();
        		        student = new Student();
        		        student.idIndeks=1001;
        		        student.osoba = osoba;
        		        student.idRelacja=storage.createLink();
        		        db.addRecord(student);
        		        storage.commit();
        		        
        		        iterator = db.select(Osoba.class, "idOsoba=100");
        		        osoba = (Osoba)iterator.next();
        		        wykladowca = new Wykladowca();
        		        wykladowca.idPracownik=100;
        		        wykladowca.osoba=osoba;
        		        db.addRecord(wykladowca);
        		        storage.commit();
      		        
        		        kierunek = new Kierunek();
        		        kierunek.idKierunek=100;
        		        kierunek.nazwaKierunku="Informatyka";
        		        db.addRecord(kierunek);
        		        storage.commit(); 
        		               		 
        		        przedmiot = new Przedmiot();
        		        przedmiot.idPrzedmiot=10;
        		        przedmiot.nazwa="SBD";
        		        przedmiot.idRelacja=storage.createLink();
        		        db.addRecord(przedmiot);             		        
        		        storage.commit();
        		        
                	   break;
                  case 4:
                	  
                	  //tabela Osoba
                	  try
                      {
	                      ArrayList<String> ar = new ArrayList<String>();
	                      File csvFile = new File("osoba.csv");
	                      BufferedReader br = new BufferedReader(new FileReader(csvFile));
	                      String line = "";
	                     
	                      int lineNumber = 0;
	
	                      while ((line = br.readLine()) != null) {
	                      String[] arr = line.split(";");
	                      //for the first line it'll print
	
	
	                      osoba = new Osoba();
	        		      osoba.idOsoba=Integer.parseInt(arr[0]);  
	        		      
	        		      osoba.imie=arr[1];
	        		      osoba.nazwisko = arr[2];
	        		      osoba.ulica = arr[3];
	        		      osoba.kodPocztowy = arr[4];
	        		      osoba.miasto = arr[5];
	        		      db.addRecord(osoba);
	        		      storage.commit();
	
	                      lineNumber++;
                      	  } 
                      
	                      br.close();
	                      ar.clear();
	                      
                      }catch(IOException ex) {
                    	  ex.printStackTrace();
                      }
                	  
                	//tabela Student
                	  try
                      {
	                      ArrayList<String> ar = new ArrayList<String>();
	                      File csvFile = new File("student.csv");
	                      BufferedReader br = new BufferedReader(new FileReader(csvFile));
	                      String line = "";
	                     
	                      int lineNumber = 0;
	
	                      while ((line = br.readLine()) != null) {
	                      String[] arr = line.split(";");
	
	        		      iterator = db.select(Osoba.class, "idOsoba=" + arr[1]);
	        		      osoba = (Osoba)iterator.next();
	        		      student = new Student();
	        		      student.idIndeks=Integer.parseInt(arr[0]);
	        		      student.osoba = osoba;
	        		      student.idRelacja=storage.createLink();
	        		      db.addRecord(student);
	        		      storage.commit();
	        		      
	                      lineNumber++;
                      	  } 
                      
	                      br.close();
	                      ar.clear();
	                      
                      }catch(IOException ex) {
                    	  ex.printStackTrace();
                      }
              		  
                	//tabela Wykladowca
                	  try
                      {
	                      ArrayList<String> ar = new ArrayList<String>();
	                      File csvFile = new File("wykladowca.csv");
	                      BufferedReader br = new BufferedReader(new FileReader(csvFile));
	                      String line = "";
	                     
	                      int lineNumber = 0;
	
	                      while ((line = br.readLine()) != null) {
	                      String[] arr = line.split(";");
	
	                      iterator = db.select(Osoba.class, "idOsoba="+arr[1]);
	        		      osoba = (Osoba)iterator.next();
	        		      wykladowca = new Wykladowca();
	        		      wykladowca.idPracownik=Integer.parseInt(arr[0]);
	        		      wykladowca.osoba=osoba;
	        		      db.addRecord(wykladowca);
	        		      storage.commit();
	        		      
	                      lineNumber++;
                      	  } 
                      
	                      br.close();
	                      ar.clear();
	                      
                      }catch(IOException ex) {
                    	  ex.printStackTrace();
                      }
                	  
                	//tabela Kierunek
                	  try
                      {
	                      ArrayList<String> ar = new ArrayList<String>();
	                      File csvFile = new File("kierunek.csv");
	                      BufferedReader br = new BufferedReader(new FileReader(csvFile));
	                      String line = "";
	                     
	                      int lineNumber = 0;
	
	                      while ((line = br.readLine()) != null) {
	                      String[] arr = line.split(";");
	
	                      kierunek = new Kierunek();
	                      kierunek.idKierunek=Integer.parseInt(arr[0]);  
	                      kierunek.nazwaKierunku=arr[1];
	                      kierunek.idRelacja=storage.createLink();
	        		      db.addRecord(kierunek);
	        		      storage.commit();
	        		      lineNumber++;
                      	  } 
                      
	                      br.close();
	                      ar.clear();
	                      
                      }catch(IOException ex) {
                    	  ex.printStackTrace();
                      }
                	  
                	  //tabela Przedmiot
                	  try
                      {
	                      ArrayList<String> ar = new ArrayList<String>();
	                      File csvFile = new File("przedmiot.csv");
	                      BufferedReader br = new BufferedReader(new FileReader(csvFile));
	                      String line = "";
	                     
	                      int lineNumber = 0;
	
	                      while ((line = br.readLine()) != null) {
	                      String[] arr = line.split(";");
	
	                      przedmiot = new Przedmiot();
	                      przedmiot.idPrzedmiot=Integer.parseInt(arr[0]);  
	                      przedmiot.nazwa=arr[1];
	                      przedmiot.idRelacja=storage.createLink();
	        		      db.addRecord(przedmiot);
	        		      storage.commit();
	
	                      lineNumber++;
                      	  } 
                      
	                      br.close();
	                      ar.clear();
	                      
                      }catch(IOException ex) {
                    	  ex.printStackTrace();
                      }
                	  
                	  
                	  //tabela RelKierunekStudentPrzedmiot
                	  try
                      {
	                      ArrayList<String> ar = new ArrayList<String>();
	                      File csvFile = new File("relksp.csv");
	                      BufferedReader br = new BufferedReader(new FileReader(csvFile));
	                      String line = "";
	                     
	                      int lineNumber = 0;
	
	                      while ((line = br.readLine()) != null) {
	                      String[] arr = line.split(";");
	
	                      iterator = db.select(Student.class, "idIndeks=" + arr[2]);
	        		      student = (Student)iterator.next();
	        		      iterator = db.select(Kierunek.class, "idKierunek=" + arr[1]);
	        		      kierunek = (Kierunek)iterator.next();
	        		      iterator = db.select(Przedmiot.class, "idPrzedmiot=" + arr[3]);
	        		      przedmiot = (Przedmiot)iterator.next();
	                      relKSP = new RelKierunekStudentPrzedmiot();
	                      relKSP.idRelacja = Integer.parseInt(arr[0]);  
	                      relKSP.kierunek = kierunek;
	                      relKSP.indeks = student;
	                      relKSP.przedmiot = przedmiot;
	        		      db.addRecord(relKSP);
	        		      storage.commit();
	        		 //     System.out.println("RelacjaKSP=> idRelacja: " + arr[0] + ", Id Kierunek: " + arr[1] + ", Id Student: " + arr[2] + ", Id Przedmiot: " + arr[3]);
	                      lineNumber++;
                      	  } 
                      
	                      br.close();
	                      ar.clear();
	                      
                      }catch(IOException ex) {
                    	  ex.printStackTrace();
                      }
                	  
                	  
                	  
                	  
              		  break;
                  case 5:
                   
                	  iterator = db.getRecords(Osoba.class);
                      while (iterator.hasNext()) { 
                          osoba = (Osoba)iterator.next();
                          System.out.println("Osoba=> idOsoba: " + osoba.idOsoba + ", imiê: " + osoba.imie+ ", Nazwisko: " + osoba.nazwisko+ ", Ulica: " + osoba.ulica+ ", Kod pocztowy: " + osoba.kodPocztowy+ ", Miasto: " + osoba.miasto+ ", Data dodania: " + osoba.dataDodania);
                      }       	  
                      
                      iterator = db.getRecords(Student.class);
                      while (iterator.hasNext()) { 
                          student = (Student)iterator.next();
                          System.out.println("Student=> IdIndeks: " + student.idIndeks + ", Id Osoby: " + student.osoba+ ", Id Relacja: " + student.idRelacja);
                      }
                      
                      iterator = db.getRecords(Wykladowca.class);
                      while (iterator.hasNext()) { 
                          wykladowca = (Wykladowca)iterator.next();
                          System.out.println("Wykladowca=> idPracownik: " + wykladowca.idPracownik + ", Id Osoby: " + wykladowca.osoba);
                      }
                      
                      iterator = db.getRecords(Kierunek.class);
                      while (iterator.hasNext()) { 
                          kierunek = (Kierunek)iterator.next();
                          System.out.println("Kierunek=> idKierunek: " + kierunek.idKierunek + ", Nazwa: " + kierunek.nazwaKierunku);
                      }
                	  
                      iterator = db.getRecords(RelKierunekStudentPrzedmiot.class);
                      while (iterator.hasNext()) { 
                          relKSP = (RelKierunekStudentPrzedmiot)iterator.next();
                          System.out.println("RelacjaKSP=> idRelacja: " + relKSP.idRelacja + ", Id Kierunek: " + relKSP.kierunek + ", Id Student: " + relKSP.indeks + ", Id Przedmiot: " + relKSP.przedmiot);
                      }
                      
                      
                      iterator = db.getRecords(Przedmiot.class);
                      while (iterator.hasNext()) { 
                          przedmiot = (Przedmiot)iterator.next();
                          System.out.println("Przedmiot=> idPrzedmiot: " + przedmiot.idPrzedmiot + ", Nazwa: " + przedmiot.nazwa+ ", Id Relacja: " + przedmiot.idRelacja);
                      }
                      
                      break;
                  case 6:               	  
                	  	osoba = new Osoba();
                	  	osoba.dataDodania();
                	  
                	  	//Osoba student
        	  			//wykonanie metody z klasy Osoba
                	  	
        	  			osoba.idOsoba=Integer.parseInt(input("Osoba id(1000): ")); 
        	  			osoba.idStudent=storage.createLink();
        	  			osoba.imie=input("Osoba imie : ");
        	  			osoba.nazwisko = input("Osoba Nazwisko: ");
        	  			osoba.ulica = input("Osoba ulica: ");
        	  			osoba.kodPocztowy = input("Osoba kod pocztowy: ");
        	  			osoba.miasto = input("Osoba miasto: ");
        	  			osoba.dataDodania=osoba.dataDodania().toString();
        	  			db.addRecord(osoba);
        	  			storage.commit();
                	  
                	  
                    break;
                  case 7:
                	
                    break;
                  case 8:
                  int i;
                	  
                	  Przedmiot przedmiot1 = new Przedmiot();
                	  iterator = db.select(Przedmiot.class, "idPrzedmiot=57");
                     
                	 
                	  przedmiot1 = (Przedmiot)iterator.next();
                      result = new HashSet();
                      System.out.println("id: "+ iterator.hasNext());
                      while (iterator.hasNext()) {                         
                          przedmiot = (Przedmiot)iterator.next();
                          System.out.println("id: "+ iterator.hasNext());
                          System.out.println("id: "+ przedmiot.idRelacja.size());
                        
                         for (i = przedmiot.idRelacja.size(); --i >= 0;) { 
                        	  System.out.println("id: " + przedmiot.idRelacja.size());
                              relKSP = (RelKierunekStudentPrzedmiot)przedmiot.idRelacja.get(i);
                              result.add(relKSP.kierunek);
                          }
                      }
                      iterator = result.iterator();
                      while (iterator.hasNext()) { 
                          przedmiot = (Przedmiot)iterator.next();
                          System.out.println("Detial ID: " + przedmiot.nazwa);
                      }
                                      	  
                	  break;
                  case 9:
                    storage.close();
                    return;
                    
                }
               
            } catch (StorageError x) { 
                System.out.println("Error: " + x.getMessage());
                
            }
             
        }

	}

}
