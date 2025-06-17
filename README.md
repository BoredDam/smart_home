
# Progetto: *Smart Home Simulator*

## Descrizione
Realizzare una simulazione di una casa intelligente in cui vari dispositivi (termostati, luci, porte, videocamere, ecc.) possono essere controllati tramite un'applicazione centrale. Gli utenti possono programmare comportamenti automatici, scenari (es: "modalità vacanza", "modalità notte") e reagire ad eventi (es: "porta aperta", "temperatura troppo bassa").

---

## Piano di lavoro settimanale

### Settimana 1 — Analisi e Architettura Base
- Definire le entità principali (`Device`, `SmartHomeController`, `Scenario`, `Event`)
- Progettare l'architettura generale (diagramma UML di massima)
- Implementare prototipi di dispositivi diversi e del controller centrale (`SmartHomeController`)

### Settimana 2 — Comunicazione tra Oggetti
- I dispositivi si registrano agli eventi del controller (es: "quando la temperatura cambia")
- Permettere di creare comandi (accendi luce, chiudi porta) che possono essere schedulati o eseguiti a richiesta
- Primi test di funzionamento

### Settimana 3 — Scenari e Automatismi
- Creare scenari usando diversi comportamenti a seconda dello scenario attivo
- Cambiare comportamento dei dispositivi in base al loro stato interno (es: una porta può essere "aperta", "chiusa", "bloccata")
- Debug intensivo

### Settimana 4 — Rifinitura, Estensioni e Presentazione
- Estendere il progetto:
  - Consentire di estendere dinamicamente le funzionalità dei dispositivi (es: videocamera con rilevamento movimento)
  - Consentire di integrare un "vecchio" tipo di dispositivo incompatibile con il sistema moderno
- Preparare una presentazione tecnica e una demo funzionante

---

## Obiettivi di valutazione
- Correttezza e chiarezza nell'applicazione dei pattern
- Qualità del codice (pulizia, modularità, aderenza agli standard Java)
- Capacità di collaborazione e divisione dei compiti
- Creatività nell'estendere il progetto
- Presentazione e documentazione tecnica

---

## Classi principali suggerite

### 1. `SmartHomeController`
Responsabile della registrazione dei dispositivi e della gestione degli eventi.

**Metodi principali:**
```java
addDevice(Device device)
removeDevice(Device device)
triggerEvent(Event event)
```

---

### 2. `Device` (Interfaccia / Classe astratta)
Tutti i dispositivi devono implementare questa interfaccia base.

**Metodi minimi:**
```java
void performAction(String action)
void update(Event event)
```

➡️ Sottoclassi concrete: `Light`, `Door`, `Thermostat`, `Camera`

---

### 3. `DeviceFactory`
Classe dedicata alla creazione di dispositivi.

**Esempio di metodo:**
```java
public static Device createDevice(String type, String name);
```

---

### 4. Comandi
Creare comandi come oggetti eseguibili:

```java
interface Command {
  void execute();
}
```

**Esempi di comandi:**
- `TurnOnLightCommand`
- `LockDoorCommand`
- `SetTemperatureCommand`

➡️ Il controller o uno scheduler potrà eseguire i comandi

---

### 5. `Event` (Classe base per eventi)
Rappresenta un evento nel sistema.

**Attributi base:**
- Tipo evento (`String`)
- Eventuali dati extra (es: temperatura rilevata)

➡️ I `Device` registrati reagiscono agli eventi

---

### 6. `Scenario`
Rappresenta diversi comportamenti preconfigurati.

**Esempi:**
- Modalità "Notte" → spegne tutte le luci, chiude tutte le porte
- Modalità "Vacanza" → accende le luci a intervalli random

**Interfaccia associata:**
```java
interface ScenarioBehaviour {
  void apply(SmartHomeController controller);
}
```

---

### 7. Comportamento dei dispositivi
Esempio: una `Door` ha diversi stati:
- `Open`
- `Closed`
- `Locked`

➡️ Il comportamento dipende dallo stato

---

### 8. Funzionalità dinamiche per i dispositivi
Esempio: aggiungere funzionalità come il "motion detection" a una `Camera` esistente:
```java
Camera motionCamera = new MotionDetection(baseCamera);
```

---

### 9. Supporto per dispositivi legacy
Integrare un `OldHeater` (vecchio riscaldatore) non conforme con `Device`.

---

## Consigli pratici
- Usare pacchetti Java chiari per organizzare il progetto (es: `model`, `controller`, `commands`, `events`, `scenarios`)
- Scrivere test unitari semplici per i componenti principali (consigliato: JUnit)
- Prima di codificare, disegnare uno o due diagrammi per chiarirsi le idee

---

## Cosa consegnare
- Codice sorgente completo (pulito e ben commentato)
- Diagrammi UML (semplificati) di:
  - Architettura generale
  - Flusso di eventi
- Breve documentazione (`.md` o `.rtf`) che spiega:
  - Come funziona il sistema
  - Quali pattern sono stati usati e dove
  - Divisione del lavoro tra gli studenti
- Presentazione di 5-10 minuti per spiegare il progetto
- Breve demo
