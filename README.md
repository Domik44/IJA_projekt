# IJA_projekt
V rámci tohoto projektu byla vytvořena jednoduchá aplikace v jazyce Java, ve které uživatel může vytvářet diagramy tříd a sekvenční diagramy.

## Dates:  
-	task_1 -> 13.3.2022  
-	task_2 -> 15.3.2022  
-	task_3 -> 12.4.2022  
-	delivery time -> 8.5.2022

## Folder architecture:  

- **src/**             (adres.) zdrojové soubory (hierarchie balíků)  
- **data/**            (adres.) připravené datové soubory  
- **build/**           (adres.) přeložené class soubory  
- **doc/**             (adres.) programová dokumentace  
- **dest/**            (adres.) umístění výsledného jar archivu (+ dalších potřebných souborů) po kompilaci aplikace,   
                           tento adresář bude představovat adresář spustitelné aplikace  
- **lib/**             (adres.) externí soubory a knihovny (balíky třetích stran, obrázky apod.), které vaše aplikace využívá  
- **readme.txt**       (soubor) základní popis projektu (název, členové týmu, ...)  
                           informace ke způsobu překladu a spuštění aplikace  
- **rozdeleni.txt**    (soubor) soubor obsahuje rozdělení bodů mezi členy týmu;   
                           pokud tento soubor neexistuje, předpokládá se rovnoměrné rozdělení  
- **requirements.pdf** (soubor) aktualizovaný seznam požadavků

## Příklady překladu:
- v kořenovém adresáři:
- mvn 
	 - vyčistí předtím vytvořené soubory, přeloží aplikaci a vytvoří spustitelný jar file + vygeneruje javadoc
- mvn clean package javadoc:javadoc
	 - udělá to samé co předchozí
- mvn clean
	 - vyčistí dest, doc (krom složky others) adresáře a vymaže build adresář
- mvn package
	 - přeloží .java soubory do build/classes a vytvoří .jar soubor v adresáři dest
	 - zkopíruje závislosti do adresáře dest/dependencies a vstupní soubor do dest/data
- mvn javadoc:javadoc
	 - vytvoří java dokumentaci do adresáře doc
 
## Příklady spuštění:
- V adresáři dest:
	- java -jar ija-app.jar
	- kliknutím myší na ija-app.jar soubor


