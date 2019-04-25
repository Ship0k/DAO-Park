package by.park.dao.service;

import by.park.dao.connect.SQLServer;
import by.park.dao.OwnerDAO;
import by.park.entity.Forester;
import by.park.entity.object.Plant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLServerOwner implements OwnerDAO {

    public SQLServerOwner() {
    }

    @Override
    public int insertPlant(String title) {
        int id = -1;
        String addSql = "INSERT Plants (Title) VALUES (?)";
        String extractSql = "SELECT TOP 1 Id FROM Plants ORDER BY ID DESC";

        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addSql);
             PreparedStatement preparedStatement2 = connection.prepareStatement(extractSql))
        {
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement2.executeQuery();
            id = resultSet.getInt("Id");
            System.out.println("Растение " + title + " добавлено в базу с присвоением Id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Plant findPlant(int idPlant) {
        String sql = "SELECT Id, Title FROM Plants WHERE Id = ?";
        Plant plant = new Plant();

        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setLong(1, idPlant);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                plant.setId(resultSet.getInt("Id"));
                plant.setTitle(resultSet.getString("Surname"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return plant;
    }

    @Override
    public void taskLanding(Plant plant) {
        String sql = "INSERT ListOwnerTasks (PlantId, TaskId) VALUES (?, 1)";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, plant.getId());

            preparedStatement.executeUpdate();
            System.out.println("Заданее успешно добавлено в лист заданий");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void taskArtProcessing(Plant plant) {
        String sql = "INSERT ListOwnerTasks (PlantId, TaskId) VALUES (?, 2)";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, plant.getId());

            preparedStatement.executeUpdate();
            System.out.println("Заданее успешно добавлено в лист заданий");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void taskTreatment(Plant plant) {
        String sql = "INSERT ListOwnerTasks (PlantId, TaskId) VALUES (?, 3)";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, plant.getId());

            preparedStatement.executeUpdate();
            System.out.println("Заданее успешно добавлено в лист заданий");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void taskDestruction(Plant plant) {
        String sql = "INSERT ListOwnerTasks (PlantId, TaskId) VALUES (?, 4)";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, plant.getId());

            preparedStatement.executeUpdate();
            System.out.println("Заданее успешно добавлено в лист заданий");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewForesterReport() {
        List<Forester> report = new ArrayList<>();
        String sql = "SELECT Title + ' ' + p.Id Plant, TaskType FROM ListForesterReports lr " +
                "JOIN ListOwnerTasks lt ON lr.ListOwnerTaskId = lt.Id " +
                "JOIN Plants p ON lt.PlantId = p.ID " +
                "JOIN Tasks t ON lt.TaskId = t.ID " +
                "ORDER BY lr.Id";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery())
        {
            while (resultSet.next()) {
                Forester forester = new Forester();
                forester.setPlant(resultSet.getString("Plant"));
                forester.setTask(resultSet.getString("TaskType"));

                report.add(forester);
            }
            for (Forester f : report) {
                System.out.println(f);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccordingReport(int idReportForester) {
        String extractSql = "SELECT PlantId, TaskId " +
                "FROM ListForesterReports lr " +
                "JOIN ListOwnerTasks lt ON lr.ListOwnerTaskId = lt.Id " +
                "WHERE lr.Id = ?";
        String  landingSql = "UPDATE PlantsDetails SET LangingDate = CURRENT_TIMESTAMP WHERE ID = ?";
        String  artSql = "UPDATE PlantsDetails SET ArtWorkN = ArtWorkN + ? WHERE ID = ?";
        String  treatmentSql = "UPDATE PlantsDetails SET TreatmentN = TreatmentN + ? WHERE ID = ?";
        String  deleteSql = "UPDATE PlantsDetails SET DestructionDate = CURRENT_TIMESTAMP WHERE ID = ?";
        int plantId = -1;
        int taskId = -1;
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement extract = connection.prepareStatement(extractSql);
             PreparedStatement landing = connection.prepareStatement(landingSql);
             PreparedStatement art = connection.prepareStatement(artSql);
             PreparedStatement treatment = connection.prepareStatement(treatmentSql);
             PreparedStatement delete = connection.prepareStatement(deleteSql))
        {
            extract.setLong(1, idReportForester);
            ResultSet resultSet = extract.executeQuery();

            while (resultSet.next()) {
                plantId = resultSet.getInt("PlantId");
                taskId = resultSet.getInt("TaskId");
            }
            if (taskId == 1) {
                landing.setInt(1, plantId);
                landing.executeUpdate();
                System.out.println("Обновления внесены");
            }else if (taskId == 2) {
                art.setInt(1, 1);
                art.setInt(2, plantId);
                art.executeUpdate();
                System.out.println("Обновления внесены");
            }else if (taskId == 3) {
                treatment.setInt(1, 1);
                treatment.setInt(2, plantId);
                treatment.executeUpdate();
                System.out.println("Обновления внесены");
            }else if (taskId == 4) {
                delete.setInt(1, plantId);
                delete.executeUpdate();
                System.out.println("Обновления внесены");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
