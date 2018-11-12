class TreeNode<T extends Comparable<T>> {
    
    TreeNode(T value) {
        this.value = value;
    }
    
    T value;
    TreeNode<T> left;
    TreeNode<T> right;
}

class Tree<T extends Comparable<T>> {
    private TreeNode<T> root;
    
    // Best Case: 1
    // Worst Case: n
    // Average Case: log n (Bei balancierten Bäumen)
    private void insert(TreeNode<T> root, TreeNode<T> element) {
        // if value to insert is smaller than root
        if (element.value.compareTo(root.value) <= 0) {
            if (root.left == null) {
                root.left = element;
            } else {
                insert(root.left, element);
            }
        } else {
            if (root.right == null) {
                root.right = element;
            } else {
                insert(root.right, element);
            }
        }
    }
    
    // Best Case: n
    // Worst Case: n
    // Average Case: n
    private void print(TreeNode<T> element) {
        if (element == null) {
            System.out.print("n");
        } else {
            System.out.print("(");
            print(element.left);
            System.out.print(",");
            System.out.print(element.value.toString());
            System.out.print(",");
            print(element.right);
            System.out.print(")");
        }
    }
    
    void insert(T value) {
        TreeNode<T> element = new TreeNode<>(value);
        if (root == null) {
            root = element;
        } else {
            insert(root, element);
        }
    }
    
    // Best Case: 1
    // Worst Case: n
    // Average Case: log n (Bei balancierten Bäumen)
    boolean contains(T value) {
        TreeNode<T> current = root;
        while (current != null) {
            int comparison = value.compareTo(current.value);
            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }
    
    void print() {
        print(root);
        System.out.println();
    }
    
    // Best Case: 1
    // Worst Case: n
    // Average Case: log n (Bei balancierten Bäumen)
    void deleteIter(T value) {
        TreeNode<T> current = root;
        TreeNode<T> predecessor = null;
        
        while (current != null) {
            int comparison = value.compareTo(current.value);
            if (comparison == 0) {
                deleteNode(current, predecessor);
                return;
            } else {
                predecessor = current;
                if (comparison < 0) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            
        }
    }
    
    private void deleteNode(TreeNode<T> current, TreeNode<T> predecessor) {
        boolean continueExecution = true;
        while (continueExecution) {
            continueExecution = false;

            if (current.right != null && current.left != null) {
                TreeNode<T> current2 = current.right;
                TreeNode<T> predecessor2 = current;

                while (current2.left != null) {
                    predecessor2 = current2;
                    current2 = current2.left;
                }
                current.value = current2.value;
                current = current2;
                predecessor = predecessor2;
                continueExecution = true;
            } else {
                TreeNode<T> node = null;

                if (current.right != null) {
                    node = current.right;
                } else if (current.left != null) {
                    node = current.left;
                }

                if (predecessor == null) {
                    root = node;
                } else if (current == predecessor.left) {
                    predecessor.left = node;
                } else if (current == predecessor.right) {
                    predecessor.right = node;
                }

            }
        }
        
    }
    
    
    // Best Case: 1
    // Worst Case: n
    // Average Case: log n (Bei balancierten Bäumen)
    void deleteRec(T value) {
        root = _deleteRec(value, root);
    }
    
    private TreeNode<T> _deleteRec(T value, TreeNode<T> root) {
        if (root == null) {
            return null;
        }
        int comparison = value.compareTo(root.value);
        if (comparison == 0) {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode<T> toBeDeleted = _deleteLeftMost(root.right);
                if (toBeDeleted == root.right) {
                    // add potential children of node to "root
                    root.right = toBeDeleted.right;
                }
                root.value = toBeDeleted.value;
                return root;
            }
        } else {
            if (comparison < 0) {
                root.left = _deleteRec(value, root.left);
            } else {
                root.right = _deleteRec(value, root.right);
            }
            return root;
        }
    }
    
    private TreeNode<T> _deleteLeftMost(TreeNode<T> root) {
        if (root.left == null) {
            return root;
        }
        TreeNode<T> toBeDeleted = _deleteLeftMost(root.left);
        if (toBeDeleted == root.left) {
            // add potential children of node to "root"
            root.left = toBeDeleted.right;
        }
        return toBeDeleted;
    }
}

public class Aufgabe3 {
    
    public static void main(String[] args) {
        var tree = new Tree<Integer>();
        
        tree.insert(6);
        tree.insert(3);
        tree.insert(8);
        tree.insert(7);
        tree.insert(9);
        tree.insert(1);


        tree.deleteIter(3);
        tree.deleteRec(1);
        
        tree.print();

        tree.deleteIter(6);
        tree.print();
        
    }
    
}
