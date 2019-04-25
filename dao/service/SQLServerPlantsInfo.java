package by.park.dao.service;

import by.park.dao.PlantsInfoDAO;
import by.park.dao.connect.SQLServer;
import by.park.entity.object.PlantDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLServerPlantsInfo implements PlantsInfoDAO {
    @Override
    public void viewAllPlants() {
        List<PlantDetails> infoList = new ArrayList<>();
        String sql = "SELECT Title, LandingDate, ArtWoksN, TreatmentN, DestructionDate " +
                "FROM Plants p " +
                "JOIN PlantsDetails pd ON pd.Id = p.Id " +
                "ORDER BY p.Id";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery())
        {
            while (resultSet.next()) {
                PlantDetails info = new PlantDetails();
                info.setId(resultSet.getInt("Id"));
                info.setTitle(resultSet.getString("Title"));
                info.setLandingData(resultSet.getDate("LandingDate"));
                info.setArtWorkN(resultSet.getInt("ArtWoksN"));
                info.setTreatmentN(resultSet.getInt("TreatmentN"));
                info.setDestructionDate(resultSet.getDate("DestructionDate"));

                infoList.add(info);
            }
            for (PlantDetails d : infoList) {
                System.out.println(d);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewPlantById(int idPlant) {
        String sql = "SELECT p.Id, Title, LandingDate, ArtWoksN, TreatmentN, DestructionDate " +
                "FROM Plants p " +
                "JOIN PlantsDetails pd ON pd.Id = p.Id ";
        PlantDetails info = new PlantDetails();

        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setLong(1, idPlant);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                info.setId(resultSet.getInt("Id"));
                info.setTitle(resultSet.getString("Title"));
                info.setArtWorkN(resultSet.getInt("ArtWoksN"));
                info.setTreatmentN(resultSet.getInt("TreatmentN"));
                info.setLandingData(resultSet.getDate("LandingDate"));
                info.setDestructionDate(resultSet.getDate("DestructionDate"));
            }
            System.out.println(info);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewDestroyedPlants() {
        List<PlantDetails> infoList = new ArrayList<>();
        String sql = "SELECT p.Id, Title, LandingDate, ArtWoksN, TreatmentN, DestructionDate " +
                "FROM Plants p " +
                "JOIN PlantsDetails pd ON pd.Id = p.Id " +
                "WHERE DestructionDate <> 0 " +
                "ORDER BY DestructionDate";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery())
        {
            while (resultSet.next()) {
                PlantDetails info = new PlantDetails();
                info.setId(resultSet.getInt("Id"));
                info.setTitle(resultSet.getString("Title"));
                info.setLandingData(resultSet.getDate("LandingDate"));
                info.setTreatmentN(resultSet.getInt("TreatmentN"));
                info.setArtWorkN(resultSet.getInt("ArtWoksN"));
                info.setDestructionDate(resultSet.getDate("DestructionDate"));

                infoList.add(info);
            }
            for (PlantDetails d : infoList) {
                System.out.println(d);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void numberPlantsByName(String title) {
        int quantity;
        String sql = "SELECT COUNT(Id) Quantity, Title FROM Plants WHERE Title = ? GROUP BY Title";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
             preparedStatement.setString(1, title);
             ResultSet resultSet = preparedStatement.executeQuery();

            quantity = resultSet.getInt("Quantity");
            System.out.println("Растений вида: " + title + " " + quantity + " шт.");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void numberArtProcessed() {
        int quantity;
        String sql = "SELECT SUM(ArtWorkN) Quantity FROM PlantsDetails";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {

            ResultSet resultSet = preparedStatement.executeQuery();

            quantity = resultSet.getInt("Quantity");
            System.out.println("Художественная обработка растений была " +
                    "проведена " + quantity + " раз");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void umberСured() {
        int quantity;
        String sql = "SELECT SUM(TreatmentN) Quantity FROM PlantsDetails";
        try (Connection connection = SQLServer.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {

            ResultSet resultSet = preparedStatement.executeQuery();

            quantity = resultSet.getInt("Quantity");
            System.out.println("Лечение растений было проведено " + quantity + " раз");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
