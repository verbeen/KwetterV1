package kwetter.helpers;

/**
 * Created by geh on 26-2-14.
 */
public class Triplet<U, V, W>
{
    private U first;
    private V second;
    private W third;

    public Triplet(U first, V second, W third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public U getFirst() { return first; }
    public void setFirst(U first) { this.first = first; }

    public V getSecond() { return second; }
    public void setSecond(V second) { this.second = second; }

    public W getThird() { return third; }
    public void setThird(W third) { this.third = third; }
}
