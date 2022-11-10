import java.util.*;


public class Part1 {
  public Kattio io;

  void addEdge(ArrayList<ArrayList<HashMap<String,Integer>>> am, int s, int d, int c) {
    HashMap<String,Integer> dict = new HashMap<String,Integer>();
    dict.put("v", d);
    dict.put("rc", c);
    am.get(s).add(dict);
  }

  HashMap<String,Integer> getEdge(ArrayList<ArrayList<HashMap<String,Integer>>> al, int u, int v) {
    for (HashMap<String,Integer> n: al.get(u)) {
      if (n.get("v") == v) {
        return n;
      }
    }
    return null;
  }

  void printGraph(ArrayList<ArrayList<HashMap<String,Integer>>> am, int v, int e) {
    io.println(v + 2);
    io.println((v + 1) + " " + (v + 2));
    io.println(e + v);
    for (int i = 0; i < am.size(); i++) {
      for (int j = 0; j < am.get(i).size(); j++) {
        io.println(i + " " + am.get(i).get(j).get("v") + " " + am.get(i).get(j).get("rc") );
      }
    }
    io.flush();
  }

  void readBipartiteGraph() {
    int x = io.getInt();
    int y = io.getInt();
    int v = x + y;
    int e = io.getInt();

    ArrayList<ArrayList<HashMap<String,Integer>>> am = new ArrayList<ArrayList<HashMap<String,Integer>>>(v+2);
    for (int i = 0; i < v+2; i++)
    am.add(new ArrayList<HashMap<String,Integer>>());

    for (int i = 0; i < e; ++i) {
      int a = io.getInt();
      int b = io.getInt();
      addEdge(am,a,b,1);
    }

    int s = v + 1;
    int t = v + 2;

    for (int i = 1; i <= x; i++) {
      addEdge(am,s,i,1);
    }

    for (int i = x + 1; i <= v; i++) {
      addEdge(am,i,t,1);
    }

  //  printGraph(am, v, e);

    int f_v = io.getInt();
    int f_s = io.getInt();
    int f_t = io.getInt();
    int f_mf = io.getInt();
    int f_ewpf = io.getInt();

    io.println(x + " " + y);
    io.println(f_mf);

    for (int i = 1; i <= f_ewpf; i++) {
        int f_a = io.getInt();
        int f_b = io.getInt();
        int f_f = io.getInt();
        if (f_a > 0 && f_a <= x && f_b > x && f_b <= x+y) {
          io.println(f_a + " " + f_b);
        }
    }

  }

  Part1() {
    io = new Kattio(System.in, System.out);
    readBipartiteGraph();
    io.close();
  }

  public static void main(String args[]) {
    new Part1();
  }
}
