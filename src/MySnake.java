//import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
/**
 * Змейка (Snake) — компьютерная игра, возникшая в середине 1970-х.
 * <p>
 * Игрок управляет длинным, тонким существом, напоминающим змею,
 * которое ползает по плоскости, собирая еду (яблоки),
 * избегая столкновения с собственным хвостом и краями игрового поля.
 * Каждый раз, когда змея съедает пищу, она становится длиннее,
 * что постепенно усложняет игру.
 * Игрок управляет направлением движения головы змеи (вверх, вниз,
 * влево, вправо), а хвост змеи движется следом.
 * Игрок не может остановить движение змеи.
 * @author      Pavel Shiryaev
 */

public class MySnake extends Panel 
                  implements KeyListener, FocusListener, MouseListener {
	private static final long serialVersionUID = 1L; // Чтобы не ругался.
        private static final int FIELD_WIDTH = 656;
        private static final int FIELD_HEIHGT = 470;
		Graphics g;
		Choice ch;
		String s;
		Button b;
		Label l;
		Font f = new Font("MyFont",Font.BOLD | Font.ITALIC, 15);
		int level=0, nBGColor;
		int height, width, pheight, pwidth;
		MyData mdata;
                long rc = 0;
		boolean lf= false, flag = true, focussed = false;   // True when this applet has input focus.
    Snake t1;
    Apples t2;
    
//    
//@Override
//public void MySnake() {
//    super();''
//            
//    
//}

public static void main(String[] args) {
  JFrame f = new JFrame();
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  f.addWindowListener(new java.awt.event.WindowAdapter() {
       public void windowClosing(java.awt.event.WindowEvent e) {
       System.exit(0);
       };
     });

  MySnake ms = new MySnake(FIELD_WIDTH,FIELD_HEIHGT);
//  ms.setLayout(new BorderLayout());
  //ms.setSize(WIDTH,HEIHGT); // same size as defined in the HTML APPLET
  f.add(ms);
  f.pack();
  f.setLocationRelativeTo(null);  // *** this will center your app ***
  ms.init();
  f.setSize(FIELD_WIDTH,FIELD_HEIHGT+20); // add 20, seems enough for the Frame title,
  //f.show();
  f.setVisible(true);
  ms.start();
  }
    private boolean inited = false;

    private MySnake(int width, int height) {
        super();
        this.setSize(width, height);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   public  void init() {
		setBackground(Color.orange);
		g=getGraphics();
		
		height = FIELD_HEIHGT;//getSize().height;
		width  = FIELD_WIDTH;//getSize().width;
		pwidth = (int) (width-5)/31;
		pheight = (int) (height-5)/31;
		showStatus(pwidth+" x "+pheight);

		mdata = new MyData(pwidth,pheight);
		t1 = new Snake(mdata);
		t2 = new Apples(mdata);
		mdata.setThreads(t1, t2);
		
		mdata.apple= getToolkit().getImage(this.getClass().getResource("apple.gif"));
		mdata.head = getToolkit().getImage(this.getClass().getResource("head.gif"));
		mdata.body = getToolkit().getImage(this.getClass().getResource("body.gif"));

		mdata.setGraphics(g.create(3,3,width-6,height-6));
		mdata.IsGame(false);

		addFocusListener(this);
		addMouseListener(this);
		addKeyListener(this);

        	//setLayout(null);
    	
		level=0;
		ch = new Choice();
		ch.add("Easy");
		ch.add("Hard");
		ch.add("Pipe");
		ch.add("Fanny");
		ch.add("New");
		ch.add("Super");
		ch.select("Easy");
		ch.setBackground(Color.orange);

		add(ch);	
   		ch.addItemListener (new ItemListener() {
			public void itemStateChanged (ItemEvent e) {
			    level=ch.getSelectedIndex();
		}});

		l = new Label ("Choose the level:", Label.CENTER);
		l.setFont(f);
		
		b = new Button ("GO!");
		b.setBackground(Color.magenta);
		b.setForeground(Color.yellow);
		b.setFont(f);

		b.addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				mdata.IsGame(true);
		}});

		add(l);
		add(b);
//		add(b, BorderLayout.SOUTH);
//		add(b, BorderLayout.SOUTH);
		 l.setBounds(pwidth*15-55, pheight*15-60, 150,25);
		ch.setBounds(pwidth*15-15, pheight*15-25, 70, 25);
		 b.setBounds(pwidth*15-10, pheight*15+5,    50, 25);
                inited = true;
		repaint();





		
	}
   public void start() {
		
////////////////////////
/*
	BEGIN DEBUG CODE
	    int x, y;
        while (mdata.isgame) {
			x = (int) (mdata.width * Math.random());
			y = (int) (mdata.height * Math.random());
			g.setColor(Color.red);
			g.fillOval(x*31, y*31, 30, 30);
            try {Thread.sleep(400);}
            catch(InterruptedException e) {}
        }
	 END  DEBUG CODE
*/
if (flag) {
    flag=false;

		while (!mdata.isgame) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
		}

		remove(b);
		remove(ch);
		remove(l);
		
		mdata.setLevel(level);

		requestFocus();

		t1.start();               //! Запускаем змейку
		t2.start();               //! Запускаем яблочки.

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {}

		repaint();

}
		try {
			t1.join();
		} catch (InterruptedException e) {}
		synchronized (t2) {
	    	    t2.notify();
		}
		
//		s = getParameter("url");
//		if (s==null)
//			showStatus("URL not found");
//		else {
//			s=s+mdata.scores;
//			try { getAppletContext().showDocument(new URL(s), "_top"); }
//			catch(MalformedURLException e) { showStatus("URL not found"); }
//		}

   }


   public void paint( Graphics g) { 
       if (inited) {
            RepaintFrame();
       }
    }

   public void RepaintFrame() {
//            rc++;
//            int s = 1000;
//            StringBuilder sb = new StringBuilder();
//            sb.append("mdata:");
//            sb.append(mdata  == null);
//            sb.append(" rc= ");
//            sb.append(rc);
//
//            System.err.println(sb.toString() );
//
//        try {
//            Thread.sleep(s);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(MySnake.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        if (mdata== null || mdata.f==null) {
//            return;
//        }

	    for (int i=0; i<pwidth; i++)

		for (int j=0; j<pheight; j++) {

                    
	synchronized (mdata.f) {

		    switch (mdata.f[i][j]) {
		        case 1:
			    g.drawImage(mdata.apple,i*31+3,j*31+3,null);
        		    break;
		        case -1:
			    g.drawImage(mdata.body,i*31+3,j*31+3,null);

        		    break;
		        case -2:
		    	    g.setColor(Color.black);
        		    g.fillRoundRect( i* 31+3,  j* 31+3, 30, 30, 5, 5);

        		    break;
		    }
	}
                }


      if (focussed) 
         g.setColor(Color.cyan);
      else
         g.setColor(Color.lightGray);
         
      int width = getSize().width;  // Width of the applet.
      int height = getSize().height; // Height of the applet.
      g.drawRect(0,0,width-1,height-1);
      g.drawRect(1,1,width-3,height-3);
      g.drawRect(2,2,width-5,height-5);
      

   }  // end paint()
              

   
   public void focusGained(FocusEvent evt) {
      focussed = true;
      RepaintFrame();  // redraw with cyan border
   }
   

   public void focusLost(FocusEvent evt) {
      focussed = false;
      RepaintFrame();  // redraw without cyan border
   }
   
   public void keyTyped(KeyEvent evt) {
   }  // end keyTyped()


    public  void keyPressed(KeyEvent ke) { 
    int key = ke.getKeyCode();
    switch (key) {
        case KeyEvent.VK_ESCAPE:
            mdata.IsGame(false);
            break;

        case KeyEvent.VK_PAGE_UP:
            synchronized (mdata) { if (mdata.speed<30)  mdata.speed++; }
            break;
        case KeyEvent.VK_PAGE_DOWN:
            synchronized (mdata) { if (mdata.speed>1)  mdata.speed--; }
            break;

        case KeyEvent.VK_UP:
	    mdata.newway(0,-1);
            break;
        case KeyEvent.VK_DOWN:
	    mdata.newway(0,1);
            break;
        case KeyEvent.VK_LEFT:
	    mdata.newway(-1,0);
            break;
        case KeyEvent.VK_RIGHT:
	    mdata.newway(1, 0);
            break;
    }
}


   public void   keyReleased(KeyEvent evt)   { }
   public void  mousePressed(MouseEvent evt) { requestFocus(); }   
   public void  mouseEntered(MouseEvent evt) { }
   public void   mouseExited(MouseEvent evt) { }
   public void mouseReleased(MouseEvent evt) { }
   public void  mouseClicked(MouseEvent evt) { }

    private void showStatus(String string) {
        return;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String getParameter(String url) {
        return "";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
} // end class KeyboardAndFocusDemo
