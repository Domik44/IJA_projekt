@startuml

Class Motorka {
  position 100 300
  attrib omezeni bool +
  oper postavitNaZadni str + hvezdicky:int
}

Class Auto {
  position 500 300
  attrib tazne bool +
  oper vypisInfo str -
  oper vypisBarva str + barva:str
}

Interface Vozidlo {
  position 300 100
  attrib vykon int +
  attrib hmotnost int -
  attrib znacka str ~
  attrib barva str #
  oper vypisInfo str -
  oper vypisBarva str + barva:str
}

Relation Generalization {
  lClass Vozidlo
  rClass Auto
  position 170 93
  position 470 250
  position 625 250
  position 125 0
}

Relation Generalization {
  lClass Vozidlo
  rClass Motorka
  position 70 93
  position 370 250
  position 225 250
  position 125 0
}

Relation Association {
  position 250 50
  position 800 150
  position 800 350
  position 250 50
  lClass Auto
  rClass Vozidlo
  lCard 1
  rCard *
  label je 805 280
}

Relation Aggregation {
  position 250 50
  position 0 50
  lClass Motorka
  rClass Auto
}


@enduml