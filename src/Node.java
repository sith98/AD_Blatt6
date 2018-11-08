class Node<T> {
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
