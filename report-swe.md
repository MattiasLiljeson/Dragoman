Uppgift i interpretatorteknik
=============================
Detta är inlämningen till ovanstående uppgift. Inlämningen består av två delar,
en intrepretatorbackend samt ett användargränssnitt i form av ett CLI (Command
line Interface). Interpretatorn konverterar p1-filer till ett internt format
som sedan exekveras. Då jag inte haft tillgång till lexern som konverterar prg
till p1 har jag byggt interpretatorn med de p1-program jag haft till
förfogande.

Övergripande
------------
Interpretatorn är skriven i Java och CLIt är skrivet i Groovy vilket är ett
språk som körs på Javas JVM. För att bygga en komplett interpretator med
backend och frontend används byggverktyget Gradle. Gradle bygger, testar samt
paketerar hela paketet som en JAR.

Hur kör man projektet?
----------------------
Då långt ifrån alla har Groovy installerat har jag byggt en jar innehållandes
backend och frontend där det räcker med att ha java installerat för att köra.
Den ligger i huvudkatalogen.

Det finns en hög med switchar/optioner dessa listas med `-h` eller `--help`.
Det som framförallt är intressant är troligen loggnivåerna där olika mängd
information om interpreteringen skrivs ut beroende på vald loggnivå.

För att köra: `java -jar intercli.jar -i <program>.p1`. Där <program> är ett
program som lexern konverterat till mellanformatet p1. De p1-filer som följde
med uppgiften är medskickade i denna inlämning. De ligger i katalogen
`docs/Tester`.

Ex: `java -jar intercli.jar -i -l DEBUG docs/Tester/tst6.p1`

Hur bygger man och kör projektet?
---------------------------------
För att bygga projektet krävs det att Java samt Groovy är installerat och att
båda är av en någorlunda modern variant. Gradle dras ner automagiskt av ett
wrapperskript. För att bygga gör man sedan som följer:

 1. Stega in i katalogen src
 2. Kör kommandot `gradlew build jar intercli:uberjar`. I intercli/build/libs
     ligger nu `intercli.jar`.
 3. Exekvera jaren med:
     `java -jar intercli/build/libs/intercli.jar -i <program>.p1`

För att kika runt i koden används lämpligt verktyg, ex vi/vim. Vill man köra
enhetstester rekommenderas en modern version av eclipse. För att kunna öppna
projekten i eclipse kör man kommandot `gradlew eclipse`. Då genereras
projektfiler för eclipse upp av Gradle. Från eclipse väljer man sedan att
importera projekt och pekar då på projektkatalogen varefter backend och
frontend importeras och blir körbara inifrån eclipse.

Enhetstester / unit tests
-------------------------
Interpretatorn är utvecklad enligt principen *test driven development*. Det
finns därför en stor mängd testfall som testar features och färdiga program.
Dessa återfinns i `src/inter/src/test`. Dessa är modifierade med s.k.
checkpoints där värden sparas undan. Dessa värden jämförs sedan med sedan
referensvärden.  På detta sättet säkerställs interpretatorns nuvarande
funktionalitet när man vidareutvecklar interpretatorn med ytterligare
funktionalitet.

Interpretatorn
--------------
För att kunna följa exekveringen finns det en simpel logger samt ett verktyg
som används för att visualisera djupet på rekursionen i parsern. Denna loggar
till nivån DEBUG.

Interpretatorn är en enkel enpass (Eller tvåpass då lexningen och blockanalysen
redan är gjord) med recursive-descent-parsning. Den exekverar kod samtidigt som
den körs. Den är relativt liten och kompakt i sig men innehåller en hel del
debugutskrifter samt stöd för detta som gör att den blir lite knubbigare.
Denna parser parsear block som i sin tur kan kalla andra block samt sitt eget
block. Den är alltså blockbaserad, även om ett helt program kan hanteras som
ett block. Då saknas dock stödet för lokala variabler. Bredvid detta finns en
stack. För varje block som exekveras läggs det till en ny stackram med plats
för blockets variabler etc. På detta sättet är dynamisk samt statisk fader
strikt skilda.

Stöd finns för följande:
 - Operatorer med korrekt företräde (precedence) inkl parenteser.
 - Variabler, av heltalstyp.
 - Arrayer.
 - Funktionsanrop med parametrar.
 - Booleanska uttryck.
 - Rekursion.

Operatorföreträde sker via recursive descent och ej med en matris. Detta för
enkelhetens skull. Vill man ha stöd för att blanda booleanska uttryck och
operatorer är en matrislösning kanske en smidigare lösning.

Stödet för booleanska uttryck är begränsat. Det går inte att blanda operatorer
och booleanska uttryck då det inte sker ngn recursive descent inuti de
booleanska uttrycken. Detta är tekniskt möjligt men kan ge väldigt djup
rekursion vilket kan vara ett problem på klen hårdvara (inbyggda system modell
80-tal) och kommer garanterat ge låg prestanda. Att utöka med stöd för detta
kan dock ske med enkelhet.

Interpretatorn har stöd för variabler av typen heltal samt strängar med viss
begränsning. Den är förberedd för andra typer (flyttal ex) men detta är ej
slutfört.

Funktionsanrop har bara stöd för en parameter för tillfället. Interpretatorn är
förberedd för flera parametrar men då det saknats testprogram med fler
parametrar har det stödet inte slutförts.

För att kunna testa input/output kan interpretatorn köras i
icke-interaktivt läge. Då används antingen förbestämda värden alt.
defaultvärden om förbestämda värden ej finns definerade.

Utbyggbarhet
------------
Interpretatorn kör program där blockanalys redan är gjord. I övrigt är den
relativt generell och bör utan problem kunna ta de flesta språk av Algol-typ.
För att utöka med stöd för andra språk måste en lexer med blockanalys
konstrueras som skriver till ett vettigt mellanformat. Man måste sedan skriva
en importör av detta mellanformat likt den som finns idag för p1-filer.

Det finns idag ett par saker som inte är slutförda men som är förberedda,
däribland stöd för flera typer, stöd för sammansatta uttryck. Att slutföra
detta är dock inget större jobb.

Idag sker mkt debug och loggning manuellt i recursive descent-parsern. En
arkitekturell förbättring hade varit att modularisera
debug/loggning/interpretation och att jobba mot dessa via ett gemensamt
interface. Det hade inte varit någon svårighet att samtidigt bygga stöd för
kompilering och då implementera detta som en modul. Då hade användaren kunnat
välja läge, interpretering eller kompilering. Detta hade också gjort parsern
mindre och bättre specialiserad då mycket funktionalitet flyttats till moduler.

Mycket nöje!
