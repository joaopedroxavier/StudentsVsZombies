package StudentsVsZombies;

import java.awt.*;
import java.util.TreeSet;

public class Grid {
    private int x_, y_;
    private int cell_size;
    private int M, N;
    private Cell grid[][];

    Grid(int x, int y, int cell_size, int N, int M) {
        x_ = x; y_ = y;
        this.cell_size = cell_size;
        this.M = M; this.N = N;
        grid = new Cell[N][M];
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++)
                grid[i][j] = new Cell();
        }
    }

    Point get_cell(Point pos) {
        return new Point((pos.x - x_)/cell_size, (pos.y - y_)/cell_size);
    }

    Point get_loc(Point cell) {
        return new Point((int)(x_ + cell_size*(0.5 + cell.x)),
                (int)(y_ + cell_size*(0.5 + cell.y)));
    }

    Point get_limit() { return new Point(M - 1, N - 1); }
    int get_size() { return cell_size; }
    void remove(GameObject obj, Point cell){ grid[cell.x][cell.y].remove(obj); }
    void add(GameObject obj, Point cell){ /*grid[cell.x][cell.y].add(obj);*/ }
    //Cell get_inCell(Point cell) { return grid[cell.x][cell.y]; }



}
