import java.util.*;


public class MaxMatching {
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
    System.out.println(v + 2);
    System.out.println((v + 1) + " " + (v + 2));
    System.out.println(e + v);
    for (int i = 0; i < am.size(); i++) {
      for (int j = 0; j < am.get(i).size(); j++) {
        System.out.println(i + " " + am.get(i).get(j).get("v") + " " + am.get(i).get(j).get("rc") );
      }
    }
    io.flush();
  }

  void readBipartiteGraph() {
    int x = io.getInt();
    int y = io.getInt();
    int v = x + y;
    int e = io.getInt();

    ArrayList<ArrayList<HashMap<String,Integer>>> al = new ArrayList<ArrayList<HashMap<String,Integer>>>(v+2);
    for (int i = 0; i < v+2; i++)
      al.add(new ArrayList<HashMap<String,Integer>>());

    for (int i = 0; i < e; ++i) {
      int a = io.getInt();
      int b = io.getInt();
      addEdge(al,a-1,b-1,1);
      addEdge(al,b-1,a-1,0);
    }

    int s = v + 1;
    int t = v + 2;

    for (int i = 0; i < x; i++) {
      addEdge(al,s-1,i,1);
      addEdge(al,i,s-1,0);
    }

    for (int i = x; i < v; i++) {
      addEdge(al,i,t-1,1);
      addEdge(al,t-1,i,0);
    }

    fordFulgerson(al,v+2,s-1,t-1,x,y);

  }

  boolean bfs(ArrayList<ArrayList<HashMap<String,Integer>>> cf, int s, int t, int parent[], int V)
   {
       boolean visited[] = new boolean[V];
       LinkedList<Integer> queue
           = new LinkedList<Integer>();
       queue.add(s);
       visited[s] = true;
       parent[s] = -1;

       while (queue.size() != 0) {
           int u = queue.poll();
           for (HashMap<String,Integer> n : cf.get(u)) {
               if (visited[n.get("v")] == false
                   && n.get("rc") > 0) {
                   if (n.get("v") == t) {
                       parent[n.get("v")] = u;
                       return true;
                   }
                   queue.add(n.get("v"));
                   parent[n.get("v")] = u;
                   visited[n.get("v")] = true;
               }
           }
       }

       return false;
   }

  void fordFulgerson(ArrayList<ArrayList<HashMap<String,Integer>>> cf,int N,int s,int t, int x, int y) {
    int parent[] = new int[N]; // path
    int max_flow = 0;
    int u, v;

    while(bfs(cf,s,t,parent,N)) {
      int min_path_flow = Integer.MAX_VALUE;
      for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                min_path_flow
                    = Math.min(min_path_flow, getEdge(cf,u,v).get("rc"));
      }

      for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                getEdge(cf,u,v).put("rc", getEdge(cf,u,v).get("rc")-min_path_flow);
                getEdge(cf,v,u).put("rc", getEdge(cf,v,u).get("rc")+min_path_flow);
      }

      max_flow += min_path_flow;
    }

    // Output graph
    io.println(x + " " + y);
    io.println(max_flow);
    for (int i = 0; i < x; i++) {
      for (int j = x; j < x+y; j++) {
          if (getEdge(cf,j,i) != null) {
            if (getEdge(cf,j,i).get("rc") > 0) {
              io.println((i+1) + " " + (j+1));
            }
        }
      }
    }

    io.flush();
  }

  MaxMatching() {
    io = new Kattio(System.in, System.out);
    readBipartiteGraph();
    io.close();
  }

  public static void main(String args[]) {
    new MaxMatching();
  }
}
