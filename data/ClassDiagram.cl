@startuml

Class Motorka {
  position 15 10
  attrib omezeni bool +
  oper postavitNaZadni str + hvezdicky:int
}

Class Auto {
  position 5 10
  attrib tazne bool +
  oper vypisInfo str -
  oper vypisBarva str + barva:str
}

Interface Vozidlo {
  position 10 10
  attrib vykon int +
  attrib hmotnost int -
  attrib znacka str ~
  attrib barva str #
  oper vypisInfo str -
  oper vypisBarva str + barva:str
}

Relation Generalization {
  pClass Vozidlo
  cClass Auto
  cClass Motorka
}

Relation Association {
  position 6 14
  position 6 6
  lClass Auto
  rClass Vozidlo
  lCard 0
  rCard *
  label je
}

Relation Aggregation {
  position 3 3
  position 4 4
  position 5 5
  lClass Motorka
  rClass Vozidlo
}


@enduml