package pl.sdacademy.vending.contoller.service;

import pl.sdacademy.vending.model.Tray;

import java.util.Optional;

public interface EmployeeService {

    Optional<String> addTray (Tray tray);

}
