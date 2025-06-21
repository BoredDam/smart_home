# Progetto "Smart Home Simulator"
Il seguente documento analizza il funzionamento e le scelte intraprese durante lo sviluppo del progetto "Smart Home Simulator", realizzato da Paolo Volpini e Damiano Trovato.

#### Prima dell'uso - Versione di Java
Per il progetto è stata utilizzata la versione 24 di JavaSE. Per garantire il corretto funzionamento del software, è consigliabile utilizzare quest'ultima.

## Funzionamento del sistema
"Smart Home Simulator" è un simulatore gestionale di un sistema smart home. All'interno del sistema è possibile individuare:
- **un controller principale**, controllato dall'utente tramite interfaccia apposita;
- molteplici **dispositivi**, di tipo diverso e gestiti dal controller.

All'avvio della simulazione verrà presentata all'utente un'interfaccia, "Smart Home Menu Interface", tramite la quale avrà la possibilità di **digitare comandi** selezionati in appositi **menu**. Ogni scelta viene confermata premendo il tasto "Enter" ("Invio"). Nei primi istanti di avvio, il simulatore imposterà le variabili necessarie per la corretta esecuzione del programma, e al termine mostrerà la seguente scritta:
```
Everything should be in place! Press any command to continue...
```
A questo punto basta premere "Invio" per passare al **menu principale**, da cui si potrà accedere alle funzionalità del sistema.

Le scelte del menu sono etichettate con un carattere alfanumerico seguito da una parentesi chiusa ")". Nel caso in cui non sia presente alcun carattere, qualunque input diverso dai precedenti è valido, o anche l'input vuoto. Per selezionare una voce, basta digitare il carattere mostrato e premere "Invio". Ad esempio, nel menu principale si ha la voce:
```
1) configuration menu
```
Basta allora digitare "1" e premere "Invio" per passare al successivo menu.

L'interfaccia "Smart Home Menu Interface" non mostrerà gli effetti dei comandi: questi infatti **saranno stampati a terminale** seguendo i canali di comunicazione di output standardizzato, ossia `stdout` e `stderr`. 

### Elementi del sistema
Il **controller** si occupa dell'**esecuzione dei comandi** e dell'**attivazione degli scenari** definiti dall'utente. Permette anche la **pianificazione** di questi. Inoltre, per i comandi, è anche possibile pianificare una **ripetizione** periodica. 

Un **dispositivo** è un elemento della simulazione smart home gestibile dal controller. Ogni dispositivo è dotato di uno **stato**, acceso o spento, che vincola l'esecuzione dei comandi:
- in uno stato **acceso** un dispositivo può eseguire tutti i comandi compatibili;
- in uno stato **spento** un dispositivo può essere solamente acceso tramite il comando `TurnOn`.

Tutti i dispositivi supportano i comandi `TurnOn` e `TurnOff`. Nei dispositivi rientrano anche le porte. Le **porte** costituiscono una leggera eccezione nel sistema: possono essere **aperte** o **chiuse** indipendentemente dallo stato. Tuttavia, il **blocco** delle porte può essere gestito solamente dal controller, simulando il meccanismo di un **lucchetto smart**.\
Ogni dispositivo è dotato di un tipo preciso. In particolare, un sottoinsieme di dispositivi può essere **monitorato** per l'attivazione di eventi. Il sistema supporta i seguenti tipi di dispositivi:
- **luce**. Una luce può essere semplicemente spenta o accesa;
- **condizionatore**. Per il condizionatore può essere impostato un valore di temperatura obiettivo;
- **termostato**. Per il termostato è possibile impostare valori di limite di temperatura superiore e inferiore;
- **porta**. Per la porta sono supportati comandi di blocco e di sblocco del lucchetto. Questi comandi possono essere applicati solo se la porta è chiusa;
- **telecamera**. Una telecamera supporta i comandi di registrazione video e di cattura di un'immagine. Risulta possibile aggiungere le seguenti funzionalità alla telecamera:
  - **audio HD**, che modifica il comportamento della registrazione video simulando la cattura di un audio di qualità superiore;
  - **visione notturna**, che modifica il comportamento di entrambi i comandi annotando l'utilizzo del sensore di visione notturna;
  - **visione termica**, che modifica il comportamento di entrambi i comandi annotando l'utilizzo di un sensore termico.
- **altoparlante**. Un altoparlante può ricevere i comandi di pausa, stop e riproduzione di musica. Un altoparlante spento viene automaticamente messo in stop. Risulta possibile aggiungere le seguenti funzionalità all'altoparlante, che modificano il comportamento del comando di riproduzione:
  - **Spotify**, che notifica l'utilizzo del servizio "Spotify";
  - **Youtube Music**, che notifica l'utilizzo del servizio "Youtube Music";
  - **Amazon Music**, che notifica l'utilizzo del servizio "Amazon Music".
- **vecchia stufa**. La vecchia stufa non è conforme all'interfaccia del dispositivo generico nel sistema, per cui viene adattata con un apposito adattatore. Supporta i comandi default di accensione e spegnimento.

Quando un dispositivo viene **aggiunto**, viene chiesto il **tipo** e il **nome** da assegnare. **Nomi duplicati non sono ammessi**. Inoltre, un dispositivo è automaticamente spento al momento del suo inserimento nel sistema. Infine, determinati dispositivi presentano una **configurazione default per parametri interni**:
- il **termostato** presenta automaticamente limite inferiore a 10 gradi centigradi e superiore a 50;
- il **condizionatore** presenta automaticamente temperatura obiettivo a 23 gradi centigradi;
- la **porta** è automaticamente aperta;
- lo **speaker** è automaticamente in stop, poiché il dispositivo è aggiunto come spento.

Alcuni dispositivi sono in grado di inviare una **notifica** al controller: tali dispositivi sono la telecamera, il termostato e la porta. Alla ricezione della notifica, il controller deciderà se scatenare un **evento** nel sistema. Un evento è la risposta ad una **situazione** **anomala** segnalata da un dispositivo nel sistema. Per scatenare un evento, il controller prima verificherà se il dispositivo è **monitorato**. Con "monitorato" si intende uno stato personale tracciato dal controller per stabilire se ignorare le notifiche di un dispositivo o meno. Il sistema supporta i seguenti eventi:
- **intrusione**. L'evento simula l'intrusione nella casa, intesa come effrazione. L'intrusione viene rilevata dalla telecamera e da una porta che passa direttamente da uno stato bloccato ad uno stato aperto;
- **bassa temperatura**. L'evento simula l'abbassamento eccessivo della temperatura nella casa. Tale evento è rilevato da un termostato quando la temperatura misurata è inferiore rispetto al limite inferiore impostato;
- **alta temperatura**. L'evento simula l'innalzamento eccessivo della temperatura nella casa. Tale evento è rilevato da un termostato quando la temperatura misurata è superiore rispetto al limite superiore impostato.

Quando un evento viene scatenato, vengono **automaticamente** eseguiti dei **comandi** per certi dispositivi. Più precisamente:
- "intrusione" esegue su ogni telecamera i comandi di registrazione video e di acquisizione di un'immagine;
- "bassa temperatura" e "alta temperatura" accendono tutti i condizionatori per mitigare la temperatura. Le vecchie stufe, invece, vengono accese quando la temperatura è bassa e spente quando la temperatura è alta.

Le misurazioni di temperatura e le intrusioni possono essere simulate interagendo con  l'**ambiente**. Questo ambiente è del tutto fittizio. Tramite l'ambiente è possibile:
- aprire e chiudere porte;
- simulare la rilevazione di un intruso da parte di una telecamera;
- simulare la misurazione della temperatura. La temperatura standard dell'ambiente è di 20 gradi centigradi. Questa temperatura viene modificata in virtù dei condizionatori accesi e delle temperature obiettivo di questi, e incrementata automaticamente in virtù del numero di vecchie stufe accese.

L'utente è anche in grado di definire degli **scenari**. Uno scenario è una configurazione di un set di comandi da eseguire su dispositivi a scelta e di un set di stati di monitoraggio per i dispositivi monitorabili. Ad esempio, una ipotetica modalità "notte" può essere configurata per spegnere immediatamente tutte le luci di casa. Uno scenario può anche essere **pianificato** per l'attivazione, oppure può essere attivato **istantaneamente**. Quando uno scenario viene pianificato per una certa ora, viene anche automaticamente pianificata l'esecuzione per i **successivi giorni alla stessa ora**. Una pianificazione **può essere annullata tramite** il menu apposito.

## Design Pattern usati


Per migliorare la leggibilità degli *snippet* di codice illustrati, si è deciso di rimuovere i commenti invece presenti nel codice sorgente. 

### **Command**
Il pattern comportamentale **Command** è uno dei design pattern chiave del sistema: è alla base della struttura e del comportamento dell'omonima interfaccia `Command`.

```Java
public interface Command {
    public void run();
    public void setDevice(Device dev);
}
```

Questa interfaccia è poi implementata da molteplici classi astratte, una per tipologia di device (+ una classe per i comandi universali). 

Ad esempio, la classe astratta per i comandi generali `DeviceCommand`
```Java
public abstract class DeviceCommand implements Command {
    
    protected Device device;
    public DeviceCommand() {
    }

    @Override
    public void setDevice(Device dev) {
        device = dev;
    }
}
```
Successivamente estesa da `TurnOnCommand`
```Java
public class TurnOnCommand extends DeviceCommand {
  
    public TurnOnCommand() {
        super();
    }
    
    @Override
    public void run() {
        device.turnOn();
    }
}
```

O la classe astratta `AirConditionerCommand`
```Java
public abstract class AirConditionerCommand implements Command {
    
    protected AirConditioner airConditioner;
    public AirConditionerCommand() {}
    
    @Override
    public void setDevice(Device dev) {
        airConditioner = (AirConditioner) dev;
    }
}
```

estesa dalla classe `SetTargetTemperatureCommand`. Si osserva come il costruttore di questa classe, prenda in input
un argomento, per stabilirne la `targetTemp`.
```Java
public class SetTargetTemperatureCommand extends AirConditionerCommand {

    private final float targetTemp;
    public SetTargetTemperatureCommand(float targetTemp) {
        super();
        this.targetTemp = targetTemp;
    }

    @Override
    public void run() {
        airConditioner.setTargetTemp(targetTemp);
    }
}
```

### **Observer**
Altro pattern comportamentale chiave per il funzionamento del sistema, è il pattern **Observer**. L'uso di questo pattern permette infatti allo `SmartHomeController`, che implementa l'omonima interfaccia `Observer` di *osservare* una specifica tipologia di dispositivi: quelli che estendono la classe astratta `ObservableDevice` (quali `Door`, `Thermostat` e `Camera`).

L'interfaccia `Observer`:
```Java
public interface Observer {
    void update(ObservableDevice dev, String event);
}
```
Con il metodo `update`, successivamente sovrascritto nella classe `SmartHomeController`, quest'ultimo può reagire a notifiche di eventi ricevute dagli `ObservableDevice`, ovvero:

```Java
public abstract class ObservableDevice extends Device {

    protected Observer controllerObserving;

    public ObservableDevice(String name) {
        super(name);
        
    }
    
    public void attach(Observer master) {
        controllerObserving = master;
    }

    public void detach() {
        controllerObserving = null;
    }

    public abstract void notifyObserver();

}
```

Il controller riceve quindi un *update* contenente il tipo di evento e l'`ObservableDevice` da cui è stato lanciato.

Questo è poi passato al metodo `getEvent(String type)` dell'`eventManager`, che tornerà un oggetto `Event` vero e proprio.
```java
public class SmartHomeController implements Observer {

    /*rest of the class...*/
    public void update(ObservableDevice dev, String eventType) {
        if(listenedDevices.get(dev)){ 
           Event event = eventManager.getEvent(eventType); 
           if(event == null) {
                printMessage("The device " + dev.getName() + " has sent a notification that is not currently supported by any event");
                return;
            }
            triggerEvent(event);
        }   
    }

    public void triggerEvent(Event event) {
        System.out.println("[SmartHomeController] Event " + event.getType() + " triggered!");
        device_list.forEach(dev -> event.getCommands(dev).forEach(cmd -> dev.performAction(cmd)));
    }
    /*rest of the class...*/

}
```
L'oggetto `Event` contiene al suo interno dei comandi da eseguire sui device quando questo viene lanciato.

### **State**
L'ultimo pattern comportamentale che abbiamo usato, è lo **State** pattern. È stato usato per semplificare la gestione di comportamenti variabili, in funzione dello stato del `Device` coinvolto. 
In particolare, è stato usato

- In tutti i `Device`, per gestirne il comportamento da accesi e da spenti (interfaccia `PowerState` implementata da `OnState` e `OffState`).

- Negli `Speaker`, per gestirne la riproduzione della musica (interfaccia `SpeakerState` implementata da `PlayState`, `PauseState` e `StopState`).

- Nelle `Door`, per gestirne lo stato di apertura (interfaccia `LockState` implementata da `OpenedState`, `ClosedState` e `LockedState`).

Osserviamo il caso specifico del `Device` e della sua interfaccia `PowerState`:

```Java
public interface PowerState {
    PowerState turnOn();
    PowerState turnOff();
    boolean isOn();
    boolean isOff();
    void runCommand(Command cmd);
}
```

implementata da `OnState` 
```Java
public class OnState implements PowerState {
    private static OnState instance;
    private OnState() {}

    public static OnState getInstance() {
        if(instance == null) {
            instance = new OnState();
        } 
        return instance;
    }

    @Override
    public PowerState turnOn() {
        System.out.println("Already on!");
        return instance;
    }

    @Override
    public PowerState turnOff() {
        System.out.println("Turned off!");
        return OffState.getInstance();
    }

    @Override
    public boolean isOn() {
        return true;
    }

    @Override
    public boolean isOff() {
        return false;
    }

    @Override
    public void runCommand(Command cmd) {
        cmd.run();
    }
}
```
che eseguirà i comandi regolarmente, e `OffState`
```Java
public class OffState implements PowerState {
    static OffState instance;
    private OffState() {}
    
    public static OffState getInstance() {
        if(instance == null) {
            instance = new OffState();
        }
        return instance;
    }

    @Override
    public PowerState turnOff() {
        System.out.println("Already off!");
        return instance;
    }

    @Override
    public PowerState turnOn() {
        System.out.println("Turned on!");
        return OnState.getInstance();
    }

    @Override
    public boolean isOn() {
        return false;
    }
    
    @Override
    public boolean isOff() {
        return true;
    }

    @Override
    public void runCommand(Command cmd) {
        if(cmd instanceof TurnOnCommand toc) {
            toc.run();
        }
    } 
}
```
che non permetterà l'esecuzione di alcun comando (in quanto spento), se non del comando `TurnOnCommand`. Otteniamo così una gestione semplice del *do-nothing behaviour* nel caso dei dispositivi spenti.

### **Decorator**
Il pattern **Decorator** è un pattern strutturale che, nel nostro sistema, permette di estendere a le funzionalità dei dispositivi `Speaker` e `Camera`.

- Nel dispositivo `Speaker`, è possibile installare degli `SpeakerAppDecorator`, quali `AmazonMusicApp`, `YoutubeMusicApp` e `SpotifyApp`.
- Nel dispositivo `Camera`, è possibile installare dei `CameraDecorator`, quali `NightVision`, `ThermalVision` e `HDAudio`.

Osserviamo il caso del dispositivo `Camera`. L'omonima interfaccia

```Java
public abstract class Camera extends ObservableDevice {

    public Camera(String name) {
        super(name);
    }
    @Override
    public void notifyObserver() {
        if(isOn() && controllerObserving != null) controllerObserving.update(this, "Intrusion");
    }
    public abstract void captureImage();
    public abstract void recordVideo();
}
```

è implementata sia dalla classe `BaseCamera`

```Java
public class BaseCamera extends Camera {

    public BaseCamera(String name) {
        super(name);
    }
    
    @Override
    public void captureImage() {
        printHeader();
        System.out.println("Just took a picture.");
    }

    @Override
    public void recordVideo() {
        printHeader();
        System.out.println("Recording...");
    }

    @Override
    public String getBaseType() {
        return "Camera";
    }

}
```

che dalla classe astratta `CameraDecorator`.
```Java
public abstract class CameraDecorator extends Camera {

    private final Camera wrapped;

    public CameraDecorator(Camera wrapped) {
        super(wrapped.getName());
        this.wrapped = wrapped;
    }

    @Override
    public String getType() {
        String ret = this.getClass().getSimpleName();
        Device current = wrapped;
        while(current instanceof CameraDecorator wd) {
            ret = wd.getClass().getSimpleName() + ", " + ret;
            current = wd.wrapped; 
        }
        return ret + ", BaseCamera";
    }

    @Override
    public void captureImage() {
        wrapped.captureImage();
    }

    @Override
    public void recordVideo() {
        wrapped.recordVideo();
    }

    @Override
    public String getBaseType() {
        return "Camera";
    }
}
```

Osserviamo ora il codice dei tre decoratori.


`ThermalVision`
```Java
public class ThermalVision extends CameraDecorator {

    public ThermalVision(Camera wrapped) {
        super(wrapped);
    }

    @Override
    public void captureImage() {
        printHeader();
        System.out.println("Going to take a picture with enhanced Thermal Vision...");
        super.captureImage();
    }

    @Override
    public void recordVideo() {
        printHeader();
        System.out.println("Going to record with enhanced Thermal Vision...");
        super.recordVideo();
    }
}
```

`NightVision`
```Java
public class NightVision extends CameraDecorator {

    public NightVision(Camera wrapped) {
        super(wrapped);
    }

    @Override
    public void captureImage() {
        printHeader();
        System.out.println("Going to take a picture with enhanced Night Vision...");
        super.captureImage();
    }

    @Override
    public void recordVideo() {
        printHeader();
        System.out.println("Going to record with enhanced Night Vision... spooky...");
        super.recordVideo();
    }
}
```

`HDaudio` - che non aggiunge nulla al `captureImage`!
```Java
public class HDAudio extends CameraDecorator {
    
    public HDAudio(Camera wrapped) {
        super(wrapped);
    }

    @Override
    public void captureImage() {
        super.captureImage();
    }

    @Override
    public void recordVideo() {
        printHeader();
        System.out.println("Going to record with enhanced Audio...");
        super.recordVideo();
    }
}
```

### **Adapter**
L'uso del pattern strutturale **Adapter** è stato presocché obbligatorio per permettere l'integrazione di un dispositivo *legacy* nel nostro sistema, quale l'`OldHeater`. 
```Java
public class OldHeater {
    
    private boolean state;

    public OldHeater() {
        this.state = false;
    }

    public void boot() { 
        state = true;
    }

    public void shutdown() { 
        state = false; 
    }

    public boolean getState() {
        return state;
    }
}
```

Questo dispositivo *legacy* è inadatto al sistema, in quanto non implementa l'interfaccia `Device`. Va quindi adattato, incapsulandolo in un'istanza della classe `OldHeaterAdapter`, che invece implementa l'interfaccia `Device`, facendone anche la sovrascrittura dei metodi.

```Java
public class OldHeaterAdapter extends Device {
    
    private final OldHeater adaptee;

    public OldHeaterAdapter(String name) {
        super(name);
        adaptee = new OldHeater();
    }

    @Override
    public void turnOn() {
        printHeader();
        if(isOn()) {
            System.out.print("Already on!\n");
            return;
        }
        System.out.print("Turned On!\n");
        adaptee.boot();
    }

    @Override
    public void turnOff() {
        printHeader();
        if(isOff()) {
            System.out.print("Already off!\n");
            return;
        }
        System.out.print("Turned Off!\n");
        adaptee.shutdown();
    }

    @Override
    public boolean isOn() {
        return adaptee.getState();
    }

    @Override
    public boolean isOff() {
        return !(adaptee.getState());
    }

    @Override
    public String getBaseType() {
        return "OldHeater";
    }

    @Override
    public void performAction(Command cmd) {
        if (cmd instanceof TurnOnCommand) {
            turnOn();
        } else if (cmd instanceof TurnOffCommand) {
            turnOff();
        } else {
            System.out.println("Unsupported command.");
        }
    }
}
```

### **Facade**
Data la complessità del sistema, una classe client potrebbe dipendere  da un numero elevato di classi. Per alleggerire il numero di dipendenze, la `UserFacade` offre una facciata per interfacciarsi con le funzionalità di queste classi, semplificando anche molteplici controlli.

### **Factory**
Per spostare la logica di istanziazione dei device, dei comandi e dei decoratori dal controller, sono state create tre classi `Factory`, basate sul **Factory** pattern:
- `DeviceFactory`, che ritorna oggetti di tipo `Device`.
- `DecoratorFactory`, che incapsula la logica di aggiunta dei decorator ai device.
- `CommandFactory`, che ritorna oggetti di tipo `Command`.

Le factory, inoltre, contengono informazioni aggiuntive per aiutare alla creazione del comando. Più precisamente:
- `DecoratorFactory` permette di restituire i tipi di decoratori compatibili con il dispositivo specificato, e di evitare di aggiungere più decoratori dello stesso tipo;
- `CommandFactory` verifica se un comando richiede parametri aggiuntivi per l'istanziazione.



### **Singleton**
Per limitare la creazione di determinate classi ad una singola istanza, è stato applicato il **Singleton** pattern su diverse classi, quali `SmartHomeController`, le classi `Factory`, `CommandRegister` e `EventManager`. 

## Lavoro

### Fasi preliminari - prime settimane
Durante le prime settimane di lavoro ci siamo riuniti di presenza per analizzare i requisiti del sistema, cercando di individuare le principali entità e di capire, orientativamente, il flusso generale degli eventi. Dopo una prima fase di analisi, effettuata tramite diagrammi di classe semplificati, abbiamo steso delle possibili implementazioni per le classi principali, come la classe `Device`, senza entrare nei dettagli relativi alle specifiche sottoclassi.

Un'ulteriore fase di analisi ci ha permesso di identificare meglio i ruoli delle classi e gli opportuni design pattern da usare per garantire il corretto funzionamento del sistema. Sono inoltre stati analizzati gli aspetti critici da risolvere, riguardanti principalmente lo schedulaggio dei comandi e la reazione agli eventi.

### Fasi intermedie
La fase intermedia dello sviluppo è stata svolta in maniera asincrona, sfruttando **GitHub** come principale strumento di collaborazione. Tramite aggiornamenti periodici, è stato possibile coordinare il lavoro in maniera opportuna, seguendo una logica *build-and-test*.

### Fase conclusiva - ultima settimana
Nella fase conclusiva è stata effettuata la stesura del codice dell'interfaccia grafica, della classe `Environment` per simulare l'ambiente della *smart home*, ed è stata valutata una soluzione per suddividere la stampa degli output dei dispositivi dalla stampa della user interface, con l'ausilio di `javax.swing`.

È stato quindi fatto un collaudo generale dell'intero sistema, realizzando anche una demo contenente dispositivi e scenari preimpostati, oltre alla ristesura degli UML (diagramma delle classi, diagrammi di stato e delle attività, nella directory ./uml), la stesura della relazione, e la realizzazione della documentazione (tramite commenti) seguendo gli standard javadoc. La documentazione è disponibile tramite il link presente nella descrizione della repository.


