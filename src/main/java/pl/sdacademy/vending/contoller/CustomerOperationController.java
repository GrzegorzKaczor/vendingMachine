package pl.sdacademy.vending.contoller;

import pl.sdacademy.vending.model.Product;
import pl.sdacademy.vending.model.Tray;
import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.service.repository.VendingMachineRepository;
import pl.sdacademy.vending.util.StringUtil;

import java.util.Optional;

public class CustomerOperationController {
    private final VendingMachineRepository machineRepository;
    private final Integer trayWidth = 12;

    public CustomerOperationController(VendingMachineRepository machineRepository) {
        this.machineRepository = machineRepository;

    }

    public void printMachine() {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if(!loadedMachine.isPresent()){
            System.out.println("Vending Machine out of servive");
            return;
        }
        VendingMachine machine = loadedMachine.get();
        for (int rowNo = 0; rowNo < machine.rowCount(); rowNo++) {
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
               printUpperBoundary(machine, rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printSymbol(machine, rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
               printNameProduct(machine, rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printPriceProduct(machine, rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printLowerBoundary(machine, rowNo, colNo);
            }
            System.out.println();
        }
    }

    public Optional<Product> buyProductForSymbol (String traySymbol){
        Optional<VendingMachine> loaded = machineRepository.load();
        if(loaded.isPresent()){
            VendingMachine machine = loaded.get();
            Optional<Product> boughtProduct = machine.buyProductWithSymbol(traySymbol.toUpperCase());
            machineRepository.save(machine);
            return boughtProduct;
        } else {
            System.out.println("Vending Machine out of sevrice");
            return Optional.empty();
        }
    }

    private void printUpperBoundary(VendingMachine machine, int rowNo, int colNo){
        System.out.print("+" + StringUtil.duplicateText("-", trayWidth) + "+");
    }

    private void printSymbol(VendingMachine machine, int rowNo, int colNo){
        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
        String traySymbol = tray.map(Tray::getSymbol).orElse("--");
        System.out.print("|" + StringUtil.adjustText(traySymbol, trayWidth) + "|");
    }
    private void printNameProduct(VendingMachine machine, int rowNo, int colNo){
        Optional<String> productName = machine.productNameAtPosition(rowNo, colNo);
        String formattedName = productName.orElse("--");
        System.out.print("|" + StringUtil.adjustText(formattedName, trayWidth) + "|");
    }
    private void printPriceProduct(VendingMachine machine, int rowNo, int colNo){
        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
        Long price = tray.map(Tray::getPrice).orElse(0L);
        String formatedMoney = StringUtil.formatMoney(price);
        String centredMoney = StringUtil.adjustText(formatedMoney, trayWidth);
        System.out.print("|" + centredMoney + "|");
    }

    private void printLowerBoundary(VendingMachine machine, int rowNo, int colNo){
        System.out.print("+" + StringUtil.duplicateText("-", trayWidth) + "+");
    }
}
