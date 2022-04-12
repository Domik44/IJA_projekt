Příklady překladu:
- V kořenovém adresáři 
-> příkaz: mvn 
	    -> vyčistí předtím vytvořené soubory, přeloží aplikaci a vytvoří spustitelný jar file + vygeneruje javadoc
-> příkaz: mvn clean package javadoc:javadoc
	    -> udělá to samé co předchozí

Příklady spuštění:
- V kořenovém adresáři
-> příkaz: java -jar dest/ija-app.jar
-> příkaz: java -cp dest/ija-app.jar main.Main