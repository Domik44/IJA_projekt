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

Participant  Main {
  startPos 49 0
  endPos 0 0
  lineStart 0 0
  lineEnd 0 0
}

Participant d ClassDiagram {
  startPos 287 0
  endPos 0 0
  lineStart 0 0
  lineEnd 0 0
}

Participant cl UMLClass {
  startPos 503 0
  endPos 0 0
  lineStart 0 0
  lineEnd 0 0
}

Participant al UMLAttribute {
  startPos 743 0
  endPos 0 0
  lineStart 0 0
  lineEnd 0 0
}

ActivationBox  {
  startPos 0 35
  endPos 0 458
}

ActivationBox d {
  startPos 0 84
  endPos 0 180
}

ActivationBox cl {
  startPos 0 269
  endPos 0 411
}

ActivationBox al {
  startPos 0 392
  endPos 0 511
}

@endSequence

@enduml