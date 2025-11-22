# Bike Mobile App

<h3>Egy egyszerű Kotlin projekt, ami egy GitHub-ról betöltött JSON fájl alapján biciklik listáját jeleníti meg. 
Az adatok helyileg is eltárolódnak a Room adatbázisban, minden biciklihez külön részletező képernyő tartozik.
<br>Amit tartalmaz a projekt: Retrofit, RecyclerView, Room database, Activity-n belüli navigáció</h3>

---

## Hogyan működik

### MainActivity
- Lekéri a bicikliket a GitHub JSON fájlból Retrofit segítségével  
- Menti az adatokat egy helyi Room adatbázisba
- Megjeleníti a listát Recycleview használatával
- Navigációt biztosít a részletező képernyőre

### DetailActivity
- Megkapja a bicikli azonosítóját Intenten keresztül  
- Lekéri az adatbázisból  
- Megjeleníti a bicikli részletes adatait  

### Network.kt
- Retrofit kliens létrehozása logging interceptorral  
- API interfész (`ApiService`) definiálása  

### RoomDatabase.kt
- `Bike` entitás definiálása  
- `BikeDao` a lekérdezésekhez  
- Room adatbázis példány létrehozása  

### TextAdapter.kt
- Adapter a RecyclerView számára, ami feltölti a listát  
- Kezeli az elemekre kattintást és továbbítja MainActivity-nek  

---

## Layout fájlok

### `activity_main.xml`
- RecyclerView a bicikli lista megjelenítéséhez  
- Gomb, ami a részletező képernyőt nyitja meg tesztadatokkal  

### `detail_screen.xml`
- TextView-k a brand, model, price, category, description és availability megjelenítéséhez  

### `raw_text.xml`
- Egy listaelem layoutja: brand, model, price, availability  

---

## Naming konvenciók (Kotlin + Android)

### Kotlin konvenciók

| Típus | Stílus | Példa |
|-------|--------|-------|
| Osztályok / objektumok | PascalCase | `MainActivity`, `Bike`, `Network` |
| Függvények / változók | camelCase | `loadBicycles()`, `bikeDao()` |
| Konstansok | UPPER_SNAKE_CASE | `EXTRA_ID` |
| Fájlok | általában egyezik a fő osztállyal | `DetailActivity.kt` → `DetailActivity` |

### Android konvenciók

| Típus | Stílus | Példa |
|-------|--------|-------|
| Activityk | PascalCase + `Activity` | `MainActivity.kt` |
| Adapterek | PascalCase + `Adapter` | `TextAdapter.kt` |
| Database / DAO | PascalCase + `Database` / `Dao` | `AppDatabase.kt`, `BikeDao.kt` |
| Layout fájlok | snake_case | `activity_main.xml`, `detail_screen.xml` |
| RecyclerView item layoutok | raw_*, item_* | `raw_text.xml` |
| ViewBinding osztályok | layout névből PascalCase | `raw_text.xml` → `RawTextBinding` |

---

## A projekt futtatása

1. Klónozd a repót  
2. Nyisd meg Android Studio-ban  
3. Szinkronizáld a Gradle-t
4. Futtasd emulatoron vagy fizikai eszközön
