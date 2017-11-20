
public class TryFinally {

	
	
	int tryIt() {
		int a =10;
		int b=0;
		
		try {
			-aa;
		} finally { 
			b++;
		}
		return (a+b);
		}

	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

			System.out.println(new TryFinally.tryIt());
		}
		
	}

}
