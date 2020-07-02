package sleeping; 

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class SleepingBar extends Thread {
	
 public static int chairs = 4;
static double mean_b = 5000;
static double std_b = 200;
static Random rng = new Random();
final static int b_time=(int) (mean_b + std_b * rng.nextGaussian());
static double mean_c = 1500;
static double std_c = 150;
final static int c_time=(int) (mean_c+ std_c * rng.nextGaussian());

 int k;
 		
 private static final int closing_time = b_time*2;
public static final int cust=50;
public int s=0;
boolean notcut=true;

 public static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(chairs);
 
 class Customer extends Thread {
	 int custid;
	 BlockingQueue<Integer> queue = null;
	 
	 public Customer(int i, BlockingQueue<Integer> queue)
	 {
	 
		 custid = i;
	 
		 this.queue = queue;
	 
	 }
	 
	 public void run() {
	 
		 for(;;) { 
	 
			 try {
	 int entry_door=1;
				 this.queue.add(this.custid);
	 
				 System.out.println("Customer "+this.custid +" enetered the shop from door# "+entry_door+ " and  took a chair");
				  
			 } catch (IllegalStateException e) {
				 
				 				 
				 System.out.println("There are no free chairs Customer " + this.custid + " has left the barbershop.");
	 
			 }
	 
			 break;
	 
		 }
	 
	 }
 }
 
 class Barber extends Thread {
  int j; 
	 public int num;
	 BlockingQueue<Integer> queue = null;
	   public Barber(BlockingQueue<Integer> queue, int num) {

           this.num = num;

           this.queue = queue;

       }


	public void run() {
 
		 for(;;) { // runs in an infinite loop
 
			 try {
 
				 Integer i = this.queue.poll(closing_time, TimeUnit.MILLISECONDS);
 
				 if (i==null) 
					 {
					
					close();
					break;
 
					 } 
				 this.cutHair(i); // cutting...
 
			 } catch (InterruptedException e) {
 
			 }
		 }

	 }
public void close()
{
	 System.out.println("all the customers are done the barbers are going to sleep");

	 System.out.println("Shop closing time");

}
public void cutHair(Integer i) {
	if(i<=cust) {
		
	System.out.println("The customer #"+i+" has gone to sleep");
		notcut=false;
		System.out.println("the customer"+i+" has gone to sleep The barber " + this.num + " is cutting hair for customer #" + i);
    
	}

    try {
int exit_door=2;
 
	 Thread.sleep(b_time);
        System.out.println("the barber has finished the haricut for customer"+i+" and the barber "+this.num+" has opened the exit door# "+exit_door );
        
        System.out.println("the barber "+this.num+" is sleeping");
    } catch (InterruptedException e) {

    }

}

}
 
 
 
 public static void main(String args[]) {
 
	 SleepingBar barberShop = new SleepingBar(); 
	 System.out.println("Shop Opening time");
	 barberShop.start(); 
 
 }

 public void run() {

for ( s=1;s<3;s++)
{
     Barber barber = new Barber(SleepingBar.queue, s);

     barber.start(); 


}
	 	 
	 
	 int i=1;
	 while(i <=cust) {
Customer aCustomer = new Customer(i, SleepingBar.queue);

		 aCustomer.start();
 i++;
 boolean check=false;
		 try {
			 if(check=false) {
				 Barber b=new Barber(null, i);
				 check=true;
				 b.close();
			 }
			 
			 
				
					 
			 Thread.sleep(c_time);
			 
 		 } catch (InterruptedException e) {};

	 }
 
 }

}