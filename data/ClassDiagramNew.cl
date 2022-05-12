@startuml

Class Element {
  position 509 9
  attrib name String +
  oper Element  + name:String 
  oper getName String + 
  oper rename void + name:String 
}

Class ClassDiagram {
  position 919 218
  attrib classes List<UMLClassifier> +
  oper createClass UMLClass + name:String 
  oper classifierForName UMLClassifier + name:String 
}

Class UMLAttribute {
  position 150 222
  attrib type UMLClassifier +
  oper getType UMLClassifier + 
}

Class UMLClassifier {
  position 509 220
  oper UMLClassifier  + name:String 
  oper isUserDefined boolean + 
}

Class UMLClass {
  position 514 387
  attrib attributes List<UMLAttributes> +
  oper addAttribute boolean + attr:UMLAttribute 
  oper getAttrPosition int + attr:UMLAttribute 
}

Relation Association {
  position 0 52
  position 834 270
  position 833 242
  position 250 23
  lClass ClassDiagram
  rClass UMLClassifier
}

Relation Association {
  position 250 42
  position 450 264
  position 450 230
  position 508 231
  position 0 11
  lClass UMLAttribute
  rClass UMLClassifier
}

Relation Association {
  position 250 71
  position 436 294
  position 436 438
  position 0 51
  lClass UMLAttribute
  rClass UMLClass
}

Relation Generalization {
  position 16 119
  position 524 172
  position 278 170
  position 127 0
  lClass Element
  rClass UMLAttribute
}

Relation Generalization {
  position 121 119
  position 124 0
  lClass Element
  rClass UMLClassifier
}

Relation Generalization {
  position 221 119
  position 731 167
  position 1037 168
  position 119 0
  lClass Element
  rClass ClassDiagram
}

Relation Generalization {
  position 124 84
  position 120 0
  lClass UMLClassifier
  rClass UMLClass
}

@startSequence 

Participant m Main { 
  startPos 0 0 
  endPos 0 0 
  lineStart 0 0 
  lineEnd 0 0 
}

Participant cl UMLClass { 
  startPos 642 0 
  endPos 0 0 
  lineStart 0 0 
  lineEnd 0 0 
}

Participant al UMLAttribute { 
  startPos 930 0 
  endPos 0 0 
  lineStart 0 0 
  lineEnd 0 0 
}

Participant d ClassDiagram { 
  startPos 374 0 
  endPos 0 0 
  lineStart 0 0 
  lineEnd 0 0 
}

Message <<return>>true Return {
  position 0 427 
  position 0 427 
  namePos 0 0
  startObject cl 
  endObject m 
}

Message isAbstract() Synchronous {
  position 0 469 
  position 0 469 
  namePos 0 0
  startObject m 
  endObject al 
  inconsistent 
}

Message <<return>>false Return {
  position 0 506 
  position 0 506 
  namePos 0 0
  startObject al 
  endObject m 
  inconsistent 
}

Message <<create>>("cl") Create {
  position 0 122 
  position 0 122 
  namePos 0 0
  startObject d 
  endObject cl 
}

Message <<return>>cl Return {
  position 0 177 
  position 0 177 
  namePos 0 0
  startObject d 
  endObject m 
}

Message <<return>>cInt Return {
  position 0 283 
  position 0 283 
  namePos 0 0
  startObject d 
  endObject m 
}

Message createClass() Synchronous {
  position 0 96 
  position 0 96 
  namePos 0 0
  startObject m 
  endObject d 
}

Message classifierForName() Synchronous {
  position 0 241 
  position 0 241 
  namePos 0 0
  startObject m 
  endObject d 
}

Message <<create>>("a1",cInt) Create {
  position 0 323 
  position 0 323 
  namePos 0 0
  startObject m 
  endObject al 
}

Message addAttribute() Synchronous {
  position 0 379 
  position 0 379 
  namePos 0 0
  startObject m 
  endObject cl 
}

ActivationBox d { 
  startPos 0 66 
  endPos 0 180 
}

ActivationBox cl { 
  startPos 0 328 
  endPos 0 411 
}

ActivationBox al { 
  startPos 0 416 
  endPos 0 511 
}

ActivationBox m { 
  startPos 0 31 
  endPos 0 565 
}

ActivationBox d { 
  startPos 0 66 
  endPos 0 180 
}

@endSequence

@enduml