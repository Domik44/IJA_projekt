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
  position 125 165
  position 625 520
  position 225 520
  position 125 80
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

Participant OperaceVozidlo:1 OperaceVozidlo {
  startPos 700 0
  endPos 0 0
  lineStart 15 30
  lineEnd 15 40
}

Participant Auto:1 Auto {
  startPos 100 0
  endPos 0 0
  lineStart 35 30
  lineEnd 35 40
}

Participant Motorka:1 Motorka {
  startPos 400 0
  endPos 0 0
  lineStart 55 30
  lineEnd 45 40
}

Message postavitNaZadni Synchronous {
  position 0 100
  position 0 0
  namePos 20 33
  startObject Auto:1
  endObject Motorka:1
}

Message vypisInfo Asynchronous {
  position 0 150
  position 0 0
  namePos 20 36
  startObject Motorka:1
  endObject Auto:1
}

Message <<return>> Return {
  position 0 200
  position 0 0
  namePos 20 36
  startObject Motorka:1
  endObject Auto:1
}

Message <<create>> Create {
  position 0 250
  position 0 0
  namePos 20 36
  startObject OperaceVozidlo:1
  endObject Auto:1
}

Message <<delete>> Delete {
  position 0 300
  position 0 0
  namePos 20 36
  startObject OperaceVozidlo:1
  endObject Auto:1
}


ActivationBox Auto:1 {
  startPos 0 50
  endPos 0 400
}

@endSequence

@enduml