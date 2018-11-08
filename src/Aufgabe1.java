class Node<T extends Comparable<T>> {
    T value;
    Node<T> next;

    Node(T value) {
        this.value = value;
    }

    Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node(" + value + ", " + next + ")";
    }
}

class List<T extends Comparable<T>> {
    private Node<T> first = null;


    @SafeVarargs
    List(T... values) {
        for (int i = values.length - 1; i >= 0; i--) {
            prepend(values[i]);
        }
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();


        var current = first;
        while (current != null) {
            builder.append(current.value.toString())
                .append(' ');
            current = current.next;
        }

        return builder.toString();
    }

    void append(T value) {
        if (first == null) {
            first = new Node<>(value);
        } else {
            var current = first;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<>(value);
        }
    }

    void quickSort() {
        first = quickSort(first);
    }

    private Node<T> quickSort(Node<T> first) {
        // List is zero or one elements long
        if (first == null || first.next == null) {
            return first;
        }
        Node<T> pivot = first;
        first = first.next;
        pivot.next = null;

        Node<T> smaller = null;


        Node<T> current = first;
        Node<T> previous = null;
        while (current != null) {
            // element is smaller
            if (current.value.compareTo(pivot.value) < 0) {
                if (previous == null) {
                    first = first.next;
                } else {
                    previous.next = current.next;
                }
                var next = current.next;

                current.next = smaller;
                smaller = current;
                current = next;
            } else {
                previous = current;
                current = current.next;
            }
        }

        smaller = quickSort(smaller);
        first = quickSort(first);

        pivot.next = first;
        if (smaller != null) {
            var lastSmaller = smaller;
            while (lastSmaller.next != null) {
                lastSmaller = lastSmaller.next;
            }
            lastSmaller.next = pivot;
        }
        if (smaller == null) {
            return pivot;
        }
        return smaller;
    }

    private void prepend(T value) {
        first = new Node<>(value, first);
    }
}

public class Aufgabe1 {
    public static void main(String[] args) {
        var list = new List<>(4, 1, 2, 3, 5, 6);

        list.quickSort();
        System.out.println(list);
    }
}

