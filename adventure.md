# A feladat összefoglaló leírása

A feladat egy kalandjátékot megvalósító program elkészítése. A játékban egy hőst irányítunk, aki szobáról szobára halad és kincseket gyűjt; a szobákban a kincsek néha őrizetlenül hevernek, de előfordulhat, hogy egy szörnnyel kell megküzdeni értük. Az egyszerűség kedvéért a szobák lineárisan következnek, de a hősnek van minimális lehetősége választani a továbbhaladás irányában.

Ügyeljünk arra, hogy a definiálandó osztályoknak az `adventure`, az `adventure.chest`, illetve az `adventure.room` csomagokba kell kerülniük!  A feladathoz tartozó segédletet [innen](https://bead.inf.elte.hu/files/java/adventure.zip) tölthetjük le. Itt találhatjuk a feladat leírásában hivatkozott összes állományt.

# Tesztelés

Az egyes részfeladatokhoz tartoznak külön tesztesetek, amelyeket a feladatok végén jelöltünk meg.  Ezek önállóan is fordítható és futtatható `.java` állományok a mellékelt `.jar` segítségével.  Például Windows alatt az első feladathoz tartozó tesztesetek így fordíthatóak és futtathatóak:

~~~~
> javac -cp .;tests-adventure.jar tests/TreasureTest.java
> java -cp .;tests-adventure.jar tests/TreasureTest
~~~~

Ugyanezeket a teszteseteket használja a feladathoz tartozó, tesztelést és pontbecslést végző `Test` osztály is.  Ezt Windows alatt így lehet futtatni:

~~~~
> java -cp .;tests-adventure.jar Test
~~~~

Linux alatt mindent ugyanúgy lehet futtatni, csak a `-cp` paraméterében a pontosvesszőt kettőspontra kell cserélni.

# A feladat részletes ismertetése

## `adventure.chest.Treasure` *(4 pont)*

Ez az osztály reprezentál egy darab kincset. Egy kincsnek két nem nyilvános attribútuma van, a neve és az aranyban (egész számként) kifejezett értéke. Az osztálynak meg kell valósítania a `Comparable<Treasure>` interfészt, hogy a kincsek rendezhetőek legyenek!

Az osztály az alábbi nyilvános metódusokkal rendelkezik:

 - Egy konstruktor, amely a kapott paraméterekből (név, érték) előállít egy példányt.

 - Egy `half()` nevű metódus, amely egész osztással felezi a kincs értékét.

 - A felüldefiniált `toString()` metódus, amely az alábbi szöveget adja vissza:

        [itemName] which is worth [itemValue] golds.

    ahol `[itemName]` a kincs neve, `[itemValue]` az értéke.

    Például: `"Ring which is worth 10 golds."`

 - A felüldefiniált `compareTo()` metódus, amely elsődlegesen a kincs értéke, másodlagosan a kincs neve szerint rendezzen. Az a kincs számít kisebbnek, amelyik kisebb értékű; azonos értékek esetén a név ábécé sorrendje dönt. Például egy helyes sorrend (név, érték):

        Apple, 8 < Apparel, 10 < Apple, 10 < Ring, 40 < Scarf, 40


 - A felüldefiniált `equals()` metódus, amely ellenőrzi, hogy két kincsnek ugyanaz-e a neve és az értéke. Ügyeljünk a más típusú, illetve `null` objektumok megfelelő kezelésére!

 - A felüldefiniált `hashCode()` metódus, amely a kincs nevének `hashCode()` értékéből és a kincs értékét reprezentáló számból állít elő a *kizáró vagy* művelet (XOR, Java-ban a `^` operátor) segítségével.

    Például egy 10 értékű, `Ring` nevű kincsre a `hashCode()` értéke `2547290` lesz.

*Tesztesetek:* `tests/TreasureTest.java`

## `adventure.chest.Achievement` *(1 pont)*

A hős később minden szoba kinyitásakor szerez egy `Achievement` objektumot: ez `String` formájában tartalmaz egy üzenetet a felhasználónak az eseményekről, valamint az elnyert kincsek `List<Treasure>` típusú listáját.

A nyilvános konstruktor a kapott paraméterekből (`String`, kincsek listája) feltölti az adattagokat.

Továbbá adjunk meg a `getInfo()` és a `getTreasures()` nyilvános metódusokat, amelyekkel le lehet lekérdezni a fentebb említett adattagokat!

*Tesztesetek:* `tests/AchievementTest.java`

## `adventure.room.Openable`

Ez az interfész előírja a szobák nyithatóságát. Az interfész egyetlen, `openDoor()` nevű, paraméter nélküli metódust tartalmaz, amely egy `Achievement` típusú értékkel tér vissza.

## `adventure.room.Room` *(2 pont)*

A `Room` osztály egy absztrakt osztály, amely egy nem nyilvános adattagban tárolja a kincsek listáját. Az osztály implementálja az `Openable` interfészt!

A kincsek listája legyen lekérdezhető a `getTreasures()` nevű metódussal (ennek megfelelően a visszatérési érték típusa: `List<Treasure>` legyen)!

Az osztálynak egyetlen konstruktora van, amely paraméterül megkapja a kincsek listáját. Az osztály nem tartalmazhat más konstruktort!

*Tesztesetek:* `tests/RoomTest.java`

## `adventure.room.TreasureRoom` *(3 pont)*

Ez az osztály a `Room` osztály leszármazottja. Egy `TreasureRoom` mindig vagy üres (a tárolt kincsek listája nullaelemű), vagy pontosan egy kincset tartalmaz (a tárolt kincsek listája egyelemű).

 - Egyetlen konstruktora megkapja a kincsek listáját. A lista méretét itt nem kell ellenőrizni, vagyis feltételezhetjük, hogy eleve üres vagy egyelemű listát fog kapni paraméterként.

 - A felüldefiniált `openDoor()` metódus leírja, mi történik, ha a hős kinyitja a szobát. (Emlékeztető: a metódus egy `Achievement` értékkel tér vissza.)

    - Ha a szoba üres, akkor az `Achievement` értékben tárolt üzenet legyen `"This room is empty."`, a visszaadott kincsek listája pedig egy új üres lista.

    - Ha a szoba nem üres, akkor a hős megkapja a kincset tartalmazó lista **másolatát** (ne referencia szerint adjuk át a listát, hanem új listaként!), az `Achievement` üzenete pedig `"You've got [itemName] which is worth [itemValue] golds."`; ehhez használjuk a kincs `toString()` metódusát. A szoba ezután már üresnek számít, vagyis nulla darab kincset tartalmaz!

*Tesztesetek:* `tests/TreasureRoomTest.java`

## `adventure.room.MonsterRoom` *(5 pont)*

Ez az osztály szintén a `Room` osztály leszármazottja, viszont itt bármennyi kincs lehet, és a szoba nem őrizetlen. Attribútumként szerepel a kincset őrző szörny neve (`String`), valamint egész számként kifejezett erőssége. Ezen felül véletlen szám generáláshoz tartalmaz egy osztályszintű, `java.util.Random` típusú adattagot.  Ennek a kezdeti értéke legyen egy olyan `Random` objektum, amelynek a konstruktorát 7-es értékkel hívtuk meg.

 - Az osztály tartalmaz egy nyilvános osztályszintű, `resetRandom()` metódust, amellyel újraindíthatjuk a véletlenszám-generátort. Az adattag új értéke egy `Random(x)` objektum lesz, ahol `x` a metódus paramétereként kapott egész szám.

 - Az egyedüli nyilvános konstruktor megkapja paraméterül a kincsek listáját, és a szörny nevét (ebben a sorrendben). A szörny erősségét 12 oldalú kockával dobjuk ki, vagyis egy `0` és `11` közötti véletlen szám.

 - A felüldefiniált `openDoor()` metódus hasonlóan működik, mint a `TreasureRoom` esetében. Ha a szoba üres (azaz üres a kincslista), ugyanazzal térünk vissza, mint a `TreasureRoom` esetében. Ha viszont nem üres, először meg kell küzdeni a szörnnyel: a hős dob egy 12 oldalú kockával (random szám `0` és `11` között), és ha az ő száma legalább akkora, mint a szörnyé, akkor legyőzte a szörnyet, ellenkező esetben vesztett.

     - Ha a hős nyert, a hős megkapja a kincsek listájának **másolatát** (ne referencia szerint adjuk át a listát, hanem új listaként!), és a szoba kiürül. Az `Achievement` üzenete legyen a következő:

            You defeated [monsterName]. You got:
            [itemName1] which is worth [itemValue1] golds.
            [itemName2] which is worth [itemValue2] golds.
            ...
            [itemNameN] which is worth [itemValueN] golds.

          (Az utolsó sor végén is szerepel egy sortörés.)

        Például:

            You defeated Butterfly. You got:
            Dust which is worth 2 golds.
            Pot which is worth 2 golds.
            Apple which is worth 3 golds.

     - Ha a hős vesztett, a szoba állapota változatlan marad (a szörny továbbra is őrzi a kincseket). A szörny a dühét a kincseken vezeti le, ezért az összes kincs értéke feleződik (lásd a `Treasure` osztály `half()` metódusát), az `Achievement` üzenete pedig: `"[monsterName] has won. Treasure values halved."`. A hősnek ilyenkor nem jár kincs (új üres lista)!

        Például:

            Butterfly has won. Treasure values halved.

*Tesztesetek:* `tests/MonsterRoomTest.java`

## `adventure.InvalidMapException` *(1 pont)*

Fájlból akarjuk beolvasni a térképet, ezért kell egy saját hibaosztály a térkép beolvasási hibákhoz. Az osztály az `IllegalArgumentException` osztályból származik, egyetlen adattagja a hibás sor, ahol a hiba lépett fel.

  - A nyilvános konstruktor megkapja és eltárolja a hibát okozó (`String`) sort.

  - A felüldefiniált `getMessage()` metódus visszaadja az alábbi üzenetet: `"[line] is not a valid map line."`, ahol `[line]` a hibás sor.

      Például:

        sdmvls xeqofnwe is not a valid map line.

*Tesztesetek:* `tests/InvalidMapExceptionTest.java`

## `adventure.AdventureMap`, 1. rész *(5 pont)*

Ez az osztály reprezentálja a térképet: tartalmazza a szobák listáját, a hős helyzetét (egy egész szám: hányas szoba ajtaja előtt tartózkodik), és az eddig gyűjtött kincseinek listáját.

 - A szobák listája legyen lekérdezhető a nyilvános, `getRooms()` nevű metódussal (visszatérési érték típusa: `List<Room>`)!

 - A paraméter nélküli, nyilvános konstruktor inicializálja a változókat! A térkép kezdetben egyetlen, üres szobát tartalmaz (legyen ez egy üres, kincs nélküli `TreasureRoom`), a hős pozíciója `0` (a nulladik szoba ajtajában, vagyis az üres szoba előtt áll), és eddig nincs kincse.

 - A nyilvános `loadMap()` metódus egy osztályszintű metódus, amely fájlból beolvassa a szobákat (a kincsekkel és a szörnyekkel), és visszatér egy `AdventureMap` példánnyal. Paramétere a fájl neve. A fájl egyetlen térképet tartalmaz, amelynek minden egyes sora egy szoba, és a szobák adott sorrendben követik egymást.  Amennyiben a megadott állomány nem létezik, úgy ezt jelezzük egy, a szabványos kimenetre írt üzenettel és adjunk vissza `null` értéket!

    Minden szoba az alábbi módon épül fel: először az őrző szörny neve szerepel, majd pontosvessző, utána pedig a kincsek vesszőkkel elválasztva. Egy kincs annak nevéből, majd egy szóköz után annak értékéből áll. Vagyis minden sor formátuma:

        [guardingMonsterName];[treasureName1] [value1],[treasureName2] [value2],[treasureName3] [value3] ...

     `TreasureRoom` esetén nincs őrző szörny (de van pontosvessző!), és csak egyetlen kincs szerepel. Például egy `TreasureRoom` sora így néz ki:

        ;Necklace 50

     Míg egy `MonsterRoom` sora:

        Hellhound;Medallion 8,Helmet 10,Ring 16

     Amennyiben beolvasás során valamely sor hibás, illetve nem értelmezhető, a metódus dobjon egy `InvalidMapException`-t, mely tartalmazza az egész hibás sort. Hibás sornak minősülnek az alábbiak:

       - üres sor,

       - nincs pontosvessző,

       - nincs kincs (felháborító!),

       - egy kincs értelmezhetetlen (nincs értéke, több értéke van),

       - `TreasureRoom` esetén több, mint egy kincs van.

     Ha a beolvasás sikeres volt, a metódus térjen vissza a feltöltött `AdventureMap` példánnyal!

*Tesztesetek:* `tests/AdventureMapTest1.java`

## `adventure.AdventureMap`, 2. rész *(3 pont)*

Az osztály további nyilvános metódusokat tartalmaz:

 - A `move()` metódussal léphetünk a hőssel: paraméterül megkapja a kinyitandó szoba sorszámát (amely nullától kezdődhet), és egy `String`-gel tér vissza. Ha a hős közel áll a szobához (legfeljebb 1 távolságra van), akkor odamegy, és rögtön kinyitja az adott szobát. Például, ha a hős az `N`-edik szobában áll, akkor kinyithatja az `(N - 1)`-edik, `N`-edik és az `(N + 1)`-edik szobákat (feltéve, hogy léteznek). Ilyenkor a szoba kinyitásának eredményétől függetlenül a hős már az új szoba ajtaja előtt áll.  Ha viszont nem tud az adott szobához menni, akkor csak annyit kell üzennünk, hogy `"You cannot do that."`.

     Például, ha egy hős az 1-es szoba előtt áll, megteheti, hogy kinyitja a 2-es szobát. Ha ott egy szörny lapul, akitől kikap, akkor is a hős már a 2-es szoba előtt áll. Ha a hős nem léphet egy szobába (például az 1-es szoba ajtajából rögtön a 3-asat akarja kinyitni), akkor marad ott, ahol volt!

     A szoba sikeres kinyitásának eredménye egy `Achievement`; ha ez tartalmaz kincseket, azokat a hős begyűjti.

     A metódus visszatérési értéke az alábbi szöveg:

        You tried to enter Room [roomNumber]. [Message]

     ahol `[roomNumber]` a kinyitni próbált szoba sorszáma, `[Message]` pedig:

      - az `Achievement` üzenete, ha a hős kinyithatta a szobát,

      - az alábbi szöveg, ha a hős nem nyithatta ki a szobát (messze van, vagy nem létezik a szoba): `"You cannot do that."`

     Az alábbi példában tegyük fel, hogy a hős a 4. szobában van! `move(2)` visszatérési értéke:

            You tried to enter Room 2. You cannot do that.

     Ezután `move(3)`:

            You tried to enter Room 3. Demon has won. Treasure values halved.

     a `move(2)` most már lehetséges:

            You tried to enter Room 2. This room is empty.

     Majd végül a `move(1)`:

            You tried to enter Room 1. You defeated Troll. You got:
            Apple which is worth 4 golds.
            Boots which is worth 12 golds.

 - A felüldefiniált `toString()` metódus, amely kiírja az alábbi szöveget: `"You are at Room [roomNumber]."`, ahol `[roomNumber]` annak a szobának a sorszáma, ahol a hős éppen tartózkodik. Például a játék legelején:

        You are at Room 0.

 - Egy `getHoard()` metódus, amely **sorba rendezve** visszaadja az eddig a hős által összegyűjtött kincsek listáját. A sorba rendezéshez kihasználhatjuk, hogy a `Treasure` osztály implementálja a `Comparable<Treasure>` interfészt!

*Tesztesetek:* `tests/AdventureMapTest2.java`

Jó munkát!

# Pontozás

  - *1*: 0  &mdash; 8
  - *2*: 9  &mdash; 11
  - *3*: 12 &mdash; 15
  - *4*: 16 &mdash; 19
  - *5*: 20 &mdash; 24
