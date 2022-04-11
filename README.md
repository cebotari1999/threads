# threads
README

In functia main se citeste dimensiunea fragmentelor care vor fi citite,
iar intr-o lista se stocheaza numele fisierelor. Aceste date si numele
fisierului in care se vor scrie rezulatatele se trimit la thread-ul
principal, care va coordona restul threadur-ilor.

In thread-ul principal se creeaza task-uri de tip Map, pentru toate
fisierele. Task-urile de tip Map primesc fisierul din care se vor citi
datele, offset-ul de la care se va face citirea si numarul de octeti pe
care prelucrati. La executia unui task Map prima data se verifica daca
fragmentul incepe din mijlocul unui cuvant, acest cuvant nu este luat
in considerare. Daca fragmentul anterior are la final o litera sau cifra
si fragmentul curent incepe cu o litera sau cifra, inseamna ca fragmentul
incepe in mijlocul unui cuvant, se parcurg toate caracterele pana se
ajunge la un delimitator.


Se parcurge caracter cu caracter fragmentul. Atat timp cat se citesc
litere sau cifre, se incemeteaza dimensiunea cuvantului. Cand se ajunge
la un delimitator se adauga cuvantul si numarul de aparitii in dictionar.
Daca in timp ce se parcurge un cuvant sa ajuns la finalul fragmentului
si nu sa gasit nici un separator, se citeste restul liniei din pozitia
curenta, ce parcurge cuvantul pana la un separator, in acel moment se
adauga cuvantul in dictionar si numarul lui de aparitii.

 De fiecare data cand se identifica un cuvant, se verifica
daca este cel mai lung cuvant, daca da, se salveaza.

 Rezultatele sunt receptionate de thread-ul coordonator,
acesta separa dictionarele pentru fiecare fisier in parte si le
creaza liste de cele mai lungi cuvinte. Se creeaza task-uri de tip
reduce pentru fiecare fisier, se creeaza un dictionar unic pentru
fiecare fisier aparte, apoi se calculeaza rangul acestuia.

 Thread-ul principal primeste rezulatele si le scrie in fisierul
de output.
