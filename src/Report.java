import java.time.LocalDate;

public class Report {
    private LocalDate dateOfReport = LocalDate.now();
    private String nameOfReport;
    private String typeOfReport;
    private Store store;

    public Report() {
    }

    public Report(LocalDate dateOfReport, String nameOfReport, String typeOfReport, Store store) {
        this.dateOfReport = dateOfReport;
        this.nameOfReport = nameOfReport;
        this.typeOfReport = typeOfReport;
        this.store = store;
    }

    public LocalDate getDateOfReport() {
        return dateOfReport;
    }

    public void setDateOfReport(LocalDate dateOfReport) {
        this.dateOfReport = dateOfReport;
    }

    public String getNameOfReport() {
        return nameOfReport;
    }

    public void setNameOfReport(String nameOfReport) {
        this.nameOfReport = nameOfReport;
    }

    public String getTypeOfReport() {
        return typeOfReport;
    }

    public void setTypeOfReport(String typeOfReport) {
        this.typeOfReport = typeOfReport;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void displayReportTitle(){
        UserInterface.displayBakeShop();
        System.out.println("Report: " );
        System.out.println("dateOfReport: " + dateOfReport );
        System.out.println("nameOfReport: " + nameOfReport );
        System.out.println("typeOfReport: " + typeOfReport );
        System.out.println("storeId: " + store.getStoreId());
        System.out.println("****************************************");
    }
}
