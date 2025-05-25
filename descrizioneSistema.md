# Descrizione del sistema
Il sistema presenta le seguenti entità:
- **device**, che rappresenta un dispositivo generico all'interno della smart home;
- **controller**, il controllore dei dispositivi;
- **event**, che rappresenta un evento all'interno del sistema;
- **scenario**, che rappresenta un set di configurazioni per il dispositivo;
- **command**, che rappresenta un comando inviato dal controller ad uno o più dispositivi.

## Use cases

### Configurazione
L'utente può configurare la smart home aggiungendo o rimuovendo dei dispositivi. L'utente invia una richiesta al controller per eseguire queste operazioni, specificando il tipo di dispositivo e il nome. 

L'utente è anche in grado di creare degli scenari, stabilendo:
- i dispositivi coinvolti;
- eventuali parametri di configurazione (i.e. orario, limiti di temperatura, ecc.);

### Interazione controller-device
Il controller può inviare uno o più comandi a dei dispositivi. L'utente è in grado di inviare comandi in maniera volontaria specificando il dispositivo destinatario e il comando da impartire (menu dei comandi?). 

Il controller periodicamente riceve notifiche dai device monitorati. Di default ogni device viene monitorato e invia i dati captati dall'ambiente circostante. Il controller confronta questi dati con i valori di threshold definiti nella sua configurazione. In caso di un superamento di soglia, il controller genera un evento e invia comandi ai dispositivi per stabilizzare la situazione.

### Evento
Ogni evento incapsula un determinato tipo di comando. Il device si aggiorna in base all'evento attivato nel sistema, ossia legge il tipo di evento ed estrae il comando da eseguire.

### Scenario
Ogni scenario rappresenta una configurazione diversa del controller. Nello scenario ogni dispositivo viene cablato in modo tale che siano presenti sempre due informazioni:
- nome del dispositivo;
- stato di monitoraggio. 

All'attivazione dello scenario si prevede l'esecuzione di determinati comandi. Lo scenario notte, per esempio, può spegnere tutte le luci della casa e impostare una sveglia. I comandi vengono inseriti in una coda e smistati ai dispositivi.

### Estensioni funzionalità
L'utente può aggiungere funzionalità ad un dispositivo, purché queste siano compatibili con il dispositivo selezionato.\
L'aggiunta di funzionalità prevede semplicemente la possibilità di catturare lo stesso evento in più modi diversi. Si faccia riferimento ad un visore notturno per una telecamera. Allora la telecamera sarà in grado di individuare l'intruso sia di giorno sia di notte. L'evento tuttavia è sempre l'evento di intrusione.