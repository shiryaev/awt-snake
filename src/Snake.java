/**
 * Реальзует движение змейки на игровом поле.
 * Работа происходит с общей структурой MyData.
 * Движение возможно только в свободную клетку.
 * Предполагаемая инициализация: new Apples(MyData);
 * @see         MyData
 * @author      Pavel Shiryaev
 */
public class Snake extends Thread {
    MyData d;
    public Snake(MyData td) {
            d=td;
    }
    public void run() {
	XYqueue head = d.first;
	XYqueue tail = d.first;
	int tf=0, px=0,py=0;
        int x=0,y=0;

        while (d.isgame) {
	    x=head.x; y=head.y;
	    synchronized (d.a) {
	        px=d.a.x;
		py=d.a.y;
	    }
	    px+=x; py+=y;
            if (px>=d.width) px=0;
	    if (px<0) px=d.width-1;
	    if (py>=d.height) py=0;
	    if (py<0) py=d.height-1;
	    head=head.next=new XYqueue(px, py);
	    synchronized (d.f) {
	        tf = d.f[px][py];
		//if (tf==1) d.f[px][py] = -2;
	        //    else  
		 d.f[px][py] = -1;
	    }
	    if (!((px==tail.x) && (py==tail.y)) && (tf < 0)) {
	        d.IsGame(false);
		synchronized (d.t2) {
	    	    d.t2.notify();
		}

	        return;
	    }
	    if (tf == 1) {
	    
	        d.scores++;
		if (((d.scores%5)==0) && (d.speed>1)) d.speed--;
		synchronized (d.t2) {
	    	    d.t2.notify();
		}

	    }

	    d.g.drawImage(d.body,x*31,y*31,null);
	    d.g.drawImage(d.head,px*31,py*31,null);

            if (tf != 1) {
            	d.g.clearRect(tail.x * 31, tail.y * 31, 30, 30);
		if ((px!=tail.x) || (py!=tail.y)) {
		    synchronized (d.f) {
	    		d.f[tail.x][tail.y] = 0;
	    	    }
		} else {
		    d.g.fillOval(px*31, py*31, 30, 30);
        	    d.g.fillRoundRect(px * 31+2, py * 31+2, 26, 26, 5, 5);

		}
                tail=tail.next;
            }
	
	    try {Thread.sleep(d.speed*5);}
	    catch(InterruptedException e) {}
        }
    }
}
