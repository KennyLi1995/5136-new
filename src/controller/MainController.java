package controller;

import model.BakerySystem;
import model.User;
import service.BakeryService;
import service.ReportService;
import utils.BakeryUtils;

public class MainController {

	private static BakeryService bakeryService = new BakeryService();
	private static ReportService reportService = new ReportService();

	public static void main(String args[]) {
		while (true) {
			BakerySystem bakerySystem = new BakerySystem();
			boolean check;
			BakeryUtils.displayBakeShop();
			System.out.println("***Please enter your user credential***");
			check = bakeryService.login(bakerySystem);
			while (!check) {
				BakeryUtils.displayLoginError();
				check = bakeryService.login(bakerySystem);
			}
			User currentUser = bakerySystem.getBakery().getListOfStore().get(0).getListOfUser().get(0);
			bakeryService.mainOption(currentUser, bakerySystem);
		}

	}

}
