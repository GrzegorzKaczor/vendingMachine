package pl.sdacademy.vending.contoller;

import pl.sdacademy.vending.model.Product;
import pl.sdacademy.vending.model.Tray;
import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.util.StringUtil;

import java.util.Optional;

public class CustomerOperationController {
    private final VendingMachine machine;
    private final Integer trayWidth = 12;

    public CustomerOperationController(VendingMachine machine) {
        this.machine = machine;
    }

    public void printMachine() {

        for (int rowNo = 0; rowNo < machine.rowCount(); rowNo++) {
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
               printUpperBoundary(rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printSymbol(rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
               printNameProduct(rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printPriceProduct(rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printLowerBoundary(rowNo, colNo);
            }
            System.out.println();
        }
    }

    public Optional<Product> buyProductForSymbol (String traySymbol){
            return machine.buyProductWithSymbol(traySymbol.toUpperCase());
    }

    private void printUpperBoundary(int rowNo, int colNo){
        System.out.print("+" + StringUtil.duplicateText("-", trayWidth) + "+");
    }

    private void printSymbol( int rowNo, int colNo){
        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
        String traySymbol = tray.map(Tray::getSymbol).orElse("--");
        System.out.print("|" + StringUtil.adjustText(traySymbol, trayWidth) + "|");
    }
    private void printNameProduct( int rowNo, int colNo){
        Optional<String> productName = machine.productNameAtPosition(rowNo, colNo);
        String formattedName = productName.orElse("--");
        System.out.print("|" + StringUtil.adjustText(formattedName, trayWidth) + "|");
    }
    private void printPriceProduct( int rowNo, int colNo){
        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
        Long price = tray.map(Tray::getPrice).orElse(0L);
        String formatedMoney = StringUtil.formatMoney(price);
        String centredMoney = StringUtil.adjustText(formatedMoney, trayWidth);
        System.out.print("|" + centredMoney + "|");
    }

    private void printLowerBoundary( int rowNo, int colNo){
        System.out.print("+" + StringUtil.duplicateText("-", trayWidth) + "+");
    }
}
