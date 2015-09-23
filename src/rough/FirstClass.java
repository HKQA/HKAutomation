package rough;

/**
 * Created with IntelliJ IDEA.
 * User: vikas.dhull
 * Date: 9/16/15
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class FirstClass {
      public int a;
      public int b;

    FirstClass(int a,int b){
        this.a = a;
        this.b = b;
    }
    public void add(){
        int z= a+b;
        System.out.println("sum is:" + z);
    }

    public static void main(String args[]){
        FirstClass obj1 = new FirstClass(10,20);
        obj1.add();

    }
}


