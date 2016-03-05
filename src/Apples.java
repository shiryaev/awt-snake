/**
 * Реальзует расстановку яблочек на игровом поле.
 * Работа происходит с общей структурой MyData.
 * Яблоко ставится в свободную от всего клетку.
 * Предполагаемая инициализация: new Apples(MyData);
 * @see         MyData
 * @author      Pavel Shiryaev
 */
public class Apples extends Thread {
    MyData data;
    /**
     * Ипорт настроек в класс Apples
     * @param mydata Объект включающий всю информацию об игровом поле
     */
    public Apples(MyData mydata) {
    
            data=mydata;
    }
    /**
     * Синхронизированный поток расстановки яблок
     */
    public synchronized void run() {
	    int x, y;

        while (data.isgame) {
		do {
			if (!data.isgame) return;
			x = (int) ((data.width) * Math.random());
			y = (int) ((data.height) * Math.random());
		} while (data.f[x][y] != 0);
		data.f[x][y]=1;
		data.g.drawImage(data.apple,x*31,y*31,null);
		try {this.wait();}
	        catch(InterruptedException e) {}
        }
    }
}
