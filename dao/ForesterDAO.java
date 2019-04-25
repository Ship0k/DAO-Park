package by.park.dao;

public interface ForesterDAO {
    void viewTask();
    void performTask(int idOwnerTask);
    void viewRеportList();
    void updateReport(int idOwnerTask, int foresterReportId);
    void deleteReport(int foresterReportId);
}
