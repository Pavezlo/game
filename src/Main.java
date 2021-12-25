import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Запускаем игру");

        JFrame window = new JFrame("Game"); //главное окно
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //обовляем кнопку закрытия окна
        window.setSize(400,400); //размер окна
        window.setLayout(new BorderLayout());//менеджер компоновки
        window.setLocationRelativeTo(null);//что бы окно было по центру экрана
        window.setVisible(true);//включаем видимость окна
        Game game = new Game();
        window.add(game);

        System.out.println("конец");
    }
}
