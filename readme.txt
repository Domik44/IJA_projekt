Příklady překladu:
- V kořenovém adresáři 
-> příkaz: mvn 
	    -> vyčistí předtím vytvořené soubory, přeloží aplikaci a vytvoří spustitelný jar file + vygeneruje javadoc
-> příkaz: mvn clean package javadoc:javadoc
	    -> udělá to samé co předchozí
-> příkaz: mvn clean
	    -> vyčistí dest, doc (krom složky others) adresáře a vymaže build adresář
-> příkaz: mvn package
	    -> přeloží .java soubory do build/classes a vytvoří .jar soubor v adresáři dest
	    -> zkopíruje závislosti do adresáře dest/dependencies a vstupní soubor do dest/data
-> příkaz: mvn javadoc:javadoc
	    -> vytvoří java dokumentaci do adresáře doc
 
 
Příklady spuštění:
- V adresáři dest:
	-> příkaz: java -jar ija-app.jar
	-> kliknutím myší na ija-app.jar soubor

Poznámky:
Adresář others v adresáři doc obsahuje plán
