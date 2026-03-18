package Composition;

// PersonalComputer  (個人電腦) 類別是 結合點
public class PersonalComputer extends Product{
    private ComputerCase computerCase;
	private Monitor monitor;
    private Motherboard motherboard;

 

    public PersonalComputer(String model, String manufacturer, ComputerCase computerCase, Monitor monitor,
            Motherboard motherboard) {
        super(model, manufacturer);
        this.computerCase = computerCase;
        this.monitor = monitor;
        this.motherboard = motherboard;
    }

    public ComputerCase getComputerCase() {
        return computerCase;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public Motherboard getMotherboard() {
        return motherboard;
    }
    
    
}
