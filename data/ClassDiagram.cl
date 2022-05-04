@startuml

Class Motorka {
  position 100 300
  attrib omezeni bool +
  oper postavitNaZadni str + hvezdicky:int
}

Class Auto {
  position 500 300
  attrib tazne bool +
  attrib vykon int +
  attrib hmotnost int -
  attrib znacka str ~
  attrib barva str #
  oper vypisInfo str -
  oper vypisKM int + najezd:int
}

Interface OperaceVozidlo {
  position 300 100
  oper vypisInfo str -
  oper vypisBarva str + barva:str
}

Relation Generalization {
  lClass OperaceVozidlo
  rClass Auto
  position 172 93
  position 470 250
  position 625 250
  position 125 0
}

Relation Generalization {
  lClass OperaceVozidlo
  rClass Motorka
  position 70 93
  position 370 250
  position 225 250
  position 125 0
}

Relation Generalization {
  lClass Auto
  rClass Motorka
  position 125 171
  position 625 520
  position 225 520
  position 125 86
}

Relation Association {
  position 250 50
  position 800 150
  position 800 350
  position 250 50
  lClass Auto
  rClass OperaceVozidlo
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

@startSequence

Participant Auto:1 Auto {
  startPos 100 20
  endPos 10 40
  lineStart 15 30
  lineEnd 15 40
}

Participant Motorka:1 Motorka {
  startPos 400 20
  endPos 30 40
  lineStart 35 30
  lineEnd 35 40
}

Message Ejoooooo Synchronous {
  position 10 35
  position 30 35
  namePos 20 33
  startObject Auto:1
  endObject Motorka:1
}

@endSequence

@enduml