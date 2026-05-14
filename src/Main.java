public class Main {

    public static void main(String[] args) {
        SubMain subMain = new SubMain();
        if (!subMain.init()) {
            System.out.println("시스템 초기화에 실패했습니다. 잠시 후 다시 시도해주세요.");
            return;
        }
        subMain.run();
    }
}
