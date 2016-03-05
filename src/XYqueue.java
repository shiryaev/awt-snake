/**
 * Простая рализация списка.
 * @author      Pavel Shiryaev
 */
public class XYqueue  {
    public XYqueue next;
    int x=0,y=0;
    XYqueue(int px, int py) {
                x=px; y=py;
                next = null;
    }
}
