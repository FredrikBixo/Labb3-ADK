public class Edge {
  public int to;
  public int from;
  public int flow;
  public int cap;
  public Edge invers;
  public Edge parent;

  public Edge(int from, int to, int cap, int flow) {
    this.from = from;
    this.to = to;
    this.cap = cap;
    this.flow = flow;
  }

  @Override
  public boolean equals(Object o) {
    Edge t = (Edge) o;
    if (this.to == t.to)
      return true;
    return false;
  }
}
