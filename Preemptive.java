import java.util.Comparator;

public class Preemptive {

    private  String process;
    private  int Arrive;
    private  int Burst;
    public int priority;
    public int queueNum;


    public static Comparator<Preemptive> prio = new Comparator<Preemptive>() {

        public int compare(Preemptive s1, Preemptive s2) {

            int prior1 = s1.getPriority();
            int prior2 = s2.getPriority();
            return prior1-prior2;

        }};
    public static Comparator<Preemptive> QUNUM = new Comparator<Preemptive>() {

        public int compare(Preemptive s1, Preemptive s2) {

            int prior1 = s1.getqueueNum();
            int prior2 = s2.getqueueNum();
            return prior1-prior2;

        }};
    public Preemptive(String process, int arrive, int burst, int priority,int queueNum) {
        this.process = process;
        Arrive = arrive;
        Burst = burst;
        this.priority = priority;
        this.queueNum=queueNum;

    }

    public  int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Preemptive() {
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public void setArrive(int arrive) {
        Arrive = arrive;
    }

    public void setBurst(int burst) {
        Burst = burst;
    }

    public String getProcess() {
        return process;
    }

    public int getArrive() {
        return Arrive;
    }

    public int getBurst() {
        return Burst;
    }

    public int getqueueNum() {
        return queueNum;
    }
}
