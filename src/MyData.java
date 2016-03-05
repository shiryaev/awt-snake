import java.awt.Image;
import java.awt.Point;
import java.awt.Graphics;
/**
 * Отдельный класс для хранения всей структуры данных
 * @author      Pavel Shiryaev
 */
public class MyData {
	public Image body, head, apple;
	XYqueue first;    
	Point a,b;
	Thread t1;
	Thread t2;
	public int f[][]; //! Игровое поле
	int x, y, width, height; //! Размеры игрового поля
	Graphics g;
	int speed=19; //! Начальная скорость движения
	boolean isgame;
	int level=0, scores=0;
	
	public MyData(int w, int h) {
		width=w; height=h;
		f = new int[w][h];
		y=(int) h/2;
		x=(int) w/2;
   		first=new XYqueue(x,y);	
		a=new Point(1,0);
		b=null;

	}

	public void setLevel(int l) {
		int i,j;
		switch (l) {
			case 1: // polnyj
				for ( i=0; i<width; i++) {
					f[i][0]=f[i][height-1]=-2;
					for ( j=1; j<height-1; j++)
						f[i][j]=0;
				}
				for ( j=1; j<height-1; j++)
					f[0][j]=f[width-1][j]=-2;

				break;
			case 2: // S dyrochkami
				for ( i=0; i<width; i++) {
					f[i][0]=f[i][height-1]=-2;
					for ( j=1; j<height-1; j++)
						f[i][j]=0;
				}
				for ( j=1; j<height-1; j++)
					f[0][j]=f[width-1][j]=-2;
				f[(int) width/3][0]=f[(int) width/3][height-1] =
					f[(int) 2*width/3][0]=f[(int) 2*width/3][height-1]=0;
				f[0][(int) height/2]=f[width-1][(int) height/2]=0;
				break;
			case 3: // resheto
				for ( i=0; i<width; i+=3) {
					f[i+1][0]=f[i+1][height-1]=-2;
					for ( j=1; j<height-1; j++)
						f[i][j]=0;
				}
				for ( j=1; j<height-1; j+=3)
					f[0][j]=f[width-1][j]=-2;
				f[0][0]=f[0][height-1]=f[width-1][0]=f[width-1][height-1]=-3;
			break;
			case 4: // new
				for ( i=(int) height/4; i>=0; i--)
					f[0][i]=f[i][0]=
					f[width-1][i]=f[i][height-1]=
					f[0][height-1-i]=f[width-1-i][0]=
					f[width-1][height-1-i]=f[width-1-i][height-1]=-2;
			break;
			case 5: // new
				for ( i=(int) height/4; i>=0; i--)
					f[0][i]=f[i][0]=
					f[width-1][i]=f[i][height-1]=
					f[0][height-1-i]=f[width-1-i][0]=
					f[width-1][height-1-i]=f[width-1-i][height-1]=-2;
				for ( i=4; i< width-4;i++)
				    f[i][4]=f[i][height-4]=-2;
			break;

			default: // pusto
				for ( i=0; i<width; i++)
				for ( j=0; j<height; j++)
				f[i][j]=0;
		}
	}

	public void IsGame(boolean GameStatus) {
		isgame=GameStatus;
	}

	public synchronized void newway(int x, int y) {
		if ((a.x+x)*(a.y+y)!=0) {
		    a.x=x;
		    a.y=y;
		}
	}


	public void setGraphics(Graphics graphics) {
		g=graphics;
	}
	
	public void setThreads(Thread Thread1, Thread Thread2) {
		t1=Thread1;
		t2=Thread2;
	}
}
