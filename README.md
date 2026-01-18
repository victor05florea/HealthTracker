# 🍏 HealthTracker Ecosystem - Backend Architecture

## 🌟 1. Viziunea și Scopul Aplicației
Într-o lume tot mai sedentară, monitorizarea activității fizice și a recuperării a devenit esențială pentru menținerea sănătății pe termen lung. **HealthTracker** nu este doar o bază de date, ci un ecosistem digital creat pentru a transforma datele brute în informații acționabile.

### Utilitatea în viața cotidiană:
* **Combaterea sedentarismului:** Prin monitorizarea pașilor zilnici, utilizatorul este motivat să atingă pragul de 10.000 de pași, reducând riscurile cardiovasculare.
* **Optimizarea recuperării:** Modulul de somn ajută la identificarea tiparelor de odihnă, esențiale pentru sănătatea mentală și refacerea musculară.
* **Sistematizarea antrenamentelor:** Elimină nevoia de jurnale fizice, oferind o structură clară exercițiilor și evoluției greutăților folosite.
* **Consiliere Inteligentă (AI):** Integrând un asistent virtual, utilizatorul primește sfaturi personalizate bazate pe principii științifice, fără a filtra singur mii de informații contradictorii de pe internet.

---

## 🏗️ 2. Arhitectura Tehnică și Design Patterns
Aplicația este construită pe platforma **Spring Boot 3.x**, utilizând o arhitectură stratificată (Layered Architecture) pentru a asigura separarea responsabilităților (Separation of Concerns).

### Componente Core:
1. **Controller Layer (REST API):** Punctul de interfațare cu lumea exterioară. Gestionează cererile HTTP și asigură serializarea/deserializarea automată a obiectelor Java în JSON folosind biblioteca Jackson.
2. **Service Layer (Business Logic):** (Implict în controllere pentru acest nivel) Gestionează regulile de business și coordonează fluxul de date.
3. **Data Access Layer (JPA/Repository):** Utilizează Spring Data JPA pentru a abstractiza interogările SQL. Am implementat **Derived Query Methods** (ex: `findByDate`) pentru a automatiza generarea de query-uri.
4. **Model/Entity Layer:** Reprezentarea obiectuală a schemei bazei de date.



---

## 📂 3. Module Funcționale și Fluxul de Lucru

### 🔄 A. Managementul Antrenamentelor (Relații Complexe)
Am implementat o relație **One-to-Many** între `Workout` și `Exercise`.
* **Cum funcționează:** Un antrenament acționează ca un container logic. Atunci când un utilizator adaugă un exercițiu, acesta este legat printr-o cheie externă (Foreign Key) de ID-ul antrenamentului.
* **Cascade Delete:** Am configurat `CascadeType.ALL` pentru a asigura integritatea datelor; la ștergerea unui antrenament, toate exercițiile asociate sunt eliminate automat.

### 🤖 B. Integrarea cu Inteligența Artificială (OpenAI)
Backend-ul acționează ca un **Proxy Securizat**. 
* **Flux:** Frontend -> Backend (Spring) -> OpenAI API -> Backend (Spring) -> Frontend.
* **Securitate:** Cheia API este protejată pe server, eliminând riscul de expunere a creditelor către utilizatorii finali.

### 👣 C. Persistența Activității (Steps & Sleep)
Utilizează tipuri de date Java 8+ (`LocalDate`, `LocalDateTime`) pentru a asigura precizia temporală, mapate automat de Hibernate la coloanele corespunzătoare din PostgreSQL.

---

## 🛠️ 4. Stack Tehnologic (Tech Stack)
* **Backend Framework:** Spring Boot 3
* **Limbaj:** Java 17+
* **Bază de Date:** PostgreSQL
* **ORM:** Hibernate (Java Persistence API)
* **Testare:** JUnit 5, Mockito, AssertJ
* **Build Tool:** Maven
* **Client HTTP:** RestTemplate / OkHttp

---

## 🧪 5. Calitatea Codului și Testarea
Fiabilitatea sistemului este garantată de un set de **19 teste automate**:
* **Unit Tests:** Verifică logica entităților și constructorii.
* **Web Layer Tests (`@WebMvcTest`):** Verifică dacă endpoint-urile răspund corect (200 OK) și dacă rutele sunt mapate corect.
* **Integration Tests (`@DataJpaTest`):** Verifică comunicarea reală cu baza de date H2 (in-memory) pentru a valida query-urile SQL.

---

## 🚀 6. Instalare și Configurare
1. **Prerechizite:** JDK 17+ și un server PostgreSQL activ.
2. **Configurare:** Editați `src/main/resources/application.properties` cu datele bazei de date și cheia OpenAI.
3. **Rulare:** ```bash
   mvn spring-boot:run
