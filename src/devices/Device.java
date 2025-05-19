public abstract class Device {

    String name;
    
    void performAction(String action) {};
    void update(Event event) {};
    
    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
