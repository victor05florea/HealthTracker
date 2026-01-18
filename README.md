# 🏃 HealthTracker - Aplicație de Monitorizare a Sănătății

## 📱 Despre Aplicație

**HealthTracker** este o aplicație mobilă completă care te ajută să îți monitorizezi activitatea fizică, somnul și sănătatea generală. Aplicația combină tracking-ul automat cu un asistent AI personal care îți oferă sfaturi și recomandări personalizate pentru un stil de viață mai sănătos.

### De ce este utilă?

În viața de zi cu zi, mulți oameni nu realizează cât de importantă este monitorizarea constantă a sănătății. **HealthTracker** vine să rezolve această problemă prin:

- **Conștientizare**: Vezi exact câți pași faci zilnic, cât dormi și ce antrenamente ai făcut
- **Motivație**: Vizualizarea progresului te motivează să continui și să îți depășești propriile limite
- **Planificare**: Poți planifica antrenamentele și să urmărești progresul pe termen lung
- **Sfaturi personalizate**: ChatBot-ul AI îți oferă recomandări bazate pe obiectivele tale
- **Istoric complet**: Toate datele sunt salvate și poți vedea evoluția ta în timp

## 🎯 Funcționalități Principale

### 1. 📊 Tracking Pași Zilnici
- Înregistrează numărul de pași făcuți în fiecare zi
- Vizualizează progresul zilnic
- Setează-ți obiective și urmărește realizarea lor
- Datele sunt salvate automat și pot fi consultate oricând

**Utilitate**: Mulți oameni nu realizează cât de puțin se mișcă în timpul zilei. Prin monitorizarea pașilor, poți identifica zilele când ai fost prea sedentar și poți ajusta rutina pentru a fi mai activ.

### 2. 😴 Monitorizare Somn
- Înregistrează orele de culcare și trezire
- Calculează automat durata somnului
- Păstrează istoricul complet al somnului
- Identifică pattern-uri în rutina ta de somn

**Utilitate**: Somnul de calitate este esențial pentru sănătate. Prin tracking-ul somnului, poți identifica dacă dormi suficient și dacă ai o rutină consistentă. Acest lucru te ajută să previi oboseala cronică și să îmbunătățești productivitatea.

### 3. 💪 Gestionare Antrenamente
- Creează antrenamente personalizate
- Adaugă exerciții specifice pentru fiecare antrenament
- Urmărește tipul de antrenament (ex: Piept, Spate, Cardio)
- Salvează data și ora fiecărui antrenament
- Vizualizează detalii complete despre fiecare sesiune

**Utilitate**: Pentru cei care fac sport regulat, este important să știi ce ai făcut și când. Aplicația te ajută să:
- Eviti repetarea acelorași antrenamente prea des
- Urmărești progresul în exerciții specifice
- Planifici antrenamentele viitoare bazate pe istoric
- Rămâi motivat văzând cât de consecvent ești

### 4. 🤖 ChatBot AI Personal
- Asistent virtual bazat pe ChatGPT
- Răspunde la întrebări despre fitness și sănătate
- Oferă recomandări personalizate pentru antrenamente
- Sfaturi pentru nutriție și stil de viață
- Interfață de chat intuitivă și rapidă

**Utilitate**: Nu toată lumea are acces la un antrenor personal sau la informații de calitate despre fitness. ChatBot-ul AI acoperă acest gap, oferind:
- Răspunsuri instant la întrebări despre antrenamente
- Planuri de antrenament adaptate nevoilor tale
- Explicații despre exerciții și tehnici
- Motivație și sfaturi pentru a rămâne consecvent

## 🏗️ Arhitectură Tehnică

Aplicația este construită pe o arhitectură **client-server** modernă:

### Backend (Server)
- **Tehnologie**: Java 17 + Spring Boot 3.2.3
- **Bază de date**: PostgreSQL
- **API**: RESTful API cu endpoint-uri pentru toate funcționalitățile
- **Securitate**: Cheia API OpenAI este păstrată sigură pe server, nu în aplicația mobilă

**Structura Backend-ului:**
```
backend/
├── Controllers/          # Puncte de acces API
│   ├── AiController      # Integrare ChatGPT
│   ├── HealthController  # Health check
│   ├── SleepController   # Gestionare somn
│   ├── StepsController   # Gestionare pași
│   └── WorkoutController # Gestionare antrenamente
├── Entities/             # Modele de date
│   ├── SleepSession      # Sesiuni de somn
│   ├── DailySteps        # Pași zilnici
│   ├── Workout           # Antrenamente
│   └── Exercise          # Exerciții
└── Repositories/         # Acces la baza de date
```

### Frontend (Aplicație Mobilă)
- **Tehnologie**: React Native + Expo
- **Navigare**: React Navigation (Bottom Tabs + Stack)
- **Platforme**: iOS și Android
- **UI**: Interfață modernă și intuitivă cu iconuri și culori atractive

**Structura Frontend-ului:**
```
mobile/
├── screens/              # Ecrane principale
│   ├── StepsScreen      # Tracking pași
│   ├── SleepScreen      # Tracking somn
│   ├── WorkoutScreen    # Lista antrenamente
│   ├── WorkoutDetailScreen # Detalii antrenament
│   └── ChatScreen       # ChatBot AI
└── App.js               # Configurare navigare
```

## 🔄 Cum Funcționează Aplicația

### Fluxul de Date

1. **Utilizatorul interacționează cu aplicația mobilă**
   - Introduce date despre pași, somn sau antrenamente
   - Sau pune întrebări ChatBot-ului

2. **Aplicația trimite cereri HTTP la backend**
   - Toate cererile merg către serverul Spring Boot
   - Datele sunt trimise în format JSON

3. **Backend-ul procesează cererea**
   - Validează datele primite
   - Salvează în baza de date PostgreSQL (pentru tracking)
   - Sau trimite cerere către OpenAI API (pentru ChatBot)

4. **Răspunsul este trimis înapoi**
   - Backend-ul returnează datele salvate sau răspunsul AI
   - Aplicația mobilă afișează rezultatul utilizatorului

### Exemplu: Adăugare Antrenament

```
Utilizator → Aplicație Mobilă → POST /api/workouts
                                      ↓
                              Backend Spring Boot
                                      ↓
                              Salvează în PostgreSQL
                                      ↓
                              Returnează antrenamentul salvat
                                      ↓
                              Aplicație Mobilă → Afișează succes
```

### Exemplu: ChatBot AI

```
Utilizator → Aplicație Mobilă → POST /api/chat
                                      ↓
                              Backend Spring Boot
                                      ↓
                              Trimite cerere la OpenAI API
                                      ↓
                              OpenAI procesează cu ChatGPT
                                      ↓
                              Returnează răspunsul AI
                                      ↓
                              Backend → Aplicație Mobilă → Afișează răspuns
```

## 🚀 Instalare și Configurare

### Cerințe Preliminare

- **Java 17** sau mai nou
- **Maven** 3.6+
- **PostgreSQL** 12+
- **Node.js** 16+ și npm
- **Expo CLI** (pentru aplicația mobilă)
- **Cheie API OpenAI** (pentru ChatBot)

### Configurare Backend

1. **Clonează repository-ul**
```bash
git clone <repository-url>
cd HealthTracker/backend
```

2. **Configurează baza de date PostgreSQL**
```sql
CREATE DATABASE healthtracker_db;
```

3. **Actualizează `application.properties`**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/healthtracker_db
spring.datasource.username=postgres
spring.datasource.password=PAROLA_TA

openai.api.key=CHEIA_TA_OPENAI
openai.model=gpt-3.5-turbo
```

4. **Rulează aplicația**
```bash
./mvnw spring-boot:run
```

Backend-ul va rula pe `http://localhost:8080`

### Configurare Frontend

1. **Instalează dependențele**
```bash
cd mobile
npm install
```

2. **Actualizează adresa backend-ului**
În fișierele din `screens/`, actualizează adresa IP din cererile `fetch()` cu adresa IP locală a calculatorului tău:
```javascript
fetch('http://ADRESA_TA_IP:8080/api/...')
```

3. **Pornește aplicația**
```bash
npm start
# sau
expo start
```

4. **Scanează QR code-ul** cu aplicația Expo Go pe telefonul tău

## 📡 Endpoint-uri API

### Health Check
- `GET /test` - Verifică dacă serverul funcționează

### Pași
- `GET /api/steps/today` - Obține pașii pentru ziua de azi
- `POST /api/steps` - Salvează/actualizează pașii pentru ziua curentă

### Somn
- `GET /api/sleep` - Obține toate sesiunile de somn
- `POST /api/sleep` - Adaugă o sesiune de somn nouă

### Antrenamente
- `GET /api/workouts` - Obține toate antrenamentele
- `POST /api/workouts` - Creează un antrenament nou
- `DELETE /api/workouts/{id}` - Șterge un antrenament
- `POST /api/workouts/{id}/exercises` - Adaugă un exercițiu la un antrenament

### ChatBot AI
- `POST /api/chat` - Trimite mesaj către ChatBot și primește răspuns

## 🧪 Testare

Backend-ul include teste unitare pentru majoritatea funcționalităților:

```bash
cd backend
./mvnw test
```

## 📦 Tehnologii Folosite

### Backend
- **Spring Boot** - Framework Java pentru aplicații web
- **Spring Data JPA** - Abstraccție pentru lucrul cu baza de date
- **PostgreSQL** - Baza de date relațională
- **RestTemplate** - Client HTTP pentru comunicare cu OpenAI

### Frontend
- **React Native** - Framework pentru aplicații mobile
- **Expo** - Platformă pentru dezvoltare React Native
- **React Navigation** - Navigare între ecrane
- **Expo Vector Icons** - Iconuri pentru interfață

### AI
- **OpenAI GPT-3.5 Turbo** - Model de limbaj pentru ChatBot

## 🔒 Securitate

- Cheia API OpenAI este stocată sigur pe server, nu în codul aplicației mobile
- Backend-ul folosește CORS pentru a permite doar cereri autorizate
- Datele utilizatorilor sunt stocate local în baza de date PostgreSQL

## 📈 Viitor

Funcționalități planificate:
- Autentificare utilizatori
- Sincronizare cloud pentru date
- Grafice și statistici avansate
- Notificări pentru obiective
- Integrare cu dispozitive wearable (smartwatch, fitness tracker)
- Planuri de nutriție personalizate


---

**Fă primul pas către un stil de viață mai sănătos! 🏃‍♂️💪**

