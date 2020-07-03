package zemiA;

public class sample {
    public void AAA(){
        int a=0;
        if(a==0){
            System.out.println("OK");
            a++;
            if(a==1){
                a++;
            }
        }else{
            System.out.println("NO");
        }
    }

    public void BBB(){
        int b=0;
        if(b==0){
            System.out.println("OK");
        }else if(b==1){
            System.out.println("NO");
        }else{
            System.out.println("Um");
        }
    }

    public void CCC(){
        int c=0;

        switch(c){
            case 0:
                System.out.println("OK");
                break;
            case 1:
                System.out.println("NO");
                break;
            case 2:
                System.out.println("Um");
                break;
        }
    }
}
