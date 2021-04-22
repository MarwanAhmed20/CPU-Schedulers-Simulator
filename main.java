import java.util.Scanner;

public class main {
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        Preemptive preemptive;
        Process processClass =new Process();
        int number;
        String process;
        int arrive;
        int Burst;
        int priority;
        int queueNum;
        System.out.println("Enter the number of process");
        number = input.nextInt();
        for(int i=0;i<number;i++)
        {
            System.out.println("Enter the process");
            process = input.next();
            System.out.println("Enter the arrive time");
            arrive = input.nextInt();
            System.out.println("Enter the Burst time");
            Burst = input.nextInt();
            System.out.println("Enter the priority ");
            priority = input.nextInt();
            System.out.println("Enter the queue Numumber");
            queueNum=input.nextInt();
            processClass.getInfo(new Preemptive(process,arrive,Burst,priority,queueNum));
        }
        //processClass.printInfo();
       /* System.out.println("Enter your context switching");
        int context= input.nextInt();
        processClass.timeAvg(context);*/

        //processClass.Averageperiority();
        //processClass.printInfo();

        int quantum;
        System.out.println("Enter the Burst quantum time");
        quantum= input.nextInt();
       /* processClass.getTimeAvgRobin( quantum,context);*/
        processClass.MultiLevel(quantum);
    }
}
