package net.vishalsrivastava.space.efficient.probabilistic;

import org.apache.commons.codec.digest.MurmurHash3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.BitSet;

/**
 * Author Vishal Srivastava
 * This class implements the basic bloom filter
 * <p>
 * Copied implementation from
 * https://www.geeksforgeeks.org/bloom-filters-introduction-and-python-implementation/
 */
public class BloomFilter<T> {
    private final int hashCount;
    // size of bit set
    private final int size;
    // bit set
    private final BitSet bitSet;


    /**
     * Bloom filter constructor
     *
     * @param itemCount                maximum number of elements it is going to support
     * @param falsePositiveProbability false positive probability if its 5% pass 0.05
     */
    public BloomFilter(int itemCount, double falsePositiveProbability) {
        // Calculate the size of the bit set
        size = getSize(itemCount, falsePositiveProbability);
        // Number of hash function to be used
        hashCount = getHashCount(size, itemCount);
        // Initialise the bitset of size n and values are by default false
        bitSet = new BitSet(size);
    }

    /**
     * Add an item in the filter
     *
     * @param element to be added to the filter
     */
    public void add(T element) {
        for (int i = 0; i < hashCount; i++) {
            byte[] data = getBytes(element);
            long[] hash = MurmurHash3.hash128(data, 0, data.length, i);
            int index = Math.abs((int) (hash[1] % size));
            bitSet.set(index, true);
        }
    }

    /**
     * Check for existence of an item in filter
     *
     * @param element check for availability in the filter
     * @return if exists in filter as true else false
     */
    public boolean exists(T element) {
        for (int i = 0; i < hashCount; i++) {
            byte[] data = getBytes(element);
            long[] hash = MurmurHash3.hash128(data, 0, data.length, i);
            int index = Math.abs((int) (hash[1] % size));
            if (!bitSet.get(index))
                return false;
        }
        return true;
    }

    private byte[] getBytes(T element) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                objectOutputStream.writeObject(element);
                objectOutputStream.flush();
                return byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private int getHashCount(int size, int itemCount) {
        return (int) (((double) size / (double) itemCount) * Math.log(2));
    }

    /**
     * Return the size for bitmap to used using
     * following formula
     * - (itemCount * lg(falsePositiveProbability)) / (lg(2)^2)
     *
     * @param itemCount                number of items expected to be stored in filter
     * @param falsePositiveProbability False Positive probability in decimal
     * @return the sze computed
     */
    private int getSize(int itemCount, double falsePositiveProbability) {
        return Math.abs((int) ((double) itemCount * Math.log(falsePositiveProbability) / (Math.pow(Math.log(2), 2))));
    }
}

