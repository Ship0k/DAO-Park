package by.park.dao.service;

import by.park.dao.connect.SQLServer;
import by.park.dao.ForesterDAO;
import by.park.entity.Forester;
import by.park.entity.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLServerForester implements ForesterDAO {

    @Override
    public void viewTask() {
        List<Owner> task = new ArrayList<>();
        String sql = "SELECT lt.Id, Title + ' ' + p.Id Plant, TaskType " +
                "FROM ListOwnerTasks lt " +
                "JOIN Plants p ON lt.PlantId = p.ID " +
                "JOIN Tasks t ON lt.TaskId = t.ID " +
                "ORDER BY lt.Id";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery())
        {
            while (resultSet.next()) {
                Owner owner = new Owner();
                owner.setId(resultSet.getInt("Id"));
                owner.setPlant(resultSet.getString("Plant"));
                owner.setTask(resultSet.getString("TaskType"));

                task.add(owner);
            }
            for (Owner o : task) {
                System.out.println(o);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void performTask(int idOwnerTask) {
        String sql = "INSERT ListForesterReports (OwterTaskId) VALUES (?)";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idOwnerTask);

            preparedStatement.executeUpdate();
            System.out.println("Выполненное задание дабавленно в лист отчет");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewRеportList() {
        List<Forester> report = new ArrayList<>();
        String sql = "SELECT lr.Id, Title + ' ' + p.Id Plant, TaskType " +
                "FROM ListForesterReports lr " +
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
                forester.setId(resultSet.getInt("Id"));
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
    public void updateReport(int idOwnerTask, int foresterReportId) {
        String sql = "UPDATE ListForesterReports SET OwterTaskId = ? WHERE Id = ? ";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idOwnerTask);
            preparedStatement.setInt(2, foresterReportId);

            preparedStatement.executeUpdate();
            System.out.println("Отчет под номером Id=" + foresterReportId +
                    " успешно обнавлен");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReport(int foresterReportId) {
        String sql = "DELETE FROM ListForesterReports WHERE Id = ? ";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, foresterReportId);

            preparedStatement.executeUpdate();
            System.out.println("Отчет под номером Id=" + foresterReportId + " удален");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
