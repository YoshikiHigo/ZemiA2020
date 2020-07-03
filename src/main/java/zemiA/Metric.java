package zemiA;

public class Metric {
    private ZemiAVisitor visitor;

    public Metric(ZemiAVisitor visitor){
        this.visitor = visitor;
    }

    public int getNOM(){
        return visitor.getMethod_Count();
    }


    public int getWMC(){
        return visitor.getIf_Count() + (visitor.getCase_Count()-visitor.getSwitch_Count()) + visitor.getMethod_Count();
    }

    public int getAMW(){
        return getWMC()/visitor.getMethod_Count();
    }
}
