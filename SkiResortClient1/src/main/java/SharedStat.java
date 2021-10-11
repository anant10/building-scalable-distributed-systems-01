import java.util.ArrayList;
import java.util.List;

public class SharedStat {
  private int sucessfullPosts;
  private int failedPosts;
  private List<Long> responseTimes;

  public SharedStat() {
    this.sucessfullPosts = 0;
    this.failedPosts = 0;
    this.responseTimes = new ArrayList<>();
  }

  public int getSucessfullPosts() {
    return sucessfullPosts;
  }

  public int getFailedPosts() {
    return failedPosts;
  }

  public synchronized void incSuccessfulPosts(int posts){
    this.sucessfullPosts += posts;
  }

  public synchronized void incFailedPosts(int posts){
    this.failedPosts += posts;
  }

  public synchronized void addAllResponseTimes(List<Long> resTimes){
    this.responseTimes.addAll(resTimes);
  }

  public List<Long> getResponseTimes() {
    return responseTimes;
  }
}
