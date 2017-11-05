package StudentsVsZombies;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class Grid {
    private int x_, y_;
    private int cell_size;
    private int M, N;
    private ArrayList<Spawnable> grid[][];

    Grid(int x, int y, int cell_size, int N, int M) {
        x_ = x; y_ = y;
        this.cell_size = cell_size;
        this.M = M; this.N = N;
        grid = new ArrayList[N][M];
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++)
                grid[i][j] = new Cell();
        }
    }

    public Point get_cell(Point pos) {
        return new Point((pos.x - x_)/cell_size, (pos.y - y_)/cell_size);
    }

    public Point get_loc(Point cell) { return new Point(x_ + cell_size*cell.y, (int)(y_ + cell_size*(cell.x) - 0.2)); }

    public Point get_limit() { return new Point( N - 1, M - 1); }

    public ArrayList<Spawnable> getListOfObjects(Point cell) { return grid[cell.y][cell.x]; }

    int get_size() { return cell_size; }

    void remove(GameObject obj, Point cell){ grid[cell.y][cell.x].remove(obj); }

    void add(Spawnable obj, Point cell){
        grid[cell.y][cell.x].add(obj);
    }

    ArrayList<Spawnable> get_inCell(Point cell) { return grid[cell.y][cell.x]; }



}
