package 퀴즈2;

public class DaeGuFactory implements Runnable {
	private Shared s;
	private String name;
	
	public DaeGuFactory(Shared s) {
		this.name = "DaeGu";
		this.s = s;
	}
	
	public void run() {
		
		int[] data = s.getFactory(name);

		System.out.println(name+" 공장 스레드 ==== "+data[0]+","+data[1]+","+data[2]+","+data[3]+" ====");
		
		while(true) {
			if(data[3]==10) {
				System.out.println(name+" 공장 스레드 ==== 자동차 10대 생산 완료 ====");
				System.out.println(name+" 공장 스레드 ==== 남은 부품  "+data[0]+","+data[1]+","+data[2]+","+data[3]+"  ====");
				break;
			}
			if(data[0]>=1 && data[1]>=4 && data[2]>=4) {
				System.out.println(name+" 공장 스레드 ==== 자동차 1대 생산 ====");
				data[3]++;
				data[0]--;
				data[1]-=4;
				data[2]-=4;
				if(data[3]==10) {
					System.out.println(name+" 공장 스레드 ==== 자동차 10대 생산 완료 ====");
					System.out.println(name+" 공장 스레드 ==== 남은 부품  "+data[0]+","+data[1]+","+data[2]+","+data[3]+"  ====");
					break;
				}
				System.out.println(name+" 공장 스레드 ==== 남은 부품  "+data[0]+","+data[1]+","+data[2]+","+data[3]+"  ====");
				try {
					Thread.sleep(500);
				}
				catch(InterruptedException e) { return; }
			}
			if(data[0]<1) {
				System.out.println(name+" 공장 스레드 ==== 몸체 1개 생산 ====");
				data[0]++;
				try {
					Thread.sleep(500);
				}
				catch(InterruptedException e) { return; }
			}
			if(data[1]<4) {
				System.out.println(name+" 공장 스레드 ==== 문 1개 생산 ====");
				data[1]++;
				try {
					Thread.sleep(500);
				}
				catch(InterruptedException e) { return; }
			}
			if(data[2]<4) {
				System.out.println(name+" 공장 스레드 ==== 바퀴 1개 생산 ====");
				data[2]++;
				try {
					Thread.sleep(500);
				}
				catch(InterruptedException e) { return; }
			}
		}
		
		System.out.println(name+" 공장 스레드 종료합니다.");
	}
}
