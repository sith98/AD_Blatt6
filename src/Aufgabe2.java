import java.util.Random;

class CircularList<T> {
    private int size;
    
    private Node<T> first;
    private Node<T> last;
    
    CircularList(T firstValue) {
        first = new Node<>(firstValue);
        first.next = first;
        last = first;
        size = 1;
    }
    
    T get() {
        return first.value;
    }
    
    void add(T value) {
        first = new Node<>(value, first);
        last.next = first;
        size += 1;
    }
    
    T remove() {
        if (size > 2) {
            T value = first.value;
            last.next = first.next;
            size -= 1;
            return value;
        } else {
            throw new IllegalStateException("Can't remove only list element");
        }
    }
    
    void moveForward() {
        first = first.next;
        last = last.next;
    }
    
    int getSize() {
        return size;
    }
    
    @Override
    public String toString() {
        var builder = new StringBuilder();
        var current = first;
        do {
            builder.append(current.value)
                .append(' ');
            current = current.next;
        } while (first != current);
        return builder.toString();
    }
}

public class Aufgabe2 {
    public static void main(String[] args) {
        var list = new CircularList<>(1);
        for (int i = 2; i <= 49; i++) {
            list.add(i);
        }
        
        var random = new Random();
        for (int i = 0; i < 6; i++) {
            var nSteps = random.nextInt(list.getSize());
            for (int j = 0; j < nSteps; j++) {
                list.moveForward();
            }
            System.out.println(list.remove());
        }
    }
}
