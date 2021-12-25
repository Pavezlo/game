import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Game extends JComponent {
    public static final int FIEELD_EMPTY = 0;//пустое поле
    public static final int FIELD_X = 10;//поле с крестиком
    public static final int FIELD_O = 200;//поле с ноликом
    int[][] field; //объявим двууумерный массив игрового поля
    boolean isXturn;

    public Game(){
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        field = new int[3][3]; //выделяем память под массив для создания компонента
        initGame();
    }

    public void initGame() {
        for(int i=0; i<3; ++i){
            for(int j=0;j<3;++j){
                field[i][j] = FIEELD_EMPTY; // очищаем массив, заполняя его
            }
        }
        isXturn = true;
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if(e.getButton() == MouseEvent.BUTTON1) {//проверяем что нажата клавиша
            int x = e.getX(); //координа х клика
            int y = e.getY(); //координа у клика
            // переводим координаты в индексы в массиве field
            int i = (int) ((float) x/getWidth()*3);
            int j = (int) ((float) y/getHeight()*3);
            //проверяем что выбранная ячейка пуста и в неё можно сходить
            if(field[i][j] == FIEELD_EMPTY){
                //прочерка чей ход, если Х стоваим крестик, если О ставим нолик
                field[i][j] = isXturn?FIELD_X:FIELD_O;
                isXturn = !isXturn; //меняем флаг хода
                repaint(); //проверка компанента, вызавается метод paintComponent()
                int res = checkState();
                if(res!=0){
                    if(res == FIELD_O*3){
                        //победил круужок
                        JOptionPane.showMessageDialog(this, "нолики выиграли!", "Победа!",JOptionPane.INFORMATION_MESSAGE);
                    }else if(res ==FIELD_X*3){
                        //победил крестик
                        JOptionPane.showMessageDialog(this, "крестики выиграли!", "Победа!",JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(this, "Ничья!", "Ничья!",JOptionPane.INFORMATION_MESSAGE);
                    }
                    //перезапускаем игру
                    initGame();
                    //перерисовываем поле
                    repaint();
                }
            }
        }
    }

    void drawXO(Graphics g){
        for (int i=0;i<3;++i){
            for(int j = 0;j<3;++j){
                if(field[i][j] ==FIELD_X){
                    drawX(i,j,g);
                }else if (field[i][j] ==FIELD_O){
                    drawO(i,j,g);
                }
            }
        }
    }

    int checkState(){
        // проверяем диагонали
        int diag = 0;
        int diag2 = 0;
        for (int i = 0; i< 3; i++){
            // сумма значений по дмагонали от левого угла
            diag += field[i][i];
            // сумма значений по диагонали от правого угла
            diag2 += field[i][2-i];
        }
        //если по диагонали стоят одни крестики или одни нолики выходим из метода
        if(diag == FIELD_O * 3||diag == FIELD_X * 3){return diag;}
        //то же саммое для второй диагонали
        if(diag2 == FIELD_O * 3||diag2 == FIELD_X * 3){return diag2;}
        int check_i,check_j;
        boolean hasEmpty = false;
        //будем бегать по всем рядам
        for(int i=0; i<3; i++){
            check_i = 0;
            check_j = 0;
            for(int j=0;j<3;j++){
                //суммируем знаки в текущем ряду
                if(field[i][j] == 0){
                    hasEmpty = true;
                }
                check_j += field[i][j];
                check_i += field[j][i];
            }
            //если выигрыш крестика или нолика, то выходим
            if(check_i == FIELD_O*3||check_i==FIELD_X*3){
                return check_i;
            }

            if(check_j == FIELD_O*3||check_j==FIELD_X*3){
                return check_j;
            }
        }
        if(hasEmpty) return 0; else return -1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //очищаем холст
        g.clearRect(0,0,getWidth(),getHeight());
        //рисуем сетку из линий
        drawGrid(g);
        //ресууем крестики и нолики
        drawXO(g);
    }

    // метод отрисовки поля
    void drawGrid(Graphics g){
        int w = getWidth(); //ширина игрового поля
        int h = getHeight(); //высота игрового поля
        int dw = w/3; //ширина одной ячейки
        int dh = h/3; //высота одной ячейки
        g.setColor(Color.BLUE);
        for(int i = 1; i<3 ; i++){
            g.drawLine(0, dh*i, w, dh*i);
            g.drawLine(dw*i, 0, dw*i, h);
        }
    }

    void drawX(int i, int j, Graphics g){
        g.setColor(Color.BLACK);
        int dw = getWidth()/3;
        int dh = getHeight()/3;
        int x = i* dw;
        int y = j* dh;
        //линия \
        g.drawLine(x,y, x+dw, y+dh);
        //линия /
        g.drawLine(x,y+dh,x+dw,y);
    }

    void drawO(int i, int j, Graphics g){
        g.setColor(Color.BLACK);
        int dw = getWidth()/3;
        int dh = getHeight()/3;
        int x = i* dw;
        int y = j* dh;
        //нолик
        g.drawOval(x+5*dw/100,y,dw*9/10,dh);
    }
}
