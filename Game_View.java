package project;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import project.star.*;

public class Game_View extends JFrame {
    static final int SIZE = 500;
    static Color[][][] colorArray = new Color[SIZE][SIZE][SIZE];
    static Color[][] colorPrint = new Color[SIZE][SIZE];

    static int radius = 10; // 반지름
    static int width = 500;
    static int height = 500;
    static String[] str = { "X", "O" }; // 콘솔에서 평면으로 출력할 때에 표시할 기호

    // 시작 좌표
    int startX = 250;
    int startY = 250;
    int startZ = 250;

    static int visualRange = 50; // 시야
    static String[] XYdirection = { "X", "-Y", "-X", "Y" };
    static String[] Zdirection = {"Z", "Zero_Point", "-Z", "Zero_Point"};
    int XYcount = 0, Zcount = 0;
    static String select = "X";
    static int angle = 0; // 각도

    static Graphics buffG;
    Image buffImg;

    Toolkit imageTool = Toolkit.getDefaultToolkit();
    Image img = imageTool.getImage("src/project/resource/cursor.png");
    int xpos = 500;
    int ypos = 500;

    Game_View() {
        setTitle("JFrame 테스트"); // 프레임 제목 설정.
        setSize(SIZE, SIZE); // 프레임의 크기 설정.
        setLocationRelativeTo(null);// 창이 가운데 나오게
        setResizable(false); // 프레임의 크기 변경 못하게 설정.
        setLayout(null);
        setVisible(true); // 프레임 보이기;
        customcursor(); // 커서 숨기기
        setDefaultCloseOperation(EXIT_ON_CLOSE); // 프레임의 x버튼 누르면 프로세스 종료.

        // 마우스 감지
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                xpos = e.getX() - 10;
                ypos = e.getY() - 10;
                if (xpos > 250) {
                    System.out.print("오른쪽 ");
                    if (ypos > 250) {
                        System.out.print("오른쪽 아래 ");
                    } else {
                        System.out.print("오른쪽 위 ");
                    }
                } else if (xpos < 250) {
                    System.out.print("왼쪽 ");
                    if (ypos < 250) {
                        System.out.print("왼쪽 위 ");
                    } else {
                        System.out.print("왼쪽 아래 ");
                    }
                }
                System.out.println("거리: " + getDistance(xpos, ypos, 250, 250));
            }
        });

        addMouseListener(new MouseListener() {
            // 마우스가 해당 컴포넌트를 클릭
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            // 마우스가 해당 컴포넌트 영역 안으로 들어올때 발생
            @Override
            public void mouseEntered(MouseEvent e) {
            }

            // 마우스가 해당 컴포넌트 영역 밖으로 나갈때 발생
            @Override
            public void mouseExited(MouseEvent e) {
            }

            // 마우스 버튼을 누를때 발생
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.print("마우스 눌림 " + e.getX() + ":" + e.getY() + " ");
                System.out.println("현재 좌표: " + startX + ":" + startY + ":" + startZ + " 현재 시야: " + visualRange);
            }

            // 눌러진 마우스를 놓을때 발생
            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });

         // 키보드 감지
         addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (visualRange >= 0 && visualRange <= colorArray.length) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_W:
                            if (startX < colorArray.length && startY < colorArray.length && startZ < colorArray.length) {
                                if (select.equals("Z")) {
                                    startZ++;
                                } else if (select.equals("Y")) {
                                    startY++;
                                } else if (select.equals("X")) {
                                    startX++;
                                }
                                // printRange2D(visualRange); // 콘솔창 테스트
                                insertRect(startX, startY, startZ, visualRange);
                                // sum2DArray(visualRange);
                            }
                            if (startX > 0 && startY > 0 && startZ > 0) {
                                if (select.equals("-Z")) {
                                    startZ--;
                                } else if (select.equals("-Y")) {
                                    startY--;
                                } else if (select.equals("-X")) {
                                    startX--;
                                }
                                // printRange2D(visualRange); // 콘솔창 테스트
                                insertRect(startX, startY, startZ, visualRange);
                                // sum2DArray(visualRange);
                            }
                            break;
                        case KeyEvent.VK_D:
                            if (select.equals("Y")) {
                                XYcount = 0;
                            } else {
                                XYcount++;
                            }
                            select = XYdirection[XYcount];
                            System.out.println("오른쪽으로 회전");
                            insertRect(startX, startY, startZ, visualRange);
                            // sum2DArray(visualRange);
                            break;
                        case KeyEvent.VK_A:
                            if (select.equals("X")) {
                                XYcount = XYdirection.length - 1;
                            } else {
                                XYcount--;
                            }
                            select = XYdirection[XYcount];
                            System.out.println("왼쪽으로 회전");
                            insertRect(startX, startY, startZ, visualRange);
                            // sum2DArray(visualRange);
                            break;
                        case KeyEvent.VK_E:
                            if (select.equals("Z")) {
                                Zcount = Zdirection.length - 1;
                            } else {
                                Zcount++;
                            }
                            select = Zdirection[Zcount];
                            System.out.println("위로 회전");
                            insertRect(startX, startY, startZ, visualRange);
                            // sum2DArray(visualRange);
                            break;
                        case KeyEvent.VK_C:
                            if (select.equals("-Z")) {
                                Zcount = Zdirection.length - 1;
                            } else {
                                Zcount--;
                            }
                            select = XYdirection[XYcount];
                            System.out.println("아래쪽으로 회전");
                            insertRect(startX, startY, startZ, visualRange);
                            // sum2DArray(visualRange);
                            break;
                        default:
                            System.out.println("w 전진 | D 오른쪽 회전 | S 왼쪽 회전 | E 위로 | C 아래로");
                            break;
                    }
                    System.out.println("현재 좌표: " + startX + ":" + startY + ":" + startZ + " 현재 시야: " + visualRange);
                }
            }
        });
    }

    // 정육면체의 절단면 출력
    public static void insertRect(int x, int y, int z, int range) {
        System.out.println("현재 보는 방향:" + select + " 현재 시야: " + range);

        for (int a = 0; a < colorPrint.length; a++) {
            for (int b = 0; b < colorPrint[a].length; b++) {
                if (select.equals("Z")) {
                    colorPrint[a][b] = colorArray[a][b][z - range];
                } else if (select.equals("-Z")) {
                    colorPrint[a][b] = colorArray[a][b][z];
                } else if (select.equals("X")) {
                    colorPrint[a][b] = colorArray[x - range][a][b];
                } else if (select.equals("-X")) {
                    colorPrint[a][b] = colorArray[x][a][b];
                } else if (select.equals("Y")) {
                    colorPrint[a][b] = colorArray[a][y - range][b];
                } else if (select.equals("-Y")) {
                    colorPrint[a][b] = colorArray[a][y][b];
                } 
            }
        }
    }

    // 3차원 배열 검은색으로 채움
    public static void paintView() {
        for (int x = 0; x < colorArray.length; x++) {
            for (int y = 0; y < colorArray[x].length; y++) {
                for (int z = 0; z < colorArray[x][y].length; z++) {
                    colorArray[x][y][z] = Color.black;
                }
            }
        }
    }

    // 화면 출력
    public static void printView() {
        for (int x = 0; x < colorPrint.length; x++) {
            for (int y = 0; y < colorPrint[x].length; y++) {
                colorPrint[x][y] = Color.black;
            }
        }
    }

    // 구 삽입 함수
    public static void insertSphere(int x, int y, int z, int r, Color color) {
        for (int a = 0; a < colorArray.length; a++) {
            for (int b = 0; b < colorArray[a].length; b++) {
                for (int c = 0; c < colorArray[a][b].length; c++) {
                    if (getDistance(x, y, z, a, b, c) <= r) {
                        colorArray[a][b][c] = color;
                    }
                }
            }
        }
    }

    // 3차원 배열에서 두 좌표간의 거리를 재는 함수
    public static int getDistance(int x1, int y1, int z1, int x2, int y2, int z2) {
        int d;
        int xd, yd, zd;
        xd = (int) Math.pow((x2 - x1), 2);
        yd = (int) Math.pow((y2 - y1), 2);
        zd = (int) Math.pow((z2 - z1), 2);
        d = (int) Math.sqrt(yd + xd + zd);
        return d;
    }

    // 2차원 배열에서 두 좌표간의 거리를 재는 함수
    static int getDistance(int x, int y, int x1, int y1) {
        int d;
        int xd, yd;
        yd = (int) Math.pow((y1 - y), 2);
        xd = (int) Math.pow((x1 - x), 2);
        d = (int) Math.sqrt(yd + xd);
        return d;
    }

    // 시야에 해당하는 거리만큼 2차원배열로 출력
    public static void printRange2D(int range) {
        for (int x = 0; x < colorPrint.length; x++) {
            for (int y = 0; y < colorPrint[x].length; y++) {
                if (!(Integer.toHexString(colorPrint[x][y].getRGB()).equals("ff000000"))) { // ff000000 = Color.black
                    System.out.print(str[1] + " ");
                } else {
                    System.out.print(str[0] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("현재: " + select);
    }

    // range에 해당하는 2차원 배열 합치기
    public static void sum2DArray(int count) {
        for (int x = 0; x < colorPrint.length; x++) {
            for (int y = 0; y < colorPrint[x].length; y++) {
                colorPrint[x][y] = intPrint(x, y, count);
            }
        }
    }

    // count 수 만큼 절단면들의 합
    public static Color intPrint(int x, int y, int count) {
        if (count == 0) {
            return colorArray[visualRange][x][y];
        } else if (count == 1) {
            return colorArray[visualRange + 1][x][y];
        } else {
            return sumColor(intPrint(x, y, count - 2), intPrint(x, y, count - 1));
        }
    }

    // 색 합치기
    public static Color sumColor(Color color1, Color color2) {
        String[] strColor = new String[3];
        String str = "#";
        strColor[0] = Integer
                .toHexString((color1.getRed() + color2.getRed()) >= 255 ? 255 : (color1.getRed() + color2.getRed()));
        strColor[1] = Integer.toHexString(
                (color1.getGreen() + color2.getGreen()) >= 255 ? 255 : (color1.getGreen() + color2.getGreen()));
        strColor[2] = Integer.toHexString(
                (color1.getBlue() + color2.getBlue()) >= 255 ? 255 : (color1.getBlue() + color2.getBlue()));
        for (String s : strColor) {
            if (s.equals("0"))
                s = "00";
            str += s;
        }

        return Color.decode(str);
    }

    // 배경 프레임 크기만큼 픽셀을 찍음
    public static void drawRect(Color[][] color) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buffG.setColor(color[i][j]);
                buffG.fillRect(i, j, 1, 1); // 배경 프레임 크기만큼
            }
        }
    }

    // 커서 숨기기
    public void customcursor() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image cursorimage = tk.getImage("");
        Point point = new Point(20, 20);
        Cursor cursor = tk.createCustomCursor(cursorimage, point, "cursor");
        setCursor(cursor);
    }

    @Override
    public void paint(Graphics g) {
        buffImg = createImage(getWidth(), getHeight()); // 버퍼링용 이미지 ( 도화지 )
        buffG = buffImg.getGraphics(); // 버퍼링용 이미지에 그래픽 객체를 얻어야 그릴 수 있다고 한다. ( 붓? )
        update(g);
    }

    @Override
    public void update(Graphics g) {
        buffG.clearRect(0, 0, 854, 480); // 백지화
        drawRect(colorPrint);
        buffG.drawImage(img, xpos, ypos, this); // cursor 그리기
        g.drawImage(buffImg, 0, 0, this); // 화면g 버퍼(buffG)에 그려진 이미지(buffImg)옮김. ( 도화지에 이미지를 출력 )
        repaint();
    }

    public static void main(String[] args) {
        // 행성
        Sun sun = new Sun(200, "태양", Color.decode("#ff8c00"));
        Mercury mercury = new Mercury(3, "수성", Color.decode("#a9a9a9"));
        Venus venus = new Venus(10, "금성", Color.decode("#f0e68c"));
        Earth earth = new Earth(10, "지구", Color.decode("#1e90ff"));
        Mars mars = new Mars(7, "화성", Color.decode("#daa520"));
        // Asteroid asteroid = new Asteroid(2, "소행성", Color.decode("#778899"));
        Jupiter jupiter = new Jupiter(90, "목성", Color.decode("#d3d3d3"));
        Saturn saturn = new Saturn(70, "토성", Color.decode("#f0e68c"));
        Uranus uranus = new Uranus(40, "천왕성", Color.decode("#e0ffff"));
        Neptune neptune = new Neptune(40, "해왕성", Color.decode("#00bfff"));
        Pluto pluto = new Pluto(40, "명왕성", Color.decode("#cd853f"));
        new Game_View();
        paintView(); // 3차원 배열 검은색으로 채움

        // 구 3차원 배열에 삽입
        // 태양계
        insertSphere(1, 1, 1, sun.radius, sun.color);
        insertSphere(50, 50, 50, mercury.radius, mercury.color);
        insertSphere(60, 60, 60, venus.radius, venus.color);
        insertSphere(80, 80, 80, earth.radius, earth.color);
        insertSphere(100, 100, 100, mars.radius, mars.color);
        /* insertSphere(90, 95, 95, asteroid.radius, asteroid.color);
        insertSphere(80, 100, 90, asteroid.radius, asteroid.color);
        insertSphere(80, 90, 90, asteroid.radius, asteroid.color);
        insertSphere(80, 90, 90, asteroid.radius, asteroid.color);
        insertSphere(80, 90, 90, asteroid.radius, asteroid.color);
        insertSphere(80, 90, 90, asteroid.radius, asteroid.color); */
        insertSphere(200, 200, 200, jupiter.radius, jupiter.color);
        insertSphere(250, 250, 250, saturn.radius, saturn.color);
        insertSphere(300, 300, 300, uranus.radius, uranus.color);
        insertSphere(350, 350, 350, neptune.radius, neptune.color);
        insertSphere(400, 400, 400, pluto.radius, pluto.color);

        printView(); // 구를 넣은 뷰 화면에 출력
    }
}
