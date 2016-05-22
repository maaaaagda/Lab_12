/**
 * Created by Magdalena Polak on 19.05.2016.
 */
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

// GRAFY NIESKIEROWANE
class Graph {
    // liczba krawedzi
    private int e;
    // liczba wierzcholkow
    private int v;
    // tablica list sasiedztwa danego wierzcholka
    private List<Integer>[] adjacencyList;

    /**
     *
     * @param v
     *            ilosc wierzcholkow w grafie
     */
    @SuppressWarnings("unchecked")
    public Graph(int v) {
        this.v = v;
        this.e = 0;
        adjacencyList = (List<Integer>[]) new List[v];
        for (int i = 0; i < v; i++) {
            adjacencyList[i] = new ArrayList<Integer>();
        }
    }

    /**
     * Dodaje krawedz v-w do grafu nieskierowanego.
     *
     * @param v
     *            jeden z wierzcholkow krawedzi
     * @param w
     *            drugi z wierzcholkow krawedzi
     */
    public void addEdge(int v, int w) {
        adjacencyList[v].add(w);
        adjacencyList[w].add(v);
        e++;
    }

    /**
     *
     * @return liczbe krawedzi
     */
    public int getNumberOfEdges() {
        return e;
    }

    /**
     * @return liczbe wierzcholkow
     */
    public int getNumberOfVertices() {
        return v;
    }

    /**
     * Zwraca liste sasiedztwa danego wierzcholka.
     *
     * @param v
     *            indeks wierzcholka skierowanego
     * @return zwraca iterowalna kolekcje wierzcholkow sasiadujacych
     */
    public Iterable<Integer> getAdjacencyList(int v) {
        return adjacencyList[v];
    }

    /**
     * @return opis grafu.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        s.append("wierzcholki: ").append(v).append("; krawedzie: ").append(e)
                .append(newLine);
        for (int i = 0; i < v; i++) {
            s.append(i).append(": ");
            for (int w : adjacencyList[i]) {
                s.append(w).append(" ");
            }
            s.append(newLine);
        }
        return s.toString();
    }
}

class DFSPaths {
    // tablica krawedzi ktora jest
// przechowuje wierzcholki z ktorych mozna sie dostac do biezacego
// okreslonego indeksem tablicy
    private int[] edgeTo;
    // tablica odwiedzonych wierzcholkow
    private boolean[] marked;
    // wierzcholek zrodlowy, z ktorego rozpoczynamy przeszukiwanie
    private final int source;

    public DFSPaths(Graph graph, int source) {
        this.source = source;
        edgeTo = new int[graph.getNumberOfVertices()];
        marked = new boolean[graph.getNumberOfVertices()];
        dfs(graph, source);
    }

    /**
     *
     * @param vertex
     *            indeks wierzcholka dla ktorego ma byc sprawdzenie istnienia
     *            sciezki
     * @return true jesli istnieje sciezka z wierzcholka zrodlowego danego w
     *         konstruktorze do wierzcholka {@code vertex}
     */
    public boolean hasPathTo(int vertex) {
        return marked[vertex];
    }

    /**
     *
     * @param vertex
     *            docelowy wierzcholek
     * @return stos wierzcholkow prowadzacych ze zrodal {@code source} do celu
     *         {@code vertex} jesli sciezka nie istnieje zwracana jest pusta
     *         kolekcja
     */
    public Iterable<Integer> getPathTo(int vertex) {
        Deque<Integer> path = new ArrayDeque<Integer>();
// jesli nie istnieje sciezka zwroc pusta sciezke
        if (!hasPathTo(vertex)) {
            return path;
        }
// dopoki istnieje wierzcholek dodawaj go do stosu
        for (int w = vertex; w != source; w = edgeTo[w]) {
            path.push(w);
            System.out.println("stos: " + path);

        }
// dodaj na koniec krawedz zrodlowa
        path.push(source);
        System.out.println("stos: " + path);
        return path;
    }

    private void dfs(Graph graph, int vertex) {
// oznaczamy wierzcholek jako odwiedzony
        marked[vertex] = true;
        System.out.println("marked: " + vertex);
// dla kazdego sasiedniego wierzcholka jesli nie jest oznaczony
// wywolujemy rekurencyjnie metode dfs, ktora odwiedzi wierzchoki i
// zapisze trase
        for (int w : graph.getAdjacencyList(vertex)) {
            if (!marked[w]) {
                edgeTo[w] = vertex;
                 dfs(graph, w);
            }
        }
    }
}

class BFSPaths {
    // tablica krawedzi ktora jest
// przechowuje wierzcholki z ktorych mozna sie dostac do biezacego
// okreslonego indeksem tablicy
    private int[] edgeTo;
    // tablica odwiedzonych wierzcholkow
    private boolean[] marked;
    // wierzcholek zrodlowy, z ktorego rozpoczynamy przeszukiwanie
    private final int source;
    private Queue<Integer> priorityQueue;

    public BFSPaths(Graph graph, int source) {
        this.source = source;
        edgeTo = new int[graph.getNumberOfVertices()];
        marked = new boolean[graph.getNumberOfVertices()];
        priorityQueue = new PriorityQueue<Integer>(graph.getNumberOfVertices());
        priorityQueue.offer(source);
        bfs(graph, source);
    }

    /**
     *
     * @param vertex
     *            indeks wierzcholka dla ktorego ma byc sprawdzenie istnienia
     *            sciezki
     * @return true jesli istnieje sciezka z wierzcholka zrodlowego danego w
     *         konstruktorze do wierzcholka {@code vertex}
     */
    public boolean hasPathTo(int vertex) {
        return marked[vertex];
    }

    /**
     *
     * @param vertex
     *            docelowy wierzcholek
     * @return stos wierzcholkow prowadzacych ze zrodal {@code source} do celu
     *         {@code vertex} jesli sciezka nie istnieje zwracana jest pusta
     *         kolekcja
     */
    public Iterable<Integer> getPathTo(int vertex) {
        Deque<Integer> path = new ArrayDeque<Integer>();
// jesli nie istnieje sciezka zwroc pusta sciezke
        if (!hasPathTo(vertex)) {
            return path;
        }
// dopoki istnieje wierzcholek dodawaj go do stosu
        for (int w = vertex; w != source; w = edgeTo[w]) {
            path.push(w);
        }
// dodaj na koniec krawedz zrodlowa
        path.push(source);
        return path;
    }

    private void bfs(Graph graph, int vertex) {
// oznaczamy wierzcholek jako odwiedzony
        marked[vertex] = true;
        System.out.println("marked: " +vertex);

// dodajemy wierzcholek zrodlowy do kolejki
        priorityQueue.offer(vertex);

// dopoki kolejka nie jest pusta, wybieramy krawedz o najnizszym
// priorytecie
// i oznaczamy jako odwiedzone wierzcholki z listy sasiedztwa usuwanego
// wierzcholka
// oraz dodajemy wierzcholki z listy sasiedztwa do kolejki
        while (!priorityQueue.isEmpty()) {
            System.out.println("kolejka: " + priorityQueue);
            int v = priorityQueue.remove();
            for (int w : graph.getAdjacencyList(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    System.out.println("marked: " + w);
                    priorityQueue.offer(w);
                }
            }
        }
    }
}

// GRAFY SKIEROWANE
class DirectedGraph {
    // liczba krawedzi
    private int e;
    // liczba wierzcholkow
    private int v;
    // tablica list sasiedztwa danego wierzcholka
    private List<Integer>[] adjacencyList;

    /**
     *
     * @param v
     *            ilosc wierzcholkow w grafie
     */
    @SuppressWarnings("unchecked")
    public DirectedGraph(int v) {
        this.v = v;
        this.e = 0;
        adjacencyList = (List<Integer>[]) new List[v];
        for (int i = 0; i < v; i++) {
            adjacencyList[i] = new ArrayList<Integer>();
        }
    }

    /**
     * Dodaje krawedz v-w do grafu skierowanego.
     *
     * @param v
     *            wierzcholek poczatkowy
     * @param w
     *            wierzcholek koncowy
     */
    public void addEdge(int v, int w) {
        adjacencyList[v].add(w);
        e++;
    }

    /**
     *
     * @return liczbe krawedzi
     */
    public int getNumberOfEdges() {
        return e;
    }

    /**
     * @return liczbe wierzcholkow
     */
    public int getNumberOfVertices() {
        return v;
    }

    /**
     * Zwraca liste sasiedztwa danego wierzcholka.
     *
     * @param v
     *            indeks wierzcholka skierowanego
     * @return zwraca iterowalna kolekcje wierzcholkow sasiadujacych
     */
    public Iterable<Integer> getAdjacencyList(int v) {
        return adjacencyList[v];
    }

    /**
     * @return opis grafu.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        s.append("wierzcholki: ").append(v).append("; krawedzie: ").append(e)
                .append(newLine);
        for (int i = 0; i < v; i++) {
            s.append(i).append(": ");
            for (int w : adjacencyList[i]) {
                s.append(w).append(" ");
            }
            s.append(newLine);
        }
        return s.toString();
    }
}

class DFSDirectedPaths {
    // tablica krawedzi ktora jest
// przechowuje wierzcholki z ktorych mozna sie dostac do biezacego
// okreslonego indeksem tablicy
    private int[] edgeTo;
    // tablica odwiedzonych wierzcholkow
    private boolean[] marked;
    // wierzcholek zrodlowy, z ktorego rozpoczynamy przeszukiwanie
    private final int source;

    public DFSDirectedPaths(DirectedGraph graph, int source) {
        this.source = source;
        edgeTo = new int[graph.getNumberOfVertices()];
        marked = new boolean[graph.getNumberOfVertices()];
        dfs(graph, source);
    }

    /**
     *
     * @param vertex
     *            indeks wierzcholka dla ktorego ma byc sprawdzenie istnienia
     *            sciezki
     * @return true jesli istnieje sciezka z wierzcholka zrodlowego danego w
     *         konstruktorze do wierzcholka {@code vertex}
     */
    public boolean hasPathTo(int vertex) {
        return marked[vertex];
    }

    /**
     *
     * @param vertex
     *            docelowy wierzcholek
     * @return stos wierzcholkow prowadzacych ze zrodal {@code source} do celu
     *         {@code vertex} jesli sciezka nie istnieje zwracana jest pusta
     *         kolekcja
     */
    public Iterable<Integer> getPathTo(int vertex) {
        Deque<Integer> path = new ArrayDeque<Integer>();
// jesli nie istnieje sciezka zwroc pusta sciezke
        if (!hasPathTo(vertex)) {
            return path;
        }
// dopoki istnieje wierzcholek dodawaj go do stosu
        for (int w = vertex; w != source; w = edgeTo[w]) {
            path.push(w);
          //  System.out.println("stack: " + path);
        }
// dodaj na koniec krawedz zrodlowa
        path.push(source);
        return path;
    }

    private void dfs(DirectedGraph graph, int vertex) {
// oznaczamy wierzcholek jako odwiedzony
        marked[vertex] = true;
        System.out.println("marked: " + vertex);
// dla kazdego sasiedniego wierzcholka jesli nie jest oznaczony
// wywolujemy rekurencyjnie metode dfs, ktora odwiedzi wierzchoki i
// zapisze trase
        for (int w : graph.getAdjacencyList(vertex)) {
            if (!marked[w]) {
                edgeTo[w] = vertex;
                dfs(graph, w);
            }
        }
    }
}

class BFSDirectedPaths {
    // tablica krawedzi ktora jest
// przechowuje wierzcholki z ktorych mozna sie dostac do biezacego
// okreslonego indeksem tablicy
    private int[] edgeTo;
    // tablica odwiedzonych wierzcholkow
    private boolean[] marked;
    // wierzcholek zrodlowy, z ktorego rozpoczynamy przeszukiwanie
    private final int source;
    private Queue<Integer> priorityQueue;

    public BFSDirectedPaths(DirectedGraph graph, int source) {
        this.source = source;
        edgeTo = new int[graph.getNumberOfVertices()];
        marked = new boolean[graph.getNumberOfVertices()];
        priorityQueue = new PriorityQueue<Integer>(graph.getNumberOfVertices());
        priorityQueue.offer(source);
        bfs(graph, source);
    }

    /**
     *
     * @param vertex
     *            indeks wierzcholka dla ktorego ma byc sprawdzenie istnienia
     *            sciezki
     * @return true jesli istnieje sciezka z wierzcholka zrodlowego danego w
     *         konstruktorze do wierzcholka {@code vertex}
     */
    public boolean hasPathTo(int vertex) {
        return marked[vertex];
    }

    /**
     *
     * @param vertex
     *            docelowy wierzcholek
     * @return stos wierzcholkow prowadzacych ze zrodal {@code source} do celu
     *         {@code vertex} jesli sciezka nie istnieje zwracana jest pusta
     *         kolekcja
     */
    public Iterable<Integer> getPathTo(int vertex) {
        Deque<Integer> path = new ArrayDeque<Integer>();
// jesli nie istnieje sciezka zwroc pusta sciezke
        if (!hasPathTo(vertex)) {
            return path;
        }
// dopoki istnieje wierzcholek dodawaj go do stosu
        for (int w = vertex; w != source; w = edgeTo[w]) {
            path.push(w);
            System.out.println("Enqueued: " + w);
        }
// dodaj na koniec krawedz zrodlowa
        path.push(source);
        return path;
    }

    private void bfs(DirectedGraph graph, int vertex) {
// oznaczamy wierzcholek jako odwiedzony
        marked[vertex] = true;
        System.out.println("marked: " + vertex);

// dodajemy wierzcholek zrodlowy do kolejki
        priorityQueue.offer(vertex);

// dopoki kolejka nie jest pusta, wybieramy krawedz o najnizszym
// priorytecie
// i oznaczamy jako odwiedzone wierzcholki z listy sasiedztwa usuwanego
// wierzcholka
// oraz dodajemy wierzcholki z listy sasiedztwa do kolejki
        while (!priorityQueue.isEmpty()) {
            System.out.println("kolejka: " + priorityQueue);
            int v = priorityQueue.remove();
            for (int w : graph.getAdjacencyList(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    priorityQueue.offer(w);
                }
            }
        }
    }
}


 class Paths {

    public static void main(String[] args) {
// Przyklad ze strony http://www.algorytm.org/algorytmy-grafowe/przeszukiwanie-grafu-wszerz-bfs-i-w-glab-dfs.html
// UWAGA: graf skierowany zostal przerobiony na graf nieskierowany
// stan na dzien 2013-04-08
// grafy nieskierowane
    /*    Graph graph = new Graph(6);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        graph.addEdge(1, 4);

        graph.addEdge(2, 3);
        graph.addEdge(2, 5);

        graph.addEdge(3, 0);

        graph.addEdge(4, 3);

        System.out.println("Graf nieskierowany: " + graph);

        System.out.println("\nDFS - sciezki nieskierowane");
// droga z 1 do 5
        DFSPaths dfs1 = new DFSPaths(graph, 0);
        for (int it : dfs1.getPathTo(5)) {
            System.out.print(it + " ");
        }
        System.out.println("\n----------");


        System.out.println("\nBFS - sciezki nieskierowane");
// droga z 1 do 5
        BFSPaths bfs1 = new BFSPaths(graph, 0);
        for (int it : bfs1.getPathTo(5)) {
            System.out.print(it + " ");
        }
        System.out.println("\n----------");


// --------------------------------------------
// grafy skierowane
        DirectedGraph digraph = new DirectedGraph(6);

        digraph.addEdge(0, 1);
        digraph.addEdge(0, 2);

        digraph.addEdge(1, 4);

        digraph.addEdge(2, 3);
        digraph.addEdge(2, 5);

        digraph.addEdge(3, 0);

        digraph.addEdge(4, 3);


        System.out.println("\n\nGraf skierowany: " + digraph);

        System.out.println("\nDFS - sciezki skierowane");
// droga z 1 do 5
        DFSDirectedPaths dfs3 = new DFSDirectedPaths(digraph, 0);
        for (int it : dfs3.getPathTo(5)) {
            System.out.print(it + " ");
        }
        System.out.println("\n----------");


        System.out.println("\nBFS - sciezki skierowane");
// droga z 1 do 5
        BFSDirectedPaths bfs3 = new BFSDirectedPaths(digraph, 0);
        for (int it : bfs3.getPathTo(4)) {
            //System.out.print(it + " ");
        }
        System.out.println("\n----------");  */
        System.out.println("**************************************************");
        Graph grap = new Graph(10);

        grap.addEdge(1, 2);
        grap.addEdge(1, 3);

        grap.addEdge(3, 4);

        grap.addEdge(3, 8);
        grap.addEdge(4, 5);
        grap.addEdge(4, 6);
        grap.addEdge(4, 7);
        grap.addEdge(8, 7);
        grap.addEdge(8, 9);
        grap.addEdge(9, 6);



        System.out.println("Graf nieskierowany: " + grap);

        System.out.println("\nDFS - sciezki nieskierowane");
// droga z 1 do 5
        DFSPaths dfs2 = new DFSPaths(grap, 1);
        for (int it : dfs2.getPathTo(9)) {
      //      System.out.print(it + " ");
        }
        System.out.println("\n----------");

        System.out.println("\nBFS - sciezki nieskierowane");
// droga z 1 do 5
        BFSPaths bfs2 = new BFSPaths(grap, 1);
        for (int it : bfs2.getPathTo(9)) {
           // System.out.print(it + " ");
        }
        System.out.println("\n----------");

    }
}