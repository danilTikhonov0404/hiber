package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class UserDaoJDBCImpl implements UserDao {

    final Connection connection = Util.getconnection();


    final String cratequery = "CREATE TABLE IF NOT EXISTS `user`.`users`\n" +
            "(`id` INT NOT NULL AUTO_INCREMENT,\n" +
            "`NAME` VARCHAR(45),\n"+
            "`LASTNAME` VARCHAR(45) ,\n " +
            "`AGE` INT, \n" +
            "PRIMARY KEY (`id`));";
    final String dropquery ="DROP TABLE IF EXISTS `users`";
    final String savequery = "INSERT INTO users(name,lastName,age) VALUES (?,?,?)";
    final String removquery ="DELETE FROM users WHERE id=? ";
    final String getallquery = "select * from users";
    final String cleanquery ="DELETE FROM `user`.`users`";
    public void createUsersTable() {
        try(PreparedStatement preparedStatement = connection.prepareStatement(cratequery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void dropUsersTable() {

        try(PreparedStatement preparedStatement = connection.prepareStatement(dropquery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(savequery)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setInt(3,age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {

        try(PreparedStatement preparedStatement = connection.prepareStatement(removquery)) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(getallquery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {

        try(PreparedStatement preparedStatement = connection.prepareStatement(cleanquery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
