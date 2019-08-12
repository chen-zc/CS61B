public class OffByN implements CharacterComparator {

    int n;
    public OffByN(int N) {
        this.n = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        if (diff == this.n || diff == -this.n) {
            return true;
        }
        else {
            return false;
        }
    }
}
