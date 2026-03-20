import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "RIBERA";
        String password = "ribera";

        int opcion2;

        do {
            System.out.println("==============================");
            System.out.println("\nMenuCRUD");
            System.out.println("==============================");
            System.out.println("1. Insertar empleado");
            System.out.println("2. Mostrar empleados");
            System.out.println("3. Actualizar empleado");
            System.out.println("4. Eliminar empleado");
            System.out.println("0. Salir");

            opcion2 = teclado.nextInt();
            teclado.nextLine();

            switch (opcion2) {

                // INSERT
                case 1:
                    System.out.print("ID: ");
                    int id = teclado.nextInt();
                    teclado.nextLine();

                    System.out.print("Nombre: ");
                    String nombre = teclado.nextLine();

                    System.out.print("Salario: ");
                    double salario = teclado.nextDouble();

                    try (Connection conn = DriverManager.getConnection(url, user, password)) {
                        String sql = "INSERT INTO empleado2 (ID, NOMBRE, SALARIO) VALUES (?, ?, ?)";
                        PreparedStatement ps = conn.prepareStatement(sql);

                        ps.setInt(1, id);
                        ps.setString(2, nombre);
                        ps.setDouble(3, salario);

                        ps.executeUpdate();
                        System.out.println("Empleado insertado correctamente");

                    } catch (SQLException e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;

                // SELECT
                case 2:
                    try (Connection conn = DriverManager.getConnection(url, user, password);
                         Statement st = conn.createStatement()) {

                        ResultSet rs = st.executeQuery("SELECT * FROM empleado2");

                        while (rs.next()) {
                            System.out.println("ID: " + rs.getInt("ID") +
                                    " Nombre: " + rs.getString("NOMBRE") +
                                    " Salario: " + rs.getDouble("SALARIO"));
                        }

                    } catch (SQLException e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;

                // UPDATE
                case 3:
                    System.out.print("Nombre del empleado a actualizar: ");
                    String nombreAct = teclado.nextLine();

                    System.out.print("Nuevo salario: ");
                    double nuevoSalario = teclado.nextDouble();

                    try (Connection conn = DriverManager.getConnection(url, user, password)) {
                        String sql = "UPDATE empleado2 SET SALARIO=? WHERE NOMBRE=?";
                        PreparedStatement ps = conn.prepareStatement(sql);

                        ps.setDouble(1, nuevoSalario);
                        ps.setString(2, nombreAct);

                        int filas = ps.executeUpdate();
                        System.out.println("Filas actualizadas: " + filas);

                    } catch (SQLException e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;

                // DELETE
                case 4:
                    System.out.print("Nombre del empleado a eliminar: ");
                    String nombreDel = teclado.nextLine();

                    try (Connection conn = DriverManager.getConnection(url, user, password)) {
                        String sql = "DELETE FROM empleado2 WHERE NOMBRE=?";
                        PreparedStatement ps = conn.prepareStatement(sql);

                        ps.setString(1, nombreDel);

                        int filas = ps.executeUpdate();
                        System.out.println("Filas eliminadas: " + filas);

                    } catch (SQLException e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida");
            }

        } while (opcion2 != 0);
    }
}