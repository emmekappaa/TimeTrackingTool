# Time Tracking Tool

## Descrizione del sistema

Il Time Tracking Tool è una piattaforma progettata per supportare ricercatori e responsabili scientifici nella gestione e nel monitoraggio di progetti di ricerca.
Il sistema facilita la rendicontazione delle ore di lavoro e la gestione dei progetti.

## Obiettivi del sistema

Il Time Tracking Tool è progettato per automatizzare e semplificare il processo di gestione delle ore lavorative dei progetti di ricerca e dell'assegnamento di questi ultimi a nuovi ricercatori.
Il sistema deve fornire un'interfaccia intuitiva e facile da usare per gli utenti.

Il sistema deve supportare le seguenti funzionalità principali:

- Gestione delle ore giornaliere
- Visualizzazione report mensili
- Assegnazione progetti a ricercatori
- Storico ore lavorate
- Firma report ore mensili di un progetto

## Glossario

- Ricercatore (Researcher): utente che partecipa ai progetti e registra le ore lavorate
- Responsabile Scientifico (Manager): utente che partecipa ai progetti, assegna progetti ai ricercatori e ne monitora le ore mensili
- Report: visualizzazione delle ore lavorate su un progetto per specifico mese
- Firma: approvazione mediante firma delle ore lavorate relative ad un progetto e ad un mese specifico
- TimeLog: registrazione di ore lavorative relative ad una giornata e ad un progetto realizzate da un ricercatore/responsabile scientifico

## Scenari

### 1. Autenticazione nel sistema e Registrazione TimeLog (Ricercatore)

Il sistema deve permettere al Ricercatore di autenticarsi e successivamente di registrare i suoi TimeLog giornalieri

### 2. Visualizza e Firma di Report (Ricercatore)

Il sistema deve permettere ad un Ricercatore di visualizzare e firmare le ore relativamente ad un progetto su base mensile

### 3. Visualizza e Firma di Report (Responsabile Scientifico)

Il sistema deve permettere al Responsabile Scientifico di firmare e dunque approvare i report dei ricercatori

### 4. Gestione di una Registrazione Errata di un TimeLog

Il sistema deve verificare la registrazione di un TimeLog con un numero di ore lavorative superiore alle 8

### 5. Consultazione Storico Ore

Il sistema deve offrire uno storico delle ore lavorative registrate

### 6. Autenticazione nel sistema e Registrazione TimeLog (Responsabile Scientifico)

Il sistema deve permettere al Responsabile Scientifico di autenticarsi e successivamente di registrare i suoi TimeLog giornalieri

### 7. Creazione di un Progetto e Assegnazione Ricercatori

Il sistema deve permettere al Responsabile Scientifico di creare un nuovo progetto e quindi assegnarlo a determinati Ricercatori scelti

### 8. Rifiuto di un Progetto

Il sistema deve fornire la possibilità ai Ricercatori di rifiutare un progetto assegnatogli da parte del Responsabile Scientifico

### 9. Accettazione di un Progetto

Il sistema deve fornire la possibilità ai Ricercatori di accettare un progetto assegnatogli da parte del Responsabile Scientifico

## Permessi

- Creazione Progetti: Responsabile Scientifico
- Lettura Progetti: Responsabile Scientifico, Ricercatore
- Creazione TimeLog: Responsabile Scientifico, Ricercatore
- Lettura TimeLog: Responsabile Scientifico, Ricercatore
- Lettura Report: Responsabile Scientifico, Ricercatore

## Test e Qualità del codice

Per verificare il funzionamento e l'interazione dei componenti del sistema sono stati svolti i seguenti test di Sistema utilizzando la libreria Selenium:

### testLoginAsResearcher

Test che verifica l'autenticazione di un utente Ricercatore. Coinvolge tutte gli scenari riguardanti il Ricercatore

### testAddTimeLog

Test che verifica l'aggiunta di un TimeLog regolare da parte di un utente Ricercatore. Scenari coinvolti: 1

### testErrorOnExceedingMaxHours

Test che verifica l'apparizione di un messaggio di errore in caso di aggiunta di un TimeLog errato. Scenari coinvolti: 4

### testMonthlyReport

Test che verifica il funzionamento del sistema di gestione dei report mensili. Scenari coinvolti: Scenari coinvolti: 2,3

### testManagerFlow

Test che verifica l'aggiunta corretta ed errata di un TimeLog da parte di un utente Responsabile Scientifico. Scenari coinvolti: 6

### testResearcherAcceptsPendingProject

Test che verifica il funzionamento del sistema di creazione, assegnazione e accettazione di un nuovo Progetto. Scenari coinvolti: 7, 8, 9

### testAddTwoHoursAndVerifyInHistory

Test che verifica la corretta visualizzazione dello storico delle ore. Scenari coinvolti: 5

Sono stati scritti inoltre numerosi test di unità sfruttando la libreria JUnit, utilizzati per verificare e garantire il funzionamento dei metodi e delle classi utilizzate all'interno dell'applicazione.
Successivamente per verificare la coverage (copertura) del codice scritto è stato utilizzato il tool built-in dell'IDE IntelliJ raggiungendo i seeguenti risultati:

![Code Coverage](/images/coverage_intellij.png "Code Coverage")


## Autori

- Alessandro Aldegheri
- Michele Cipriani
- Venturi Davide
