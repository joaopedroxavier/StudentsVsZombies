package StudentsVsZombies;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int x_, y_;
    private int cell_size;
    private  int M, N;
    private ArrayList<GameObject> grid[][];

    Grid(int x, int y, int size, int m, int n) {
        x_ = x; y_ = y;
        cell_size = size;
        M = m; N = n;
        grid = (ArrayList<GameObject>[][]) new Object[M][N];
        for(int i = 0; i < N; i++) for(int j = 0; i < M; j++)
            grid[i][j] = new ArrayList<GameObject>();
    }

}
