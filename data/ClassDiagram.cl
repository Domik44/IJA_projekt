@startuml

Class Auto {
  attrib vykon int +
  attrib hmotnost int -
  attrib znacka str ~
  attrib barva str #
  attrib tazne bool +
  oper vypisInfo str -
  oper vypisBarva str + barva:str
}

Interface Vozidlo {

  oper vypisInfo str -
  oper vypisBarva str + barva:str
}

Relation Generalization {
  lClass Vozidlo
  rClass Auto
}


@enduml