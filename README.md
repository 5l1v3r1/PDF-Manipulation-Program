# PDF-Manipulation-Program
------------------------------------------------------
------------------------------------------------------
Este programa está escrito en Java usando la librería PDFReader. Su funcionalidad es copiar un conjunto de palabras situadas en diversos PDFs localizados en un directorio y escribirlas en el documento "final/soldaduras.txt". Este conjunto de palabras se encuentran entre el campo que contiene "SOLDADURAS" y el siguiente campo escrito en mayúsculas.

Ejemplo:

SOLDADURAS REALIZADAS

Ayer por la tarde.

Hace tres semanas.

No quedó bien la anterior.


RESISTENCIA DEL METAL
.
.
.

El programa copiaría solo:

Ayer por la tarde.
Hace tres semanas.
No quedó bien la anterior.

------------------------------------------------------
------------------------------------------------------
This program is written in Java with library PDFBox. Its functionality is copying in some PDFs located in a directory the words between two fields, in this case: the field "SOLDADURAS..." and some field in upper case. The copied words are written in the file -> "final/soldaduras.txt".
