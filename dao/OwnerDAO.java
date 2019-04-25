package by.park.dao;

import by.park.entity.object.Plant;

public interface OwnerDAO {
    int insertPlant(String title);
    Plant findPlant(int idPlant);
    void taskLanding(Plant plant);
    void taskTreatment(Plant plant);
    void taskArtProcessing(Plant plant);
    void taskDestruction(Plant plant);
    void viewForesterReport();
    void updateAccordingReport(int idReportForester);
}
