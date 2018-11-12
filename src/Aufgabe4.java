import java.util.Random;

@SuppressWarnings("WeakerAccess")
class Matrix {
    int height, width, size;
    
    private int[] field;
    
    Matrix(int height, int width) {
        this.height = height;
        this.width = width;
        this.size = width * height;
        field = new int[size];
    }
    
    static Matrix random(int height, int width) {
        var random = new Random();
        var matrix = new Matrix(height, width);
        
        for (int i = 0; i < matrix.field.length; i++) {
            matrix.field[i] = random.nextInt(10);
        }
        return matrix;
    }
    
    
    int get(int row, int col) {
        return field[row * width + col];
    }
    
    void set(int row, int col, int value) {
        field[row * width + col] = value;
    }
    
    
    Matrix add(Matrix m) {
        if (m.width != width || m.height != height) {
            throw new IllegalArgumentException("Matrix dimensions don't match");
        }
        
        var newField = new int[size];
        
        for (int i = 0; i < size; i++) {
            newField[i] = field[i] + m.field[i];
        }
        
        var newMatrix = new Matrix(height, width);
        newMatrix.field = newField;
        return newMatrix;
    }
    
    Matrix sub(Matrix m) {
        
        if (m.width != width || m.height != height) {
            throw new IllegalArgumentException("Matrix dimensions don't match");
        }
        
        var newField = new int[size];
        
        for (int i = 0; i < size; i++) {
            newField[i] = field[i] - m.field[i];
        }
        
        var newMatrix = new Matrix(height, width);
        newMatrix.field = newField;
        return newMatrix;
    }
    
    Matrix mult(Matrix m) {
        if (width != m.height) {
            throw new IllegalArgumentException("Matrix dimensions don't match");
        }
        
        var newMatrix = new Matrix(height, m.width);
        
        for (int col = 0; col < newMatrix.width; col++) {
            for (int row = 0; row < newMatrix.height; row++) {
                var sum = 0;
                for (int i = 0; i < width; i++) {
                    sum += get(row, i) * m.get(i, col);
                }
                
                newMatrix.set(row, col, sum);
            }
        }
        
        return newMatrix;
    }
    
    private static boolean isPowerOf2(int n) {
        for (int i = 1; i > 0; i <<= 1) {
            if (n == i) return true;
        }
        return false;
    }
    
    Matrix multStrassen(Matrix m) {
        if (this.width != this.height || m.width != m.height || this.width != m.width) {
            throw new IllegalStateException("multStrassen only works for square matrices");
        }
        
        var dim = this.width;
        
        if (!isPowerOf2(dim)) {
            throw new IllegalStateException("multStrassen only works for matrices with a dimension that is a power of two");
        }
        
        return _multStrassen(m);
    }
    
    private Matrix _multStrassen(Matrix m) {
        var dim = this.width;
        if (dim <= 2) {
            return this.mult(m);
        }
        
        var nextDim = dim / 2;
        
        Matrix this11 = new Matrix(nextDim, nextDim);
        Matrix this12 = new Matrix(nextDim, nextDim);
        Matrix this21 = new Matrix(nextDim, nextDim);
        Matrix this22 = new Matrix(nextDim, nextDim);
        
        Matrix m11 = new Matrix(nextDim, nextDim);
        Matrix m12 = new Matrix(nextDim, nextDim);
        Matrix m21 = new Matrix(nextDim, nextDim);
        Matrix m22 = new Matrix(nextDim, nextDim);
        
        // fill matrices
        for (int i = 0; i < nextDim; i++) {
            for (int j = 0; j < nextDim; j++) {
                this11.set(i, j, this.get(i, j));
                this12.set(i, j, this.get(i, j + nextDim));
                this21.set(i, j, this.get(i + nextDim, j));
                this22.set(i, j, this.get(i + nextDim, j + nextDim));
                
                m11.set(i, j, m.get(i, j));
                m12.set(i, j, m.get(i, j + nextDim));
                m21.set(i, j, m.get(i + nextDim, j));
                m22.set(i, j, m.get(i + nextDim, j + nextDim));
            }
        }
        
        Matrix h1 = this11.add(this22).mult(m11.add(m22));
        Matrix h2 = this21.add(this22).mult(m11);
        Matrix h3 = this11.mult(m12.sub(m22));
        Matrix h4 = this22.mult(m21.sub(m11));
        Matrix h5 = this11.add(this12).mult(m22);
        Matrix h6 = this21.sub(this11).mult(m11.add(m12));
        Matrix h7 = this12.sub(this22).mult(m21.add(m22));
        
        Matrix result = new Matrix(dim, dim);
        
        for (int i = 0; i < nextDim; i++) {
            for (int j = 0; j < nextDim; j++) {
                int i1 = h1.get(i, j);
                int i2 = h2.get(i, j);
                int i3 = h3.get(i, j);
                int i4 = h4.get(i, j);
                int i5 = h5.get(i, j);
                int i6 = h6.get(i, j);
                int i7 = h7.get(i, j);
                result.set(i, j,
                    i1 + i4 - i5 + i7
                );
                result.set(i, j + nextDim,
                    i3 + i5
                );
                result.set(i + nextDim, j,
                    i2 + i4
                );
                result.set(i + nextDim, j + nextDim,
                    i1 - i2 + i3 + i6
                );
            }
        }
        
        return result;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        for (int rowStart = 0; rowStart < size; rowStart += width) {
            for (int i = rowStart; i < rowStart + width; i++) {
                builder
                    .append(field[i])
                    .append(' ');
            }
            builder.append('\n');
        }
        
        return builder.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix)) {
            return false;
        }
        var matrix = (Matrix) obj;
        if (this.width != matrix.width || this.height != matrix.height) {
            return false;
        }
        
        for (int i = 0; i < this.field.length; i++) {
            if (this.field[i] != matrix.field[i]) {
                return false;
            }
        }
        
        return true;
    }
}

public class Aufgabe4 {
    public static void main(String[] args) {
        var dim = 1 << 11;
        
        var a = Matrix.random(dim, dim);
        var b = Matrix.random(dim, dim);
        
        Matrix result, strassenResult;
        
        long start, end;
        start = System.currentTimeMillis();
        result = a.mult(b);
        end = System.currentTimeMillis();
        System.out.println(end - start);
        
        start = System.currentTimeMillis();
        strassenResult = a.multStrassen(b);
        end = System.currentTimeMillis();
        System.out.println(end - start);
        
        System.out.println(result.equals(strassenResult));
    }
}
