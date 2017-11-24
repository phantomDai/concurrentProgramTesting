package priority;

public class BitReversedCounter {
    int counter, reverse, highBit;
    BitReversedCounter(int initialValue) {
        counter = initialValue;
        reverse = 0;
        highBit = -1;
    }

    public int reverseIncrement() {
        if (counter++ == 0) {
            reverse = highBit = 1;
            return reverse;
        }
        int bit = highBit >> 1;
        while (bit != 0) {
            reverse ^= bit;
            if ((reverse & bit) != 0) break;
            bit >>= 1;
        }
        if (bit == 0)
            reverse = highBit <<= 1;
        return reverse;
    }

    public int reverseDecrement() {
        counter--;
        int bit = highBit >> 1;
        while (bit != 0) {
            reverse ^= bit;
            if ((reverse & bit) == 0) {
                break;
            }
            bit >>= 1;
        }
        if (bit == 0) {
            reverse = counter;
            highBit >>= 1;
        }
        return reverse;
    }

    public int get() {
        return counter;
    }
}
