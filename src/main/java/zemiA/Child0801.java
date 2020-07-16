package zemiA;

public class Child0801 extends Parent0801 { // Parentクラス（親クラス）を継承。
    public String cs1="子クラスのメンバ変数が参照されました。";
    public Child0801(){
        System.out.println("自クラスのコンストラクタ（引数なし）が呼ばれました。");
    }
    public void cm() {
        System.out.println("子クラスのメソッドが呼ばれました。");
    }
	@Override
	public String toString() {
		return "Child0801 [cs1=" + cs1 + "]";
	}
}
