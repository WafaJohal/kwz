
public class tas {
	int x = 0;
	public tas(){
		x = 0;
		System.out.println(""+val());
	}

	public Integer val(){
		
		return x+1;
	}
	
	public void main(String arg0){
		tas t = new tas();
	}
}
