package robot;

import static java.lang.Thread.sleep;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
//import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;
//import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
//import javax.swing.JTextArea;
import java.awt.GridLayout;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Robot extends JFrame implements KeyListener {
	Timer timer;
	int second=0;
	int minute;
	int max = 0;
    int xEnd;
    int yEnd;
    DecimalFormat dFormat = new DecimalFormat();
    String ddSecond,ddMinute;
	public JPanel cn;
	public JPanel pn;
	Color colorNumber = Color.GREEN; //mau chu
    Color colorDefault = Color.YELLOW; //mau o khong di

    Color colorGo = Color.BLACK; // mau o da di
    Color colorBarricade = Color.BLUE;
    final int maxSize = 1000;
    int col, row; //luu so hang, so cot
    int sum;
    int X0,Y0;
    int X=X0,Y=Y0;
    int xTam, yTam;
    ImageIcon icon = new ImageIcon("E:\\workspace\\java\\robot\\src\\gumby.gif");
    
    JButton matrix[][] = new JButton[maxSize][maxSize]; //tao mang nut ma tran cho nguoi hoi
    boolean check[][] = new boolean[maxSize][maxSize]; //tao mang de danh dau da di
    boolean checkMax[][] = new boolean[maxSize][maxSize]; //tao mang de danh dau da di
    boolean checkDefault[][] = new boolean[maxSize][maxSize];
    int values[][] = new int[maxSize][maxSize];
    
    ArrayList<Integer> myList = new ArrayList<Integer>();
    ArrayList<String> myString = new ArrayList<String>();
    JButton size;
    JFrame frame = new JFrame();
    
    JLabel Time = new JLabel("set limit time");
    
	/**
	 * Launch the application.
	 */
    public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Robot frame = new Robot("Test");
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Robot(String tittle) {
		super(tittle);
		loadFile("/robot/src/robot/matrix.txt");
        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 723, 526);
		cn = new JPanel();
		cn.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(cn);
		cn.setLayout(null);
		
		X= X0; 
		Y=Y0;
		
		
        
		JLabel lblGame = new JLabel("Game");
		lblGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblGame.setFont(new Font("Arial", Font.BOLD, 34));
		lblGame.setBounds(556, 10, 121, 50);
		cn.add(lblGame);
		
		JButton Backtracking = new JButton("Vét cạn");
		Backtracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Backtracking();
			}
		});
		Backtracking.setFont(new Font("Arial", Font.PLAIN, 30));
		Backtracking.setForeground(new Color(0, 0, 0));
		Backtracking.setBounds(538, 270, 161, 39);
		cn.add(Backtracking);
		
		JButton btnNewButton_1 = new JButton("GREEDY");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				greedy();
			}
		});
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 30));
		btnNewButton_1.setBounds(538, 335, 161, 39);
		cn.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Reset");
		btnNewButton_2.setFont(new Font("Arial", Font.PLAIN, 30));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDefault();
			}
		});
		btnNewButton_2.setBounds(538, 398, 161, 39);
		cn.add(btnNewButton_2);
		
		
		Time.setText("0:30");
		Time.setHorizontalAlignment(SwingConstants.CENTER);
		Time.setFont(new Font("Arial", Font.PLAIN, 30));
		Time.setBounds(556, 99, 107, 50);
		minute = 0;
		second = 30;
        
        
		cn.add(Time);
		
		JButton buttonPlay = new JButton("Play");
		
		buttonPlay.setFont(new Font("Arial", Font.BOLD, 30));
		buttonPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel pn = new JPanel();
				pn.removeAll();
				pn.setLayout(new GridLayout(3, 3, 0, 0));
				pn.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				pn.setBounds(10, 10, 518, 458);
				
				for (int i=0; i<col;i++){
		            for (int j=0; j<row;j++){
//		            	sum += getNumber(X0, Y0);
		                check[i][j] = false;
		                checkDefault[i][j] = false;
		                matrix[i][j] = new JButton(String.valueOf(values[i][j]));
		                matrix[i][j].setForeground(colorNumber);
		                matrix[i][j].setBackground(colorDefault);
		                matrix[i][j].setFont(new Font("Arial",Font.BOLD,20));
		                matrix[i][j].addKeyListener(new KeyAdapter() {
		        			@Override
		        			public void keyPressed(KeyEvent e) {
		        				if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
		        		            System.exit(0);
		        		        if (e.getKeyCode()==KeyEvent.VK_DOWN){//nhan xuong
		        		            if (X<col-1 && check[X+1][Y] == false){
		        		                X++;
		        		                updateColor(X,Y);
		        		                check[X][Y] = true;
		        		                sum+=getNumber(X, Y);
		        		            }
		        		            else {
		        		            	sum += getNumber(X0, Y0);
		        		            	timer.stop();
		        		            	dialogResult(sum);
		        		            }
		        		        }
		        		        if (e.getKeyCode()==KeyEvent.VK_RIGHT){//nhan nut phai
		        		            if (Y<row-1 && check[X][Y+1] == false){
		        		                Y++;
		        		                updateColor(X,Y);
		        		                check[X][Y] = true;
		        		                sum+=getNumber(X, Y);
		        		            }
		        		            else {
		        		            	sum += getNumber(X0, Y0);
		        		            	timer.stop();
		        		            	dialogResult(sum);
			        		        	
		        		            }
		        		        }
		        		        if (e.getKeyCode()==KeyEvent.VK_LEFT){ //nhan nut trai
		        		            if (Y>0 && check[X][Y-1] == false){
		        		                Y--;
		        		                updateColor(X,Y);
		        		                check[X][Y] = true;
		        		                sum+=getNumber(X, Y);
		        		            }
		        		            else {
		        		            	sum += getNumber(X0, Y0);
		        		            	timer.stop();
		        		            	dialogResult(sum);
			        		        	
		        		            }
		        		        }
		        		        if(e.getKeyCode()==KeyEvent.VK_UP){ //nhan nut len
		        		            if (X>0 && check[X-1][Y] == false){
		        		                X--;
		        		                updateColor(X,Y);
		        		                check[X][Y] = true;
		        		                sum+=getNumber(X, Y);
		        		            }
		        		            else {
		        		            	sum += getNumber(X0, Y0);
		        		            	timer.stop();
		        		            	dialogResult(sum);
			        		        	
		        		            }
		        		        }
//		        		        else if (X>0 && check[X-1][Y] == true &&Y>0 && check[X][Y-1] == true &&  Y<row-1 && check[X][Y+1] == true &&X<col-1 && check[X+1][Y] == true ) {
//		        		        	dialogResult(sum);
//		        		        	timer.stop();
//		        		        }
		        		      
		        		        if (X == xEnd-1 && Y == yEnd-1) {
		        		        	sum += getNumber(X0, Y0);
		        		        	timer.stop();
		        		        	dialogResult(sum);
		        		        	
		        		        }
		        		        
		        			}
		        		});
		                
		                if (getNumber(i,j)==-1){
		                    matrix[i][j].setIcon(icon);
		                    matrix[i][j].setBackground(colorBarricade);
		                    matrix[i][j].setText("");
		                    check[i][j] = true;
		                    checkDefault[i][j] = true;

		                }
		                pn.add(matrix[i][j]);
		                cn.setFocusable(false);
		                pn.setFocusable(true);
		                pn.setRequestFocusEnabled(true);
		                pn.requestFocus();
		            }
		        }
				matrix[X0][Y0].setBackground(colorGo);
//				check[X0][Y0] = true;
				setLocationRelativeTo(null);
				setVisible(true);
				cn.add(pn);
				
				countDownTimer();
		        timer.start();
			}
		});
		buttonPlay.setBounds(538, 203, 161, 39);
		cn.add(buttonPlay);
//		setLocationRelativeTo(null);
//		setVisible(true);
		
	}


	public void greedy(){
		
        if(check[X0][Y0] == true){
            dialogResult(0);
        }else {
        	sum = getNumber(X0, Y0);
        	while (X <col && Y <row){
                check[X0][Y0] = true;
                
                int x=0, y=0;
                int maxX = 0, maxY = 0;
                int maximum = 0;
                boolean to=false; //kiem tra neu ca 4 phia deu bi chan thi ket thuc
                for (int i = 0; i < 4; i++) {
                    switch (i) {
                        case 0: {
                            x = X + 1;
                            y = Y;
                            if (X < col - 1 && check[x][y] == false) {
                                if (maximum < getNumber(x, y)) {
                                    maximum = getNumber(x, y);
                                    maxX = x;
                                    maxY = y;
                                    to = true;
                                }
                            }
                        }
                        case 1: {
                            x = X;
                            y = Y + 1;
                            if (Y < row - 1 && check[x][y] == false) {
                                if (maximum < getNumber(x, y)) {
                                    maximum = getNumber(x, y);
                                    maxX = x;
                                    maxY = y;
                                    to = true;
                                }
                            }
                        }
                        case 2: {
                            x = X - 1;
                            y = Y;
                            if (X > 0 && check[x][y] == false) {
                                if (maximum < getNumber(x, y)) {
                                    maximum = getNumber(x, y);
                                    maxX = x;
                                    maxY = y;
                                    to = true;
                                }
                            }
                        }
                        case 3: {
                            x = X;
                            y = Y - 1;
                            if (Y > 0 && check[x][y] == false) {
                                if (maximum < getNumber(x, y)) {
                                    maximum = getNumber(x, y);
                                    maxX = x;
                                    maxY = y;
                                    to = true;
                                }
                            }
                        }
                    }
                }
                if (to==false) {
                    dialogResult(0);
                    break;
                }

                X = maxX;
                Y = maxY;
                sum = sum + maximum;
                updateColor(X, Y);
                check[X][Y] = true;
                if (X == xEnd - 1 && Y == yEnd - 1){
                    dialogResult(sum);
                    break;
                }
                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                }
        }
            
        }
    }

    public  void Step(int x, int y,String s){
    	if (check[xEnd][yEnd]==true)
            return;
        if (x<col-1 && check[x+1][y] == false){ //xuong
            check[x][y] = true;
            sum += getNumber(x,y);
            Step(x+1,y,s+"D ");
            sum -= getNumber(x,y);
            check[x][y] = false;
        }
        if (y<row-1 && check[x][y+1] == false){ //qua phai
            check[x][y] = true;
            sum += getNumber(x,y);
            Step(x,y+1,s+"R ");
            sum -= getNumber(x,y);
            check[x][y] = false;
        }
        if (y>0 && check[x][y-1] == false){ // qua trai
            check[x][y] = true;
            sum += getNumber(x,y);
            Step(x,y-1,s+"L ");
            sum -= getNumber(x,y);
            check[x][y] = false;
        }
        if (x>0 && check[x-1][y] == false){ // len
            check[x][y] = true;
            sum += getNumber(x,y);
            Step(x-1,y,s+"U ");
            sum -= getNumber(x,y);
            check[x][y] = false;
        }
        
        if (x == xEnd-1 && y == yEnd-1){
            sum += getNumber(xEnd-1,yEnd-1);

            myList.add(sum);
            myString.add(s);

            sum -= getNumber(xEnd-1,yEnd-1);

            return;
        }
    }
    public void Backtracking(){
        myList.clear();
        int x=X0;
        int y=Y0;
        if(check[X0][Y0] == true) dialogResult(0);
        else{
            Step(X0,Y0,"");
            if(myList.isEmpty() == false) {
                int MAX = Collections.max(myList);
                String s = new String();
                s = myString.get(myList.indexOf(MAX));
                String[] result = s.split(" ");

                for (String str : result) {
                    switch (str) {
                        case "D": {
                            x++;
                            matrix[x][y].setBackground(colorGo);
                            continue;
                        }
                        case "R": {
                            y++;
                            matrix[x][y].setBackground(colorGo);
                            continue;
                        }
                        case "L": {
                            y--;
                            matrix[x][y].setBackground(colorGo);
                            continue;
                        }
                        case "U": {
                            x--;
                          
                            matrix[x][y].setBackground(colorGo);
                            continue;
                        }
                    }
                }
                System.out.println(MAX);
                dialogResult(MAX);
            }
            else {
                dialogResult(0);
            }
        }
    }
    public  void display(){ //hien thi ma tran de kiem tra
        for(int i=0;i<col;i++){
            for(int j=0;j<row;j++){
                System.out.print(checkMax[i][j] +"\t");
            }
            System.out.println();
        }
    }
	
	public void keyPressed(KeyEvent e){
//		    System.out.println("Key pressed: " + e.getKeyCode()); 
		
        if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
            System.exit(0);
        if (e.getKeyCode()==KeyEvent.VK_DOWN){//nhan xuong
            if (X<col-1 && check[X+1][Y] == false){
                X++;
                updateColor(X,Y);
                check[X][Y] = true;
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT){//nhan nut phai
            if (Y<row-1 && check[X][Y+1] == false){
                Y++;
                updateColor(X,Y);
                check[X][Y] = true;
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT){ //nhan nut trai
            if (Y>0 && check[X][Y-1] == false){
                Y--;
                updateColor(X,Y);
                check[X][Y] = true;
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_UP){ //nhan nut len
            if (X>0 && check[X-1][Y] == false){
                X--;
                updateColor(X,Y);
                check[X][Y] = true;
            }
        }
    }

	public void dialogResult(int result){
        JDialog dialog = new JDialog(frame, "Result", true);
        JPanel mainGui = new JPanel(new BorderLayout());

        mainGui.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel Result = new JLabel();
        if(result == 0){
        	Result.setText("Khong co duong di !!!");
            Result.setFont(new Font("Arial",Font.BOLD,20));
        }
        else if (result == -1) {
        	Result.setText("Het thoi gian !!!");
            Result.setFont(new Font("Arial",Font.BOLD,20));
        }
        else {
        	Result.setText(String.valueOf(result));
            Result.setFont(new Font("Arial",Font.BOLD,70));
            
        }

        mainGui.add(Result, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        mainGui.add(buttonPanel, BorderLayout.SOUTH);
        JButton close = new JButton("Close");
        close.addActionListener(e->dialog.setVisible(false));
        buttonPanel.add(close);
        mainGui.setVisible(true);
        dialog.setContentPane(mainGui);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
	public void updateColor(int xNew,int yNew){
        matrix[xNew][yNew].setBackground(colorGo);
    }
	
	public int getNumber(int x, int y){
        return Integer.valueOf(matrix[x][y].getText());
    }
	
	public void loadFile(String fileName){
        try {
            File myObj = new File("src/robot/matrix.txt");
            Scanner myReader = new Scanner(myObj);
            ImageIcon icon = new ImageIcon("src/gumby.gif"); //dua icon vao day
            String data = myReader.nextLine();
            String list[] = data.split(" ");
            row =  Integer.valueOf(list[0]);
            col =  Integer.valueOf(list[1]);

            X0 = Integer.valueOf(list[2]);
            Y0 = Integer.valueOf(list[3]);
            xEnd = Integer.valueOf(list[4]);
            yEnd = Integer.valueOf(list[5]);
            xTam = X0;
            yTam = Y0;
            System.out.println(col);
            int i =0;
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                list = data.split(" ");
                for (int j =0; j<row; j++){
                    values[i][j] = Integer.valueOf(list[j]);
                }
                i++;
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
	public void setDefault(){
    	X =xTam;
    	Y=yTam;
    	sum =0;
        for (int i=0;i<col;i++)
            for (int j=0;j<row;j++){
                if (checkDefault[i][j] == false) {
                	matrix[i][j].setBackground(colorDefault);
                	check[i][j] = false;
                }else
                	check[i][j] = true;
            }
        matrix[X0][Y0].setBackground(colorGo);
//        check[X0][Y0] = true;
    }
    public static int getRandomNumber(int a, int b){ //ham tao ngau nhien 1 so tu a-> b
        Random random = new Random();
        return a + random.nextInt(b);
    }
    public void keyReleased(KeyEvent e){
    }
    public void keyTyped(KeyEvent e) {
    }
    public void countDownTimer() {
    	timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				second--;
				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);
				Time.setText(ddMinute + ":"+ddSecond);
				if (second == -1) {
					second = 59;
					minute--;
					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);
					Time.setText(ddMinute + ":"+ddSecond);
				}
				if (minute ==0 && second ==0) {
					timer.stop();
					dialogResult(-1);
				}
			}
    	});
    }
}
