package zemiA;

public class Metric {
    private MetricVisitor visitor;

    public Metric(MetricVisitor visitor){
        this.visitor = visitor;
    }

    public int getNOM(){
        return visitor.getMethod_Count();
    }


    public int getWMC(){
        int WMC=0;

        for(int num : visitor.CYCLO){
            WMC = WMC + num;
        }
        return WMC;
    }

    public int getAMW(){
        return getWMC()/getNOM();
    }
}
