import java.util.*;


public class Part2 {
  public Kattio io;

  void readFlow() {
    int v = io.getInt();
    int s = io.getInt();
    int t = io.getInt();
    int e = io.getInt();

    ArrayList<LinkedList<Edge>> edges = new ArrayList<LinkedList<Edge>>(v);
    for (int i = 0; i < v; i++)
    edges.add(new LinkedList<Edge>());

    for (int i = 0; i < e; ++i) {
      int a = io.getInt();
      int b = io.getInt();
      int c = io.getInt();

      Edge ab = new Edge(a-1,b-1,c,0);
      Edge ba = new Edge(b-1,a-1,0,0);

      edges.get(a-1).add(ab);
      edges.get(b-1).add(ba);
      ab.invers = ba;
      ba.invers = ab;
    }

    /*
    int count = 0;
    for (LinkedList<Edge> l : edges) {
      for (Edge le : l) {
        count++;
      }
    }
    System.out.println(count);
    */

    fordFulgerson(edges,s-1,t-1,v);

  }

  private Edge BFS(ArrayList<LinkedList<Edge>> g, int s, int t, int N)  {
    boolean[] visited = new boolean[N];
    LinkedList<Edge> q
    = new LinkedList<Edge>();
    Edge e = new Edge(s,s,0,0);
    e.parent = null;
    q.add(e);
    visited[s] = true;

    while (q.size() != 0) {
      Edge u = (Edge) q.poll();
      for (Edge n : g.get(u.to)) {
        if (visited[n.to] == false
        && (n.cap - n.flow) > 0) {
          if (n.to == t) {
            n.parent = u;
            return n;
          }
          q.add(n);
          n.parent = u;
          visited[n.to] = true;
        }
      }
    }

    return null;
  }

  void fordFulgerson(ArrayList<LinkedList<Edge>> g,int s,int t, int N) {

    Edge x;
    int maxflow = 0;
    while ((x = BFS(g,s,t,N)) != null) {
      int r = Integer.MAX_VALUE;
      LinkedList<Edge> p = new LinkedList<Edge>();

      // Find min residual path
      while (x.parent != null) {
        p.add(x);
        r = Math.min(r, x.cap - x.flow);
        x = x.parent;
      }

      // Update edges in path.
      for (Edge e : p) {
        e.flow += r;
        e.invers.flow = -e.flow;
      }
      maxflow += r;
    }

    // PRINT OUTPUT
    ArrayList<Edge> edges = new ArrayList<Edge>();

    io.println(N);
    io.println((s+1) + " " + (t+1) + " " + maxflow);

    for (LinkedList<Edge> l : g) {
      for (Edge le : l) {
        if (le.flow > 0) {
          Edge edge = new Edge(le.from,le.to,0, le.flow);
          edges.add(edge);
        }
      }
    }
    io.println(edges.size());

    for (Edge e: edges) {
      io.println((e.from+1) + " " + (e.to+1) + " " +  e.flow);
    }
  }

  Part2() {
    io = new Kattio(System.in, System.out);
    readFlow();
    io.close();
  }

  public static void main(String args[]) {
    new Part2();
  }
}
