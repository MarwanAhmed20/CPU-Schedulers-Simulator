//import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Process {
    static int time = 0;
    public int context;
    // public int quantum;

    public Process() {}


    public static List<Preemptive> DB=new ArrayList<>();

    public static ArrayList<Preemptive>p=new ArrayList<Preemptive>();

    public static List<Process>operations = new ArrayList<>();

    public void getInfo(Preemptive preemptive)
    {
        DB.add(new Preemptive(preemptive.getProcess(),preemptive.getArrive(),
                preemptive.getBurst(),preemptive.getPriority(),preemptive.getqueueNum()));
    }

    public void printInfo()
    {
        for(int i=0;i<DB.size();i++)
        {
            System.out.print("the process is "+DB.get(i).getProcess());
            System.out.print("  The Arrive time is : "+DB.get(i).getArrive());
            System.out.print("  the Burst time is : "+DB.get(i).getBurst());
            System.out.println("  the priority is : "+DB.get(i).getPriority());
        }
    }

    public void timeAvg(int context)
    {
        getTimeAvg(DB, context);
    }

    public void findWaitingTime(List<Preemptive> DB, int[] waitTime, int context)
    {
        int burst[] = new int[DB.size()];

        for (int i = 0; i < DB.size(); i++)
            burst[i] = DB.get(i).getBurst();

        int complete = 0, t = 0, min = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        boolean check = false;

        while (complete != DB.size()) {

            for (int j = 0; j < DB.size(); j++)
            {
                if ((DB.get(j).getArrive() <= t) &&
                        (burst[j] < min) && burst[j] > 0) {
                    min = burst[j];
                    shortest = j;
                    check = true;
                }
            }

            if (check == false) {
                t++;
                continue;
            }

            burst[shortest]--;

            min = burst[shortest];
            if (min == 0)
                min = Integer.MAX_VALUE;

            if (burst[shortest] == 0) {

                complete++;
                check = false;

                finish_time = t + 1+context;

                waitTime[shortest] = finish_time -
                        DB.get(shortest).getBurst() -
                        DB.get(shortest).getArrive();

                if (waitTime[shortest] < 0)
                    waitTime[shortest] = 0;
            }
            t++;
        }
    }

    public void getTurnAroundTime(List<Preemptive> DB,int waitTime[],int turnAroundTime[])
    {
        for(int i=0;i<DB.size();i++)
        {
            turnAroundTime[i]=DB.get(i).getBurst()+waitTime[i];
        }
    }

    public void getTimeAvg(List<Preemptive>DB,int context)
    {
        int waitTime[] = new int[DB.size()], turnAroundTime[] = new int[DB.size()];
        int  number_waitTime = 0, number_turnAroundTime = 0;

        findWaitingTime(DB, waitTime,context);

        getTurnAroundTime(DB,waitTime,turnAroundTime);

        System.out.println("Processes " +
                " Burst time " +
                " Waiting time " +
                " Turn around time");

        for (int i = 0; i < DB.size(); i++) {
            number_waitTime = number_waitTime + waitTime[i];
            number_turnAroundTime = number_turnAroundTime + turnAroundTime[i];
            System.out.println(" " + DB.get(i).getProcess() + "\t\t\t\t"
                    + DB.get(i).getBurst() + "\t\t\t\t " + waitTime[i]
                    + "\t\t\t\t" + turnAroundTime[i]);
        }
        System.out.println("The average time :"+number_waitTime/DB.size());
        System.out.println("the average Turnaround  :"+number_turnAroundTime/DB.size());
    }

    public void findWaitingTimePeriority(List<Preemptive>DB,int turn[],int waitTime[])
    {

        for(int i=0;i<p.size();i++)
        {

            waitTime[i]=turn[i]-p.get(i).getBurst();
        }

    }

    public void getTurnAroundTimePeriority(List<Preemptive>DB,int turnAroundTime[])
    {
        for(int i=0;i<p.size();i++)
        {
            turnAroundTime[i]=turnAroundTime[i]-p.get(i).getArrive();
        }
    }

    public void Averageperiority()
    {
        getAveragePeriority(DB);
    }

    public void toGetPeriority(int turnAroundTime[],int waitTime[])
    {
        for(int i=0;i<p.size();i++)
        {
            turnAroundTime[i]=turnAroundTime[i]-p.get(i).getArrive();
            waitTime[i]=turnAroundTime[i]-p.get(i).getBurst();
        }
    }
    public void getAveragePeriority(List<Preemptive>DB)
    {
        int currentTime=0;
        int original[]=new int [DB.size()];

        int waitTime[] = new int[DB.size()], turnAroundTime[] = new int[DB.size()];
        int  number_waitTime = 0, number_turnAroundTime = 0;

        Preemptive preemptive=new Preemptive();
        Collections.sort(DB, Preemptive.prio);

        for(int i=0;i<DB.size();i++)
        {
            original[i]=DB.get(i).getBurst();
        }

        int index=0;
        while(p.size()!=DB.size())
        {
            for(int i=0;i<DB.size();i++)
            {
                if(DB.get(i).getArrive()<=currentTime&&original[i]!=0)
                {
                    currentTime++;
                    original[i]=original[i]-1;
                    if(original[i]==0)
                    {
                        p.add(DB.get(i));
                        turnAroundTime[index]=currentTime;
                        index++;
                    }
                    i=-1;
                }
            }
            currentTime++;
        }


        toGetPeriority(turnAroundTime,waitTime);

       /* System.out.println("Processes " +
                " Burst time " +
                " Waiting time " +
                " Turn around time");*/

        for (int i = 0; i < p.size(); i++) {
            number_waitTime = number_waitTime + waitTime[i];
            number_turnAroundTime = number_turnAroundTime + turnAroundTime[i];
          /* System.out.println(" " + p.get(i).getProcess() + "\t\t\t\t"
                    + p.get(i).getBurst() + "\t\t\t\t " + waitTime[i]
                    + "\t\t\t\t" + turnAroundTime[i]);*/
        }
       // System.out.println("The average time :"+(double)number_waitTime/p.size());
       // System.out.println("the average Turnaround  :"+(double)number_turnAroundTime/p.size());
    }
    public void Round_Robin(List <Preemptive>DB,int quantum,int wait[],int context,int BT){
        int check=0;
        int burst[] = new int[DB.size()];
        int x=0;
        for (int i = 0; i < DB.size(); i++)
            burst[i] = DB.get(i).getBurst();
        while(x==0) {
            x = 1;
            for(int i=0;i<DB.size();i++) {
                if(burst[i]!=0) {
                    x=0;
                    if(burst[i]>quantum) {
                        time+=quantum;
                        burst[i]-=quantum;
                    }
                    else {
                         BT=DB.get(i).getBurst();
                        time+=burst[i]+context;
                        //System.out.println("t:"+time);
                        //System.out.println("BT:"+BT);
                        wait[i]=time-BT;
                        burst[i]=0;
                    }
                }
            }
            if (x==1)
                break;
        }
    }
    public void getTimeAvgRobin(int quantum,int context)
    {
        getTimeAvgRobin( DB,quantum,context);
    }
    public void getTimeAvgRobin(List<Preemptive> DB,int quantum,int context)
    {
        int wait[] = new int[DB.size()], turnAroundTime[] = new int[DB.size()];
        int  number_waitTime = 0, number_turnAroundTime = 0;
        int time=0;
        int BT=0;

       // System.out.println(DB.size());
        Round_Robin(DB,quantum, wait,context,BT);

        getTurnAroundRobin(DB,wait,turnAroundTime,0);

        System.out.println("Processes " +
                " Burst time " +
                " Waiting time " +
                " Turn around time");

        for (int i = 0; i < DB.size(); i++) {
            number_waitTime = number_waitTime + wait[i];
            number_turnAroundTime = number_turnAroundTime + turnAroundTime[i];
            System.out.println(" " + DB.get(i).getProcess() + "\t\t\t\t"
                    + DB.get(i).getBurst() + "\t\t\t\t " + wait[i]
                    + "\t\t\t\t" + turnAroundTime[i]);
        }

        System.out.println("The average time :"+(double)number_waitTime/DB.size());
        System.out.println("the average Turnaround  :"+(double)number_turnAroundTime/DB.size());

    }

    public void getTurnAroundRobin(List<Preemptive> DB,int wait[],int turnAroundTime[],int count)
    {
        for(int i=0;i<DB.size()-count;i++)
        {
            turnAroundTime[i]=DB.get(i).getBurst()+wait[i];
            //System.out.println( "RRRRRR"+turnAroundTime[i]);
        }
    }

    public void MultiLevel(int quantum) {

        int number_waitTime = 0, number_turnAroundTime = 0;
        getAveragePeriority(DB);

        //int BT = 0;
        int count=0;
        ArrayList<Preemptive> topPriorityList = new ArrayList<Preemptive>();
        int waitTime[] = new int[DB.size()], turnAroundTime[] = new int[DB.size()];
        int wait[] = new int[DB.size()];
        int index = 0;
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i).getqueueNum() == 1) {
                topPriorityList.add(p.get(i));
                Round_Robin(topPriorityList, quantum, waitTime, 0, 0);
                //System.out.println("time2"+time);
                //System.out.println("top:"+topPriorityList.size());

                for (int j = 0; j < topPriorityList.size(); j++) {
                  //  System.out.println(waitTime[j]);
                    wait[index] = waitTime[j];
                    index++;
                }
                
                topPriorityList.remove(0);
            }
            else
            {
            	topPriorityList.add(p.get(i));
            	count++;
                findWaitingTime2(p,wait,index);
             /*   topPriorityList.add(p.get(i));
                getTimeAvgRobin(topPriorityList, quantum, 0);
                topPriorityList.remove(0);*/
                topPriorityList.remove(0);
            }
        }
      getTurnAroundRobin(p, wait, turnAroundTime,count);
      if(count>0)
      {

          findTurnAroundTime2(p,  wait,  turnAroundTime,index);
      }
        System.out.println("Processes " +
                " Burst time " +
                " Waiting time " +
                " Turn around time");

        for (int i = 0; i < p.size(); i++) {
            number_waitTime = number_waitTime + wait[i];
            number_turnAroundTime = number_turnAroundTime + turnAroundTime[i];
            System.out.println(" " + p.get(i).getProcess() + "\t\t\t\t"
                    + p.get(i).getBurst() + "\t\t\t\t " + wait[i]
                    + "\t\t\t\t" + turnAroundTime[i]);
        }

        System.out.println("The average time :"+(double)number_waitTime/p.size());
        System.out.println("the average Turnaround  :"+(double)number_turnAroundTime/p.size());
    }

    public void findWaitingTime2(List<Preemptive>DB, int wt[],int index)
    {

        for (int  i = index-1; i < DB.size() ; i++ ) {
            wt[i] =  DB.get(i-1).getBurst() + wt[i-1] ;
            //System.out.println(wt[i]);
            }
    }
    public void findTurnAroundTime2(List<Preemptive>DB, int wt[], int tat[],int index)
    {
        for (int  i = index-1; i < DB.size() ; i++) {
            tat[i] = DB.get(i).getBurst() + wt[i];
           // System.out.println("turnnn"+tat[i]);
            }
    }
    
}